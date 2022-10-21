package edu.school21.cinema.services;

import edu.school21.cinema.models.Authentication;
import edu.school21.cinema.repositories.AuthenticationsRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final AuthenticationsRepository repository;

    @Override
    public void save(Authentication auth) {
        repository.save(auth);
    }

    @Override
    public List<Authentication> getAuthsByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
