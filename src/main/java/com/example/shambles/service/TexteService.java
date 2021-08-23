package com.example.shambles.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.shambles.model.Texte;
import com.example.shambles.model.User;
import com.example.shambles.posterDto.TexteDto;
import com.example.shambles.repository.TexteRepository;
import com.example.shambles.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class TexteService {
	
	@Autowired
	private TexteRepository texteRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private final ModelMapper modelMapper = new ModelMapper();
	
	public Optional<Texte> getTexte(final Long id) {
		return texteRepository.findById(id);
	}
	
	public Iterable<Texte> getTexte() {
		return texteRepository.findAllByOrderByIdDesc();
	}
	
	// --------------------delete-----------------------------
//	public User saveTexte(Texte texte, Long idUser) {
//		Optional<User> userOptional = userRepository.findById(idUser);
//		User user = null;
//		if (userOptional.isPresent()) {
//			user = userOptional.get();
//			user.getTextes().add(texte);
//			return userRepository.save(user);
//
//		}
//
//		return user;
//	}
	// --------------------enregistrer-------------------------------
	public User saveTexte(Texte texte, Long idUser) {
		Optional<User> userOptional = userRepository.findById(idUser);
		User user = null;
		if (userOptional.isPresent()) {
			user = userOptional.get();
			texte.setUser(user);
			user.getTextes().add(texte);
			return userRepository.save(user);

		}

		return user;
	}
	
	/*---------------------------------------update---------------------------------------------*/
	/*----------------on transforme notre entity texte en Dto puis l'envoyer dans la bdd----------------------------------------------------*/

	public Texte updateTexte(TexteDto texteDto) {
		Texte texte = modelMapper.map(texteDto, Texte.class);
		return texteRepository.save(texte);
	}
	
	
	
	
	
	
	
	

}
