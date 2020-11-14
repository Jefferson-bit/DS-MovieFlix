package com.crash.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crash.entities.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>{
	
	@Query("SELECT DISTINCT obj FROM Genre obj JOIN FETCH obj.movies WHERE obj IN :genres")
	List<Genre> findGenresMovie(List<Genre> genres);
	
	@Query("SELECT obj FROM Genre obj WHERE obj.name LIKE %:name%")
	Page<Genre> searchForGenre(String name, Pageable pageable);
}
