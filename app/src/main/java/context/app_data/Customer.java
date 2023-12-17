package context.app_data;

public class Customer {
    private int Id;
    private String Name;
    private String UserName;
    private String Password;
    private int StatusId;

    public Customer(int id, String name, String userName, String password, int statusId) {
        Id = id;
        Name = name;
        UserName = userName;
        Password = password;
        StatusId = statusId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }
}
