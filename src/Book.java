import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Book {
    private int isbn;
    private String title;
    private String author ;
    private  int qte ;

    private Boolean status;

    public Book() {
    }

    public Book(int isbn, String title, String author, int qte, Boolean status) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.qte = qte;
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

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
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
                "\nqte=" + qte +
                "\nstatus=" + status +
                "\n=============================== \n";
    }


    public void create(Database db, Book book) throws SQLException {
        String insertedQuery = "INSERT INTO book(title, author, qte,status) values(?,?,?,?)";
        PreparedStatement stmt = db.query(insertedQuery);
        db.bind(stmt, 1, book.getTitle());
        db.bind(stmt, 2 , book.getAuthor());
        db.bind(stmt, 3 , book.getQte());
        db.bind(stmt , 4, getStatus());

        int affectedRows = stmt.executeUpdate();

        if(affectedRows <= 0){
            throw new SQLException("Insertion failed");
        }
    }

    public void list(Database db) throws SQLException {

        String selectQuery = "SELECT * FROM book";
        PreparedStatement stmt = db.query(selectQuery);
        ResultSet resultSet = db.executeQuery(stmt);

        while (resultSet.next()){
            Book bk = new Book();
            bk.setIsbn(resultSet.getInt("isbn"));
            bk.setTitle(resultSet.getString("title"));
            bk.setAuthor(resultSet.getString("author"));
            bk.setQte(resultSet.getInt("qte"));
            bk.setStatus(resultSet.getBoolean("status"));

            System.out.println(bk.toString());
        }
    }



    public Book findByIsbn(Database db, int isbn) throws SQLException {
        Book bk = null ;

        String selectQuery = "SELECT * FROM book WHERE isbn = ?";
        PreparedStatement stmt = db.query(selectQuery);
        db.bind(stmt, 1, isbn);

        ResultSet resultSet = stmt.executeQuery();

        if(resultSet.next()){
            int _isbn = resultSet.getInt("isbn");resultSet.getInt("isbn");
            String _title = resultSet.getString("title");
            String _author = resultSet.getString("author");
            int _qte = resultSet.getInt("qte");
            Boolean _status = resultSet.getBoolean("status");
            bk = new Book(_isbn, _title, _author, _qte, _status);
        }

        return bk;


    }











}
