package ru.rest.youth.songs.youthsongs.data;

public class Song {
    Integer number;
    String text;
    String description;

    public Song(Integer number, String description, String text) {
        this.number = number;
        this.text = text;
        this.description = description;
    }

    public Song() {
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

