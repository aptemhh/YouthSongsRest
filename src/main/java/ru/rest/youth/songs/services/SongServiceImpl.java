package ru.rest.youth.songs.services;

import org.springframework.stereotype.Service;
import ru.rest.youth.songs.data.Song;
import ru.rest.youth.songs.Songs;
import ru.rest.youth.songs.repository.SongRepository;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.ArrayList;
import java.util.List;

@Service
public class SongServiceImpl implements SongService {

    @Resource
    private SongRepository songRepository;

    @Override
    public Song findBySongNumber(Long number)
    {
        return songRepository.findOne(number);
    }

    @Override
    public void getXmlAllSongs(ServletOutputStream printWriter) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(Songs.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(new Songs().setSongs(getSongList()), printWriter);
    }

    @Override
    public List<Song> getSongList()
    {
        return songRepository.findAll();
    }

    @Override
    public List<Song> getSongListShort(String text)
    {
        return songRepository.getAllSongShort(text);
    }

    @Override
    public List<Song> getSongListShort()
    {
        return songRepository.getAllSongShort();
    }

//    /**
//     * Инициализация песен в базе
//     * @throws SQLException ошибка sql
//     */
//    @Deprecated
//    public void load() throws SQLException {
//        File file = new File("E:\\Novy_textovy_dokument.txt");
//        Pattern pattern = Pattern.compile("\\d+\\.(.+)");
//        try {
//            StringBuilder stringBuilder;
//            String name;
//            try (Scanner scanner = new Scanner(file)) {
//                stringBuilder = new StringBuilder();
//                name = "";
//                Boolean aBoolean = false;
//                while (scanner.hasNextLine()) {
//                    String s = scanner.nextLine();
//                    if (pattern.matcher(s).matches()) {
//                        if (aBoolean) {
//                            add(name, stringBuilder.toString());
//                        }
//
//
//                        stringBuilder = new StringBuilder();
//                        Matcher matcher = pattern.matcher(s);
//                        matcher.find();
//                        name = matcher.group(1);
//                        aBoolean = true;
//                        stringBuilder.append(name);
//                        continue;
//                    }
//                    stringBuilder.append("\n");
//                    stringBuilder.append(s);
//                }
//            }
//            add(name, stringBuilder.toString());
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    private void add(String desc, String text) throws SQLException
//    {
//        try (Connection conn = dataSource.getConnection()) {
//            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO SONG VALUES (NULL,?,?)")) {
//                ps.setString(1, desc);
//                ps.setString(2, text);
//                ps.execute();
//            }
//        }
//    }

    @Override
    public List<Song> getSongListShortByParty(String name)
    {
//        List<Song> songList = new ArrayList<>();
//        try (Connection conn = dataSource.getConnection()) {
//            String sql = "SELECT " +
//                    "p.SONG_ID ID, " +
//                    "s.DESCRIPTION " +
//                    "FROM PARTY_SONGS p " +
//                    "LEFT JOIN SONG s " +
//                    "ON p.SONG_ID = s.ID " +
//                    "WHERE PARTY_ID = " +
//                    "(SELECT id " +
//                    "FROM PARTY " +
//                    "WHERE name = ?) " +
//                    "ORDER BY  p.POSITION";
//
//            try (PreparedStatement ps = conn.prepareStatement(sql)) {
//                ps.setString(1, name);
//
//
//                try (ResultSet rs = ps.executeQuery()) {
//                    while (rs.next()) {
//                        songList.add(new Song(rs.getInt(ID),
//                                rs.getString(DESCRIPTION)));
//                    }
//                }
//            }
//            return songList;
//        }
        return new ArrayList<>();
    }
}
