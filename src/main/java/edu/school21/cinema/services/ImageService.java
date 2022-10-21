package edu.school21.cinema.services;

import edu.school21.cinema.models.Image;

import javax.servlet.http.Part;
import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

public interface ImageService {
    void save(Part part, Long userId);
    Optional<Image> findById(Long id);
    List<Image> getImagesByUserId(Long userId);
    void mkdir();
    FileInputStream getImageFile(String imageId);
    Optional<Image> findLastImageByUserId(Long userId);
}
