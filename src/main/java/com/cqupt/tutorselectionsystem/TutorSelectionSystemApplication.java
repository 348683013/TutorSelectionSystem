package com.cqupt.tutorselectionsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.cqupt.tutorselectionsystem.student.mapper","com.cqupt.tutorselectionsystem.admin.mapper"})
public class TutorSelectionSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TutorSelectionSystemApplication.class, args);
    }

}
