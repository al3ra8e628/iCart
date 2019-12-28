package appPackage.al3ra8e.icart.entities;

/**
 * Created by al3ra8e on 3/9/2018.
 */

public class Customer {
    private int id ;
    private String email ;
    private String name ;

    public Customer() {
    }

    public Customer(int id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
