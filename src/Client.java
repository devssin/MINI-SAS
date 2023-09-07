import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Client {
    private int membershipId;
    private String name;
    private String phoneNumber ;

    public Client(int membershipId, String name, String phoneNumber) {
        this.membershipId = membershipId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public void add(Database db, Client client) throws SQLException {
        try{
            String selectQuery = "INSERT INTO client(name, phone_number) VALUES(?,?)";
            PreparedStatement stmt = db.query(selectQuery);
            db.bind(stmt, 1,  client.getName());
            db.bind(stmt, 2, client.getPhoneNumber());

            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Client Created successfully");
            }else {
                System.out.println("There was a problem in this operation");
            }
        }catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }



    @Override
    public String toString() {
        return "\n=========================== \n" +
                "name='" + name + '\'' +
                "\n, phoneNumber='" + phoneNumber + '\'' ;

    }
}
