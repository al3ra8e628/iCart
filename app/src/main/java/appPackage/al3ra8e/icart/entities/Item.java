package appPackage.al3ra8e.icart.entities;


public class Item {
    private int itemId ;
    private int departmentId ;
    private int societyId ;
    private String itemName ;
    private String price ;
    private String details;

    private int departmentPriority ;


    public Item() {

    }


    public Item(String price , String itemName) {
        this.itemName = itemName;
        this.price = price;

    }

    public Item(int itemId, int departmentId, int societyId, String itemName , String price) {
        this.itemId = itemId;
        this.departmentId = departmentId;
        this.societyId = societyId;
        this.itemName = itemName;
        this.price = price ;
    }


    public int getDepartmentPriority() {
        return departmentPriority;
    }

    public void setDepartmentPriority(int departmentPriority) {
        this.departmentPriority = departmentPriority;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getSocietyId() {
        return societyId;
    }

    public void setSocietyId(int societyId) {
        this.societyId = societyId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
