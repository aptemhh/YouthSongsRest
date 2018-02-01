package ru.rest.youth.songs.data;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Song {
    Integer number;

    @XmlElement
    String description;
    @XmlElement
    String text;

    public Song(Integer number, String description, String text) {
        this.number = number;
        this.description = description;
        this.text = text;
    }

    public Song(Integer number, String description) {
        this.number = number;
        this.description = description;
    }

    public Song()
    {}

    public Integer getNumber() {
        return number;
    }


    public String getText() {
        return text;
    }


    public String getDescription() {
        return description;
    }
}

