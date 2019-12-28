package appPackage.al3ra8e.icart.entities;

public class Society {

    private int id;
    private String name;
    private String city;
    private int numberOfDepartment;

    public static Society selectedSociety;

    public Society() {
    }

    public Society(int id, String name, String city, int numberOfDepartment) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.numberOfDepartment = numberOfDepartment;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumberOfDepartment() {
        return numberOfDepartment;
    }

    public void setNumberOfDepartment(int numberOfDepartment) {
        this.numberOfDepartment = numberOfDepartment;
    }

    public static Society getSelectedSociety() {
        return selectedSociety;
    }

    public static void setSelectedSociety(Society selectedSociety) {
        Society.selectedSociety = selectedSociety;
    }
}