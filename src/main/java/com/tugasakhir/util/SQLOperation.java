package com.tugasakhir.util;

public enum SQLOperation {
    EQUAL("="),
    NOT_EQUAL("!="),
    GREATER_THAN(">"),
    GREATER_THAN_EQUAL(">="),
    LESS_THAN("<"),
    LESS_THAN_EQUAL("<="),
    IN("IN"),
    NOT_IN("NOT IN"),
    LIKE("LIKE"),
    NOT_LIKE("NOT LIKE"),
    IS_NULL("IS NULL"),
    IS_NOT_NULL("IS NOT NULL"),
    BETWEEN("BETWEEN"),
    NOT_BETWEEN("NOT BETWEEN"),
    EXISTS("EXISTS"),
    NOT_EXISTS("NOT EXISTS"),
    AND("AND"),
    OR("OR"),
    GROUP_BY("GROUP BY");

    private final String operation;

    public String getOperation() {
        return operation;
    }
    SQLOperation(String operation) {
        this.operation = operation;
    }
}
