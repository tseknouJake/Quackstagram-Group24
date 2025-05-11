package TableManaging.DAOs;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementBinder<T> {
    void bind(PreparedStatement ps, T obj) throws SQLException;
}
