package TableManaging.Parsers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;

public interface ResultSetParser<T> {
    T fromResultSet(ResultSet rs) throws SQLException;

    /** e.g. "INSERT INTO users(username,password,...) VALUES (?,?,...)" */
    String buildInsertSQL(String tableName);

    /** binds the INSERT parameters */
    void bindInsert(PreparedStatement ps, T row) throws SQLException;

    /** e.g. "UPDATE users SET password=?,bio=? WHERE id=?" */
    String buildUpdateSQL(String tableName);

    void bindUpdate(PreparedStatement ps, T row) throws SQLException;

    /** optional: build DELETE ... WHERE ... or bind condition */
    String buildDeleteSQL(String tableName);

    void bindDelete(PreparedStatement ps, Predicate<T> condition) throws SQLException;
}

