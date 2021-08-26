package com.example.shambles.model;

import java.util.HashSet;
import java.util.Set;

public enum ERole {
  
	ROLE_USER,
    ROLE_MODERATOR,
    ROLE_ADMIN;
	
	
	
	//---------il permet de covertir en string les roles-----------
	
	public static Set<ERole> ConvertFromString(Set<String> role) {
        Set<ERole> roles = new HashSet<>();
        role.forEach(str -> roles.add(ERole.valueOf(str)));
        return roles;
    }
}
