package com.tugasakhir.dao.dashboard;

import com.tugasakhir.configuration.DBHandler;
import com.tugasakhir.configuration.Response;
import com.tugasakhir.util.jwt.JwtTokenResponse;
import com.tugasakhir.util.jwt.SessionUtil;
import com.tugasakhir.util.mapper.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Transactional
public class DashboardDaoImpl extends DBHandler implements DashboardDao {
    public DashboardDaoImpl(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    @Override
    public ResponseEntity<?> getDataDashboard(HttpServletRequest request) {
        try {
            JwtTokenResponse response = SessionUtil.getUserData(request);
            Object[] obj = {
                    response.getUser_role()
            };
            List<LinkedHashMap<String, String>> linkedHashMaps = ExecuteCallPostgre("", obj, new Mapper());
            return Response.response(linkedHashMaps, HttpStatus.OK);
        } catch (RuntimeException e){
            return Response.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
