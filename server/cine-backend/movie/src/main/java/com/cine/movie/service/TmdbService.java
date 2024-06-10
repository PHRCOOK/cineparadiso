package com.cine.movie.service;

import com.cine.movie.dto.TmdbResponse;
import com.cine.movie.model.Movie;
import com.cine.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TmdbService {

    private final RestTemplate restTemplate;
    private final MovieRepository movieRepository;

    @Value("${tmdb.api.key}")
    private String apiKey;

    private final String urlTemplate = "https://api.themoviedb.org/3/movie/now_playing?api_key=%s&language=en-US&page=1";

    public TmdbService(RestTemplate restTemplate, MovieRepository movieRepository) {
        this.restTemplate = restTemplate;
        this.movieRepository = movieRepository;
    }

    public void fetchAndSaveMovies() {
        String url = String.format(urlTemplate, apiKey);
        TmdbResponse response = restTemplate.getForObject(url, TmdbResponse.class);
        List<Movie> movies = response.getResults().stream()
                .filter(result -> result.getPosterPath() != null) // Filtrar películas sin imagen
                .limit(20) // Limitar a 20 películas
                .map(result -> new Movie(result.getId(), result.getTitle(), result.getPosterPath()))
                .collect(Collectors.toList());
        movieRepository.saveAll(movies);
    }
}