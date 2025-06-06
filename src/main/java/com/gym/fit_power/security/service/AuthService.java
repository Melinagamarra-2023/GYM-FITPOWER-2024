package com.gym.fit_power.security.service;


import com.gym.fit_power.dto.ClientDTO;
import com.gym.fit_power.dto.request.RequestNutri;
import com.gym.fit_power.dto.request.TrainerRequestDto;
import com.gym.fit_power.exception.EntitySaveException;
import com.gym.fit_power.security.dto.AuthResponse;
import com.gym.fit_power.security.dto.LoginRequest;
import com.gym.fit_power.security.dto.RegisterRequest;
import com.gym.fit_power.security.model.ERole;
import com.gym.fit_power.security.model.Role;
import com.gym.fit_power.security.model.User;
import com.gym.fit_power.security.repository.RoleRepository;
import com.gym.fit_power.security.repository.UserRepository;

import com.gym.fit_power.service.ClientService;
import com.gym.fit_power.service.NutriService;
import com.gym.fit_power.service.TrainerService;
import jakarta.transaction.Transactional;
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

    private final TrainerService trainerService;
    private final NutriService nutriService;
    private final ClientService clientService;

    @Transactional // Ensures atomicity for user and profile creation
    public void register(RegisterRequest registerRequest) {

        if (userRepository.existsByCuit(registerRequest.getCuit())) {
            throw new IllegalArgumentException("There is already a registered user with that CUIT.");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("The email is already associated with an account.");
        }

        Set<String> requestedRoleNames = registerRequest.getRoles();
        if (requestedRoleNames == null || requestedRoleNames.isEmpty()) {
            throw new IllegalArgumentException("User roles must be specified.");
        }

        Set<Role> roles = requestedRoleNames.stream()
                .map(roleName -> {
                    ERole erole;
                    try {
                        erole = ERole.valueOf(roleName.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Invalid role specified: " + roleName);
                    }
                    return roleRepository.findByName(erole)
                            .orElseThrow(() -> new IllegalArgumentException("Role not found in database: " + roleName));
                })
                .collect(Collectors.toSet());

        User user = User.builder()
                .cuit(registerRequest.getCuit())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);

        // Create corresponding profiles based on roles
        for (Role role : user.getRoles()) {
            ERole roleEnum = role.getName();
            try {
                switch (roleEnum) {
                    case TRAINER:
                        createTrainerProfile(registerRequest);
                        break;
                    case NUTRITIONIST:
                        createNutritionistProfile(registerRequest);
                        break;
                    case USER:
                        createClientProfile(registerRequest);
                        break;
                    // Add cases for other roles if necessary
                    default:
                        // Optional: Log if a role is unhandled for profile creation,
                        // or if all roles should have a profile.
                        System.out.println("No specific profile creation logic for role: " + roleEnum);
                }
            } catch (EntitySaveException e) {
                // Re-throw as RuntimeException to trigger transaction rollback
                throw new RuntimeException("Failed to create profile for role " + roleEnum.name() +
                        " for user " + user.getEmail() + ". Error: " + e.getMessage(), e);
            }
        }
    }

    private void createTrainerProfile(RegisterRequest registerRequest) throws EntitySaveException {
        TrainerRequestDto trainerDto = new TrainerRequestDto();
        trainerDto.setCuit(registerRequest.getCuit());
        trainerDto.setName(registerRequest.getName());
        trainerDto.setLastname(registerRequest.getLastname());
        trainerDto.setEmail(registerRequest.getEmail());
        trainerDto.setPhoneNumber(registerRequest.getPhone()); // Maps 'phone' from RegisterRequest to 'phoneNumber'
        trainerService.save(trainerDto); // This method already checks for existing CUIT in TrainerRepository
    }

    private void createNutritionistProfile(RegisterRequest registerRequest) throws EntitySaveException {
        RequestNutri nutriDto = new RequestNutri();
        nutriDto.setCuit(registerRequest.getCuit());
        nutriDto.setName(registerRequest.getName());
        nutriDto.setLastname(registerRequest.getLastname());
        nutriDto.setEmail(registerRequest.getEmail());
        nutriDto.setPhone(registerRequest.getPhone());
        nutriService.create(nutriDto);
    }

    private void createClientProfile(RegisterRequest registerRequest) throws EntitySaveException {
        ClientDTO clientDto = new ClientDTO();
        clientDto.setCuit(registerRequest.getCuit());
        clientDto.setName(registerRequest.getName());
        clientDto.setLastname(registerRequest.getLastname());
        clientDto.setEmail(registerRequest.getEmail());
        clientDto.setPhone(registerRequest.getPhone());
        clientDto.setAssignedGym(registerRequest.getAssignedGym());
        clientService.create(clientDto);
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
