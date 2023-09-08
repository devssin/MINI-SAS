import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        Book book = new Book();
        Database db = new Database();



//        System.out.println("Enter the name of the book");
//        book.setTitle(scanner.nextLine());
//        System.out.println("Enter the name of the book");
//        book.setAuthor(scanner.nextLine());
//        System.out.println("Enter the qte");
//        book.setQuantity(scanner.nextInt());
//        scanner.nextLine();
//        Boolean isAvailable = book.getQuantity() > 0 ;
//        book.setStatus(isAvailable);
//
//        book.create(db, book);


//
//        book.list(db);


//        System.out.println("Enter Book isbn");
//        int isbn = scanner.nextInt();
//        scanner.nextLine();
//
//        Book searchedBook = book.findByIsbn(db, isbn);
//
//        if(searchedBook == null){
//            System.out.println("Sorry we cannot find this book");
//        }else{
//            System.out.println(searchedBook.toString());
//
//            System.out.println("Enter the updated name (press enter to leave it as before)");
//            String updatedTitle = scanner.nextLine();
//            if (!updatedTitle.isEmpty()){
//                searchedBook.setTitle(updatedTitle);
//            }
//            System.out.println("Enter the updated author name (press enter to leave it as it is)");
//            String updatedAuthor = scanner.nextLine();
//            if (!updatedAuthor.isEmpty()){
//                searchedBook.setAuthor(updatedAuthor);
//            }
//            System.out.println("Enter the updated quantity (enter -1 to leave it as it is)");
//            int updatedQuantity = Integer.parseInt(scanner.nextLine());
//
//
//
//            if (updatedQuantity != -1){
//                searchedBook.setQuantity(updatedQuantity);
//            }
//            searchedBook.update(db, searchedBook);
//
//        }

//        System.out.println("Enter the book name or its author's name");
//        String query = scanner.nextLine();

//        book.search(db, query);


        // loan book
//        System.out.println("Enter the book isbn");
//        int _isbn = scanner.nextInt();
//        scanner.nextLine();
//        System.out.println("Enter the client membership Id");
//        int _membershipId = scanner.nextInt();
//        scanner.nextLine();
//        System.out.println("Enter Loan date (yyyy-mm-dd)");
//        String _loanDate = scanner.nextLine();
//        System.out.println("Enter Return date (yyyy-mm-dd)");
//        String _returnDate = scanner.nextLine();
//
//        Loan _loan = new Loan(_isbn, _membershipId, _loanDate, _returnDate, false);
//
//        _loan.borrow(db, _loan);

//        loanBook();
//        returnBook();

        loanBook();


    }


    public static void loanBook() throws SQLException {
        Database db = new Database();
        Book book = new Book();
        Client client = new Client();
        Loan loan = new Loan();
        int _membershipId = 0;



//        if (book.findByIsbn(db, _isbn) != null) {
//            System.out.println("Enter the client membership Id");
//            int _membershipId = scanner.nextInt();
//            scanner.nextLine();
//            if (client.findByMembershipId(db, _membershipId) != null) {
//                if (!loan.findLoan(db, _isbn, _membershipId)) {
//                    System.out.println("Enter Loan date (yyyy-mm-dd)");
//                    String _loanDate = scanner.nextLine();
//                    System.out.println("Enter Return date (yyyy-mm-dd)");
//                    String _returnDate = scanner.nextLine();
//                    loan = new Loan(_isbn, _membershipId, _loanDate, _returnDate, false);
//                    loan.borrow(db, loan);
//                } else {
//                    System.out.println("This client has already borrowed this bock and he hasn't return it yet");
//                }
//
//            } else {
//                System.out.println("Client not found ");
//
//            }
//
//
//        } else {
//            System.out.println("Book not found ");
//        }
        Boolean isValid = true;
        do{
            System.out.println("1-Loan to an existed client : \t 1-Loan to a new client");
            int loanTo = scanner.nextInt();
            scanner.nextLine();
            switch (loanTo) {
                case 1:
                    System.out.println("Enter the client membership Id");
                    _membershipId = scanner.nextInt();
                    scanner.nextLine();
                    isValid = true;
                    break;
                case 2:
                    _membershipId = addClient();
                    isValid = true;
                    break;
                default:
                    System.out.println("invalid input");
                    isValid = false;
                    break;
            }
        }while (!isValid);

        if(client.findByMembershipId(db, _membershipId) == null){
            System.out.println("Client not found");
            return;
        }

        System.out.println("Enter the book isbn");
        int _isbn = scanner.nextInt();
        scanner.nextLine();
        if(book.findByIsbn(db, _isbn) == null){
            System.out.println("Book not found");
            return;
        }

        if(loan.findLoan(db, _isbn,_membershipId)){
            System.out.println("This client has already borrowed this book and he hasn't return it yet");
            return;
        }
        System.out.println("Enter Loan date (yyyy-mm-dd)");
        String _loanDate = scanner.nextLine();
        System.out.println("Enter Return date (yyyy-mm-dd)");
        String _returnDate = scanner.nextLine();
        loan = new Loan(_isbn, _membershipId, _loanDate, _returnDate, false);
        loan.borrow(db, loan);








    }



    public static int addClient() throws SQLException {
        Database db = new Database();
        Client client = new Client();

        System.out.println("Enter Client Name: ");
        client.setName(scanner.nextLine());
        System.out.println("Enter Client phone number :");
        client.setPhoneNumber(scanner.nextLine());

        client.add(db, client);

        return Client.lastInsertedId;

    }



    public static void returnBook() throws SQLException {
        Database db = new Database();
        Book book = new Book();
        Client client = new Client();
        Loan loan = new Loan();
        System.out.println("Enter Book Isbn");
        int _isbn = scanner.nextInt();
        scanner.nextLine();
        if (book.findByIsbn(db, _isbn) != null) {
            System.out.println("Enter Client Membership ID");
            int _membershipId = scanner.nextInt();
            scanner.nextLine();
            if (client.findByMembershipId(db, _membershipId) != null) {

                if (loan.findLoan(db, _isbn, _membershipId)) {
                    loan.returnBook(db, _isbn, _membershipId);
                } else {
                    System.out.println("No loan was found");
                }

            }


        } else {
            System.out.println("Book Not Found");
        }


    }

}

