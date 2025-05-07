package TableManaging;

import java.sql.*;
import java.util.ArrayList;
import java.util.function.Predicate;
import TableManaging.Parsers.Parser;

public class TableDB<T> implements Table<T> {
    private final Connection conn;
    private final String tableName;
    private final Parser<T> parser;

    public TableDB(String tableName, Parser<T> parser) throws SQLException {
        this.conn = DBConnectionManager.getConnection();
        this.tableName = tableName;
        this.parser = parser;
    }

    @Override
    public ArrayList<T> fetchRows(Predicate<T> condition) {
        var rows = new ArrayList<T>();
        String sql = "SELECT * FROM " + tableName;
        try (var stmt = conn.createStatement();
             var rs   = stmt.executeQuery(sql)) {
            var md = rs.getMetaData();
            int cols = md.getColumnCount();
            while (rs.next()) {
                // build a CSV string that Parser can consume:
                var sb = new StringBuilder();
                for (int i = 1; i <= cols; i++) {
                    sb.append(rs.getString(i) != null ? rs.getString(i) : "");
                    if (i < cols) sb.append(",");
                }
                T obj = parser.parseRow(sb.toString());
                if (condition == null || condition.test(obj)) {
                    rows.add(obj);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rows;
    }

    @Override
    public void insertRow(T row, boolean append) {
        String csv   = parser.toCSV(row);
        String[] parts = csv.split(",", -1);
        String placeholders = String.join(",", java.util.Collections.nCopies(parts.length, "?"));
        String sql = "INSERT INTO " + tableName + " VALUES (" + placeholders + ")";
        try (var ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < parts.length; i++) {
                ps.setString(i+1, parts[i]);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateRows(Predicate<T> condition, T newData) {
        throw new UnsupportedOperationException(
                "For updates, write DAO methods using explicit WHERE clauses."
        );
    }

    @Override
    public void deleteRows(Predicate<T> condition) {
        throw new UnsupportedOperationException(
                "For deletes, write DAO methods using explicit WHERE clauses."
        );
    }
}
