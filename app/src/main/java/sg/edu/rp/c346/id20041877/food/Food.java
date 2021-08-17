package sg.edu.rp.c346.id20041877.food;

import java.io.Serializable;

public class Food implements Serializable {

    private int id;
    private String name;
    private String location;
    private String comment;
    private int stars;

    public Food(int id, String name, String location, String comment, int stars) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.comment = comment;
        this.stars = stars;
    }

    public Food(String name, String location, String comment, int stars) {
        this.name = name;
        this.location = location;
        this.comment = comment;
        this.stars = stars;
    }

    public int getId() { return id; }

    public Food setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() { return name; }

    public Food setName(String name) {
        this.name = name;
        return this;
    }

    public String getLocation() { return location; }

    public Food setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getComment() { return comment; }

    public Food setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public int getStars() { return stars; }

    public Food setStars(int stars) {
        this.stars = stars;
        return this;
    }
}
