package edu.school21.cinema.repositories;

import edu.school21.cinema.models.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryImpl implements UsersRepository{
    private final JdbcTemplate template;

    private final String SQL_FIND_ALL = "SELECT * FROM cinema.users";

    private final String SQL_FIND_BY_ID = "SELECT * FROM cinema.users WHERE id = ?";

    private final String SQL_UPDATE = "UPDATE cinema.users SET email = ?, firstName = ?, lastName = ?, " +
            "phoneNumber = ?, password = ? WHERE id = ?";

    private final String SQL_SAVE = "INSERT INTO cinema.users (email, firstName, lastName, phoneNumber, password) " +
            "VALUES (?, ?, ?, ?, ?)";

    private final String SQL_DELETE = "DELETE FROM cinema.users WHERE id = ?";

    private final String SQL_FIND_BY_EMAIL = "SELECT * FROM cinema.users WHERE email = ?";

    private final RowMapper<User> userRowMapper = BeanPropertyRowMapper.newInstance(User.class);

    public UsersRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        List<User> userList = template.query(SQL_FIND_BY_ID, userRowMapper, id);
        if (userList.isEmpty()) {
            return null;
        } else {
            return Optional.of(userList.get(0));
        }
    }

    @Override
    public List<User> findAll() {
        return template.query(SQL_FIND_ALL, userRowMapper);
    }

    @Override
    public void save(User entity) {
        template.update(SQL_SAVE, entity.getEmail(), entity.getFirstName(), entity.getLastName(),
                entity.getPhoneNumber(), entity.getPassword());
        Optional<User> tmp = findByEmail(entity.getEmail());
        tmp.ifPresent(user -> entity.setId(user.getId()));
    }

    @Override
    public void update(User entity) {
        template.update(SQL_UPDATE, entity.getEmail(), entity.getFirstName(), entity.getLastName(),
                entity.getPhoneNumber(), entity.getPassword(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        template.update(SQL_DELETE, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> userList = template.query(SQL_FIND_BY_EMAIL, userRowMapper, email);
        if (userList.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(userList.get(0));
        }
    }
}
