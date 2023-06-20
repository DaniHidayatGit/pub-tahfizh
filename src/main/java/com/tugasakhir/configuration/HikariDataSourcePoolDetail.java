package com.tugasakhir.configuration;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HikariDataSourcePoolDetail {
    private final HikariDataSource dataSource;

    public HikariDataSourcePoolDetail(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public HikariPool getHikariPool() {
        return (HikariPool) new DirectFieldAccessor(dataSource).getPropertyValue("pool");
    }

    public int getActive() {
        try {
            return getHikariPool().getActiveConnections();
        } catch (Exception ex) {
            return -1;
        }
    }

    public int getWaiting() {
        try {
            return getHikariPool().getThreadsAwaitingConnection();
        } catch (Exception ex) {
            return -1;
        }
    }

    public int getMax() {
        return dataSource.getMaximumPoolSize();
    }

    public String restart() {
        try {
            getHikariPool().softEvictConnections();
            return "Success";
        } catch (Exception ex) {
            return "Failed";
        }
    }

    public String suspend() {
        try {
            getHikariPool().suspendPool();
            return "SUSPEND";
        } catch (Exception ex) {
            return "Failed";
        }
    }

    public String resume() {
        try {
            getHikariPool().resumePool();
            return "RESUME";
        } catch (Exception ex) {
            return "Failed";
        }
    }
}
