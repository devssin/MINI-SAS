import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Book {
    private int isbn;
    private String title;
    private String author;
    int quantity;
    private Boolean status;
    public Book() {
    }

    public Book(int isbn, String title, String author, int quantity, Boolean status) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.status = status;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "===============================\n" +
                "isbn=" + isbn +
                "\ntitle='" + title + '\'' +
                "\nauthor='" + author + '\'' +
                "\nquantity=" + quantity +
                "\nstatus=" + status +
                "\n=============================== \n";
    }


    public void create(Database db, Book book) throws SQLException {
        String insertedQuery = "INSERT INTO book(title, author, quantity,status) values(?,?,?,?)";
        PreparedStatement stmt = db.query(insertedQuery);
        db.bind(stmt, 1, book.getTitle());
        db.bind(stmt, 2, book.getAuthor());
        db.bind(stmt, 3, book.getQuantity());
        db.bind(stmt, 4, getStatus());

        int affectedRows = stmt.executeUpdate();

        if (affectedRows <= 0) {
            throw new SQLException("Insertion failed");
        } else {
            System.out.println("the book was created successfully");
        }
    }

    public void list(Database db) throws SQLException {

        String selectQuery = "SELECT * FROM book WHERE status = true";
        PreparedStatement stmt = db.query(selectQuery);
        ResultSet resultSet = db.executeQuery(stmt);

        while (resultSet.next()) {
            Book bk = new Book();
            bk.setIsbn(resultSet.getInt("isbn"));
            bk.setTitle(resultSet.getString("title"));
            bk.setAuthor(resultSet.getString("author"));
            bk.setQuantity(resultSet.getInt("quantity"));
            bk.setStatus(resultSet.getBoolean("status"));

            System.out.println(bk.toString());
        }
    }


    public  Book findByIsbn(Database db, int isbn) throws SQLException {
        Book bk = null;

        String selectQuery = "SELECT * FROM book WHERE isbn = ?";
        PreparedStatement stmt = db.query(selectQuery);
        db.bind(stmt, 1, isbn);

        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            int _isbn = resultSet.getInt("isbn");
            String _title = resultSet.getString("title");
            String _author = resultSet.getString("author");
            int _quantity = resultSet.getInt("quantity");
            Boolean _status = resultSet.getBoolean("status");
            bk = new Book(_isbn, _title, _author, _quantity, _status);
        }


        return bk;
    }

    public void search(Database db, String query) throws SQLException {
        try {

            String selectQuery = "SELECT * FROM book WHERE title LIKE ? OR author LIKE ?";
            PreparedStatement stmt = db.query(selectQuery);
            db.bind(stmt, 1, "%" + query + "%");
            db.bind(stmt, 2, "%" + query + "%");
            ResultSet resultSet = stmt.executeQuery();


            if (resultSet.next()) {
                do {
                    Book bk = new Book();
                    bk.setIsbn(resultSet.getInt("isbn"));
                    bk.setTitle(resultSet.getString("title"));
                    bk.setAuthor(resultSet.getString("author"));
                    bk.setQuantity(resultSet.getInt("quantity"));
                    bk.setStatus(resultSet.getBoolean("status"));
                    System.out.println(bk.toString());

                } while (resultSet.next());
            } else {
                System.out.println("No book found");
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }


    }

    public void update(Database db, Book book) throws SQLException {
        try {
            String selectQuery = "Update book SET title = ? , author = ? , quantity = ? ,  status = ? WHERE isbn = ?";
            PreparedStatement stmt = db.query(selectQuery);
            db.bind(stmt, 1, book.getTitle());
            db.bind(stmt, 2, book.getAuthor());
            db.bind(stmt, 3, book.getQuantity());
            db.bind(stmt, 4, getStatus());
            db.bind(stmt, 5, book.getIsbn());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Book was updated successfully \nUpdate book : \n");
                System.out.println(book.toString());
            } else {
                System.out.println("There is a problem in this operation");
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }


    }


    public void delete(Database db, int isbn) throws SQLException {

        try {
            String selectQuery = "DELETE FROM book WHERE isbn = ?";
            PreparedStatement stmt = db.query(selectQuery);
            db.bind(stmt, 1, isbn);
            int rowsAffected = db.executeUpdate(stmt);
            System.out.println(rowsAffected > 0 ? "Delete successful " : "There was an error in this operation");
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

    }

    public int getAailableBooksCount(Database db) throws SQLException {

        String selectQuery = "SELECT COUNT(*) FROM book WHERE status = true";
        PreparedStatement stmt = db.query(selectQuery);
        ResultSet resultSet = db.executeQuery(stmt);
        int count = -1;
        if(resultSet.next()){
            count = resultSet.getInt(1);
        }
        return count;
    }

}
