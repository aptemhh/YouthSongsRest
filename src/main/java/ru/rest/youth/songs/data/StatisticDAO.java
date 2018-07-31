package ru.rest.youth.songs.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class StatisticDAO {

    private DataSource dataSource;

    private static final String INSERT_SQL = "INSERT INTO statistic (number) VALUES (?)";

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(Integer number) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
                ps.setInt(1, number);
                ps.execute();
            }
        }
    }
}