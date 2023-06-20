package com.tugasakhir.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Helpers {

    public static String dateFormat(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    public static String toSnakeCase(String str) {
        return str.replaceAll("\\s+", "_").toLowerCase();
    }

    public static String toUpperCase(String str) {
        return str.replaceAll("_", " ").toUpperCase();
    }

    public static String getString(Object obj) {
        if (obj == null) return "";
        else return String.valueOf(obj);
    }

    public static String getPartOfTime(Date date, int part) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(part));
    }

    public static String encodeBase64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    public static String decodeBase64(String str) {
        return new String(Base64.getDecoder().decode(str));
    }

    public static Date getDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String toJson(T t) {
        Gson gson = new Gson();
        return gson.toJson(t);
    }


    public static Map<String, Object> jsonStringToMap(String jsonString) {
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
