package com.tugasakhir.util.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class Mapper implements RowMapper<LinkedHashMap<String, String>> {

    @Override
    public LinkedHashMap<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
        int noOfColumnIndex = rs.getMetaData().getColumnCount();
        LinkedHashMap<String, String> hashMap = new LinkedHashMap<>();
        for (int columnIndex = 1; columnIndex <= noOfColumnIndex; columnIndex++) {
            String columnName = rs.getMetaData().getColumnName(columnIndex);
            Object columnValue = rs.getObject(columnIndex);
            if (columnValue != null) {
                hashMap.put(columnName.toLowerCase(), columnValue.toString());
            } else {
                hashMap.put(columnName.toLowerCase(), "");
            }
        }
        return hashMap;
    }
}
