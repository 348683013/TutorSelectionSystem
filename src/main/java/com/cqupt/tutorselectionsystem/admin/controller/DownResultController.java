package com.cqupt.tutorselectionsystem.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cqupt.tutorselectionsystem.admin.domain.Admin;
import com.cqupt.tutorselectionsystem.admin.dto.DownloadResultFileDTO;
import com.cqupt.tutorselectionsystem.admin.service.AdminService;
import com.cqupt.tutorselectionsystem.admin.utils.ExcelTool;
import com.cqupt.tutorselectionsystem.admin.utils.ObjToMap;
import com.cqupt.tutorselectionsystem.student.domain.Requests;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.service.RequestsService;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Admin/DownloadResult")
public class DownResultController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private RequestsService requestsService;

    @Value("${excelFile.upload.path}")
    private String fileUploadPath;

    //生成导出表
    @ResponseBody
    @RequestMapping(path = "/createResultFile")
    public ResultMsg createResultFile(HttpSession session, HttpServletRequest request) {
        String token = request.getHeader("token"); //从请求头中获取这个token的值
        //先从session中取这个用户信息
        Admin admin = (Admin) session.getAttribute(token);
        if (admin == null) {
            //当session中没有这个用户的时候再进行查询
            LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Admin::getToken, token);
            admin = adminService.getOne(queryWrapper);
            //查询出来之后再次放入session
            if (admin != null) {
                session.setAttribute(token, admin);
            }
        }
        //如果admin为null则表示没登录
        if (admin == null) {
            return ResultMsg.fail().add("msg", "管理员未登录！");
        }

        //生成导出表
        //找到所有选择成功的
        LambdaQueryWrapper<Requests> requestsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        requestsLambdaQueryWrapper.eq(Requests::getStatus, 1);
        List<Requests> requestsList = requestsService.list(requestsLambdaQueryWrapper);
        List<Map<String, String>> mapList = new ArrayList<>();
        for (Requests requests : requestsList) {
            //根据requests中的studentId查找学生账号（学号）
            LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
            studentLambdaQueryWrapper.eq(Student::getStudentId, requests.getStudentId());
            Student student = studentService.getOne(studentLambdaQueryWrapper);
            DownloadResultFileDTO downloadResultFileDTO = new DownloadResultFileDTO();
            downloadResultFileDTO.setStudentName(student.getRealname());
            downloadResultFileDTO.setStudentNumber(student.getAccountNumber());
            downloadResultFileDTO.setTeacherName(requests.getTeacherName());
            Map<String, String> map = ObjToMap.objToMap(downloadResultFileDTO);
            mapList.add(map);
        }
        //输出到文件
        String outputFile = fileUploadPath + "result.xlsx";
        ExcelTool.exportExcelFile(mapList, outputFile);

        return ResultMsg.success().add("msg", "输出成功！");
    }
}
