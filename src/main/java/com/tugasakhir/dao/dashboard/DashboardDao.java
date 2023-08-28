package com.tugasakhir.dao.dashboard;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface DashboardDao {
    ResponseEntity<?> getDataDashboard(HttpServletRequest request);
}
