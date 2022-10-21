package edu.school21.cinema.repositories;

import java.util.Optional;
import edu.school21.cinema.models.User;

public interface UsersRepository extends CrudRepository<User>{
    Optional<User> findByEmail(String email);
}
