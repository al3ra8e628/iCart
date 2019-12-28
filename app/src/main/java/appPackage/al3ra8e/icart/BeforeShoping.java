package appPackage.al3ra8e.icart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import appPackage.al3ra8e.icart.connection.AccessLinks;
import appPackage.al3ra8e.icart.connection.AppController;
import appPackage.al3ra8e.icart.entities.Department;
import appPackage.al3ra8e.icart.entities.TransactionalItem;
import appPackage.al3ra8e.icart.util.DateFormat;
import appPackage.al3ra8e.icart.util.Logging;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BeforeShoping extends AppCompatActivity {
    ArrayList<TransactionalItem> transactions ;
    ArrayList<Department> departments ;

    LinearLayout transactionsContainer ;

    AppCompatActivity activity ;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_shoping);
        getSupportActionBar().setTitle("Ranked Items Table");
        activity = this ;
        initial() ;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initial() {

        transactionsContainer = (LinearLayout) findViewById(R.id.transactionsContainer);

        Intent intent = getIntent() ;
        Bundle bundle = intent.getExtras() ;

        transactions = (ArrayList<TransactionalItem>) bundle.getSerializable("transactions");
        departments  = (ArrayList<Department>) bundle.getSerializable("departments");
        printItems();

    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void printItems() {
        for(int i = 0 ; i< transactions.size() ; i++){
            View view  = getLayoutInflater().inflate(R.layout.transaction_table_element , null) ;

            TextView department = (TextView) view.findViewById(R.id.department);
            TextView item = (TextView) view.findViewById(R.id.item);
            TextView price = (TextView) view.findViewById(R.id.times);

            LinearLayout transactionLayout = (LinearLayout) view.findViewById(R.id.transactionLayout);

            String departmentName = departments.get(departments.indexOf(new Department(transactions.get(i).getDepartmentId()))).getName() ;
            department.setText(departmentName);
            item.setText(transactions.get(i).getItemName());
            price.setText(transactions.get(i).getPrice());

            if(i % 2 == 0){
                transactionLayout.setBackgroundDrawable(getDrawable(R.color.even_element));
            }else{
                transactionLayout.setBackgroundDrawable(getDrawable(R.color.odd_element));
                department.setTextColor(getResources().getColor(R.color.even_element));
                item.setTextColor(getResources().getColor(R.color.even_element));
                price.setTextColor(getResources().getColor(R.color.even_element));
            }
            transactionsContainer.addView(view);
        }

    }


    public void startShopping(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("transactions", transactions);
        bundle.putSerializable("departments", departments);
        intent.putExtras(bundle);


        saveTransaction(transactions , intent) ;

    }

    private void saveTransaction(ArrayList<TransactionalItem> transactions , final Intent intent){
        final JSONArray dataArray = new JSONArray() ;

        String date = new DateFormat(new Date()).getDefaultFormat() ;


        for(TransactionalItem temp : transactions){
            JSONObject dataTemp = new JSONObject() ;

            try {
                dataTemp.put("CUSTOMER_ID" , new Logging(this).getCustomer().getId()+"") ;
                dataTemp.put("CITY_ID" , ""+temp.getCityId()) ;
                dataTemp.put("ASSOCIATIONS_ID" , ""+temp.getSocietyId()) ;
                dataTemp.put("DEPARTMENT_ID" , ""+temp.getDepartmentId()) ;
                dataTemp.put("ITEM_ID" , ""+temp.getItemId()) ;
                dataTemp.put("TRANS_DATE" , ""+date) ;
                dataArray.put(dataTemp) ;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        final ProgressDialog progressDialog = ProgressDialog.show(this , "save transactions" ,  "save ..." , true , false) ;

        StringRequest request = new StringRequest(Request.Method.POST, AccessLinks.ADD_TRANSACTIONS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response" , response) ;
                        int state = Integer.parseInt(response) ;

                        switch (state){
                            case 0 :
                                /*error in inserting*/
                                Toast.makeText(getApplicationContext() , "try again please." , Toast.LENGTH_LONG).show();
                                break;
                            case 1 :
                                startActivity(intent);
                                setResult(RESULT_OK , new Intent());
                                activity.finish();
                                //activity.finish();
                                break;
                        }

                        progressDialog.dismiss();
                    }
                }
                , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext() , "pad internet Connection" , Toast.LENGTH_LONG).show();
                    }
                }
                ){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String , String> params = new HashMap<>() ;
                params.put("Transactions" , dataArray.toString()) ;
                return  params ;
            }
        } ;

        AppController.getInstance().addToRequestQueue(request);

    }



}
