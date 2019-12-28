package appPackage.al3ra8e.icart.entities;


public class City {
    private int cityId ;
    private String cityName ;
    private int numberOfSocieties ;


    public City() {
    }

    public City(int cityId, String cityName, int numberOfSocieties) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.numberOfSocieties = numberOfSocieties;
    }


    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getNumberOfSocieties() {
        return numberOfSocieties;
    }

    public void setNumberOfSocieties(int numberOfSocieties) {
        this.numberOfSocieties = numberOfSocieties;
    }
}
