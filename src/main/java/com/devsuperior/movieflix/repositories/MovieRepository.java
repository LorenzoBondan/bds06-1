package com.devsuperior.movieflix.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devsuperior.movieflix.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("SELECT DISTINCT obj FROM Movie obj "
			+ "WHERE obj.genre.id = :genreId "
			+ "ORDER BY obj.title")
	Page<Movie> findByGenre(Long genreId, Pageable pageable); // TRAZER SÓ OS FILMES DO GÊNERO SELECIONADO
	
	@Query("SELECT obj "
			+ "FROM Movie obj "
			+ "ORDER BY obj.title")
	Page<Movie> findWhenGenreNullOrZero(Pageable pageable); // TRAZER TUDO QUANDO LIMPAR O CAMPO DE BUSCA
	
	//SELECT * FROM movies WHERE genre.id = :genreId ORDER BY movie.title;
	//SELECT * FROM movies ORDER BY title;
}
