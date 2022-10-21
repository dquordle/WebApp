package edu.school21.cinema.services;

import edu.school21.cinema.models.Authentication;

import java.util.List;

public interface AuthenticationService {
    void save(Authentication auth);
    List<Authentication> getAuthsByUserId(Long userId);
}
