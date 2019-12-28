package appPackage.al3ra8e.icart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class LogIn extends AppCompatActivity {

    EditText email ;
    EditText password ;
    TextView regis ;
    AppCompatActivity activity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        if(new Logging(getApplicationContext()).getCustomer() != null){
            startActivity(new Intent(this, Home.class));
            this.finish();
        }
        initial() ;
    }

    private void initial() {
        activity = this ;
        email = (EditText) findViewById(R.id.cus_name);
        password = (EditText) findViewById(R.id.cus_conf_password);
        regis = (TextView) findViewById(R.id.regist);
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(activity , CreateAccountActivity.class) , 1234);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1234){
            if(resultCode == RESULT_OK){
                Log.e("result" , "OKAY") ;
                    activity.finish();
            }
        }
    }

    public void log_in(View view) {

            if(isEmailOKAY() && isPasswordRight()) {
                HashMap<String, String> params = new HashMap<>();
                params.put("EMAIL" , email.getText().toString()) ;
                params.put("PASSWORD" , password.getText().toString()) ;

                final ProgressDialog progressDialog =  ProgressDialog.show(activity , "login" , "loading ...." , true , false) ;

                new RequestBuilder()
                        .setMethod(Request.Method.POST)
                        .setUrl(new UrlBuilder(AccessLinks.LOG_IN).setUrlParameter(params).getUrl())
                        .onResponse(new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progressDialog.dismiss();
                                if(!response.has("response")){
                                    try {
                                        Customer temp = new Customer(response.getInt("ID") , response.getString("NAME") , response.getString("EMAIL")) ;
                                        new Logging(getApplicationContext()).saveCustomerInfo(temp) ;
                                        startActivity(new Intent(activity , Home.class));
                                        activity.finish();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    Toast.makeText(getApplicationContext() , "Email or password incorrect !" , Toast.LENGTH_LONG).show();
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

                        .executePost();

            }

    }

    private boolean isPasswordRight() {
        if(password.getText().equals("")){
            Toast.makeText(getApplicationContext() , "username or password incorrect" , Toast.LENGTH_SHORT).show();
            return false ;
        }

        String pass = password.getText().toString() ;
        if(pass.length() < 8){
            Toast.makeText(getApplicationContext() , "username or password incorrect" , Toast.LENGTH_SHORT).show();
            return false ;
        }
        return true ;
    }

    public boolean isEmailOKAY() {
        if(email.getText().equals("")){
            Toast.makeText(getApplicationContext() , "username or password incorrect" , Toast.LENGTH_SHORT).show();
            return false ;
        }
        String strEmail = email.getText().toString() ;
        if(!TextHelper.validateEmail(strEmail)){
            Toast.makeText(getApplicationContext() , "username or password incorrect" , Toast.LENGTH_SHORT).show();
            return false ;
        }
        return true ;

    }


}
