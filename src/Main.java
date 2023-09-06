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


        System.out.println("Enter Book isbn");
        int isbn = scanner.nextInt();
        scanner.nextLine();

        Book searchedBook = book.findByIsbn(db, isbn);

        if(searchedBook == null){
            System.out.println("Sorry we cannot find this book");
        }else{
            System.out.println(searchedBook.toString());

            System.out.println("Enter the updated name (press enter to leave it as before)");
            String updatedTitle = scanner.nextLine();
            if (!updatedTitle.isEmpty()){
                searchedBook.setTitle(updatedTitle);
            }
            System.out.println("Enter the updated author name (press enter to leave it as it is)");
            String updatedAuthor = scanner.nextLine();
            if (!updatedAuthor.isEmpty()){
                searchedBook.setAuthor(updatedAuthor);
            }
            System.out.println("Enter the updated quantity (enter -1 to leave it as it is)");
            int updatedQuantity = Integer.parseInt(scanner.nextLine());



            if (updatedQuantity != -1){
                searchedBook.setQuantity(updatedQuantity);
            }
            searchedBook.update(db, searchedBook);

        }

//        System.out.println("Enter the book name or its author's name");
//        String query = scanner.nextLine();

//        book.search(db, query);

 }
}

