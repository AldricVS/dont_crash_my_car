package com.zma.dontcrashmycar.scores;

import java.io.Serializable;

public class Event implements Serializable {

    int id;
    String name_user;
    int score;

    public Event() {

    }

    public Event(int id, String name_user, int score) {
        this.id = id;
        this.name_user = name_user;
        this.score = score;

    }

    // ID
    public int getID() {
        return this.id;
    }

    public void setID(int id) {
        this.id = id;
    }

    // User Name
    public String getNameUser() {
        return this.name_user;
    }

    public void setNameUser(String name_user) {
        this.name_user = name_user;
    }

    // Score
    public int getScore(){ return this.score; }

    public void setScore(int score){ this.score = score; }


    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name user='" + name_user + '\'' +
                ", score=" + score +
                '}';
    }
}
