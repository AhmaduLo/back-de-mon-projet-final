package com.example.shambles.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shambles.model.Poster;



@Repository
@Transactional
public interface PosterRepository extends JpaRepository<Poster,Long> {
	public List<Poster> findAllByOrderByIdDesc();
}
