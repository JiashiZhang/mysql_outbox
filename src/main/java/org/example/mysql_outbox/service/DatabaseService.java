package org.example.mysql_outbox.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DatabaseService {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS AUTH_SUBSCRIPTION_RENEWAL (
                subscriptionId VARCHAR(50) NOT NULL,
                renewalId VARCHAR(50) NOT NULL,
                membershipId INT NOT NULL,
                renewalStartDate DATE NOT NULL,
                renewalEndDate DATE NOT NULL,
                tcNumber VARCHAR(50) DEFAULT NULL,
                totalAmount DECIMAL(18, 2) NOT NULL,
                status VARCHAR(20) NOT NULL,
                statusTransactionRecord TEXT DEFAULT NULL,
                PRIMARY KEY (membershipId)
            )
        """;
        jdbcTemplate.execute(sql);
        System.out.println("Table AUTH_SUBSCRIPTION_RENEWAL created or already exists.");
    }

    public void insertOneMillionRecords() {
        String sql = """
            INSERT INTO AUTH_SUBSCRIPTION_RENEWAL 
            (subscriptionId, renewalId, membershipId, renewalStartDate, renewalEndDate, tcNumber, totalAmount, status, statusTransactionRecord) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        int batchSize = 1000; // 每次插入的批量大小
        List<Object[]> batchArgs = new ArrayList<>();
        int membershipId = 1; // 假设 membershipId 是手动管理的递增值

        for (int i = 0; i < 1_000_000; i++) {
            // 随机生成测试数据
            Object[] record = new Object[]{
                    UUID.randomUUID().toString(), // subscriptionId
                    UUID.randomUUID().toString(), // renewalId
                    membershipId++,              // membershipId，递增
                    "2024-01-01",                // renewalStartDate
                    "2024-12-31",                // renewalEndDate
                    null,                        // tcNumber
                    Math.random() * 1000,        // totalAmount
                    "ACTIVE",                    // status
                    "Sample transaction record " + i // statusTransactionRecord
            };
            batchArgs.add(record);

            // 每到批量大小时执行插入并清空列表
            if (batchArgs.size() == batchSize) {
                jdbcTemplate.batchUpdate(sql, batchArgs);
                batchArgs.clear();
                System.out.println("Inserted " + i + " records...");
            }
        }

        // 插入剩余未处理的数据
        if (!batchArgs.isEmpty()) {
            jdbcTemplate.batchUpdate(sql, batchArgs);
        }
        System.out.println("Inserted 1,000,000 records successfully.");
    }
}