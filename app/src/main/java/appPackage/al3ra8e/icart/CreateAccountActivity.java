package appPackage.al3ra8e.icart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import appPackage.al3ra8e.icart.connection.AccessLinks;
import appPackage.al3ra8e.icart.connection.RequestBuilder;
import appPackage.al3ra8e.icart.entities.Customer;
import appPackage.al3ra8e.icart.util.Logging;
import appPackage.al3ra8e.icart.util.TextHelper;
import appPackage.al3ra8e.icart.util.UrlBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class CreateAccountActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText password;
    EditText confPassword;
    AppCompatActivity activity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        getSupportActionBar().setTitle("New Account");
        activity = this ;
        initial();
    }

    private void initial() {
        name = (EditText) findViewById(R.id.customer_name);
        email = (EditText) findViewById(R.id.cus_email);
        password = (EditText) findViewById(R.id.cus_password);
        confPassword = (EditText) findViewById(R.id.conf_password);
    }

    public void createNewCustomer(View view) {
        if(!name.getText().toString().equals("")){
        if(isEmailOKAY()){
            if(isPasswordRight()){
            HashMap<String , String> params = new HashMap<>() ;
            params.put("NAME" , ""+name.getText().toString()) ;
            params.put("EMAIL" , ""+email.getText().toString()) ;
            params.put("PASSWORD" , ""+password.getText().toString()) ;

            String url = new UrlBuilder(AccessLinks.ADD_CUTOMER).setUrlParameter(params).getUrl() ;

                final ProgressDialog progressDialog = ProgressDialog.show(activity , "Create Account" , "save data ..." , true , false) ;

            new RequestBuilder()
                    .setUrl(url)
                    .setMethod(Request.Method.GET)
                    .onResponse(new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int res = response.getInt("response") ;
                                switch (res) {
                                    case -1:
                                        Toast.makeText(getApplicationContext(), "This email already exists !", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 0:
                                        Toast.makeText(getApplicationContext(), "Something went wrong please try again !", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                                if(res > 0) {
                                    Intent intent = new Intent() ;
                                    intent.putExtra("res" , "1") ;
                                    setResult(Activity.RESULT_OK, intent);
                                    logIn(res);
                                    startActivity(new Intent(activity , Home.class));
                                    activity.finish();
                                }
                                progressDialog.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();

                            }
                        }
                    })
                    .onError(new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            progressDialog.dismiss();
                        }
                    })
                    .execute();

        }
        }}else{
            Toast.makeText(getApplicationContext() , "incorrect name" , Toast.LENGTH_SHORT).show();
        }


    }

    private void logIn(int res) {
        Customer customer = new Customer(res  , email.getText().toString() , name.getText().toString()) ;
        new Logging(getApplicationContext()).saveCustomerInfo(customer) ;
    }

    private boolean isPasswordRight() {
        if(password.getText().toString().equals("")){
            Toast.makeText(getApplicationContext() , "password cannot be empty" , Toast.LENGTH_SHORT).show();
            return false ;
        }

        if((!password.getText().toString().equals(confPassword.getText().toString()))){
            Toast.makeText(getApplicationContext() , "passwords not matches" , Toast.LENGTH_SHORT).show();
            return false ;
        }

        String pass = password.getText().toString() ;
        if(pass.length() < 8){
            Toast.makeText(getApplicationContext() , "password must be at least 8 characters" , Toast.LENGTH_SHORT).show();
            return false ;
        }
        return true ;
    }

    public boolean isEmailOKAY() {
        if(email.getText().toString().equals("")){
            Toast.makeText(getApplicationContext() , "email cannot be empty" , Toast.LENGTH_SHORT).show();
            return false ;
        }
        String strEmail = email.getText().toString() ;
        if(!TextHelper.validateEmail(strEmail)){
            Toast.makeText(getApplicationContext() , "incorrect email" , Toast.LENGTH_SHORT).show();
            return false ;
        }
        return true ;

    }
}
