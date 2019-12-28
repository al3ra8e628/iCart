package appPackage.al3ra8e.icart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import appPackage.al3ra8e.icart.connection.AccessLinks;
import appPackage.al3ra8e.icart.connection.RequestBuilder;
import appPackage.al3ra8e.icart.entities.City;
import appPackage.al3ra8e.icart.entities.Society;
import appPackage.al3ra8e.icart.util.UrlBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {
    ListView societiesListView ;
    AppCompatSpinner cities ;
    ArrayList<Society> societies ;
    ArrayList<City> citiesArray ;
    boolean checked ;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initial() ;
    }

    private void initial() {
        checked = false ;
        societiesListView = (ListView) findViewById(R.id.societies);
        cities = (AppCompatSpinner) findViewById(R.id.cities);
        getCities() ;


        cities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                new RequestBuilder()
                        .setUrl(AccessLinks.TEST_CONNECTION).setMethod(Request.Method.GET)
                        .onResponse(new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                checked = false ;
                                getSocieties(citiesArray.get(position).getCityId());
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
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void getSocieties(int cityId){

        final ProgressDialog progressDialog =  ProgressDialog.show(this , "Loading Data" , "loading ...." , true , false) ;

        new RequestBuilder()
                .setUrl(new UrlBuilder(AccessLinks.GET_SOCIETY_BY_CITY).setUrlParameter("CITY_ID" , cityId+"").getUrl())
                .setMethod(Request.Method.GET)
                .onResponse(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.has("response")) {
                            societiesListView.setAdapter(new SocietyAdapter(new ArrayList<Society>()));
                            progressDialog.dismiss();
                        }else{
                        try {
                            societies = new ArrayList<>();
                            JSONArray jsonArr = response.getJSONArray("Societies");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObject = jsonArr.getJSONObject(i);
                                Society temp = new Society();
                                temp.setId(jsonObject.getInt("SOCIETY_ID"));
                                temp.setName(jsonObject.getString("SOCIETY_NAME"));
                                temp.setNumberOfDepartment(jsonObject.getInt("number_of_department"));
                                societies.add(temp);
                                societiesListView.setAdapter(new SocietyAdapter(societies));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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



    public void getCities(){
        new RequestBuilder()
                .setUrl(AccessLinks.GET_CITIES)
                .setMethod(Request.Method.GET)
                .onResponse(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            citiesArray = new ArrayList<>();
                            JSONArray jsonArr = response.getJSONArray("Cities");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObject = jsonArr.getJSONObject(i);
                                City temp = new City() ;
                                temp.setCityId(jsonObject.getInt("CITY_ID"));
                                temp.setCityName(jsonObject.getString("CITY_NAME"));
                                temp.setNumberOfSocieties(jsonObject.getInt("NUMBER_OF_SOCIETIES"));
                                citiesArray.add(temp) ;
                                cities.setAdapter(new CitiesAdapter(citiesArray));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                })
                .onError(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
                .execute();
    }

    public void next(View view) {
        if(checked) {
            final Intent intent = new Intent(this, RankedItems.class);
            intent.putExtra("societyId", Society.getSelectedSociety().getId() + "");
            intent.putExtra("cityId" , citiesArray.get((int) cities.getSelectedItemId()).getCityId()+"") ;


            new RequestBuilder()
                    .setUrl(AccessLinks.TEST_CONNECTION).setMethod(Request.Method.GET)
                    .onResponse(new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            startActivityForResult(intent , 12);
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
        }else{
            Toast.makeText(getApplicationContext() , "select society !" , Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 12  && resultCode == RESULT_OK){
            this.finish();
        }
    }



    class SocietyAdapter extends BaseAdapter {
        ArrayList<CheckBox> checkBoxes ;
        ArrayList<Society> societies ;

        public SocietyAdapter (ArrayList<Society> societies) {
            this.societies = societies ;
            checkBoxes = new ArrayList<>() ;
        }

        @Override
        public int getCount() {
         return societies.size() ;
        }

        @Override
        public Object getItem(int position) {
            return  checkBoxes.get(position) ;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View view = getLayoutInflater().inflate(R.layout.spinner_element_item_view_associations ,  null) ;

            TextView societyName = (TextView) view.findViewById(R.id.superText);
            TextView departmentsNumber = (TextView) view.findViewById(R.id.subText);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            checkBoxes.add(checkBox) ;
            societyName.setText("Name : "+societies.get(position).getName());
            departmentsNumber.setText("departments : "+societies.get(position).getNumberOfDepartment());

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    for (int i = 0; i < societies.size() ; i++) {
                        CheckBox checkBox = (CheckBox) societiesListView.getAdapter().getItem(i);
                        checkBox.setChecked(false);
                    }
                    checkBox.setChecked(isChecked);
                    Society.setSelectedSociety(societies.get(position));
                    checked = isChecked ;
                }
            });
            return view ;
        }
    }


    class CitiesAdapter extends BaseAdapter {

        ArrayList<City> cities ;
        public CitiesAdapter (ArrayList<City> cities) {
            this.cities = cities ;
        }

        @Override
        public int getCount() {
            return cities.size() ;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.spinner_element_item_view_associations ,  null) ;
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            checkBox.setVisibility(View.INVISIBLE);
            TextView cityName = (TextView) view.findViewById(R.id.superText);
            TextView societies = (TextView) view.findViewById(R.id.subText);

            cityName.setText("City : "+cities.get(position).getCityName());
            societies.setText("Societies : "+cities.get(position).getNumberOfSocieties());

            return view ;
        }
    }

}
