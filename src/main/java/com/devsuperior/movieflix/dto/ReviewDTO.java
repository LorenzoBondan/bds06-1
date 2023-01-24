package com.devsuperior.movieflix.dto;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

public class ReviewDTO implements Serializable {

    private Long id;

    @NotBlank(message = "invalid black field")
    private String text;
    private Long movieId;
    private UserDTO user;

    public ReviewDTO() {
    }

    public ReviewDTO(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public ReviewDTO(Long id, String text, Long movieId, UserDTO user) {
        this.id = id;
        this.text = text;
        this.movieId = movieId;
        this.user = user;
    }

    public ReviewDTO(Review entity) {
        this(entity.getId(), entity.getText(), entity.getMovie().getId(), new UserDTO(entity.getUser()));
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewDTO reviewDTO = (ReviewDTO) o;
        return id.equals(reviewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
