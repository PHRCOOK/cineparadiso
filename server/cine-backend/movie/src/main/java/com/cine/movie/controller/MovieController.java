package com.cine.movie.controller;

import com.cine.movie.model.Movie;
import com.cine.movie.repository.MovieRepository;
import com.cine.movie.service.TmdbService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MovieController {

    private final TmdbService tmdbService;
    private final MovieRepository movieRepository;

    public MovieController(TmdbService tmdbService, MovieRepository movieRepository) {
        this.tmdbService = tmdbService;
        this.movieRepository = movieRepository;
    }

    // Endpoint para obtener y guardar películas desde la API de TMDB
    @GetMapping("/fetch-movies")
    public String fetchMovies() {
        tmdbService.fetchAndSaveMovies();
        return "Movies fetched and saved successfully!";
    }

    // Endpoint para obtener todas las películas desde la base de datos
    @GetMapping("/movies")
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
}