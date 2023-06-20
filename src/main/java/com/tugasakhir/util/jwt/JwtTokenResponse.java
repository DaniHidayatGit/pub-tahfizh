package com.tugasakhir.util.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class JwtTokenResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer user_id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String user_name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String user_role;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;
}
