package edu.school21.cinema.services;

import edu.school21.cinema.models.User;

public interface UsersService {
	User signUp(String email, String firstName, String lastName, String phoneNumber, String password);
	User signIn(String email, String password);
	boolean isPresent(String email);
}
