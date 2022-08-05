package com.cqupt.tutorselectionsystem.student.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cqupt.tutorselectionsystem.student.domain.Requests;
import com.cqupt.tutorselectionsystem.student.service.RequestsService;
import com.cqupt.tutorselectionsystem.student.mapper.RequestsMapper;
import org.springframework.stereotype.Service;

/**
* @author pc
* @description 针对表【t_requests】的数据库操作Service实现
* @createDate 2022-08-05 16:49:12
*/
@Service
public class RequestsServiceImpl extends ServiceImpl<RequestsMapper, Requests>
    implements RequestsService{

}




