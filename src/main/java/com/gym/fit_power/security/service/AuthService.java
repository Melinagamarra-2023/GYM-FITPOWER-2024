package com.gym.fit_power.security.service;


import com.gym.fit_power.security.dto.AuthResponse;
import com.gym.fit_power.security.dto.LoginRequest;
import com.gym.fit_power.security.dto.RegisterRequest;
import com.gym.fit_power.security.model.ERole;
import com.gym.fit_power.security.model.Role;
import com.gym.fit_power.security.model.User;
import com.gym.fit_power.security.repository.RoleRepository;
import com.gym.fit_power.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public void register(RegisterRequest registerRequest) {

        if (userRepository.existsByCuit(registerRequest.getCuit())) {
            throw new IllegalArgumentException("There is already a registered user with that cuit");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new IllegalArgumentException("The email is already associated with an account");
        }
        Set<String> rolName = registerRequest.getRoles();
        Set<Role> roles = rolName.stream()
                .map(roleName -> roleRepository.findByName(ERole.valueOf(roleName.toUpperCase()))
                        .orElseThrow(() -> new IllegalArgumentException("Role not found in database: " + roleName)))
                .collect(Collectors.toSet());

        var user = User.builder()
                .cuit(registerRequest.getCuit())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
    }
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()

                )
        );
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }


}
