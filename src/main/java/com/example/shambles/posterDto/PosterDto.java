package com.example.shambles.posterDto;

import com.example.shambles.model.User;

public class PosterDto {
	private Long id;
	private String photo;
	private String description;
	private boolean isLike=false;
	
	private User user;
	
	
	

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isLike() {
		return isLike;
	}
	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
	

