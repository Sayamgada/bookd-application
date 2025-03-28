package com.Bookd.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Bookd.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {

	boolean existsByGenreName(String genreName);
	
}
