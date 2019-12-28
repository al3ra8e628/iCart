package appPackage.al3ra8e.icart;
import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import appPackage.al3ra8e.icart.connection.AccessLinks;
import appPackage.al3ra8e.icart.connection.RequestBuilder;
import appPackage.al3ra8e.icart.entities.Transaction;
import appPackage.al3ra8e.icart.util.Logging;
import appPackage.al3ra8e.icart.util.UrlBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class TransactionActivity extends AppCompatActivity {
    AppCompatActivity activity ;
    String societyId ;
    String date ;
    LinearLayout transactionsContainer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        getSupportActionBar().setTitle("History Transactions Table");
        initial() ;
        getData() ;
    }

    public void getData() {
        HashMap<String , String> params = new HashMap<>() ;
        params.put("CUSTOMER_ID" , new Logging(getApplicationContext()).getCustomer().getId()+"") ;
        params.put("SOCIETY_ID" , societyId) ;
        params.put("DATE" , date) ;

        String url = new UrlBuilder(AccessLinks.GET_TRANSACTIONS).setUrlParameter(params).getUrl() ;

        final ProgressDialog progressDialog =  ProgressDialog.show(this , "Loading Transactions" , "loading ...." , true , false) ;


        new RequestBuilder()
                .setMethod(Request.Method.GET)
                .setUrl(url)
                .onResponse(new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList<Transaction> transactions = new ArrayList<>();
                            JSONArray jsonArr = response.getJSONArray("Transactions");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObject = jsonArr.getJSONObject(i);
                                Transaction temp = new Transaction() ;
                                temp.setItem(jsonObject.getString("item_name"));
                                temp.setDepartment(jsonObject.getString("department_name"));
                                temp.setTimes(jsonObject.getInt("times"));
                                transactions.add(temp) ;
                            }

                            fillIntoTable(transactions);
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                })
                .onError(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext() , "no internet connection" , Toast.LENGTH_SHORT).show(); ;
                        error.printStackTrace();
                    }
                })
                .execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void fillIntoTable(ArrayList<Transaction> transactions){

        int i = 0  ;
        for(Transaction transaction : transactions) {
            i++ ;
            View view = getLayoutInflater().inflate(R.layout.transaction_table_element, null);
            TextView department = (TextView) view.findViewById(R.id.department);
            TextView item = (TextView) view.findViewById(R.id.item);
            TextView times = (TextView) view.findViewById(R.id.times);

            department.setText(transaction.getDepartment()+"");
            item.setText(transaction.getItem()+"");
            times.setText(transaction.getTimes()+"");

            LinearLayout layout = (LinearLayout) view.findViewById(R.id.transactionLayout);

            if(i % 2 == 0){
                layout.setBackgroundDrawable(getDrawable(R.color.even_element));
            }else{
                layout.setBackgroundDrawable(getDrawable(R.color.odd_element));
                department.setTextColor(getResources().getColor(R.color.even_element));
                item.setTextColor(getResources().getColor(R.color.even_element));
                times.setTextColor(getResources().getColor(R.color.even_element));
            }
            transactionsContainer.addView(view);
        }

    }

    private void initial() {
        activity = this ;
        societyId = getIntent().getStringExtra("societyId") ;
        date = getIntent().getStringExtra("date") ;
        transactionsContainer = (LinearLayout) findViewById(R.id.transactionsContainer);
    }


}
