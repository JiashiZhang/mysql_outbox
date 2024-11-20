package org.example.mysql_outbox.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    public DatabaseService() {
        System.out.println("DatabaseService Bean 已被创建");
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        System.out.println("Create Success!");
    }
}
