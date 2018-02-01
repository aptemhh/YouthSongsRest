package ru.rest.youth.songs.data;

import javax.servlet.ServletOutputStream;
import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JdbcSongDAO {

    private DataSource dataSource;
    private static final String GET_SING_LIST = "SELECT ID,DESCRIPTION,TEXT " +
            "FROM SONG " +
            "ORDER BY ID";
    private static final String GET_SING_LIST_SHORT = "SELECT ID,DESCRIPTION " +
            "FROM SONG " +
            "ORDER BY ID";

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Song findBySongNumber(int number) {
        String sql = "SELECT ID,DESCRIPTION,TEXT FROM SONG WHERE ID = ?";

        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, number);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new Song(
                                rs.getInt("ID"),
                                rs.getString("DESCRIPTION"),
                                rs.getString("TEXT")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getXmlAllSongs(ServletOutputStream printWriter) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Songs.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(new Songs().setSongs(getSongList()), printWriter);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public List<Song> getSongList() {
        List<Song> songList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(GET_SING_LIST)) {
                try (ResultSet rs = ps.executeQuery()) {
                    for (; rs.next(); ) {
                        songList.add(new Song(rs.getInt("ID"),
                                rs.getString("DESCRIPTION"),
                                rs.getString("TEXT")));
                    }
                }
            }
            return songList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Song> getSongListShort(String text) {
        try {
            return Collections.singletonList(findBySongNumber(Integer.parseInt(text)));
        }
        catch (NumberFormatException e) {
            //#todo запилить валидацию
            List<Song> songList = new ArrayList<>();
            try (Connection conn = dataSource.getConnection()) {
                String sql;
                if (text == null) {
                    sql = GET_SING_LIST_SHORT;
                } else {
                    sql = "SELECT ID,DESCRIPTION " +
                            "FROM SONG " +
                            "where DESCRIPTION like '%" + text + "%' " +
                            "OR TEXT like'%" + text + "%' " +
                            "ORDER by ID";
                }
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    try (ResultSet rs = ps.executeQuery()) {
                        for (; rs.next(); ) {
                            songList.add(new Song(rs.getInt("ID"),
                                    rs.getString("DESCRIPTION")));
                        }
                    }
                }
                return songList;
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
        }
    }
}
