package com.cine.movie.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Movie {
    @Id
    private Long id;
    private String name;
    private String image;

    public Movie() {}

    public Movie(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public Movie(Long id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
