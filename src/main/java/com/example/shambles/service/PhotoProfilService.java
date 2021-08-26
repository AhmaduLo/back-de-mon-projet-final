package com.example.shambles.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shambles.model.PhotoProfil;
import com.example.shambles.model.User;
import com.example.shambles.repository.PhotoProfilRepository;
import com.example.shambles.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class PhotoProfilService {
	
	@Autowired
	private PhotoProfilRepository photoProfilRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Optional<PhotoProfil> getPhotoProfil(final Long id){
		return photoProfilRepository.findById(id);
	}
	
	public void deletPhotoProfil(final Long id) {
		photoProfilRepository.deleteById(id);
	}
	
	public User savePhotoProfil(PhotoProfil photoProfil, Long idUser) {
		Optional<User> userOptional = userRepository.findById(idUser);
		User user = null;
		
		if (userOptional.isPresent()) {
			user = userOptional.get();
			//photoProfil.setUser(user);
			//user.getPosters().add(photoProfil);
			return userRepository.save(user);
		}
		return user;
	}
	
}
