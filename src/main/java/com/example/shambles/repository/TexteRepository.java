package com.example.shambles.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shambles.model.Texte;



@Repository
@Transactional
public interface TexteRepository  extends JpaRepository<Texte, Long> {
	public List<Texte> findAllByOrderByIdDesc();
	public List<Texte> findByUserId(Long id);
	
	
}

//select * from texte order by Id Desc 
