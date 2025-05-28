package org.isp.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthIndicator implements HealthIndicator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Health health() {
        try {
            int result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            if (result == 1) {
                return Health.up()
                        .withDetail("database", "H2")
                        .withDetail("status", "Available")
                        .build();
            }
            return Health.down()
                    .withDetail("database", "H2")
                    .withDetail("status", "Unavailable")
                    .build();
        } catch (Exception e) {
            return Health.down()
                    .withDetail("database", "H2")
                    .withDetail("status", "Unavailable")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}
