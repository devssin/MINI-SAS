import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Database db = new Database();

    public static void main(String[] args) throws SQLException {

        int choice = 0;

        System.out.println("==================== Welcome ========================");
        do {
            choice = menu();
            scanner.nextLine();

            switch (choice){
                case 1:
                    addBook();
                    break;
                case 2 :
                    listBooks();
                    break;
                case 3:
                    findBookByIsbn();
                    break;
                case 4:
                    search();
                    break;
                case 5 :
                    update();
                    break;
                case 6:
                    delete();
                    break;
                case 7:
                    loanBook();
                    break;
                case 8:
                    returnBook();
                    break;
                case 9:
                    statistics();
                    break;
                case 0:
                    System.out.println("Thanks for using our app ❤️");
                    System.exit(0);
                    break;




            }
            System.out.println("============================================ \n");
        }while (choice != 0);



    }

    public static void listBooks() throws SQLException {
        Book _book= new Book();
        _book.list(db);
        scanner.nextLine();

    }



    public static void addBook() throws SQLException {

        System.out.println("Enter book title");
        String _title = scanner.nextLine();
        System.out.println("Enter book author");
        String _author = scanner.nextLine();
        System.out.println("Enter quantity");
        int _quantity = scanner.nextInt();
        Boolean _status = _quantity > 0;
        Book _book = new Book();
        _book.setTitle(_title);
        _book.setAuthor(_author);
        _book.setQuantity(_quantity);
        _book.setStatus(_status);

        _book.create(db , _book);

        scanner.nextLine();
    }

    public static void findBookByIsbn() throws SQLException {

        Book _book = new Book();

        System.out.println("Enter book isbn");
        int _isbn = scanner.nextInt();
        scanner.nextLine();
        _book = _book.findByIsbn(db,_isbn);

        if (_book != null){
            System.out.println(_book.toString());

        }else {
            System.out.println("Book not found");
        }
        scanner.nextLine();
    }

    public static void search() throws SQLException {
        Book _book = new Book();
        System.out.println("Enter book author or name");
        String _searchedBook = scanner.nextLine();
        _book.search(db, _searchedBook);
        scanner.nextLine();
    }

    public static void update() throws SQLException {
        Book _book = new Book();

        System.out.println("Enter book isbn");
        int _isbn = scanner.nextInt();
        scanner.nextLine();
        _book = _book.findByIsbn(db,_isbn);

        if (_book != null){
            System.out.println(_book.toString());
            System.out.println("Enter the updated name (press enter to leave it as before)");
            String updatedTitle = scanner.nextLine();
            if (!updatedTitle.isEmpty()){
                _book.setTitle(updatedTitle);
            }
            System.out.println("Enter the updated author name (press enter to leave it as it is)");
            String updatedAuthor = scanner.nextLine();
            if (!updatedAuthor.isEmpty()){
                _book.setAuthor(updatedAuthor);
            }
            System.out.println("Enter the updated quantity (enter -1 to leave it as it is)");
            int updatedQuantity = Integer.parseInt(scanner.nextLine());
            if (updatedQuantity != -1){
                _book.setQuantity(updatedQuantity);
            }
            _book.update(db, _book);


        }else {
            System.out.println("Book not found");
        }

        scanner.nextLine();





    }


    public  static void delete() throws SQLException {
        Book _book = new Book();

        System.out.println("Enter book isbn");
        int _isbn = scanner.nextInt();
        scanner.nextLine();
        _book = _book.findByIsbn(db,_isbn);

        if (_book == null){

            System.out.println("Book not found");
            return;
        }
        System.out.println(_book.toString());
        System.out.println("Press 1 to confirm delete , press any button to cancel ");

        String choice = scanner.nextLine();

        if (Objects.equals(choice, "1")){
            _book.delete(db, _isbn);
        }
    }







    public static void loanBook() throws SQLException {
        Database db = new Database();
        Book book = new Book();
        Client client = new Client();
        Loan loan = new Loan();
        int _membershipId = 0;
        boolean isValid = true;
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



    public static int menu(){
        System.out.println("1- Add new book :");
        System.out.println("2- List of available books :");
        System.out.println("3- Search Book by isbn:");
        System.out.println("4- Search Book by name or author:");
        System.out.println("5- Update a book:");
        System.out.println("6- Delete a book:");
        System.out.println("7- Borrow a book:");
        System.out.println("8- Return a book:");
        System.out.println("9- Statistics Report :" );
        System.out.println("0- Exit :" );
        System.out.println("Chose an operation");
        return scanner.nextInt();
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

    public static void statistics() throws SQLException{
        Book _book = new Book();
        Loan _loan = new Loan();

        // Specify the file path
        String filePath = "statistics.txt";

        try {
            // Create a FileWriter with the specified file path
            FileWriter fileWriter = new FileWriter(filePath);

            // Create a BufferedWriter to write to the file
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the data to the file
            bufferedWriter.write(" Loaned book :" + _loan.getLoanedBooks(db));
            bufferedWriter.newLine();
            bufferedWriter.write("Lost books : " + _loan.getLostBooks(db));


            // Close the BufferedWriter (this will also close the FileWriter)
            bufferedWriter.close();

            System.out.println("Statistics has been written to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

