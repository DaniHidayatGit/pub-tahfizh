package com.tugasakhir.configuration;

import com.tugasakhir.util.mapper.MessageMapper;
import com.tugasakhir.util.SQLWhereObject;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.jdbc.PgArray;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
public class DBQueryHandler extends JdbcDaoSupport {

    private final String log_template_execute_query = "EXECUTE QUERY -- PROCESS: {} -- DATE: {}";

    String msg = "";

    /**
     * ? Create Connection
     * @Return Connection
     */
    public Connection Connect(){
        try{
            Connection con;
            con = this.getJdbcTemplate().getDataSource().getConnection();
            HikariDataSourcePoolDetail hikariDataSourcePoolDetail = new HikariDataSourcePoolDetail((HikariDataSource) getDataSource());
            log.info("Get Connection -- Hikari Pool Active Connection: {} : {} / {}", hikariDataSourcePoolDetail.getWaiting(), hikariDataSourcePoolDetail.getActive(), hikariDataSourcePoolDetail.getMax());
            con.setAutoCommit(false);
            return con;
        }catch (Exception e){
            e.printStackTrace();
            msg = e.getMessage();
            log.info("Error: " + msg);
            throw new RuntimeException(e);
        }
    }
    /**
     * ? Rollback Connection
     * @Param Connection
     */
    public void Rollback(Connection con){
        try{
            con.rollback();
        }catch (Exception e){
            e.printStackTrace();
            msg = e.getMessage();
            log.info("Error: " + msg);
            throw new RuntimeException(e);
        }
    }
    /**
     * ? Commit Connection
     * @Param Connection
     */
    public void Commit(Connection con){
        try{
            con.commit();
        }catch (Exception e){
            e.printStackTrace();
            msg = e.getMessage();
            log.info("Error: " + msg);
            throw new RuntimeException(e);
        }
    }
    /**
     * ? Close Connection
     * @Param Connection
     */
    public void Close(Connection con){
        try{
            con.close();
            HikariDataSourcePoolDetail hikariDataSourcePoolDetail = new HikariDataSourcePoolDetail((HikariDataSource) getDataSource());
            log.info("Close Connection -- Hikari Pool Active Connection: {} / {}", hikariDataSourcePoolDetail.getActive(), hikariDataSourcePoolDetail.getMax());
        }catch (Exception e){
            e.printStackTrace();
            msg = e.getMessage();
            log.info("Error: " + msg);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param con Connection
     * @param query Select
     * @param mapper RowMapper
     */
    public <T> T ExecuteSinggleDQLQuery(Connection con, String query, Object[] obj, RowMapper<T> mapper) {
        T model = null;
        ResultSet rs = null;
        CallableStatement call = null;
        try {
            call = con.prepareCall(query);
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] == null) {
                    call.setNull(i + 1, Types.NULL);
                }else if (obj[i].getClass().equals(String.class)) {
                    call.setString(i + 1, (String) obj[i]);
                } else if (obj[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 1, (Integer) obj[i]);
                } else if (obj[i].getClass().equals(Long.class)) {
                    call.setLong(i + 1, (Long) obj[i]);
                } else if (obj[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 1, (Float) obj[i]);
                } else if (obj[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 1, (Double) obj[i]);
                }else if (obj[i].getClass().equals(Boolean.class)) {
                    call.setBoolean(i + 1, (Boolean) obj[i]);
                } else if (obj[i].getClass().equals(Date.class)) {
                    call.setDate(i + 1, (Date) obj[i]);
                } else if (obj[i].getClass().equals(java.util.Date.class)) {
                    java.util.Date date = (java.util.Date) obj[i];
                    call.setDate(i + 1, new Date(date.getTime()));
                } else if (obj[i].getClass().equals(Timestamp.class)) {
                    call.setTimestamp(i + 1, (Timestamp) obj[i]);
                } else if (obj[i].getClass().equals(String[].class)) {
                    final Array array = con.createArrayOf("varchar", (String[]) obj[i]);
                    call.setArray(i + 1, array);
                }
            }
            log.info(log_template_execute_query, query, new java.util.Date());
            rs = call.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            try {
                if (rsmd == null){
                    throw new RuntimeException("No Data Found");
                }
                if (rs.next()) {
                    if (rsmd.getColumnCount() == 1) {
                        model = mapper.mapRow(rs, 0);
                    } else {
                        model = mapper.mapRow(rs, 0);
                    }
                }
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
        return model;
    }

    /**
     * @param con Connection
     * @param query Select
     * @param mapper RowMapper
     */
    public <T> List<T> ExecuteDQLQuery(Connection con, String query, Object[] obj, RowMapper<T> mapper) {
        List<T> list = new ArrayList<>();
        ResultSet rs = null;
        CallableStatement call = null;
        try {
            call = con.prepareCall(query);
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] == null) {
                    call.setNull(i + 1, Types.NULL);
                }else if (obj[i].getClass().equals(String.class)) {
                    call.setString(i + 1, (String) obj[i]);
                } else if (obj[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 1, (Integer) obj[i]);
                } else if (obj[i].getClass().equals(Long.class)) {
                    call.setLong(i + 1, (Long) obj[i]);
                } else if (obj[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 1, (Float) obj[i]);
                } else if (obj[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 1, (Double) obj[i]);
                }else if (obj[i].getClass().equals(Boolean.class)) {
                    call.setBoolean(i + 1, (Boolean) obj[i]);
                } else if (obj[i].getClass().equals(Date.class)) {
                    call.setDate(i + 1, (Date) obj[i]);
                } else if (obj[i].getClass().equals(java.util.Date.class)) {
                    java.util.Date date = (java.util.Date) obj[i];
                    call.setDate(i + 1, new Date(date.getTime()));
                } else if (obj[i].getClass().equals(Timestamp.class)) {
                    call.setTimestamp(i + 1, (Timestamp) obj[i]);
                } else if (obj[i].getClass().equals(String[].class)) {
                    final Array array = con.createArrayOf("varchar", (String[]) obj[i]);
                    call.setArray(i + 1, array);
                }
            }
            log.info(log_template_execute_query, call, new java.util.Date());
            rs = call.executeQuery();
            while (rs.next()){
                ResultSetMetaData rsmd = rs.getMetaData();
                try {
                    if (rsmd == null){
                        throw new RuntimeException("No Data Found");
                    }
                    if (!msg.isEmpty() && rsmd.getColumnCount() == 1) {
                        list.add(mapper.mapRow(rs, 0));
                        msg = "error";
                    } else {
                        list.add(mapper.mapRow(rs, 0));
                        msg = "sukses";
                    }
                } catch (Exception e) {
                    throw new SQLException(e.getMessage());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
        return list;
    }

    /**
     * @param con Connection
     * @param query Select
     * @return Object
     */
    public Object ExecuteDQLQueryRetObject(Connection con, String query, Object[] obj) {
        Object model = null;
        ResultSet rs = null;
        CallableStatement call = null;
        try {
            call = con.prepareCall(query);
            for (int i = 0; i < obj.length; i++) {
                if (obj[i].getClass().equals(String.class)) {
                    call.setString(i + 1, (String) obj[i]);
                } else if (obj[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 1, (Integer) obj[i]);
                } else if (obj[i].getClass().equals(Long.class)) {
                    call.setLong(i + 1, (Long) obj[i]);
                } else if (obj[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 1, (Float) obj[i]);
                } else if (obj[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 1, (Double) obj[i]);
                }else if (obj[i].getClass().equals(Boolean.class)) {
                    call.setBoolean(i + 1, (Boolean) obj[i]);
                } else if (obj[i].getClass().equals(Date.class)) {
                    call.setDate(i + 1, (Date) obj[i]);
                } else if (obj[i].getClass().equals(java.util.Date.class)) {
                    java.util.Date date = (java.util.Date) obj[i];
                    call.setDate(i + 1, new Date(date.getTime()));
                } else if (obj[i].getClass().equals(Timestamp.class)) {
                    call.setTimestamp(i + 1, (Timestamp) obj[i]);
                } else if (obj[i].getClass().equals(String[].class)) {
                    final Array array = con.createArrayOf("varchar", (String[]) obj[i]);
                    call.setArray(i + 1, array);
                }
            }
            log.info(log_template_execute_query, query, new java.util.Date());
            rs = call.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            try {
                if (rs.next()) {
                    Object result = rs.getObject(1);
                    if (result.getClass() == PgArray.class) {
                        PgArray pgArray = (PgArray) result;
                        model = pgArray.getArray();
                    } else {
                        model = result;
                    }
                }
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) { /* ignored */}
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /* ignored */}
            }
        }
        return model;
    }

    /**
     * @param con Connection
     * @param query Insert, Update, Delete
     * @param obj Object Parameter
     */
    public void ExecuteDMLQuery(Connection con, String query, Object[] obj) {
        try (CallableStatement call = con.prepareCall(query)) {
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] == null) {
                    call.setNull(i + 1, Types.NULL);
                } else if (obj[i].getClass().equals(String.class)) {
                    call.setString(i + 1, (String) obj[i]);
                } else if (obj[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 1, (Integer) obj[i]);
                } else if (obj[i].getClass().equals(Long.class)) {
                    call.setLong(i + 1, (Long) obj[i]);
                } else if (obj[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 1, (Float) obj[i]);
                } else if (obj[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 1, (Double) obj[i]);
                }else if (obj[i].getClass().equals(Boolean.class)) {
                    call.setBoolean(i + 1, (Boolean) obj[i]);
                } else if (obj[i].getClass().equals(Date.class)) {
                    call.setDate(i + 1, (Date) obj[i]);
                } else if (obj[i].getClass().equals(java.util.Date.class)) {
                    java.util.Date date = (java.util.Date) obj[i];
                    call.setDate(i + 1, new Date(date.getTime()));
                } else if (obj[i].getClass().equals(Timestamp.class)) {
                    call.setTimestamp(i + 1, (Timestamp) obj[i]);
                } else if (obj[i].getClass().equals(String[].class)) {
                    final Array array = con.createArrayOf("varchar", (String[]) obj[i]);
                    call.setArray(i + 1, array);
                }
            }
            log.info(log_template_execute_query, call, new java.util.Date());
            call.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public <T> List<T> Select(Connection con ,String query, RowMapper<T> mapper) {
        List<T> result = new ArrayList<>();
        ResultSet rs = null;
        CallableStatement call = null;
        try{
            call = con.prepareCall(query);
            log.info(log_template_execute_query, query, new java.util.Date());
            rs = call.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            try {
                while (rs.next()) {

                    try {
                        msg = rs.getString("Message");
                    } catch (Exception e) {
                        msg = "";
                    }
                    if (result.size() == 0 && !msg.isEmpty() && rsmd.getColumnCount() == 1) {
                        MessageMapper mapperMsg = new MessageMapper();
                        mapper = (RowMapper<T>) mapperMsg;
                        result.add(mapper.mapRow(rs, 0));
                        msg = "error";
                    } else {
                        result.add(mapper.mapRow(rs, 0));
                        msg = "sukses";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msg = e.getMessage();
                log.info("Error: " + msg);
                throw new RuntimeException(e);
            }
            return  result;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (call != null) {
                try {
                    rs.close();
                    call.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public <T> T SinggleSelect(Connection con, String query, RowMapper<T> mapper) {
        T model = null;
        ResultSet rs = null;
        CallableStatement call = null;
        try {
            call = con.prepareCall(query);
            log.info(log_template_execute_query, query, new java.util.Date());
            rs = call.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            try {
                if (rs.next()) {
                    if (rsmd.getColumnCount() == 1) {
                        model = mapper.mapRow(rs, 0);
                    } else {
                        model = mapper.mapRow(rs, 0);
                    }
                }
            } catch (Exception e) {
                throw new SQLException(e.getMessage());
            }
            return model;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (call != null) {
                try {
                    rs.close();
                    call.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public <T> List<T> Select(Connection con, String table, String[] columns, List<SQLWhereObject> Where, RowMapper<T> mapper) {
        List<T> result = new ArrayList<>();
        ResultSet rs = null;
        CallableStatement call = null;
        try{
            String state = "";
            for (int i = 0; i < columns.length; i++) {
                if(i == 0){
                    state = columns[i];
                }else{
                    state = state + ", " + columns[i];
                }
            }
            String where = "";
            for (int i = 0; i < Where.size(); i++) {
                if(i == 0){
                    where = where + " " + Where.get(i).getField() + " " + Where.get(i).getOperator().getOperation() + " " + Where.get(i).getWhere();
                }else{
                    where = where + " AND " + Where.get(i).getField() + " " + Where.get(i).getOperator().getOperation() + " " + Where.get(i).getWhere();
                }
            }
            String query = "SELECT " + state + " FROM " + table + " WHERE " + where;
            call = con.prepareCall(query);
            log.info(log_template_execute_query, query, new java.util.Date());
            rs = call.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            try {
                while (rs.next()) {

                    try {
                        msg = rs.getString("Message");
                    } catch (Exception e) {
                        msg = "";
                    }
                    if (result.size() == 0 && !msg.isEmpty() && rsmd.getColumnCount() == 1) {
                        MessageMapper mapperMsg = new MessageMapper();
                        mapper = (RowMapper<T>) mapperMsg;
                        result.add(mapper.mapRow(rs, 0));
                        msg = "error";
                    } else {
                        result.add(mapper.mapRow(rs, 0));
                        msg = "sukses";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msg = e.getMessage();
                log.info("Error: " + msg);
                throw new RuntimeException(e);
            }
            return  result;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (call != null) {
                try {
                    rs.close();
                    call.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public <T> List<T> Select(Connection con, String table, String[] columns, List<SQLWhereObject> Where, Boolean GroupBy, RowMapper<T> mapper) {
        List<T> result = new ArrayList<>();
        ResultSet rs = null;
        CallableStatement call = null;
        try {
            if (GroupBy){
                if (columns[0].equals("*")) {
                    throw new RuntimeException("Group By tidak bisa menggunakan *");
                }
            }
            String state = "";
            for (int i = 0; i < columns.length; i++) {
                if (i == 0) {
                    state = columns[i];
                } else {
                    state = state + ", " + columns[i];
                }
            }
            String where = "";
            for (int i = 0; i < Where.size(); i++) {
                if (i == 0) {
                    where = where + " " + Where.get(i).getField() + " " + Where.get(i).getOperator().getOperation() + " " + Where.get(i).getWhere();
                } else {
                    where = where + " AND " + Where.get(i).getField() + " " + Where.get(i).getOperator().getOperation() + " " + Where.get(i).getWhere();
                }
            }
            String query = "SELECT " + state + " FROM " + table + " WHERE " + where + " GROUP BY " + state;
            call = con.prepareCall(query);
            log.info(log_template_execute_query, query, new java.util.Date());
            rs = call.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            try {
                while (rs.next()) {

                    try {
                        msg = rs.getString("Message");
                    } catch (Exception e) {
                        msg = "";
                    }
                    if (result.size() == 0 && !msg.isEmpty() && rsmd.getColumnCount() == 1) {
                        MessageMapper mapperMsg = new MessageMapper();
                        mapper = (RowMapper<T>) mapperMsg;
                        result.add(mapper.mapRow(rs, 0));
                        msg = "error";
                    } else {
                        result.add(mapper.mapRow(rs, 0));
                        msg = "sukses";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msg = e.getMessage();
                log.info("Error: " + msg);
                throw new RuntimeException(e);
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (call != null) {
                try {
                    rs.close();
                    call.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public <T> List<T> Select(Connection con, String table, String[] columns, List<SQLWhereObject> Where, int page_in, int page_size, RowMapper<T> mapper) {
        List<T> result = new ArrayList<>();
        ResultSet rs = null;
        CallableStatement call = null;
        try{
            String state = "";
            for (int i = 0; i < columns.length; i++) {
                if(i == 0){
                    state = columns[i];
                }else{
                    state = state + ", " + columns[i];
                }
            }
            String where = "";
            for (int i = 0; i < Where.size(); i++) {
                if(i == 0){
                    where = where + " " + Where.get(i).getField() + " " + Where.get(i).getOperator().getOperation() + " " + Where.get(i).getWhere();
                }else{
                    where = where + " AND " + Where.get(i).getField() + " " + Where.get(i).getOperator().getOperation() + " " + Where.get(i).getWhere();
                }
            }
            String query = "SELECT " + state + " FROM " + table + " WHERE " + where + " offset " + ((page_in - 1) * page_size) + " rows fetch next " + page_size + " rows only";
            call = con.prepareCall(query);
            log.info(log_template_execute_query, query, new java.util.Date());
            rs = call.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            try {
                while (rs.next()) {

                    try {
                        msg = rs.getString("Message");
                    } catch (Exception e) {
                        msg = "";
                    }
                    if (result.size() == 0 && !msg.isEmpty() && rsmd.getColumnCount() == 1) {
                        MessageMapper mapperMsg = new MessageMapper();
                        mapper = (RowMapper<T>) mapperMsg;
                        result.add(mapper.mapRow(rs, 0));
                        msg = "error";
                    } else {
                        result.add(mapper.mapRow(rs, 0));
                        msg = "sukses";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                msg = e.getMessage();
                log.info("Error: " + msg);
                throw new RuntimeException(e);
            }
            return  result;

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (call != null) {
                try {
                    rs.close();
                    call.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void Insert(Connection con, String TableName, LinkedHashMap<String, Object> linkedHashMap){
        CallableStatement call = null;
        try{
            String column = "";
            for(int i = 0; i < linkedHashMap.size(); i++) {
                if (i == 0) {
                    column += linkedHashMap.keySet().toArray()[i];
                } else {
                    column += "," + linkedHashMap.keySet().toArray()[i];
                }
            }
            String value = "";
            for(int i = 0; i < linkedHashMap.size(); i++){
                if (i == 0){
                    value = "?";
                }else{
                    value = value + ",?";
                }
            }
            String sql = "INSERT INTO " + TableName + "(" + column + ") VALUES(" + value + ")";
            call = con.prepareCall(sql);
            for (int i = 0; i < linkedHashMap.size(); i++) {
                if (linkedHashMap.values().toArray()[i].getClass().equals(String.class)) {
                    call.setString(i + 1, (String) linkedHashMap.values().toArray()[i]);
                } else if (linkedHashMap.values().toArray()[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 1, (Integer) linkedHashMap.values().toArray()[i]);
                } else if (linkedHashMap.values().toArray()[i].getClass().equals(Long.class)) {
                    call.setLong(i + 1, (Long) linkedHashMap.values().toArray()[i]);
                } else if (linkedHashMap.values().toArray()[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 1, (Float) linkedHashMap.values().toArray()[i]);
                } else if (linkedHashMap.values().toArray()[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 1, (Double) linkedHashMap.values().toArray()[i]);
                }else if (linkedHashMap.values().toArray()[i].getClass().equals(Boolean.class)) {
                    call.setBoolean(i + 1, (Boolean) linkedHashMap.values().toArray()[i]);
                } else if (linkedHashMap.values().toArray()[i].getClass().equals(Date.class)) {
                    call.setDate(i + 1, (Date) linkedHashMap.values().toArray()[i]);
                } else if (linkedHashMap.values().toArray()[i].getClass().equals(java.util.Date.class)) {
                    java.util.Date date = (java.util.Date) linkedHashMap.values().toArray()[i];
                    call.setDate(i + 1, new Date( date.getTime() ) );
                } else if (linkedHashMap.values().toArray()[i].getClass().equals(String[].class)) {
                    final Array array = con.createArrayOf("varchar", (String[]) linkedHashMap.values().toArray()[i]);
                    call.setArray(i + 1, array);
                }
            }
            log.info(log_template_execute_query, call.toString(), new java.util.Date());
            call.execute();
        }catch (Exception e){
            e.printStackTrace();
            msg = e.getMessage();
            log.info("Error: " + msg);
            throw new RuntimeException(e);
        }finally {
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void Update (Connection con, String TableName, LinkedHashMap<String, Object> Set, List<SQLWhereObject> Where){
        CallableStatement call = null;
        try{
            String set = "";
            for (int i = 0; i < Set.size(); i++) {
                if (i == 0) {
                    set = Set.keySet().toArray()[i] + " = ?";
                } else {
                    set = set + ", " + Set.keySet().toArray()[i] + " = ?";
                }
            }
            String where = "";
            for (int i = 0; i < Where.size(); i++) {
                if (i == 0) {
                    where = " WHERE " + Where.get(i).getField() + " " + Where.get(i).getOperator().getOperation() + " " + Where.get(i).getWhere();
                } else {
                    where = where + " AND " + Where.get(i).getField() + " " + Where.get(i).getOperator().getOperation() + " " + Where.get(i).getWhere();
                }
            }
            String sql = "UPDATE " + TableName + " SET " + set + " " + where;
            call = con.prepareCall(sql);
            for (int i = 0; i < Set.size(); i++) {
                if (Set.values().toArray()[i].getClass().equals(String.class)) {
                    call.setString(i + 1, (String) Set.values().toArray()[i]);
                } else if (Set.values().toArray()[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 1, (Integer) Set.values().toArray()[i]);
                } else if (Set.values().toArray()[i].getClass().equals(Long.class)) {
                    call.setLong(i + 1, (Long) Set.values().toArray()[i]);
                } else if (Set.values().toArray()[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 1, (Float) Set.values().toArray()[i]);
                } else if (Set.values().toArray()[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 1, (Double) Set.values().toArray()[i]);
                } else if (Set.values().toArray()[i].getClass().equals(Boolean.class)) {
                    call.setBoolean(i + 1, (Boolean) Set.values().toArray()[i]);
                } else if (Set.values().toArray()[i].getClass().equals(Date.class)) {
                    call.setDate(i + 1, (Date) Set.values().toArray()[i]);
                } else if (Set.values().toArray()[i].getClass().equals(java.util.Date.class)) {
                    java.util.Date date = (java.util.Date) Set.values().toArray()[i];
                    call.setDate(i + 1, new Date( date.getTime() ) );
                } else if (Set.values().toArray()[i].getClass().equals(String[].class)) {
                    final Array array = con.createArrayOf("varchar", (String[]) Set.values().toArray()[i]);
                    call.setArray(i + 1, array);
                }
            }
            log.info(log_template_execute_query, call, new java.util.Date());
            call.execute();
        }catch (Exception e){
            e.printStackTrace();
            msg = e.getMessage();
            log.info("Error: " + msg);
            throw new RuntimeException(e);
        }
        finally {
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void Delete(Connection con, String tableName, List<SQLWhereObject> where) {
        CallableStatement call = null;
        try {
            String Where = "";
            for (int i = 0; i < where.size(); i++) {
                if (i == 0) {
                    Where = where.get(i).getField() + " " + where.get(i).getOperator().getOperation() + " " + where.get(i).getWhere();
                } else {
                    Where = Where + " AND " + where.get(i).getField() + " " + where.get(i).getOperator().getOperation() + " " + where.get(i).getWhere();
                }
            }
            String sql = "DELETE FROM " + tableName + " WHERE " + Where;
            call = con.prepareCall(sql);
            log.info(log_template_execute_query, call, new java.util.Date());
            call.execute();
        } catch (Exception e) {
            e.printStackTrace();
            msg = e.getMessage();
            log.info("Error Delete: " + msg);
            throw new RuntimeException(e);
        }finally {
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String CallFunctionRetString(Connection con, String functionName, Object[] params) {
        String ret = "";
        CallableStatement call = null;
        try{
            String state = "";
            for(int i = 0; i < params.length; i++){
                if (i == 0){
                    state = "?";
                }else{
                    state = state + ",?";
                }
            }

            String sql = "{? = call " + functionName + "(" + state + ")}";
            call =  con.prepareCall(sql);
            call.registerOutParameter(1, Types.VARCHAR);
            for (int i = 0; i < params.length; i++) {
                if (params[i].getClass().equals(String.class)) {
                    call.setString(i + 2, (String) params[i]);
                } else if (params[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 2, (Integer) params[i]);
                } else if (params[i].getClass().equals(Long.class)) {
                    call.setLong(i + 2, (Long) params[i]);
                } else if (params[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 2, (Float) params[i]);
                } else if (params[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 2, (Double) params[i]);
                }else if (params[i].getClass().equals(Boolean.class)) {
                    call.setBoolean(i + 2, (Boolean) params[i]);
                } else if (params[i].getClass().equals(Date.class)) {
                    call.setDate(i + 2, (Date) params[i]);
                } else if (params[i].getClass().equals(java.util.Date.class)) {
                    java.util.Date date = (java.util.Date) params[i];
                    call.setDate(i + 2, new Date( date.getTime() ) );
                } else if (params[i].getClass().equals(String[].class)) {
                    final Array array = con.createArrayOf("varchar", (String[]) params[i]);
                    call.setArray(i + 2, array);
                }
            }
            log.info(log_template_execute_query, call, new java.util.Date());
            call.execute();
            ret = call.getString(1);
            return ret;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String CallFunctionRetString(Connection con, String functionName, LinkedHashMap<String, Object> params) {
        String ret = "";
        CallableStatement call = null;
        try{
            String state = "";
            for(int i = 0; i < params.size(); i++){
                if (i == 0){
                    state = "?";
                }else{
                    state = state + ",?";
                }
            }

            String sql = "{? = call " + functionName + "(" + state + ")}";
            call =  con.prepareCall(sql);
            call.registerOutParameter(1, Types.VARCHAR);
            for (int i = 0; i < params.size(); i++) {
                if (params.values().toArray()[i].getClass().equals(String.class)) {
                    call.setString(i + 2, (String) params.values().toArray()[i]);
                } else if (params.values().toArray()[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 2, (Integer) params.values().toArray()[i]);
                } else if (params.values().toArray()[i].getClass().equals(Long.class)) {
                    call.setLong(i + 2, (Long) params.values().toArray()[i]);
                } else if (params.values().toArray()[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 2, (Float) params.values().toArray()[i]);
                } else if (params.values().toArray()[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 2, (Double) params.values().toArray()[i]);
                }else if (params.values().toArray()[i].getClass().equals(Boolean.class)) {
                    call.setBoolean(i + 2, (Boolean) params.values().toArray()[i]);
                } else if (params.values().toArray()[i].getClass().equals(Date.class)) {
                    call.setDate(i + 2, (Date) params.values().toArray()[i]);
                } else if (params.values().toArray()[i].getClass().equals(java.util.Date.class)) {
                    java.util.Date date = (java.util.Date) params.values().toArray()[i];
                    call.setDate(i + 2, new Date( date.getTime() ) );
                } else if (params.values().toArray()[i].getClass().equals(String[].class)) {
                    final Array array = con.createArrayOf("varchar", (String[]) params.values().toArray()[i]);
                    call.setArray(i + 2, array);
                }
            }
            log.info(log_template_execute_query, call, new java.util.Date());
            call.execute();
            ret = call.getString(1);
            return ret;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public <T> List<T> ExecuteCallPostgre(Connection con, String functionName, Object[] objects, RowMapper<T> mapper) {
        List<T> list = new ArrayList<>();
        CallableStatement call = null;
        ResultSet rs = null;
        try {
            String state = "";
            for (int i = 0; i < objects.length; i++) {
                if (i == 0) {
                    state = "?";
                } else {
                    state = state + ",?";
                }
            }
            String sql = "{? = call " + functionName + "(" + state + ")}";
            log.info(sql);

            call = con.prepareCall(sql);
            call.registerOutParameter(1, Types.OTHER);
            for (int i = 0; i < objects.length; i++) {
                if (objects[i].getClass().equals(String.class)) {
                    call.setString(i + 2, (String) objects[i]);
                } else if (objects[i].getClass().equals(Integer.class)) {
                    call.setInt(i + 2, (Integer) objects[i]);
                } else if (objects[i].getClass().equals(Long.class)) {
                    call.setLong(i + 2, (Long) objects[i]);
                } else if (objects[i].getClass().equals(Float.class)) {
                    call.setFloat(i + 2, (Float) objects[i]);
                } else if (objects[i].getClass().equals(Double.class)) {
                    call.setDouble(i + 2, (Double) objects[i]);
                } else if (objects[i].getClass().equals(Boolean.class)) {
                    call.setBoolean(i + 2, (Boolean) objects[i]);
                } else if (objects[i].getClass().equals(Date.class)) {
                    call.setDate(i + 2, (Date) objects[i]);
                } else if (objects[i].getClass().equals(java.util.Date.class)) {
                    java.util.Date date = (java.util.Date) objects[i];
                    call.setDate(i + 2, new Date(date.getTime()));
                } else if (objects[i].getClass().equals(String[].class)) {
                    final Array array = con.createArrayOf("varchar", (String[]) objects[i]);
                    call.setArray(i + 2, array);
                }
            }
            log.info(log_template_execute_query, call, new java.util.Date());
            call.execute();
            rs = (ResultSet) call.getObject(1);
            ResultSetMetaData rsmd = rs.getMetaData();

            try {
                while (rs.next()) {
                    if (list.size() == 0 && rsmd.getColumnCount() == 1) {
                        list.add(mapper.mapRow(rs, 0));
                    } else {
                        list.add(mapper.mapRow(rs, 0));
                    }
                }
            } catch (Exception e) {
                e.getMessage();
                list.add((T) e.getMessage());
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (call != null) {
                try {
                    call.close();
                } catch (SQLException e) {
                    /* ignored */
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    /* ignored */
                }
            }
        }
    }


    public <T> T ExecuteSingleCallPostgre(Connection con, String functionName, Object[] obj, RowMapper<T> mapper) {
        T model = null;
        CallableStatement call = null;
        ResultSet rs = null;
        try {
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
            log.info(sql);
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
                } else if (obj[i].getClass().equals(Date.class)) {
                    call.setDate(i + 2, (Date) obj[i]);
                } else if (obj[i].getClass().equals(java.util.Date.class)) {
                    java.util.Date date = (java.util.Date) obj[i];
                    call.setDate(i + 2, new Date(date.getTime()));
                } else if (obj[i].getClass().equals(String[].class)) {
                    final Array array = con.createArrayOf("varchar", (String[]) obj[i]);
                    call.setArray(i + 2, array);
                }
            }
            log.info(log_template_execute_query, call, new java.util.Date());
            call.execute();
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
        }

        return model;
    }
}
