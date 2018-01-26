package ru.rest.youth.songs.youthsongs.data;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcSongDAO {


    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Song findBySongNumber(int number){

        String sql = "SELECT * FROM SONG WHERE ID = ?";

        Song song = null;
        try(Connection conn = dataSource.getConnection()) {
            try(PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, number);
                try(ResultSet rs = ps.executeQuery())
                {
                    if (rs.next()) {
                        song = new Song(
                                rs.getInt("ID"),
                                rs.getString("DESCRIPTION"),
                                rs.getString("TEXT")
                        );
                    }
                }
            }
            return song;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
