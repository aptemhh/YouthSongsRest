package ru.rest.youth.songs.data;



public class Song {
    Integer number;
    String text;
    String description;

    public Song(Integer number, String description, String text) {
        this.number = number;
        this.text = text;
        this.description = description;
    }


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

