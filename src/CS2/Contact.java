package CS2;

public class Contact {
    private int id;
    private String name;
    private String address;
    private String phone;

    @Override
    public String toString() {
        return " "+ name + " " + phone + " "  + address;
    }
    public Contact(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}