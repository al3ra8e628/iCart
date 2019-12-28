package appPackage.al3ra8e.icart.entities;

public class Transaction {

    private long transactionId ;
    private int times ;
    private String department ;
    private String society ;
    private String customer ;
    private String date ;
    private String item ;


    public Transaction() {
    }


    public Transaction(long transactionId, int times, String department, String society, String customer, String date, String item) {
        this.transactionId = transactionId;
        this.times = times;
        this.department = department;
        this.society = society;
        this.customer = customer;
        this.date = date;
        this.item = item;
    }


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
