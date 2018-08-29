package ru.rest.youth.songs.services;

import ru.rest.youth.songs.data.Song;

import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBException;;
import java.util.List;

public interface SongService {

    Song findBySongNumber(Long number);

    void getXmlAllSongs(ServletOutputStream printWriter) throws JAXBException;

    List<Song> getSongList();

    List<Song> getSongListShort(String text);

    List<Song> getSongListShort();

    List<Song> getSongListShortByParty(String name);
}
