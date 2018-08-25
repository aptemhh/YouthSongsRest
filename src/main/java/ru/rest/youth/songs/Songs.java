package ru.rest.youth.songs;

import ru.rest.youth.songs.data.Song;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Song.class})
public class Songs {

    @XmlElement(name = "song")
    private List<Song> songs = null;

    public List<Song> getSongs() {
        return songs;
    }

    public Songs setSongs(List<Song> songs) {
        this.songs = songs;
        return this;
    }
}
