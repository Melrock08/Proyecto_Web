package com.melrock.proyecto_web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import javax.sql.DataSource;
import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SchemaSmokeTest {

    @Autowired
    DataSource dataSource;

    @Test
    void canOpenConnection() throws Exception {
        try (Connection c = dataSource.getConnection()) {
            assertThat(c.isValid(2)).isTrue();
        }
    }
}
