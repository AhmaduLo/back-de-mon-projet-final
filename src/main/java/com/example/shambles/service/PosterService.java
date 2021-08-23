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
public class PosterService {
	@Autowired
	private PosterRepository posterRepository;

	@Autowired
	private UserRepository userRepository;

	/*----------pour obtenir un seul object--------------------*/

	public Optional<Poster> getPoster(final Long id) {
		return posterRepository.findById(id);
	}

	/*----------pour obtenir tout les objects--------------------*/
	public Iterable<Poster> getPoster() {
		return posterRepository.findAllByOrderByIdDesc();
	}

	/*----------pour supprimer un object--------------------*/
	public void deletPoster(final Long id) {
		posterRepository.deleteById(id);
	}

	/*----------pour enregistrer tout les objects--------------------*/
	public User savePoster(Poster poster, Long idUser) {
		Optional<User> userOptional = userRepository.findById(idUser);
		User user = null;
		if (userOptional.isPresent()) {
			user = userOptional.get();
			poster.setUser(user);
			user.getPosters().add(poster);
			return userRepository.save(user);
		}
		return user;

	}
}
