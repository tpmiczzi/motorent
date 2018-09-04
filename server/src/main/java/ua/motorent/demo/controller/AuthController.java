package ua.motorent.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Api(value = "authentication", description = "Authentication controller")
public class AuthController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

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

    @ApiOperation(value = "Login user", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully login", response = JwtAuthenticationResponseDto.class),
            @ApiResponse(code = 401, message = "Sorry, You're not authorized to access this resource.")
    })
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

    @ApiOperation(value = "Registration new user", response = ResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registration"),
            @ApiResponse(code = 400, message = "Username is already taken!"),
            @ApiResponse(code = 400, message = "Email Address already in use!"),
            @ApiResponse(code = 400, message = "User Role not set.")
    }
    )
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
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User resultUser = userRepository.save(user);

        logger.info("=== user created - " + resultUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(resultUser.getUsername()).toUri();

        return sendSuccess("User registered successfully");
//        return ResponseEntity.created(location).body("User registered successfully");
    }
}
