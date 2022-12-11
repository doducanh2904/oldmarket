package com.due.oldmarket.controller;

import com.due.oldmarket.controller.FileController;
import com.due.oldmarket.dto.JwtResponse;
import com.due.oldmarket.dto.LoginRequest;
import com.due.oldmarket.dto.MessageResponse;
import com.due.oldmarket.dto.SignupRequest;
import com.due.oldmarket.model.ERole;
import com.due.oldmarket.model.File;
import com.due.oldmarket.model.Role;
import com.due.oldmarket.model.User;
import com.due.oldmarket.payload.UploadFileResponse;
import com.due.oldmarket.reponsitory.RoleReponsitory;
import com.due.oldmarket.reponsitory.UserReponsitory;
import com.due.oldmarket.security.ServiceSecurity.UserDetailsImpl;
import com.due.oldmarket.security.jwt.JwtUtils;
import com.due.oldmarket.service.FileStorageService;
import com.due.oldmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserReponsitory userRepository;

    @Autowired
    RoleReponsitory roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        List<File> fileList = fileStorageService.findByUserId(userDetails.getId());
        List<UploadFileResponse> uploadFileResponsesList = new ArrayList<>();
        UploadFileResponse uploadFileResponse;
        for (File file : fileList){
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("file/downloadFile/")
                    .path(file.getId())
                    .toUriString();
            uploadFileResponse = new UploadFileResponse() ;
            uploadFileResponse.setFileName(file.getFileName());
            uploadFileResponse.setFileId(file.getId());
            uploadFileResponse.setFileDownloadUri(fileDownloadUri);
            uploadFileResponse.setFileType(file.getFileType());
            uploadFileResponsesList.add(uploadFileResponse);
        }


        return ResponseEntity.ok(new JwtResponse(
                jwt,
                roles,
                userDetails.getId(),
                userDetails.getFullName(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getPassword(),
                userDetails.getPhoneNumber(),
                userDetails.getAddress(),
                userDetails.getSex(),
                userDetails.getBirthday(),
                userDetails.getStatus(),
                uploadFileResponsesList
        ));


    }

    @PostMapping("/signup")
    public ResponseEntity<UploadFileResponse> registerUser(@ModelAttribute SignupRequest signUpRequest,
                                                           @RequestParam("file") MultipartFile file) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new UploadFileResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new UploadFileResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                signUpRequest.getPhoneNumber(),
                signUpRequest.getAddress(),
                signUpRequest.getSex(),
                signUpRequest.getBirthday(),
                signUpRequest.getStatus()
                );
        Set<String> strRoles = signUpRequest.getRole();
        System.out.println("=========>>>>>> "+ strRoles);
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        user.setStatus("active");
        userRepository.save(user);
        Long idUser = userService.findByUsernamOrEmailAut(signUpRequest.getEmail()).getIdUser();
        if(file.isEmpty()){
            return ResponseEntity
                    .badRequest()
                    .body(new UploadFileResponse("User registered successfully and no avatar yet"));
        }
        File dbFile = fileStorageService.storeFile(file, idUser);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("file/downloadFile/")
                .path(dbFile.getId())
                .toUriString();


        return ResponseEntity.ok(new UploadFileResponse(dbFile.getId(), dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize(),"User registered successfully!"));
    }
}
