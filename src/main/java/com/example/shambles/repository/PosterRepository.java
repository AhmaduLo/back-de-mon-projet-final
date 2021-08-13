package com.example.shambles.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shambles.model.Poster;




public interface PosterRepository extends JpaRepository<Poster,Long> {
	public List<Poster> findAllByOrderByIdDesc();
}
