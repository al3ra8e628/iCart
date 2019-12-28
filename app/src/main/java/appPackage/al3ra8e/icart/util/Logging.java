package appPackage.al3ra8e.icart.util;

import android.content.Context;
import android.content.SharedPreferences;

import appPackage.al3ra8e.icart.entities.Customer;



public class Logging {

    Context context ;

    public  Logging (Context context){
        this.context = context ;
    }

    public void saveCustomerInfo(Customer customer) {

        SharedPreferences sp = context.getSharedPreferences("LOGGED_IN" , context.MODE_PRIVATE) ;
        SharedPreferences.Editor spe = sp.edit() ;
        spe.putString(Keys.CUSTOMER_ID_KEY  , customer.getId()+"") ;
        spe.putString(Keys.CUSTOMER_NAME_KEY  , customer.getName()+"") ;
        spe.putString(Keys.CUSTOMER_EMAIL_KEY  , customer.getEmail()+"") ;
        spe.commit() ;

    }

    public Customer getCustomer() {
        SharedPreferences prefs = context.getSharedPreferences("LOGGED_IN" , context.MODE_PRIVATE);
        String  email = prefs.getString(Keys.CUSTOMER_EMAIL_KEY , null);
        if(email != null) {
            String name = prefs.getString(Keys.CUSTOMER_NAME_KEY, null);
            String id = prefs.getString(Keys.CUSTOMER_ID_KEY , "-1") ;
            return  new Customer(Integer.parseInt(id) , email , name) ;
        }
        return null ;
    }

    public void logout(){
        SharedPreferences sp = context.getSharedPreferences("LOGGED_IN" , context.MODE_PRIVATE) ;
        SharedPreferences.Editor spe = sp.edit() ;
        spe.remove(Keys.CUSTOMER_ID_KEY) ;
        spe.remove(Keys.CUSTOMER_NAME_KEY) ;
        spe.remove(Keys.CUSTOMER_EMAIL_KEY) ;
        spe.commit() ;
    }

}
