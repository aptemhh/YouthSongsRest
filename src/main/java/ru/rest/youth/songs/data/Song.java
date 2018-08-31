package ru.rest.youth.songs.data;

import com.fasterxml.jackson.annotation.JsonInclude;

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
    private Long number;

    @XmlElement
    @Column(name = "DESCRIPTION")
    private  String description;

    @XmlElement
    @Column(name = "TEXT")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private  String text;

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

