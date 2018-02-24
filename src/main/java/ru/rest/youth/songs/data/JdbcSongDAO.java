package ru.rest.youth.songs.data;

import javax.servlet.ServletOutputStream;
import javax.sql.DataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            errorSong.setText(e.getMessage());
            return errorSong;
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
            errorSong.setText(e.getMessage());
            return Collections.singletonList(errorSong);
        }
    }

    public List<Song> getSongListShort(String text) {
        try {
            return Collections.singletonList(findBySongNumber(Integer.parseInt(text)));
        }
        catch (NumberFormatException e) {
            List<Song> songList = new ArrayList<>();
            try (Connection conn = dataSource.getConnection()) {
                String sql;
                if (text == null) {
                    sql = GET_SING_LIST_SHORT;
                } else {
                    sql = "SELECT ID,DESCRIPTION " +
                            "FROM SONG " +
                            "where DESCRIPTION like ? " +
                            "OR TEXT like ? " +
                            "ORDER by ID";
                }
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    if (text != null)
                    {
                        String parameter = "%" + text + "%";
                        ps.setString(1, parameter);
                        ps.setString(2, parameter);
                    }

                    try (ResultSet rs = ps.executeQuery()) {
                        for (; rs.next(); ) {
                            songList.add(new Song(rs.getInt("ID"),
                                    rs.getString("DESCRIPTION")));
                        }
                    }
                }
                return songList;
            } catch (SQLException e1) {
                errorSong.setText(e1.getMessage());
                return Collections.singletonList(errorSong);
            }
        }
    }

    @Deprecated
    public void load()
    {
        File file = new File("E:\\Novy_textovy_dokument.txt");
        Pattern pattern = Pattern.compile("\\d+\\.(.+)");
        try {
            Scanner scanner = new Scanner(file);
            StringBuilder stringBuilder = new StringBuilder();
            String name = "";
            Boolean aBoolean=false;
           for(; scanner.hasNextLine();) {
               String s = scanner.nextLine();
               if (pattern.matcher(s).matches()) {
                   if (aBoolean) {
                       add(name, stringBuilder.toString());
                   }


                   stringBuilder = new StringBuilder();
                   Matcher matcher = pattern.matcher(s);
                   matcher.find();
                   name = matcher.group(1);
                   aBoolean = true;
                   stringBuilder.append(name);
                   continue;
               }
               stringBuilder.append("\n");
               stringBuilder.append(s);
           }
           add(name, stringBuilder.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void add(String desc, String text)
    {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO SONG VALUES (null,?,?)")) {
                ps.setString(1, desc);
                ps.setString(2, text);
                ps.execute();
                ps.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Song errorSong = new Song("Произошла ошибка","");
}
