package appPackage.al3ra8e.icart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import appPackage.al3ra8e.icart.connection.AccessLinks;
import appPackage.al3ra8e.icart.connection.RequestBuilder;
import appPackage.al3ra8e.icart.util.Logging;

import org.json.JSONObject;


public class Home extends AppCompatActivity {
    AppCompatActivity activity ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //getSupportActionBar().setTitle("Home") ;
        activity = this ;
    }






    public void startAction(View view) {
            new RequestBuilder()
                    .setUrl(AccessLinks.TEST_CONNECTION).setMethod(Request.Method.GET)
                    .onResponse(new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            startActivity(new Intent(activity, StartActivity.class));
                        }
                    })
                    .onError(new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(getApplicationContext() , "no internet connection" , Toast.LENGTH_SHORT).show();
                        }
                    })
                    .execute();
    }



    public void historyAction(View view) {
        new RequestBuilder()
                .setUrl(AccessLinks.TEST_CONNECTION).setMethod(Request.Method.GET)
                .onResponse(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        startActivity(new Intent(activity , HistoryActivity.class));
                    }
                })
                .onError(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext() , "no internet connection" , Toast.LENGTH_SHORT).show();
                    }
                })
                .execute();

    }

    public void aboutUsAction(View view) {
        Intent intent = new Intent(this , AboutUs.class) ;
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void contact_us(View view) {

        View email = getLayoutInflater().inflate(R.layout.email_view , null) ;
        TextView showText = (TextView) email.findViewById(R.id.email);
        showText.setTextIsSelectable(true);


        AlertDialog alertDialog =
                new AlertDialog.Builder(this)
                                .setTitle("Contact Us")
                                .setView(email)
                                .setCancelable(true)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(getDrawable(R.drawable.app_icon)).show() ;
    }


    public void logoutAction(View view) {
        Log.e("i'm here" , "@_@") ;
        new  AlertDialog.Builder(this)
                .setTitle("logout")
                .setMessage("are you sure ?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Logging(getApplicationContext()).logout();
                        activity.finish();
                        startActivity(new Intent(activity , LogIn.class));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show() ;





    }

    public void developedByAction(View view) {
        Intent intent = new Intent(this , yabbaDabbaDoo.class) ;
        startActivity(intent);
    }
}
