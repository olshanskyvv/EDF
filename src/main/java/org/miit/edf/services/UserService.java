package org.miit.edf.services;

import lombok.RequiredArgsConstructor;
import org.miit.edf.models.User;
import org.miit.edf.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}
