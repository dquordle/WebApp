package edu.school21.cinema.services;

import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@AllArgsConstructor
public class UsersServiceImpl implements UsersService{
	private  UsersRepository repository;
	private  PasswordEncoder passwordEncoder;

	@Override
	public User signUp(String email, String firstName, String lastName, String phoneNumber, String password) {
		User user = new User(null, email, firstName, lastName, phoneNumber, passwordEncoder.encode(password));
		repository.save(user);
		Optional<User> userOptional = repository.findByEmail(email);
		return userOptional.orElse(null);
	}

	@Override
	public User signIn(String email, String password) {
		Optional<User> user = repository.findByEmail(email);
		if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
			return user.get();
		}
		return null;
	}

	@Override
	public boolean isPresent(String email) {
		return repository.findByEmail(email).isPresent();
	}
}
