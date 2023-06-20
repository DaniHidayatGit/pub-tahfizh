package com.tugasakhir.dao.login;

import com.tugasakhir.configuration.DBHandler;
import com.tugasakhir.configuration.Response;
import com.tugasakhir.model.login.LoginRequest;
import com.tugasakhir.util.mapper.Mapper;
import com.tugasakhir.util.jwt.JwtTokenResponse;
import com.tugasakhir.util.jwt.JwtTokenUtil;
import com.tugasakhir.util.jwt.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.LinkedHashMap;

import static com.tugasakhir.util.Helpers.getString;

@Slf4j
@Service
@Transactional
public class LoginDaoImpl extends DBHandler implements LoginDao {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    LoginDaoImpl(DataSource dataSource, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.setDataSource(dataSource);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public ResponseEntity<?> doLogin(LoginRequest loginRequest, HttpServletRequest request) {
        try {
            Object[] obj = {loginRequest.getUser_name()};
            LinkedHashMap<String, String> linkedHashMap = ExecuteSingleCallPostgre("user_func_login", obj, new Mapper());
            linkedHashMap.remove("user_password");

            JwtTokenResponse jwtTokenResponse = new JwtTokenResponse();
            jwtTokenResponse.setUser_id(Integer.parseInt(getString(linkedHashMap.get("user_id"))));
            jwtTokenResponse.setUser_name(getString(linkedHashMap.get("user_name")));
            jwtTokenResponse.setUser_role(getString(linkedHashMap.get("role_id")));
            jwtTokenResponse.setEmail(getString(linkedHashMap.get("mail")));

            String token = jwtTokenUtil.generateToken(jwtTokenResponse);
            linkedHashMap.put("token", token);

            Object[] objToken = {linkedHashMap.get("user_name"), request.getRemoteAddr(), token};
            String resultToken = ExecuteUpdateCallPostgres("user_func_insert_token", objToken);
            if(!resultToken.equals("Success")){
                return Response.response(resultToken, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return Response.response(linkedHashMap, "Login Berhasil", HttpStatus.OK);
        } catch (RuntimeException e){
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> doLogout(HttpServletRequest request) {
        try {
            JwtTokenResponse response = SessionUtil.getUserData(request);
            String token = SessionUtil.getUserToken(request);

            Object[] obj = {response.getUser_name(), token};
            String result = ExecuteUpdateCallPostgres("user_func_logout_token", obj);
            if(!result.equals("Success")){
                return Response.response(result, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return Response.response("User " + response.getUser_name() + " berhasil logout", HttpStatus.OK);
        } catch (RuntimeException e){
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
