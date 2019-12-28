package appPackage.al3ra8e.icart.connection;
public interface AccessLinks {

   //String DOMAIN ="192.168.8.102:82" ; //WIFI
   //String DOMAIN ="192.168.0.100" ; //Local
   String DOMAIN ="eazygollc.com" ; // GLOBAL

   String LOG_IN                       = "http://"+DOMAIN+"/icart/webService/logIn.php" ;
   String GET_HISTORY_FOR_CUSTOMER     = "http://"+DOMAIN+"/icart/webService/getHistoryForCustomer.php" ;
   String GET_TRANSACTIONS             = "http://"+DOMAIN+"/icart/webService/getTransactions.php" ;
   String GET_CITIES                   = "http://"+DOMAIN+"/icart/webService/getCities.php" ;
   String ADD_TRANSACTIONS             = "http://"+DOMAIN+"/icart/webService/addTransactions.php" ;
   String GET_SOCIETY_BY_CITY          = "http://"+DOMAIN+"/icart/webService/getSocietiesByCity.php" ;
   String GET_DEPARTMENTS_BY_SOC       = "http://"+DOMAIN+"/icart/webService/getDepartmentsBySociety.php" ;
   String GET_ITEMS_BY_DEPARTMENT      = "http://"+DOMAIN+"/icart/webService/getItemByDepartment.php" ;
   String ADD_CUTOMER                  = "http://"+DOMAIN+"/icart/webService/addCustomer.php" ;
   String TEST_CONNECTION              = "http://"+DOMAIN+"/icart/webService/testConnection.php" ;


}
