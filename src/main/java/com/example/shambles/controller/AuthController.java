package com.example.shambles.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.example.shambles.Request.LoginRequest;
import com.example.shambles.Request.SignupRequest;
import com.example.shambles.model.ERole;
import com.example.shambles.model.Poster;
import com.example.shambles.model.Role;
import com.example.shambles.model.User;
import com.example.shambles.posterDto.PosterDto;
import com.example.shambles.posterDto.UserDto;
import com.example.shambles.repository.RoleRepository;
import com.example.shambles.repository.UserRepository;
import com.example.shambles.response.JwtResponse;
import com.example.shambles.response.MessageResponse;
import com.example.shambles.security.jwt.JwtUtils;
import com.example.shambles.service.UserDetailsImpl;
import com.example.shambles.service.UserService;
import com.example.shambles.utils.FileUtilePhotoProfil;
import com.example.shambles.utils.FileUtilePoster;




@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    
    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = (Authentication) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication((org.springframework.security.core.Authentication) authentication);
        String jwt = jwtUtils.generateJwtToken((org.springframework.security.core.Authentication) authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) ((org.springframework.security.core.Authentication) authentication).getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest
//    		
//    		) {
//    	//@RequestPart("img") MultipartFile file
//    	
//        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Username is already taken!"));
//        }
//
//        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(new MessageResponse("Error: Email is already in use!"));
//        }
//
//        // Create new user's account
//        User user = new User(signUpRequest.getUsername(),
//                signUpRequest.getEmail(),        
//                encoder.encode(signUpRequest.getPassword()));
//        
//        // user = upload(file,signUpRequest);
//        
//        
//
//        Set<ERole> eRoles = ERole.ConvertFromString(signUpRequest.getRoles());
//        Set<Role> roles = new HashSet<>();
//
//        if (eRoles == null) {
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            signUpRequest.getRoles().forEach(role -> {
//                        Role currentRole = roleRepository.findByName( ERole.valueOf(role))
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(currentRole);
//                    }
//
//            );
//            
//        }
//        
//        user.setRoles(roles);
//        userRepository.save(user);
//
//        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
//        
//        
//        
//    }
    
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
        if (userRepository.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByUsername(signupRequest.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        //Create new user's account
        User user = new User(signupRequest.getUsername(),
                             signupRequest.getEmail(),
                             encoder.encode(signupRequest.getPassword()));
                            
        Set<ERole> strRoles = ERole.ConvertFromString(signupRequest.getRoles());
        Set<Role> roles = new HashSet<>();

        if (strRoles.isEmpty()){
            Role userRole = roleRepository.findByName(ERole. ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        }else{
            signupRequest.getRoles().forEach(role ->{
                Role currentRole = roleRepository.findByName( ERole.valueOf(role))
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(currentRole);

            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    
    
    /*----------recuperer tout les utilisateurs----------------------------*/
	@GetMapping("/user")
	public Iterable<User> getUser(){
		return userService.getUser();
	}
	
	/*-------------recuperer by id-------------------------------*/
	@GetMapping("/user/{id}")
	public  User getUser(@PathVariable ("id") final Long id){
		Optional<User> User = userService.getUser(id);
		if(User.isPresent()) {
			return User.get();
		}else {
			return null;
		}
	}
	
//	-------------------delete user id---------------------------------------------------------------
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	 @DeleteMapping("/user/{id}")
	    public void deleteUser(@PathVariable("id")  final Long id){
		 userService.deletUser(id);
	    }
	 
	 

	 private User upload(MultipartFile file,@Valid SignupRequest  signUpRequest ) {
			String fileName="";
			String lastFile="";
			if(file != null) {
				String[] nameExtension = Objects.requireNonNull(file.getContentType()).split("/");
				fileName="imgPost" + "." + nameExtension[1];
				lastFile =  signUpRequest.getId() != null ? signUpRequest.getImg():"";
				
				signUpRequest.setImg(fileName);
			}
			User user = userRepository.save(modelMapper.map(signUpRequest,User.class));
				if(file != null) {
					try {
						if(signUpRequest.getId()!=null) {
							FileUtilePoster.saveFileAndReplace(lastFile,file,fileName,user.getId());
						}else {
							FileUtilePoster.saveFile(user.getId(),fileName,file);
						}
						
					}catch (IOException e) {
		                e.printStackTrace();
		            }
					
				}
				return user;
		}

    
    
    
    
    
    
    
    
    
}
