package ru.rest.youth.songs.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.rest.youth.songs.data.Song;

import java.util.List;

public interface SongRepository extends CrudRepository<Song, Long>
{
    @Query("SELECT number,description FROM Song WHERE DESCRIPTION LIKE %?1% ORDER BY ID")
    public List<Song> getAllSongShort(String text);

    @Query("SELECT number,description FROM Song ORDER BY ID")
    public List<Song> getAllSongShort();

    public List<Song> findAll();
}
