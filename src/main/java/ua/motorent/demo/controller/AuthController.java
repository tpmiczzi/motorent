package ua.motorent.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ua.motorent.demo.common.dto.ResponseDto;
import ua.motorent.demo.common.dto.JwtAuthenticationResponseDto;
import ua.motorent.demo.common.dto.LoginDto;
import ua.motorent.demo.common.dto.RegisterUserDto;
import ua.motorent.demo.common.model.Role;
import ua.motorent.demo.common.model.RoleName;
import ua.motorent.demo.common.model.User;
import ua.motorent.demo.common.repository.RoleRepository;
import ua.motorent.demo.common.repository.UserRepository;
import ua.motorent.demo.exception.AppException;
import ua.motorent.demo.security.JwtTokenProvider;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends BaseController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity<ResponseDto> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return sendSuccess(new JwtAuthenticationResponseDto(jwt, new Date()));
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        if (userRepository.existsByUsername(registerUserDto.getUsername())) {
            return sendError("Username is already taken!");
        }

        if (userRepository.existsByEmail(registerUserDto.getEmail())) {
            return sendError("Email Address already in use!");
        }

        User user = new User(registerUserDto.getName(), registerUserDto.getUsername(), registerUserDto.getEmail(), registerUserDto.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(()->new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User resultUser = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(resultUser.getUsername()).toUri();

//        return sendSuccess()
        return ResponseEntity.created(location).body("User registered successfully");
    }
}
