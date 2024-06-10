package com.cine.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TmdbMovie {

    private Long id;
    private String title;

    @JsonProperty("poster_path")
    private String posterPath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}