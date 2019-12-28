package appPackage.al3ra8e.icart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import appPackage.al3ra8e.icart.connection.AccessLinks;
import appPackage.al3ra8e.icart.connection.RequestBuilder;
import appPackage.al3ra8e.icart.entities.Department;
import appPackage.al3ra8e.icart.entities.Item;
import appPackage.al3ra8e.icart.entities.TransactionalItem;
import appPackage.al3ra8e.icart.util.UrlBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class RankedItems extends AppCompatActivity {
    ListView items ;
    AppCompatSpinner departments ;
    String societyId ;
    String cityId ;

    ArrayList<Department> departmentsArray ;
    ArrayList<Item> itemsArray  ;
    ArrayList<TransactionalItem> selectedItems ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranked_items);
        selectedItems = new ArrayList<>() ;
        initial() ;
    }

    private void initial(){
        societyId = getIntent().getStringExtra("societyId") ;
        cityId = getIntent().getStringExtra("cityId") ;
        items = (ListView) findViewById(R.id.items);
        departments = (AppCompatSpinner) findViewById(R.id.departments);
        getDepartments();
        departments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                new RequestBuilder()
                        .setUrl(AccessLinks.TEST_CONNECTION).setMethod(Request.Method.GET)
                        .onResponse(new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                getItems(departmentsArray.get(position).getId());
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
    public void getDepartments(){
        HashMap<String , String> params = new HashMap<>() ;
        params.put("CITY_ID" , ""+cityId);
        params.put("SOCIETY_ID" , ""+societyId);
        String url = new UrlBuilder(AccessLinks.GET_DEPARTMENTS_BY_SOC).setUrlParameter(params).getUrl() ;

        new RequestBuilder()
                .setUrl(url)
                .setMethod(Request.Method.GET)
                .onResponse(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            departmentsArray = new ArrayList<>() ;
                            JSONArray jsonArr = response.getJSONArray("Departments");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObject = jsonArr.getJSONObject(i);
                                Department temp = new Department();
                                temp.setDepartmentDetails(jsonObject.getString("DETAILS"));
                                temp.setId(jsonObject.getInt("DEPARTMENT_ID"));
                                temp.setName(jsonObject.getString("DEPARTMENT_NAME"));
                                temp.setNumberOfItems(jsonObject.getInt("NUMBER_OF_ITEMS"));
                                departmentsArray.add(temp) ;
                                departments.setAdapter(new DepartmentAdapter(departmentsArray));
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

    public void getItems(final int departmentId) {
        HashMap<String , String> params = new HashMap<>() ;
        params.put("CITY_ID" , ""+cityId);
        params.put("SOCIETY_ID" , ""+societyId);
        params.put("DEPARTMENT_ID" , ""+departmentId);
        String url = new UrlBuilder(AccessLinks.GET_ITEMS_BY_DEPARTMENT).setUrlParameter(params).getUrl() ;

        final ProgressDialog progressDialog =  ProgressDialog.show(this , "Loading Data" , "loading ...." , true , false) ;

        new RequestBuilder()
                .setUrl(url)
                .setMethod(Request.Method.GET)
                .onResponse(new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.has("response")){
                            items.setAdapter(new ItemsAdapter(new ArrayList<Item>()));
                            progressDialog.dismiss();
                        }else{
                        try {
                            itemsArray = new ArrayList<>() ;
                            JSONArray jsonArr = response.getJSONArray("Items");
                            for (int i = 0; i < jsonArr.length(); i++) {
                                JSONObject jsonObject = jsonArr.getJSONObject(i);
                                Item temp = new Item() ;
                                temp.setDepartmentId(departmentId);
                                temp.setDepartmentPriority(jsonObject.getInt("DEPARTMENT_PRIORITY"));
                                temp.setItemId(jsonObject.getInt("ITEM_ID"));
                                temp.setItemName(jsonObject.getString("ITEM_NAME"));
                                temp.setPrice(jsonObject.getString("ITEM_PRICE"));
                                //temp.setDetails(jsonObject.getString("ITEM_DETAILS"));
                                itemsArray.add(temp) ;
                                items.setAdapter(new ItemsAdapter(itemsArray));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }}
                })
                .onError(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();
                    }
                })
                .execute();
    }

    public void purchase(View view) {
        if(selectedItems.size() > 0) {
            final Intent intent = new Intent(this, BeforeShoping.class);
            Bundle bundle = new Bundle();


            Collections.sort(selectedItems, new Comparator<TransactionalItem>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public int compare(TransactionalItem first, TransactionalItem second) {
                    if(first.getDepartmentPriority() != second.getDepartmentPriority()){
                        return Integer.valueOf(first.getDepartmentPriority()).compareTo(second.getDepartmentPriority());
                    }else{
                        return Integer.valueOf(first.getDepartmentId()).compareTo(second.getDepartmentId());
                    }
                }
            }) ;

            bundle.putSerializable("transactions", selectedItems);
            bundle.putSerializable("departments", departmentsArray);
            intent.putExtras(bundle);

            new RequestBuilder()
                    .setUrl(AccessLinks.TEST_CONNECTION).setMethod(Request.Method.GET)
                    .onResponse(new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            startActivityForResult(intent , 123);
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
            Toast.makeText(getApplicationContext() , " no items selected " , Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK){
            setResult(RESULT_OK , new Intent());
            this.finish();
        }

    }

    class DepartmentAdapter extends BaseAdapter {
        ArrayList<Department> departments ;
        public DepartmentAdapter (ArrayList<Department> departments) {
            this.departments = departments ;
        }

        @Override
        public int getCount() {
            return departments.size() ;
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
            View view = getLayoutInflater().inflate(R.layout.spinner_element_item_view_departments ,  null) ;
            TextView departmentName = (TextView) view.findViewById(R.id.superText);
            TextView items = (TextView) view.findViewById(R.id.subText);
                departmentName.setText("Department : "+departments.get(position).getName());
                items.setText("Items : "+departments.get(position).getNumberOfItems());
            return view ;
        }
    }


    class ItemsAdapter extends BaseAdapter {

        ArrayList<Item> items ;
        public ItemsAdapter (ArrayList<Item> items) {
            this.items = items ;
        }

        @Override
        public int getCount() {
            return items.size() ;
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
            final View view = getLayoutInflater().inflate(R.layout.spinner_element_item_view ,  null) ;
            final TextView itemName = (TextView) view.findViewById(R.id.superText);
            TextView price = (TextView) view.findViewById(R.id.subText);
            itemName.setText("Item : "+items.get(position).getItemName());
            price.setText("Price : "+items.get(position).getPrice());
            final ImageButton checkBox = (ImageButton) view.findViewById(R.id.checkBox);

            final TransactionalItem tItem = new TransactionalItem() ;
            tItem.setCityId(Integer.parseInt(cityId));
            tItem.setDepartmentPriority(items.get(position).getDepartmentPriority());
            tItem.setPrice(items.get(position).getPrice());
            tItem.setItemName(items.get(position).getItemName());
            tItem.setDepartmentId(items.get(position).getDepartmentId());
            tItem.setItemId(items.get(position).getItemId());
            tItem.setSocietyId(Integer.parseInt(societyId));

            if(selectedItems.contains(tItem)){
                checkBox.setImageDrawable(getDrawable(R.drawable.selected_item));
            }

            checkBox.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    if(!selectedItems.contains(tItem)){
                        selectedItems.add(tItem) ;
                        checkBox.setImageDrawable(getDrawable(R.drawable.selected_item));
                    }else{
                        selectedItems.remove(tItem) ;
                        checkBox.setImageDrawable(getDrawable(R.drawable.unselected_item));
                    }
                }
            }
            );

            return view ;
        }
    }


}
