package com.example.shambles.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shambles.model.Poster;
import com.example.shambles.model.User;
import com.example.shambles.repository.PosterRepository;
import com.example.shambles.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PosterRepository posterRepository;
	
/*----------pour obtenir un seul object--------------------*/
	
	public Optional<User>getUser(final Long id){
		return userRepository.findById(id);
	}
	
	public Iterable<User>getUser(){
		return userRepository.findAll();
	}
	/*----------pour supprimer un object--------------------*/
	public void deletUser(final Long id) {
		userRepository.deleteById(id);
	}
	
	public User saveUser( User  user ) {
		return userRepository.save(user);
		
	}
	
	/*----------pour update un object--------------------*/
	public Optional<User>updateUser(Long id){
		return userRepository.findById(id);
	}
	
	//-------------------ajouter un post au user---------------------------------------------------
//	public Poster savePost( Poster  poster,String username ) {
//		
//		Optional<User> user =  userRepository.findByUsername(username);
//		user.get().addPosts(poster);
//		return posterRepository.save(poster);
//		
//	}

}
