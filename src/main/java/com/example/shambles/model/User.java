package com.example.shambles.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private String img;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	// -----------------------------liaison du table user et poster----------------------------------------------
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	Set<Poster> posters;

	

	public Set<Poster> getPosters() {
		return posters;
	}

	public void setPosters(Set<Poster> posters) {
		this.posters = posters;
	}

	// ------------------------------liaison du table user et texte-----------------------------
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	Set<Texte> textes;

	public Set<Texte> getTextes() {
		return textes;
	}

	public void setTextes(Set<Texte> textes) {
		this.textes = textes;
	}
	
	// ------------------------------liaison du table user et photoprofil-----------------------------
//	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
//	Set<PhotoProfil>photoProfils;
//	
//	public Set<PhotoProfil> getPhotoProfil() {
//		return photoProfils;
//	}
//
//	public void setPhotoProfil(Set<PhotoProfil> photoProfil) {
//		this.photoProfils = photoProfil;
//	}
	
	
	// ----------------------------------------------------------
	
	
	
	
	
	

	public User() {
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
