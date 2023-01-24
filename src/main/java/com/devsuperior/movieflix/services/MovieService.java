package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieDTO;
import com.devsuperior.movieflix.dto.MovieMinDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

	@Transactional(readOnly = true)
	public MovieMinDTO findById(Long id) {
		Optional<Movie> movieOptional = repository.findById(id);
		Movie movie = movieOptional.orElseThrow(() -> new ResourceNotFoundException("Resource not found!"));
		return new MovieMinDTO(movie);
	}
	
	@Transactional(readOnly = true)
	public Page<MovieDTO> findByGenre(Long genreId, Pageable pageable) {
		Page<Movie> page = null;
		if(genreId == 0) { // TRAZER TUDO QUANDO LIMPAR O CAMPO DE BUSCA
			page = repository.findWhenGenreNullOrZero(pageable);
		} else { // TRAZER SÓ OS FILMES DO GÊNERO SELECIONADO
			page = repository.findByGenre(genreId, pageable);
			
		}
		return page.map(movie -> new MovieDTO(movie));
	}
}
