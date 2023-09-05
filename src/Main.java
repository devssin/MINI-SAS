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
//        book.setQte(scanner.nextInt());
//        scanner.nextLine();
//        Boolean isAvailabale = book.getQte() > 0 ;
//        book.setStatus(isAvailabale);
//
//        book.create(db, book);
//
//        book.list(db);


        System.out.println("Enter Book isbn");
        int isbn = scanner.nextInt();
        scanner.nextLine();

        Book searchedBook = book.findByIsbn(db, isbn);

        if(searchedBook == null){
            System.out.println("Sorry we cannot find this book");
        }else{
            System.out.println(book.toString());
            System.out.println("Do you really want to delete this book ?");
            System.out.println("1- Yes \t 2- No");


            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                book.delete(db, isbn);
            }
        }

//        System.out.println("Enter the book name or its author's name");
//        String query = scanner.nextLine();

//        book.search(db, query);

 }
}

