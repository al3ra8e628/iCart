package appPackage.al3ra8e.icart.entities;


public class HistoryElement {
    private int societyId ;
    private String societyname ;
    private int itemsNumber ;
    private String date ;
    private String cityName ;
    public HistoryElement() {
    }

    public HistoryElement(int societyId, String societyname, int itemsNumber, String date) {
        this.societyId = societyId;
        this.societyname = societyname;
        this.itemsNumber = itemsNumber;
        this.date = date;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getSocietyId() {
        return societyId;
    }

    public void setSocietyId(int societyId) {
        this.societyId = societyId;
    }

    public String getSocietyname() {
        return societyname;
    }

    public void setSocietyname(String societyname) {
        this.societyname = societyname;
    }

    public int getItemsNumber() {
        return itemsNumber;
    }

    public void setItemsNumber(int itemsNumber) {
        this.itemsNumber = itemsNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
