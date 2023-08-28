package com.tugasakhir.model;

import lombok.Data;

@Data
public class UserRequest {
    private String user_name;
    private String user_password;
    private Boolean user_active;
    private Integer role_id;
    private String mail;
    private String full_name;
    private String phone;
}
