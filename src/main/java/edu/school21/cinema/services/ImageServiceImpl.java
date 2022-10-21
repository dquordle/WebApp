package edu.school21.cinema.services;

import edu.school21.cinema.models.Image;
import edu.school21.cinema.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ImageServiceImpl implements ImageService{
    private final ImageRepository repository;
    @Value("${storage.path}")
    private String storagePath;

    public ImageServiceImpl(ImageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void mkdir() {
        try {
            File storageDir = new File(storagePath);
            if (!storageDir.exists()) {
                if (!storageDir.mkdirs()) {
                    throw new RuntimeException("Error creating image directory");
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileInputStream getImageFile(String fileId) {
        try {
            return new FileInputStream(storagePath + "/" + fileId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<Image> findLastImageByUserId(Long userId) {
        return repository.findLastByUserId(userId);
    }

    @Override
    public void save(Part filePart, Long userId) {
        String fileName = filePart.getSubmittedFileName();
        Long size = filePart.getSize();
        String mime = filePart.getContentType();
        Image image = new Image(null, userId, fileName, size, mime);
        repository.save(image);
        try {
            new File(storagePath + "/" + image.getId());
            filePart.write(storagePath + "/" + image.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Image> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Image> getImagesByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
