package com.example.shambles.controller;


import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.shambles.model.Poster;
import com.example.shambles.model.User;
import com.example.shambles.posterDto.PosterDto;
import com.example.shambles.repository.PosterRepository;
import com.example.shambles.service.PosterService;
import com.example.shambles.utils.FileUtilePoster;







@CrossOrigin
@RestController

public class PosterController {

	@Autowired
	private PosterService posterService;
	@Autowired
	private PosterRepository posterRepository;
	
	/*-----------------------------------------------*/
	private final ModelMapper modelMapper = new ModelMapper();
	
	/*----------recuperer tout les utilisateurs----------------------------*/
	@GetMapping("/poster")
	public Iterable<Poster> getPoster(){
		return posterService.getPoster();
	}
	
	/*-------------recuperer by id-------------------------------*/
	@GetMapping("/poster/{id}")
	public Poster getPoster(@PathVariable ("id") final Long id){
		Optional<Poster> Poster = posterService.getPoster(id);
		if(Poster.isPresent()) {
			return Poster.get();
		}else {
			return null;
		}
	}
	
	/*--------------------post -----------------------------------------------*/

//	@PostMapping("/poster")
//	public Poster CreatPoster(@RequestBody Poster p) {
//		return posterService.savePoster(p);
//	}
	
	@PostMapping("/poster/{idUser}")
	public User CreatPoster(@RequestPart("photo") MultipartFile file,
			@RequestPart ("poster") PosterDto posterDto,
			@PathVariable ("idUser") final Long idUser) {
		 Poster poster = upload(file,posterDto);
		//return poster;
		 return posterService.savePoster(poster, idUser);
	}
	
	
	
	/*-----------------delete---------------------------------*/
	 @DeleteMapping("/poster/{id}")
	    public void deletePoster(@PathVariable("id")  final Long id){
		 posterService.deletPoster(id);
	    }
	
	 /*---------------fonction upload----------------------------------------*/
		
		private Poster upload(MultipartFile file,PosterDto  posterDto ) {
			String fileName="";
			String lastFile="";
			if(file != null) {
				String[] nameExtension = Objects.requireNonNull(file.getContentType()).split("/");
				fileName="imgPost" + "." + nameExtension[1];
				lastFile = posterDto.getId() != null ?posterDto.getPhoto():"";
				
				posterDto.setPhoto(fileName);
			}
			Poster poster = posterRepository.save(modelMapper.map(posterDto,Poster.class));
				if(file != null) {
					try {
						if(posterDto.getId()!=null) {
							FileUtilePoster.saveFileAndReplace(lastFile,file,fileName,poster.getId());
						}else {
							FileUtilePoster.saveFile(poster.getId(),fileName,file);
						}
						
					}catch (IOException e) {
		                e.printStackTrace();
		            }
					
				}
				return poster;
		}
	
	
	
	
}
