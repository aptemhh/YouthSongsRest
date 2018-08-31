package ru.rest.youth.songs.services;

import ru.rest.youth.songs.data.Song;

import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBException;
import java.util.List;

public interface SongService {

    /**
     * Поиск песни по номеру
     * @param number номер
     * @return песня
     */
    Song findBySongNumber(Long number);

    /**
     * Запрос всех песен в формате xnl
     * @param printWriter писатель xml
     * @throws JAXBException ошибка парсинга
     */
    void getXmlAllSongs(ServletOutputStream printWriter) throws JAXBException;

    /**
     * @return список песен
     */
    List<Song> getSongList();

    /**
     * @param text поисковая строка
     * @return Список песен, валидных поиску
     */
    List<Song> getSongListShort(String text);

    /**
     * @return Короткий список всех песен
     */
    List<Song> getSongListShort();

    /**
     * @param name имя группы
     * @return короткий список песен группы
     */
    List<Song> getSongListShortByParty(String name);
}
