package ru.rest.youth.songs.data;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcSongDAO {


    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Song findBySongNumber(int number) {

        String sql = "SELECT ID,DESCRIPTION,TEXT FROM SONG WHERE ID = ?";

        Song song = null;
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, number);
                try (ResultSet rs = ps.executeQuery()) {
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

    public List<Song> findSongByText(String text) {
        //#TODO релизовать поиск песен по тексту
        List<Song> songs = new ArrayList<>();
        songs.add(findBySongNumber(1));
        songs.add(findBySongNumber(1));
        return songs;
    }
}
