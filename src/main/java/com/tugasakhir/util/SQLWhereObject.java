package com.tugasakhir.util;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

public class SQLWhereObject extends JdbcDaoSupport {
    private String field;
    @Enumerated(EnumType.STRING)
    private SQLOperation operator;

    private String string;

    private Integer integer;

    private Long aLong;

    private Double aDouble;

    private Boolean aBoolean;

    private String[] stringArray;

    private Integer[] integerArray;

    private Long[] longArray;

    private Double[] doubleArray;

    private Boolean[] booleanArray;

    private Date date;

    public String getField() {
        return field;

    }

    public SQLOperation getOperator() {
        return operator;
    }

    public SQLWhereObject(String field, SQLOperation operator) {
        this.field = field;
        this.operator = operator;
    }

    public SQLWhereObject(String field, SQLOperation operator, String where) {
        this.field = field;
        this.operator = operator;
        this.string = where;
    }

    public SQLWhereObject(String field, SQLOperation operator, Integer where) {
        this.field = field;
        this.operator = operator;
        this.integer = where;
    }

    public SQLWhereObject(String field, SQLOperation operator, Long where) {
        this.field = field;
        this.operator = operator;
        this.aLong = where;
    }

    public SQLWhereObject(String field, SQLOperation operator, Double where) {
        this.field = field;
        this.operator = operator;
        this.aDouble = where;
    }

    public SQLWhereObject(String field, SQLOperation operator, Boolean where) {
        this.field = field;
        this.operator = operator;
        this.aBoolean = where;
    }

    public SQLWhereObject(String field, SQLOperation operator, String[] where) {
        this.field = field;
        this.operator = operator;
        this.stringArray = where;
    }

    public SQLWhereObject(String field, SQLOperation operator, Integer[] where) {
        this.field = field;
        this.operator = operator;
        this.integerArray = where;
    }

    public SQLWhereObject(String field, SQLOperation operator, Long[] where) {
        this.field = field;
        this.operator = operator;
        this.longArray = where;
    }

    public SQLWhereObject(String field, SQLOperation operator, Double[] where) {
        this.field = field;
        this.operator = operator;
        this.doubleArray = where;
    }

    public SQLWhereObject(String field, SQLOperation operator, Boolean[] where) {
        this.field = field;
        this.operator = operator;
        this.booleanArray = where;
    }

    public SQLWhereObject(String field, SQLOperation operator, Date where) {
        this.field = field;
        this.operator = operator;
        this.date = where;
    }
    public String getWhere(){
        String where = "";
        if(string != null) {
            where = " '" + string + "'";
        }else if(integer != null) {
            where = " " + integer;
        }else if(aLong != null) {
            where = " " + aLong;
        }else if(aDouble != null) {
            where = " " + aDouble;
        }else if(aBoolean != null) {
            where = " " + aBoolean;
        }else if(stringArray != null) {
            where = " (";
            for (int i = 0; i < stringArray.length; i++) {
                if (i == stringArray.length - 1) {
                    where += "'" + stringArray[i] + "'";
                } else {
                    where += "'" + stringArray[i] + "',";
                }
            }
            where += ")";
        }else if(integerArray != null) {
            where = " (";
            for (int i = 0; i < integerArray.length; i++) {
                if (i == integerArray.length - 1) {
                    where += integerArray[i];
                } else {
                    where += integerArray[i] + ",";
                }
            }
            where += ")";
        }else if(longArray != null) {
            where = " (";
            for (int i = 0; i < longArray.length; i++) {
                if (i == longArray.length - 1) {
                    where += longArray[i];
                } else {
                    where += longArray[i] + ",";
                }
            }
            where += ")";
        }else if(doubleArray != null) {
            where = " (";
            for (int i = 0; i < doubleArray.length; i++) {
                if (i == doubleArray.length - 1) {
                    where += doubleArray[i];
                } else {
                    where += doubleArray[i] + ",";
                }
            }
            where += ")";
        }else if(booleanArray != null) {
            where = " (";
            for (int i = 0; i < booleanArray.length; i++) {
                if (i == booleanArray.length - 1) {
                    where += booleanArray[i];
                } else {
                    where += booleanArray[i] + ",";
                }
            }
            where += ")";
        }else if(date != null) {
            where = " '" + date + "'";
        }else {
            where = "";
        }
        return where;

    }

}