package com.cqupt.tutorselectionsystem.student.dto;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    public String oldPassword;
    public String newPassword;
    public String reNewPassword;
}
