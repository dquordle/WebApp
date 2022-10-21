package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Image;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends CrudRepository<Image>{
    List<Image> findByUserId(Long userId);
    Optional<Image> findLastByUserId(Long userId);
}
