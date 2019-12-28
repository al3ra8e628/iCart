package appPackage.al3ra8e.icart;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import appPackage.al3ra8e.icart.connection.AccessLinks;
import appPackage.al3ra8e.icart.connection.RequestBuilder;
import appPackage.al3ra8e.icart.entities.Customer;
import appPackage.al3ra8e.icart.entities.HistoryElement;
import appPackage.al3ra8e.icart.util.Logging;
import appPackage.al3ra8e.icart.util.UrlBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    AppCompatActivity activity ;
    ListView historyList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().setTitle("History Page");
        initial() ;
        getHistoryForCustomer(new Logging(getApplicationContext()).getCustomer());
    }

    private void initial() {
        activity = this ;
        historyList = (ListView) findViewById(R.id.historyList);

    }
    private void getHistoryForCustomer(Customer customer){
        String url = new UrlBuilder(AccessLinks.GET_HISTORY_FOR_CUSTOMER).setUrlParameter("CUSTOMER_ID" , customer.getId()+"").getUrl() ;
        final ProgressDialog pg = ProgressDialog.show(activity , "Loading History" , "Loading ..." , true , false) ;
        new RequestBuilder()
                .setUrl(url)
                .setMethod(Request.Method.GET)
                .onResponse(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList<HistoryElement> history = new ArrayList<>();
                            JSONArray jsonArr = response.getJSONArray("History");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObject = jsonArr.getJSONObject(i);
                                HistoryElement temp = new HistoryElement() ; 
                                temp.setSocietyId(jsonObject.getInt("ID"));
                                temp.setCityName(jsonObject.getString("CITY_NAME"));
                                temp.setSocietyname(jsonObject.getString("SOCIETY"));
                                temp.setItemsNumber(jsonObject.getInt("NUMBER_OF_ITEMS"));
                                temp.setDate(jsonObject.getString("TRANSACTIONS_DATE"));
                                history.add(temp) ;
                            }
                            historyList.setAdapter(new HistoryAdapter(history));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pg.dismiss();
                    }
                })
                .onError(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pg.dismiss();
                        error.printStackTrace();
                    }
                })
                .execute();
    }

    private class HistoryAdapter extends BaseAdapter {

        ArrayList<HistoryElement> history ;
        public HistoryAdapter(ArrayList<HistoryElement> history){
            this.history = history ;
        }


        @Override
        public int getCount() {
            return history.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.society_history_item_view , null) ;
            TextView socName = (TextView) view.findViewById(R.id.soc_name);
            TextView date = (TextView) view.findViewById(R.id.date);
            TextView numberOfItems = (TextView) view.findViewById(R.id.number_of_items);
            //TextView cityName = (TextView) view.findViewById(R.id.city_name);


            socName.setText("Society : "+history.get(position).getSocietyname());
            date.setText("Date : "+history.get(position).getDate());
            numberOfItems.setText("Number Of Items : "+history.get(position).getItemsNumber()+"");
            //cityName.setText("City : "+history.get(position).getCityName());

            final ConstraintLayout hisBtn = (ConstraintLayout) view.findViewById(R.id.historyBtn);
            hisBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent = new Intent(activity , TransactionActivity.class) ;

                    intent.putExtra("societyId" , history.get(position).getSocietyId()+"") ;
                    intent.putExtra("date" , history.get(position).getDate()+"") ;

                    new RequestBuilder()
                            .setUrl(AccessLinks.TEST_CONNECTION).setMethod(Request.Method.GET)
                            .onResponse(new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    startActivity(intent);
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
            });
            return view;
        }
    }




}
