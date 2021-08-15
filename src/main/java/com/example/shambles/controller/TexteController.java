package com.example.shambles.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.shambles.model.Texte;
import com.example.shambles.model.User;
import com.example.shambles.posterDto.TexteDto;
import com.example.shambles.repository.TexteRepository;
import com.example.shambles.service.TexteService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TexteController {
	
	@Autowired
	private TexteRepository texteRepository;
	
	@Autowired
	private TexteService texteService;
	
	private final ModelMapper modelMapper = new ModelMapper();
	
	
	@GetMapping("/texte")
	public Iterable<Texte>getTexte(){
		return texteService.getTexte();		
		}
	
	@GetMapping("/texte/{id}")
	public Texte getTexte(@PathVariable ("id") final Long id) {
		Optional<Texte> Texte = texteService.getTexte(id);
		if(Texte.isPresent()) {
			return Texte.get();
		}else {
			return null;
		}
		
	}
	
	
	@PostMapping("/texte/{idUser}")
	public User CreatPoster(@RequestBody Texte texte,
			@PathVariable ("idUser") final Long idUser) {
		
		return texteService.saveTexte(texte,idUser);
}
	
	/*------------------------------update------------------------------------------------*/


    @PutMapping("/texte")
    public Texte updateTexte(@RequestBody TexteDto texteDto ) {
    	
         return texteService.updateTexte(texteDto);
    		
    	
    }
 
	
	
	
	
	
	
	
	
	
	

}
