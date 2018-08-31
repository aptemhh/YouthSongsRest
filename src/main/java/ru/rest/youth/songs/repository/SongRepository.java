package ru.rest.youth.songs.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.rest.youth.songs.data.Song;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Long>
{
    @Query(value = "SELECT s.ID, s.DESCRIPTION, null TEXT FROM SONG s WHERE " +
            "s.DESCRIPTION LIKE %?1% OR s.TEXT LIKE %?1% ORDER BY s.ID" , nativeQuery = true)
    List<Song> getAllSongShort(String text);

    @Query(value = "SELECT s.ID, s.DESCRIPTION, null TEXT FROM SONG s ORDER BY s.ID" , nativeQuery = true)
    List<Song> getAllSongShort();

    List<Song> findAll();

    @Query(value = "SELECT " +
            "s.ID ID, " +
            "s.DESCRIPTION," +
            "null TEXT " +
            "FROM PARTY_SONGS p " +
            "LEFT JOIN SONG s " +
            "ON p.SONG_ID = s.ID " +
            "WHERE PARTY_ID = " +
            "(SELECT id " +
            "FROM PARTY p " +
            "WHERE p.NAME = :party_name) " +
            "ORDER BY  p.POSITION", nativeQuery = true)
    List<Song> getAllSongByParty(@Param("party_name")String partyName);
}
