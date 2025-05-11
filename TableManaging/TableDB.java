package TableManaging;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import TableManaging.DAOs.RowMapper;
import TableManaging.DAOs.StatementBinder;

public class TableDB<T> implements Table<T> {
    private final Connection conn;
    private final String tableName;
    private final RowMapper<T> mapper;
    private final StatementBinder<T> binder;

    public TableDB(String tableName,
                   RowMapper<T> mapper,
                   StatementBinder<T> binder) throws SQLException {
        this.conn = DBConnectionManager.getConnection();
        this.tableName = tableName;
        this.mapper = mapper;
        this.binder = binder;
    }


    public List<T> fetchAll(String whereClause, Object... params) {
        var results = new ArrayList<T>();
        String sql = "SELECT * FROM " + tableName
                + (whereClause != null && !whereClause.isBlank()
                ? " WHERE " + whereClause
                : "");
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // bind any WHERE parameters
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(mapper.mapRow(rs));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("fetchAll failed", ex);
        }
        return results;
    }


    public void insert(T obj) {
        String[] cols = binder.getColumns();
        String colList = String.join(",", cols);
        String placeholders = String.join(",", java.util.Collections.nCopies(cols.length, "?"));
        String sql = "INSERT INTO " + tableName
                + " (" + colList + ") VALUES (" + placeholders + ")";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            binder.bind(ps, obj);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("insert failed", ex);
        }
    }

    public void update(String setClause,
                       String whereClause,
                       Object[] setParams,
                       Object[] whereParams) {
        String sql = "UPDATE " + tableName
                + " SET " + setClause
                + " WHERE " + whereClause;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int idx = 1;
            // bind SET parameters
            for (Object o : setParams) {
                ps.setObject(idx++, o);
            }
            // bind WHERE parameters
            for (Object o : whereParams) {
                ps.setObject(idx++, o);
            }
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("update failed", ex);
        }
    }

    public void delete(String whereClause, Object... whereParams) {
        String sql = "DELETE FROM " + tableName
                + " WHERE " + whereClause;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < whereParams.length; i++) {
                ps.setObject(i + 1, whereParams[i]);
            }
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("delete failed", ex);
        }
    }

}
