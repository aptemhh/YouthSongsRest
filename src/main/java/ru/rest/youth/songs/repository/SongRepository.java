package ru.rest.youth.songs.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.rest.youth.songs.data.Song;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Long>
{
    @Query(value = "SELECT s.ID, s.DESCRIPTION, null TEXT FROM SONG s WHERE " +
            "s.DESCRIPTION LIKE %?1% OR s.TEXT LIKE %?1% ORDER BY s.ID" , nativeQuery = true)
    public List<Song> getAllSongShort(String text);

    @Query(value = "SELECT s.ID, s.DESCRIPTION, null TEXT FROM SONG s ORDER BY s.ID" , nativeQuery = true)
    public List<Song> getAllSongShort();

    public List<Song> findAll();
}
