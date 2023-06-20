package com.tugasakhir.util;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class ObjectMapper implements RowMapper<LinkedHashMap<String, Object>> {
    @Override
    public LinkedHashMap<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        int noOfColumnIndex = rs.getMetaData().getColumnCount();
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        for (int columnIndex = 1; columnIndex <= noOfColumnIndex; columnIndex++) {
            String columnName = rs.getMetaData().getColumnName(columnIndex);
            Object columnValue = rs.getObject(columnIndex);
            if (columnValue != null) {
                hashMap.put(columnName.toLowerCase(), columnValue);
            } else {
                hashMap.put(columnName.toLowerCase(), "");
            }
        }
        return hashMap;
    }

}
