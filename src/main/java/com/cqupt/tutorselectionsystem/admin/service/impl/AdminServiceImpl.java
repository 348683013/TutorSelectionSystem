package com.cqupt.tutorselectionsystem.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.tutorselectionsystem.admin.domain.Admin;
import com.cqupt.tutorselectionsystem.admin.service.AdminService;
import com.cqupt.tutorselectionsystem.admin.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author pc
* @description 针对表【t_admin】的数据库操作Service实现
* @createDate 2022-10-04 12:56:09
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

}




