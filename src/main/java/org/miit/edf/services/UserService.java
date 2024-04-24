package org.miit.edf.services;

import lombok.RequiredArgsConstructor;
import org.miit.edf.dto.response.UserDTO;
import org.miit.edf.models.User;
import org.miit.edf.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;

    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!userRepo.existsByLogin(username)) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return userRepo.findByLogin(username);
    }

    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream().map(UserDTO::new).toList();
    }

    public List<UserDTO> findByName(String name) {
        List<User> users = userRepo.findByName(name);
        if (users.isEmpty()) {
            return List.of();
        }
        return users.stream().map(UserDTO::new).toList();
    }
}
