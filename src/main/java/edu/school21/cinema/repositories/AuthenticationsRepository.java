package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Authentication;

import java.util.List;

public interface AuthenticationsRepository extends CrudRepository<Authentication> {
    List<Authentication> findByUserId(Long userId);
}
