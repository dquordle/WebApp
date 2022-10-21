package edu.school21.cinema.repositories;

import edu.school21.cinema.models.Authentication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class AuthenticationsRepositoryImpl implements AuthenticationsRepository{

    private final JdbcTemplate template;

    private final String SQL_FIND_ALL = "SELECT * FROM cinema.authentications";

    private final String SQL_FIND_BY_ID = "SELECT * FROM cinema.authentications WHERE id = ?";

    private final String SQL_UPDATE = "UPDATE cinema.authentications SET userId = ?, authDate = ?, authTime = ?, ip = ? WHERE id = ?";

    private final String SQL_SAVE = "INSERT INTO cinema.authentications (userId, authDate, authTime, ip) VALUES (?, ?, ?, ?)";

    private final String SQL_DELETE = "DELETE FROM cinema.authentications WHERE id = ?";

    private final String SQL_FIND_BY_USER_ID = "SELECT * FROM cinema.authentications WHERE userId = ?";

    private final RowMapper<Authentication> authenticationRowMapper = BeanPropertyRowMapper.newInstance(Authentication.class);

    public AuthenticationsRepositoryImpl(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Authentication> findByUserId(Long userId) {
        return template.query(SQL_FIND_BY_USER_ID, authenticationRowMapper, userId);
    }

    @Override
    public Optional<Authentication> findById(Long id) {
        List<Authentication> authentications = template.query(SQL_FIND_BY_ID, authenticationRowMapper, id);
        if (authentications.isEmpty()) {
            return null;
        } else {
            return Optional.of(authentications.get(0));
        }
    }

    @Override
    public List<Authentication> findAll() {
        return template.query(SQL_FIND_ALL, authenticationRowMapper);
    }

    @Override
    public void save(Authentication entity) {
        template.update(SQL_SAVE, entity.getUserId(), entity.getAuthDate(), entity.getAuthTime(), entity.getIp());
    }

    @Override
    public void update(Authentication entity) {
        template.update(SQL_UPDATE, entity.getUserId(), entity.getAuthDate(), entity.getAuthTime(), entity.getIp(),
                entity.getId());
    }

    @Override
    public void delete(Long id) {
        template.update(SQL_DELETE, id);
    }
}
