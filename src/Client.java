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




}
