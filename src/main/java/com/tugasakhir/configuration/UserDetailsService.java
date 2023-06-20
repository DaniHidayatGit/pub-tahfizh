package com.tugasakhir.configuration;

import com.tugasakhir.util.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

@Service
public class UserDetailsService extends DBQueryHandler implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    public UserDetailsService(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Connection con = Connect();
        try {
            Object[] objects = {username};
            HashMap<String, String> user = ExecuteSingleCallPostgre(con, "user_func_getbyusername", objects, new Mapper());
            Commit(con);
            System.out.println(user.get("user_name"));
            return new User(user.get("user_name"), user.get("user_password"), new ArrayList<>());
        } catch (Exception e) {
            Rollback(con);
            return new User("", "", new ArrayList<>());
        } finally {
            Close(con);
        }
    }
}
