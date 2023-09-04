import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "1234";

    private Connection conn;

    public Database() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement query(String query) throws SQLException {
        return conn.prepareStatement(query);
    }

    public void bind(PreparedStatement stmt, int index, Object value) throws SQLException {
        if (value instanceof Integer) {
            stmt.setInt(index, (int) value);
        } else if (value instanceof Boolean) {
            stmt.setBoolean(index, (boolean) value);
        } else if (value instanceof String) {
            stmt.setString(index, (String) value);
        } else if (value == null) {
            stmt.setNull(index, java.sql.Types.NULL);
        } else {
            throw new IllegalArgumentException("Unsupported data type");
        }
    }

    public ResultSet executeQuery(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }

    public int executeUpdate(PreparedStatement stmt) throws SQLException {
        return stmt.executeUpdate();
    }

    public void commit() throws SQLException {
        conn.commit();
    }

    public void rollback() {
        try {
            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}