package com.tugasakhir.configuration;

import com.tugasakhir.util.mapper.MessageMapper;
import oracle.jdbc.OracleTypes;
import org.json.simple.JSONObject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends JdbcDaoSupport {

    public <T> List<T> ExecuteCall(String functionName, Object[] obj, RowMapper<T> mapper) {
        List<T> model = new ArrayList<T>();
        //List<T> result = new ArrayList<T>();
        String msg = "";
        Connection con = null;
        CallableStatement call = null;
        ResultSet rs = null;
        try {
            con = this.getJdbcTemplate().getDataSource().getConnection();
            String state = "";
            for (int i = 1; i <= obj.length; i++) {
                state += "?,";
            }
            if (obj.length > 0) {
                state = state.substring(0, state.length() - 1);
            } else {
                state = "";
            }
            String sql = "{? = call " + functionName + "(" + state + ")}";
            con.setAutoCommit(false);
            call = con.prepareCall(sql);
            call.registerOutParameter(1, Types.OTHER);
            for (int i = 0; i < obj.length; i++) {
                if (obj[i].getClass().equals(String.class)) {
                    call.setString(i + 2, (String) obj[i]);
                } else if (obj[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 2, (Integer) obj[i]);
                } else if (obj[i].getClass().equals(Long.class)) {
                    call.setLong(i + 2, (Long) obj[i]);
                } else if (obj[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 2, (Float) obj[i]);
                } else if (obj[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 2, (Double) obj[i]);
                }
                /*
                 * else if(obj[i].getClass().equals(Date.class)){
                 * cstmt.sets(i+1, (Date) obj[i]); }
                 */
            }
            call.execute();
            rs = (ResultSet) call.getObject(1);
            ResultSetMetaData rsmd = rs.getMetaData();

            try {
                while (rs.next()) {

                    try {
                        msg = rs.getString("Message");
                    } catch (Exception e) {
                        msg = "";
                    }
                    if (model.size() == 0 && !msg.isEmpty() && rsmd.getColumnCount() == 1) {
                        MessageMapper mapperMsg = new MessageMapper();
                        mapper = (RowMapper<T>) mapperMsg;
                        model.add(mapper.mapRow(rs, 0));
                        msg = "error";
                    } else {
                        model.add(mapper.mapRow(rs, 0));
                        msg = "sukses";
                    }
                }
            } catch (Exception e) {
                e.getMessage();
                MessageMapper mapperMsg = new MessageMapper();
                mapper = (RowMapper<T>) mapperMsg;
                model.add((T) e.getMessage());
                msg = "error";
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            MessageMapper mapperMsg = new MessageMapper();
            mapper = (RowMapper<T>) mapperMsg;
            model.add((T) e.getMessage());
            msg = "error";
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
        //model.add((T) result);
        //model.add((T) msg);
        return model;
    }

    public <T> List<T> ExecuteCallPostgre(String functionName, Object[] obj, RowMapper<T> mapper) {
        List<T> model = new ArrayList<T>();
        //List<T> result = new ArrayList<T>();
        String msg = "";
        Connection con = null;
        CallableStatement call = null;
        ResultSet rs = null;
        try {
            con = this.getJdbcTemplate().getDataSource().getConnection();
            String state = "";
            for (int i = 1; i <= obj.length; i++) {
                state += "?,";
            }
            if (obj.length > 0) {
                state = state.substring(0, state.length() - 1);
            } else {
                state = "";
            }
            String sql = "{? = call " + functionName + "(" + state + ")}";
            con.setAutoCommit(false);
            call = con.prepareCall(sql);
            call.registerOutParameter(1, Types.OTHER);
            for (int i = 0; i < obj.length; i++) {
                if (obj[i].getClass().equals(String.class)) {
                    call.setString(i + 2, (String) obj[i]);
                } else if (obj[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 2, (Integer) obj[i]);
                } else if (obj[i].getClass().equals(Long.class)) {
                    call.setLong(i + 2, (Long) obj[i]);
                } else if (obj[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 2, (Float) obj[i]);
                } else if (obj[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 2, (Double) obj[i]);
                } else if (obj[i].getClass().equals(Boolean.class)) {
                    call.setBoolean(i + 2, (Boolean) obj[i]);
                } else if (obj[i].getClass().equals(Timestamp.class)) {
                    call.setTimestamp(i + 2, (Timestamp) obj[i]);
                }
                /*
                 * else if(obj[i].getClass().equals(Date.class)){
                 * cstmt.sets(i+1, (Date) obj[i]); }
                 */
            }
            call.execute();
            con.commit();
            rs = (ResultSet) call.getObject(1);
            ResultSetMetaData rsmd = rs.getMetaData();

            try {
                while (rs.next()) {

                    try {
                        msg = rs.getString("Message");
                    } catch (Exception e) {
                        msg = "";
                    }
                    if (model.size() == 0 && !msg.isEmpty() && rsmd.getColumnCount() == 1) {
                        MessageMapper mapperMsg = new MessageMapper();
                        mapper = (RowMapper<T>) mapperMsg;
                        model.add(mapper.mapRow(rs, 0));
                    } else {
                        model.add(mapper.mapRow(rs, 0));
                    }
                }
            } catch (Exception e) {
                e.getMessage();
                model.add((T) e.getMessage());
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            MessageMapper mapperMsg = new MessageMapper();
            model.add((T) e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
        //model.add((T) result);
        //model.add((T) msg);
        return model;
    }

    public <T> JSONObject getJSonData(String functionName, Object[] obj, RowMapper<T> mapper) {
        JSONObject json = new JSONObject();
        List<T> model = new ArrayList();
        Connection con = null;
        CallableStatement call = null;
        ResultSet rs = null;
        try {
            con = this.getJdbcTemplate().getDataSource().getConnection();
            String state = "";
            for (int i = 1; i <= obj.length; i++) {
                state += "?,";
            }
            if (obj.length > 0) {
                state = state.substring(0, state.length() - 1);
            } else {
                state = "";
            }
            String sql = "{? = call " + functionName + "(" + state + ")}";
            con.setAutoCommit(false);
            call = con.prepareCall(sql);
            call.registerOutParameter(1, OracleTypes.CURSOR);
            for (int i = 0; i < obj.length; i++) {
                if (obj[i].getClass().equals(String.class)) {
                    call.setString(i + 2, (String) obj[i]);
                } else if (obj[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 2, (Integer) obj[i]);
                } else if (obj[i].getClass().equals(Long.class)) {
                    call.setLong(i + 2, (Long) obj[i]);
                } else if (obj[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 2, (Float) obj[i]);
                } else if (obj[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 2, (Double) obj[i]);
                }
                /*
                 * else if(obj[i].getClass().equals(Date.class)){
                 * cstmt.sets(i+1, (Date) obj[i]); }
                 */
            }
            call.execute();
            rs = (ResultSet) call.getObject(1);
            ResultSetMetaData rsmd = rs.getMetaData();

            try {
                while (rs.next()) {
                    model.add(mapper.mapRow(rs, 0));
                }
                json.put("data", model);
                json.put("msg", "1");
            } catch (Exception e) {
                e.printStackTrace();
                json.put("msg", "0");
                json.put("msg_error", e.getMessage().toString());
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            json.put("msg", "0");
            json.put("msg_error", e.getMessage().toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
        return json;
    }

    public <T> JSONObject getJSonDataPostgres(String functionName, Object[] obj, RowMapper<T> mapper) {
        JSONObject json = new JSONObject();
        List<T> model = new ArrayList();
        Connection con = null;
        CallableStatement call = null;
        ResultSet rs = null;
        try {
            con = this.getJdbcTemplate().getDataSource().getConnection();
            String state = "";
            for (int i = 1; i <= obj.length; i++) {
                state += "?,";
            }
            if (obj.length > 0) {
                state = state.substring(0, state.length() - 1);
            } else {
                state = "";
            }
            String sql = "{? = call " + functionName + "(" + state + ")}";
            con.setAutoCommit(false);
            call = con.prepareCall(sql);
            call.registerOutParameter(1, Types.OTHER);
            for (int i = 0; i < obj.length; i++) {
                if (obj[i].getClass().equals(String.class)) {
                    call.setString(i + 2, (String) obj[i]);
                } else if (obj[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 2, (Integer) obj[i]);
                } else if (obj[i].getClass().equals(Long.class)) {
                    call.setLong(i + 2, (Long) obj[i]);
                } else if (obj[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 2, (Float) obj[i]);
                } else if (obj[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 2, (Double) obj[i]);
                }
                /*
                 * else if(obj[i].getClass().equals(Date.class)){
                 * cstmt.sets(i+1, (Date) obj[i]); }
                 */
            }
            call.execute();
            rs = (ResultSet) call.getObject(1);
            ResultSetMetaData rsmd = rs.getMetaData();

            try {
                while (rs.next()) {
                    model.add(mapper.mapRow(rs, 0));
                }
                json.put("data", model);
                json.put("msg", "1");
            } catch (Exception e) {
                e.printStackTrace();
                json.put("msg", "0");
                json.put("msg_error", e.getMessage().toString());
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            json.put("msg", "0");
            json.put("msg_error", e.getMessage().toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
        return json;
    }

//    public <T> JSONObject getJSonDataWithMessage(String functionName, Object[] obj, RowMapper<T> mapper, Message msg) {
//
//
//        JSONObject json = new JSONObject();
//        List<T> model = new ArrayList();
//        Connection con = null;
//        CallableStatement call = null;
//        ResultSet rs = null;
//        try {
//            con = this.getJdbcTemplate().getDataSource().getConnection();
//            String state = "";
//            for (int i = 0; i <= obj.length; i++) {
//                state += "?,";
//            }
//            if (obj.length > 0) {
//                state = state.substring(0, state.length() - 1);
//            } else {
//                state = "";
//            }
//            String sql = "{? = call " + functionName + "(" + state + ")}";
//            call = con.prepareCall(sql);
//            call.registerOutParameter(1, OracleTypes.CURSOR);
//            call.registerOutParameter(2, OracleTypes.VARCHAR);
//            for (int i = 0; i < obj.length; i++) {
//                if (obj[i].getClass().equals(String.class)) {
//                    call.setString(i + 3, (String) obj[i]);
//                } else if (obj[i].getClass().equals(Integer.class)) {
//                    call.setInt(i + 3, (Integer) obj[i]);
//                } else if (obj[i].getClass().equals(Long.class)) {
//                    call.setLong(i + 3, (Long) obj[i]);
//                } else if (obj[i].getClass().equals(Float.class)) {
//                    call.setFloat(i + 3, (Float) obj[i]);
//                } else if (obj[i].getClass().equals(Double.class)) {
//                    call.setDouble(i + 3, (Double) obj[i]);
//                }
//                /*
//                 * else if(obj[i].getClass().equals(Date.class)){
//                 * cstmt.sets(i+1, (Date) obj[i]); }
//                 */
//            }
//            call.execute();
//            rs = (ResultSet) call.getObject(1);
//            msg.setMessage(call.getString(2));
//            ResultSetMetaData rsmd = rs.getMetaData();
//
//            try {
//                while (rs.next()) {
//                    model.add(mapper.mapRow(rs, 0));
//                }
//                json.put("data", model);
//                json.put("msg", "1");
//            } catch (Exception e) {
//                e.printStackTrace();
//                json.put("msg", "0");
//                json.put("msg_error", e.getMessage().toString());
//            }
//
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            json.put("msg", "0");
//            json.put("msg_error", e.getMessage().toString());
//        } finally {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//            if (call != null) {
//                try {
//                    call.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//        }
//        return json;
//    }


//    public <T> ValidationModel<T> ExecuteCall(String functionName, Object[] obj, RowMapper<T> mapper, int outParameter) {
//        ValidationModel<T> validationModel = new ValidationModel<T>();
//        List<T> model = new ArrayList<T>();
//        Connection con = null;
//        CallableStatement call = null;
//        ResultSet rs = null;
//        try {
//            con = this.getJdbcTemplate().getDataSource().getConnection();
//            String state = "";
//            for (int i = 1; i <= obj.length; i++) {
//                state += "?,";
//            }
//            if (obj.length > 0) {
//                state = state.substring(0, state.length() - 1);
//            } else {
//                state = "";
//            }
//            String sql = "{? = call " + functionName + "(" + state + ")}";
//            call = con.prepareCall(sql);
//            call.registerOutParameter(1, OracleTypes.CURSOR);
//            for (int i = 0; i < obj.length - 1; i++) {
//                if (obj[i].getClass().equals(String.class)) {
//                    call.setString(i + 2, (String) obj[i]);
//                } else if (obj[i].getClass().equals(Integer.class)) {
//                    call.setInt(i + 2, (Integer) obj[i]);
//                } else if (obj[i].getClass().equals(Long.class)) {
//                    call.setLong(i + 2, (Long) obj[i]);
//                } else if (obj[i].getClass().equals(Float.class)) {
//                    call.setFloat(i + 2, (Float) obj[i]);
//                } else if (obj[i].getClass().equals(Double.class)) {
//                    call.setDouble(i + 2, (Double) obj[i]);
//                }
//                /*
//                 * else if(obj[i].getClass().equals(Date.class)){
//                 * cstmt.sets(i+1, (Date) obj[i]); }
//                 */
//            }
//            call.registerOutParameter(outParameter, OracleTypes.VARCHAR);
//            call.execute();
//            rs = (ResultSet) call.getObject(1);
//            String errMsg = (String) call.getObject(outParameter);
//            while (rs.next()) {
//                model.add(mapper.mapRow(rs, 0));
//            }
//            validationModel.setModel(model);
//            validationModel.setErrMsg(errMsg);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            validationModel.setErrMsg(e.getMessage());
//        } finally {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//            if (call != null) {
//                try {
//                    call.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//        }
//
//        return validationModel;
//    }

    @SuppressWarnings("unchecked")
//    public <T> T ExecuteSingleCall(String functionName, Object[] obj, RowMapper<T> mapper) {
//        T model = null;
//        Connection con = null;
//        CallableStatement call = null;
//        ResultSet rs = null;
//        try {
//            con = this.getJdbcTemplate().getDataSource().getConnection();
//            String state = "";
//            for (int i = 1; i <= obj.length; i++) {
//                state += "?,";
//            }
//            if (obj.length > 0) {
//                state = state.substring(0, state.length() - 1);
//            } else {
//                state = "";
//            }
//            String sql = "{? = call " + functionName + "(" + state + ")}";
//            call = con.prepareCall(sql);
//            call.registerOutParameter(1, OracleTypes.CURSOR);
//            for (int i = 0; i < obj.length; i++) {
//                if (obj[i].getClass().equals(String.class)) {
//                    call.setString(i + 2, (String) obj[i]);
//                } else if (obj[i].getClass().equals(Integer.class)) {
//                    call.setInt(i + 2, (Integer) obj[i]);
//                } else if (obj[i].getClass().equals(Long.class)) {
//                    call.setLong(i + 2, (Long) obj[i]);
//                } else if (obj[i].getClass().equals(Float.class)) {
//                    call.setFloat(i + 2, (Float) obj[i]);
//                } else if (obj[i].getClass().equals(Double.class)) {
//                    call.setDouble(i + 2, (Double) obj[i]);
//                }
//                /*
//                 * else if(obj[i].getClass().equals(Date.class)){
//                 * cstmt.sets(i+1, (Date) obj[i]); }
//                 */
//            }
//            call.execute();
//            rs = (ResultSet) call.getObject(1);
//            ResultSetMetaData rsmd = rs.getMetaData();
//            try {
//                while (rs.next()) {
//                    //model = mapper.mapRow(rs, 0);
//                    String msg = "";
//                    try {
//                        msg = rs.getString("Message");
//                    } catch (Exception e) {
//                        msg = "";
//                    }
//                    if (!msg.isEmpty() && rsmd.getColumnCount() == 1) {
//                        MessageMapper mapperMsg = new MessageMapper();
//                        mapper = (RowMapper<T>) mapperMsg;
//                        model = mapper.mapRow(rs, 0);
//                    } else {
//                        model = mapper.mapRow(rs, 0);
//                    }
//                }
//            } catch (Exception e) {
//                e.getMessage();
//                MessageMapper mapperMsg = new MessageMapper();
//                mapper = (RowMapper<T>) mapperMsg;
//                model = (T) e.getMessage();
//            }
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            MessageMapper mapperMsg = new MessageMapper();
//            mapper = (RowMapper<T>) mapperMsg;
//            model = (T) e.getMessage();
//        } finally {
//            if (rs != null) {
//                try {
//                    rs.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//            if (call != null) {
//                try {
//                    call.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//        }
//
//        return model;
//    }

    public <T> T ExecuteSingleCallPostgre(String functionName, Object[] obj, RowMapper<T> mapper) {
        T model = null;
        Connection con = null;
        CallableStatement call = null;
        ResultSet rs = null;
        try {

            con = this.getJdbcTemplate().getDataSource().getConnection();
            String state = "";
            for (int i = 1; i <= obj.length; i++) {
                state += "?,";
            }
            if (obj.length > 0) {
                state = state.substring(0, state.length() - 1);
            } else {
                state = "";
            }
            con.setAutoCommit(false);
            String sql = "{? = call " + functionName + "(" + state + ")}";
            call = con.prepareCall(sql);
            call.registerOutParameter(1, Types.OTHER);
            for (int i = 0; i < obj.length; i++) {
                if (obj[i].getClass().equals(String.class)) {
                    call.setString(i + 2, (String) obj[i]);
                } else if (obj[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 2, (Integer) obj[i]);
                } else if (obj[i].getClass().equals(Long.class)) {
                    call.setLong(i + 2, (Long) obj[i]);
                } else if (obj[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 2, (Float) obj[i]);
                } else if (obj[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 2, (Double) obj[i]);
                } else if (obj[i].getClass().equals(Date.class)) {
                    call.setDate(i + 2, (Date) obj[i]);
                }
            }
            call.execute();
            con.commit();
            rs = (ResultSet) call.getObject(1);
            ResultSetMetaData rsmd = rs.getMetaData();
            try {
                while (rs.next()) {
                    if (rsmd.getColumnCount() == 1) {
                        model = mapper.mapRow(rs, 0);
                    } else {
                        model = mapper.mapRow(rs, 0);
                    }
                }
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//            model = (T) e.getMessage();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        }

        return model;
    }

//    public String ExecuteUpdateCall(String functionName, Object[] obj) {
//        String ret = "";
//        Connection con = null;
//        CallableStatement call = null;
//        try {
//            con = this.getJdbcTemplate().getDataSource().getConnection();
//            String state = "";
//            for (int i = 1; i <= obj.length; i++) {
//                state += "?,";
//            }
//            if (obj.length > 0) {
//                state = state.substring(0, state.length() - 1);
//            } else {
//                state = "";
//            }
//            String sql = "{? = call " + functionName + "(" + state + ")}";
//            call = con.prepareCall(sql);
//            call.registerOutParameter(1, Types.VARCHAR);
//            for (int i = 0; i < obj.length; i++) {
//                if (obj[i].getClass().equals(String.class)) {
//                    call.setString(i + 2, (String) obj[i]);
//                } else if (obj[i].getClass().equals(Integer.class)) {
//                    call.setInt(i + 2, (Integer) obj[i]);
//                } else if (obj[i].getClass().equals(Long.class)) {
//                    call.setLong(i + 2, (Long) obj[i]);
//                } else if (obj[i].getClass().equals(Float.class)) {
//                    call.setFloat(i + 2, (Float) obj[i]);
//                } else if (obj[i].getClass().equals(Double.class)) {
//                    call.setDouble(i + 2, (Double) obj[i]);
//                }
//                /*
//                 * else if(obj[i].getClass().equals(Date.class)){
//                 * cstmt.sets(i+1, (Date) obj[i]); }
//                 */
//            }
//            call.execute();
//            ret = call.getString(1);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            ret = e.getMessage();
//        } finally {
//            if (call != null) {
//                try {
//                    call.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//        }
//
//        return ret;
//    }

    public String ExecuteUpdateCallPostgres(String functionName, Object[] obj) {
        String ret = "";
        Connection con = null;
        CallableStatement call = null;
        try {
            con = this.getJdbcTemplate().getDataSource().getConnection();
            String state = "";
            for (int i = 1; i <= obj.length; i++) {
                state += "?,";
            }
            if (obj.length > 0) {
                state = state.substring(0, state.length() - 1);
            } else {
                state = "";
            }
            String sql = "{? = call " + functionName + "(" + state + ")}";
            call = con.prepareCall(sql);
            call.registerOutParameter(1, Types.VARCHAR);
            for (int i = 0; i < obj.length; i++) {
                if (obj[i].getClass().equals(String.class)) {
                    call.setString(i + 2, (String) obj[i]);
                } else if (obj[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 2, (Integer) obj[i]);
                } else if (obj[i].getClass().equals(Long.class)) {
                    call.setLong(i + 2, (Long) obj[i]);
                } else if (obj[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 2, (Float) obj[i]);
                } else if (obj[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 2, (Double) obj[i]);
                } else if (obj[i].getClass().equals(Date.class)) {
                    call.setDate(i + 2, (Date) obj[i]);
                } else if (obj[i].getClass().equals(java.util.Date.class)) {
                    java.util.Date date = (java.util.Date) obj[i];
                    call.setDate(i + 2, new Date(date.getTime()));
                } else if (obj[i].getClass().equals(Timestamp.class)) {
                    call.setTimestamp(i + 2, (Timestamp) obj[i]);
                } else if (obj[i].getClass().equals(String[].class)) {
                    final Array array = con.createArrayOf("varchar", (String[]) obj[i]);
                    call.setArray(i + 2, array);
                }
            }
            call.execute();
            ret = call.getString(1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ret = e.getMessage();
        } finally {
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /* ignored */}
            }
        }

        return ret;
    }

//    public String[] ExecuteUpdateCallWithMessage(String functionName, Object[] obj) {
//        String[] ret = new String[2];
//        Connection con = null;
//        CallableStatement call = null;
//        try {
//            con = this.getJdbcTemplate().getDataSource().getConnection();
//            String state = "";
//            for (int i = 0; i <= obj.length; i++) {
//                state += "?,";
//            }
//            if (obj.length > 0) {
//                state = state.substring(0, state.length() - 1);
//            } else {
//                state = "";
//            }
//            String sql = "{? = call " + functionName + "(" + state + ")}";
//            call = con.prepareCall(sql);
//            call.registerOutParameter(1, OracleTypes.VARCHAR);
//            call.registerOutParameter(2, OracleTypes.VARCHAR);
//            System.out.println("Obj length" + String.valueOf(obj.length));
//            for (int i = 0; i < obj.length; i++) {
//                if (obj[i].getClass().equals(String.class)) {
//                    call.setString(i + 3, (String) obj[i]);
//                } else if (obj[i].getClass().equals(Integer.class)) {
//                    call.setInt(i + 3, (Integer) obj[i]);
//                } else if (obj[i].getClass().equals(Long.class)) {
//                    call.setLong(i + 3, (Long) obj[i]);
//                } else if (obj[i].getClass().equals(Float.class)) {
//                    call.setFloat(i + 3, (Float) obj[i]);
//                } else if (obj[i].getClass().equals(Double.class)) {
//                    call.setDouble(i + 3, (Double) obj[i]);
//                }
//                /*
//                 * else if(obj[i].getClass().equals(Date.class)){
//                 * cstmt.sets(i+1, (Date) obj[i]); }
//                 */
//            }
//            call.execute();
//            ret[0] = call.getString(1);
//            ret[1] = call.getString(2);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            ret[0] = "0";
//            ret[1] = e.getMessage();
//        } finally {
//            if (call != null) {
//                try {
//                    call.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//        }
//
//        return ret;
//    }

//    public ResultSet getRS(String functionName, Object[] obj) {
//        ResultSet rs = null;
//        Connection con = null;
//        CallableStatement call = null;
//        try {
//            con = this.getJdbcTemplate().getDataSource().getConnection();
//            String state = "";
//            for (int i = 1; i <= obj.length; i++) {
//                state += "?,";
//            }
//            if (obj.length > 0) {
//                state = state.substring(0, state.length() - 1);
//            } else {
//                state = "";
//            }
//            String sql = "{? = call " + functionName + "(" + state + ")}";
//            con.setAutoCommit(false);
//            call = con.prepareCall(sql);
//            call.registerOutParameter(1, Types.OTHER);
//            for (int i = 0; i < obj.length; i++) {
//                if (obj[i].getClass().equals(String.class)) {
//                    call.setString(i + 2, (String) obj[i]);
//                } else if (obj[i].getClass().equals(Integer.class)) {
//                    call.setInt(i + 2, (Integer) obj[i]);
//                } else if (obj[i].getClass().equals(Long.class)) {
//                    call.setLong(i + 2, (Long) obj[i]);
//                } else if (obj[i].getClass().equals(Float.class)) {
//                    call.setFloat(i + 2, (Float) obj[i]);
//                } else if (obj[i].getClass().equals(Double.class)) {
//                    call.setDouble(i + 2, (Double) obj[i]);
//                }
//            }
//            call.execute();
//            rs = (ResultSet) call.getObject(1);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return null;
//        }
//        return rs;
//    }

//    public int getJumlah(String functionName, Object[] obj) {
//        int retval = -1;
//        Connection con = null;
//        CallableStatement call = null;
//        try {
//            con = this.getJdbcTemplate().getDataSource().getConnection();
//            String state = "";
//            for (int i = 1; i <= obj.length; i++) {
//                state += "?,";
//            }
//            if (obj.length > 0) {
//                state = state.substring(0, state.length() - 1);
//            } else {
//                state = "";
//            }
//            String sql = "{? = call " + functionName + "(" + state + ")}";
//            call = con.prepareCall(sql);
//            call.registerOutParameter(1, OracleTypes.INTEGER);
//            for (int i = 0; i < obj.length; i++) {
//                if (obj[i].getClass().equals(String.class)) {
//                    call.setString(i + 2, (String) obj[i]);
//                } else if (obj[i].getClass().equals(Integer.class)) {
//                    call.setInt(i + 2, (Integer) obj[i]);
//                } else if (obj[i].getClass().equals(Long.class)) {
//                    call.setLong(i + 2, (Long) obj[i]);
//                } else if (obj[i].getClass().equals(Float.class)) {
//                    call.setFloat(i + 2, (Float) obj[i]);
//                } else if (obj[i].getClass().equals(Double.class)) {
//                    call.setDouble(i + 2, (Double) obj[i]);
//                }
//            }
//            call.execute();
//            retval = call.getInt(1);
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            if (call != null) {
//                try {
//                    call.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) { /* ignored */}
//            }
//        }
//        return retval;
//    }
}

