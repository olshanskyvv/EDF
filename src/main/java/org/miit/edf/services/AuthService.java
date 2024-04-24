package org.miit.edf.services;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.miit.edf.dto.JwtAuthenticationResponse;
import org.miit.edf.dto.PasswordDTO;
import org.miit.edf.dto.SignInRequest;
import org.miit.edf.dto.SignUpRequest;
import org.miit.edf.dto.response.UserDTO;
import org.miit.edf.models.Organisation;
import org.miit.edf.models.Role;
import org.miit.edf.models.User;
import org.miit.edf.repos.OrganisationRepo;
import org.miit.edf.utils.auth.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("AuthService")
@AllArgsConstructor
@Log4j2
public class AuthService {
    private final UserService userService;
    private final OrganisationRepo organisationRepo;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User createUser(SignUpRequest request, Role role) {
        log.debug("Start AuthService.createUser");
        Organisation organisation = organisationRepo.findById(request.getOrganizationId()).orElse(null);
        return User.builder()
                .login(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .patronymic(request.getPatronymic() == null ? "No patronymic" : request.getPatronymic())
                .organisation(organisation)
                .role(role)
                .build();
    }

    public UserDTO profile() {
        log.debug("Start AuthService.profile");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new UserDTO(userService.loadUserByUsername(username));
    }

    public void updatePassword(PasswordDTO passwordDTO) {
        log.debug("Start UserService.updatePassword");
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Check password for user with username = " + username);
        User user = userService.loadUserByUsername(username);
        String encoded = passwordEncoder.encode(passwordDTO.getPassword());
        user.setPassword(encoded);
        userService.save(user);
        log.debug("End UserService.updatePassword");
    }

    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        log.debug("Start AuthService.signUp");

        User user = createUser(request, Role.USER);

        userService.save(user);

        var jwt = jwtUtils.generateToken(user);
        log.debug("End AuthService.signUp");
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        log.debug("Start AuthService.signIn");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .loadUserByUsername(request.getUsername());

        var jwt = jwtUtils.generateToken(user);
        log.debug("End AuthService.signIn");
        return new JwtAuthenticationResponse(jwt);
    }
}
