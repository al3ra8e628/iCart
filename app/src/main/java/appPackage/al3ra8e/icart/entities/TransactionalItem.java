package appPackage.al3ra8e.icart.entities;


import java.io.Serializable;

public class TransactionalItem implements Serializable{


    private int cityId ;
    private int societyId ;
    private int departmentId ;
    private int itemId ;
    private String price ;
    private String itemName ;
    private int departmentPriority  ;



    public TransactionalItem() {
    }



    public TransactionalItem(int cityId, int societyId, int departmentId, int itemId) {
        this.cityId = cityId;
        this.societyId = societyId;
        this.departmentId = departmentId;
        this.itemId = itemId;
    }


    public int getDepartmentPriority() {
        return departmentPriority;
    }

    public void setDepartmentPriority(int departmentPriority) {
        this.departmentPriority = departmentPriority;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getSocietyId() {
        return societyId;
    }

    public void setSocietyId(int societyId) {
        this.societyId = societyId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object obj) {
        TransactionalItem item = (TransactionalItem) obj ;
        return this.cityId == item.cityId && this.departmentId == item.departmentId && this.societyId == item.societyId && this.itemId == item.itemId;
    }

    public String getItemName() {
        return itemName ;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
