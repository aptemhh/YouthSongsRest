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
import java.util.List;

public class JdbcSongDAO {


    private DataSource dataSource;

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
        } catch (SQLException e) {}
        return null;
    }

    public void getXmlAllSongs(ServletOutputStream printWriter) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Songs.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(new Songs().setSongs(getSongList(null)), printWriter);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public List<Song> getSongList(String text) {
        //#todo сделать поиск по номеру
        List<Song> songList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String sql;
            if(text == null)
            {
                sql = "SELECT ID,DESCRIPTION,TEXT " +
                        "FROM SONG " +
                        "ORDER by ID";
            }
            else {
                sql = "SELECT ID,DESCRIPTION,TEXT " +
                        "FROM SONG " +
                        "where DESCRIPTION like '%" + text + "%' " +
                        "OR TEXT like'%" + text + "%' " +
                        "ORDER by ID";
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
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
}
