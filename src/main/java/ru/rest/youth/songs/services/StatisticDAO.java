package ru.rest.youth.songs.services;

import java.sql.SQLException;

public class StatisticDAO {


    private static final String INSERT_SQL = "INSERT INTO statistic (number) VALUES (?)";


    public void insert(Long number) throws SQLException {
//        try (Connection conn = dataSource.getConnection()) {
//            try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
//                ps.setInt(1, number);
//                ps.execute();
//            }
//        }
    }
}