package com.jlog;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class DbConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("DB 연결을 확인한다.")
    void getConnection() {
        try (final Connection connection = dataSource.getConnection()) {
            assertThat(connection.getCatalog()).isEqualTo("JLOG");
            assertTableExists(connection, "MEMBER");
            assertTableExists(connection, "ROOM");
            assertTableExists(connection, "LOG");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void assertTableExists(final Connection connection, String tableName) throws SQLException {
        assertThat(connection.getMetaData().getTables(null, null, tableName, null).next()).isTrue();
    }
}
