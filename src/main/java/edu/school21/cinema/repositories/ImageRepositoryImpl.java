package edu.school21.cinema.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

import edu.school21.cinema.models.Image;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;

public class ImageRepositoryImpl implements ImageRepository{
    private final JdbcTemplate template;

    private final String SQL_FIND_ALL = "SELECT * FROM cinema.images";

    private final String SQL_FIND_BY_ID = "SELECT * FROM cinema.images WHERE id = ?";

    private final String SQL_UPDATE = "UPDATE cinema.images SET userId = ?, name = ?, size = ?, mime = ? WHERE id = ?";

    private final String SQL_SAVE = "INSERT INTO cinema.images (userId, name, size, mime) VALUES (?, ?, ?, ?)";

    private final String SQL_DELETE = "DELETE FROM cinema.images WHERE id = ?";

    private final String SQL_FIND_BY_USER_ID = "SELECT * FROM cinema.images WHERE userId = ?";

    private final String SQL_FIND_LAST_BY_USER_ID = "SELECT * FROM cinema.images WHERE userId = ? ORDER BY id DESC LIMIT 1";

    private final RowMapper<Image> imageRowMapper = BeanPropertyRowMapper.newInstance(Image.class);

    public ImageRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Image> findByUserId(Long userId) {
        return template.query(SQL_FIND_BY_USER_ID, imageRowMapper, userId);
    }

    @Override
    public Optional<Image> findLastByUserId(Long userId) {
        List<Image> images = template.query(SQL_FIND_LAST_BY_USER_ID, imageRowMapper, userId);
        if (images.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(images.get(0));
        }
    }

    @Override
    public Optional<Image> findById(Long id) {
        List<Image> images = template.query(SQL_FIND_BY_ID, imageRowMapper, id);
        if (images.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(images.get(0));
        }
    }

    @Override
    public List<Image> findAll() {
        return template.query(SQL_FIND_ALL, imageRowMapper);
    }

    @Override
    public void save(Image entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection ->
            {
                PreparedStatement ps = connection.prepareStatement(SQL_SAVE, Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, entity.getUserId());
                ps.setString(2, entity.getName());
                ps.setLong(3, entity.getSize());
                ps.setString(4, entity.getMime());
                return ps;
            } , keyHolder);
        Map<String, Object> keys = keyHolder.getKeys();
        if (keys != null) {
            Integer id = (Integer) keys.get("id");
            entity.setId(id.longValue());
        } else {
            throw new RuntimeException("Database error");
        }
    }

    @Override
    public void update(Image entity) {
        template.update(SQL_UPDATE, entity.getUserId(), entity.getName(), entity.getSize(), entity.getMime(),
                entity.getId());
    }

    @Override
    public void delete(Long id) {
        template.update(SQL_DELETE, id);
    }
}
