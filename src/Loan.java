import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Loan {
    private int isbn;
    private int membershipId;
    private String loanDate;
    private String returnDate;

    private Boolean returned = false;


    public Loan() {
    }

    public Loan(int isbn, int membershipId, String loanDate, String returnDate, Boolean returned) {
        this.isbn = isbn;
        this.membershipId = membershipId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.returned = returned;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }


    public void borrow(Database db, Loan loan) throws SQLException{

        try {
            String selectQuery = "INSERT INTO loan(isbn, membership_id, loan_date, return_date) VALUES(?,?,?,?)";
            PreparedStatement stmt = db.query(selectQuery, Statement.RETURN_GENERATED_KEYS);
            db.bind(stmt, 1, loan.getIsbn());
            db.bind(stmt, 2, loan.getMembershipId());
            db.bind(stmt, 3, loan.getLoanDate());
            db.bind(stmt, 4, loan.getReturnDate());
            int rowsAffected = db.executeUpdate(stmt);
            if(rowsAffected > 0){
                System.out.println("Book has loaned successfully");
            }else{
                System.out.println("There was an error with this operation");
            }

        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }
    }

    public Boolean findLoan(Database db, int isbn, int membershipId) throws SQLException{
        Boolean found = false;
        try{
            String selectQuery = "SELECT * FROM loan WHERE isbn = ? AND membership_id = ? and returned = 0";
            PreparedStatement stmt = db.query(selectQuery, Statement.RETURN_GENERATED_KEYS);
            db.bind(stmt, 1, isbn);
            db.bind(stmt, 2, membershipId);
            ResultSet resultSet = db.executeQuery(stmt);
            if (resultSet.next()){
                found = true;
            }
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }

        return  found;
    }



    public void returnBook(Database db, int isbn, int membershipId) throws SQLException{
        try {
            String selectQuery = "UPDATE loan SET returned = true WHERE isbn = ? and membership_id = ?";
            PreparedStatement stmt = db.query(selectQuery, Statement.RETURN_GENERATED_KEYS);
            db.bind(stmt, 1, isbn);
            db.bind(stmt, 2, membershipId);
            int rowsAffected = db.executeUpdate(stmt);
            if (rowsAffected > 0){
                System.out.println("Book returned Successfully");

            }else{
                System.out.println("There is a problem with this operation");
            }
        }catch(SQLException exception){
            System.out.println(exception.getMessage());
        }
    }
}
