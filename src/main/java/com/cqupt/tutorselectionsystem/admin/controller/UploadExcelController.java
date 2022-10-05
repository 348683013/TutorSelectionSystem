package com.cqupt.tutorselectionsystem.admin.controller;

import com.cqupt.tutorselectionsystem.admin.utils.ExcelTool;
import com.cqupt.tutorselectionsystem.admin.utils.UploadFile;
import com.cqupt.tutorselectionsystem.student.domain.Student;
import com.cqupt.tutorselectionsystem.student.domain.Teacher;
import com.cqupt.tutorselectionsystem.student.service.StudentService;
import com.cqupt.tutorselectionsystem.student.service.TeacherService;
import com.cqupt.tutorselectionsystem.student.utils.CalculateAge;
import com.cqupt.tutorselectionsystem.student.utils.ResultMsg;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CrossOrigin  //解决跨域问题的注解
@Controller
@RequestMapping(path = "/Admin/UploadExcel")
@PropertySource({"classpath:fileUpload.properties"})
public class UploadExcelController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Value("${excelFile.upload.path}")
    private String fileUploadPath;

    //上传老师Excel表格文件
    @ResponseBody
    @RequestMapping(path = "/uploadTeacherInfoExcel")
    public ResultMsg uploadTeacherInfo(HttpServletRequest request,
                                       @RequestParam("uploadFile") MultipartFile uploadFile) {
        //文件上传的绝对路径
        String uploadPath = fileUploadPath;
        String fileName = UploadFile.uploadToFolder(request, uploadPath, uploadFile);
        //文件上传到了指定磁盘路径下
        File file = new File(uploadPath, fileName);//调用ExcelTool工具中的import方法
        List<Map<String, Object>> mapList;
        try {
            mapList = ExcelTool.importExcelFile(file);
        } catch (Exception e) {
            throw new RuntimeException("读取上传文件失败！");
        }
        //把mapList中的Double转换成Integer
        List<Map<String, String>> maps = ExcelTool.mapTransform(mapList);
        List<Teacher> teacherList = new ArrayList<>();
        for (Map<String, String> map : maps) {
            Teacher teacher = new Teacher();
            mapToObjTeacher(map, teacher);
            teacherList.add(teacher);
        }
        //保存到数据库
        teacherService.saveBatch(teacherList);

        return ResultMsg.success().add("msg", "上传成功！");
    }

    //上传学生Excel表格文件
    @ResponseBody
    @RequestMapping(path = "/uploadStudentInfoExcel")
    public ResultMsg uploadStudentInfoExcel(HttpServletRequest request,
                                       @RequestParam("uploadFile") MultipartFile uploadFile) {
        //文件上传的绝对路径
        String uploadPath = fileUploadPath;
        String fileName = UploadFile.uploadToFolder(request, uploadPath, uploadFile);
        //文件上传到了指定磁盘路径下
        File file = new File(uploadPath, fileName);//调用ExcelTool工具中的import方法
        List<Map<String, Object>> mapList;
        try {
            mapList = ExcelTool.importExcelFile(file);
        } catch (Exception e) {
            throw new RuntimeException("读取上传文件失败！");
        }
        //把mapList中的Double转换成Integer
        List<Map<String, String>> maps = ExcelTool.mapTransform(mapList);
        List<Student> studentList = new ArrayList<>();
        for (Map<String, String> map : maps) {
            Student student = new Student();
            mapToObjStudent(map, student);
            studentList.add(student);
        }
        //保存到数据库
        studentService.saveBatch(studentList);

        return ResultMsg.success().add("msg", "上传成功！");
    }


    //提取map中的内容，对应赋值到teacher对象中
    public void mapToObjTeacher(Map<String, String> map, Teacher teacher){
        try {
            teacher.setRealname(map.get("realname"));
            teacher.setBirthday(CalculateAge.str2Date(map.get("birthday")));
            teacher.setAccountNumber(map.get("accountNumber"));
            teacher.setPassword(map.get("password"));
            teacher.setTeacherType(map.get("teacherType"));
            teacher.setCollegeName(map.get("collegeName"));
            teacher.setTelephone(map.get("telephone").toString());
            teacher.setEmail(map.get("email"));
            teacher.setAddress(map.get("address"));
            //计算总名额
            teacher.setTotalStudent(Integer.parseInt(map.get("totalStudent").split("\\.")[0]));
            //计算性别
            teacher.setSex(Integer.parseInt(map.get("sex").split("\\.")[0]));
            teacher.setDescription(map.get("description"));
        } catch (Exception e) {
            throw new RuntimeException("teacher对象赋值失败！");
        }
    }

    //把map赋值到student对象中
    public void mapToObjStudent(Map<String, String> map, Student student) {
        try {
            student.setRealname(map.get("realname"));
            student.setAccountNumber(map.get("accountNumber"));
            student.setPassword(map.get("password"));
            student.setClassName(map.get("className"));
            student.setTelephone(map.get("telephone"));
            student.setEmail(map.get("email"));
            student.setAddress(map.get("address"));
            //计算总名额
            student.setHasScienceClass(Integer.parseInt(map.get("hasScienceClass").split("\\.")[0]));
        } catch (Exception e) {
            throw new RuntimeException("teacher对象赋值失败！");
        }
    }
}
