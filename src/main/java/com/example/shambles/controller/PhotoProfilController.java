package com.example.shambles.controller;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.shambles.model.PhotoProfil;
import com.example.shambles.model.Poster;
import com.example.shambles.model.User;
import com.example.shambles.posterDto.PhotoProfilDto;
import com.example.shambles.posterDto.PosterDto;
import com.example.shambles.repository.PhotoProfilRepository;
import com.example.shambles.repository.UserRepository;
import com.example.shambles.service.PhotoProfilService;
import com.example.shambles.utils.FileUtilePoster;


@CrossOrigin
@RestController
public class PhotoProfilController {
	
	
	@Autowired
	private PhotoProfilRepository photoProfilRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PhotoProfilService photoProfilService;
	
	private final ModelMapper modelMapper = new ModelMapper();
	
	@GetMapping("/profilphoto/{id}")
	public PhotoProfil getPhotoProfil(@PathVariable ("id") final Long id) {
		Optional<PhotoProfil>PhotoProfil = photoProfilService.getPhotoProfil(id);
		if(PhotoProfil.isPresent()) {
			return PhotoProfil.get();
		}else {
			return null;
		}
	}
	
	@PostMapping("/profilphoto/{idUser}")
	public User CreatPhotoProfil(@RequestPart("photo") MultipartFile file,
			@RequestPart ("profilphoto") PhotoProfilDto photoProfilDto,
			@PathVariable ("idUser") final Long idUser) {
		PhotoProfil photoProfil = upload(file,photoProfilDto);
		return photoProfilService.savePhotoProfil(photoProfil, idUser);
		
	}
	
	
	private PhotoProfil upload(MultipartFile file,PhotoProfilDto photoProfilDto) {
		String fileName="";
		String lastFile="";
		if(file != null) {
			String[] nameExtension = Objects.requireNonNull(file.getContentType()).split("/");
			fileName="imgPost" + "." + nameExtension[1];
			lastFile = photoProfilDto.getId() != null ?photoProfilDto.getPhotoProfil():"";
			
			photoProfilDto.setPhotoProfil(fileName);
		}
		PhotoProfil photoProfil = photoProfilRepository.save(modelMapper.map(photoProfilDto,PhotoProfil.class));
			if(file != null) {
				try {
					if(photoProfilDto.getId()!=null) {
						FileUtilePoster.saveFileAndReplace(lastFile,file,fileName,photoProfil.getId());
					}else {
						FileUtilePoster.saveFile(photoProfil.getId(),fileName,file);
					}
					
				}catch (IOException e) {
	                e.printStackTrace();
	            }
				
			}
			return photoProfil;
	}
	
	
	
	
	
	
	
	
	
	
	

}
