package com.example.shambles.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.shambles.model.Texte;



public interface TexteRepository  extends JpaRepository<Texte, Long> {
	public List<Texte> findAllByOrderByIdDesc();
}
