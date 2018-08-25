package ru.rest.youth.songs.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
public class Song {

    @Id
    @Column(name = "ID")
    Long number;

    @XmlElement
    @Column(name = "DESCRIPTION")
    String description;

    @XmlElement
    @Column(name = "TEXT")
    String text;

    public Song(Long number, String description, String text) {
        this.number = number;
        this.description = description;
        this.text = text;
    }

    public Song(Long number, String description) {
        this.number = number;
        this.description = description;
    }

    public Song(String description, String text) {
        this.description = description;
        this.text = text;
    }

    public Song()
    {}

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @XmlTransient
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

