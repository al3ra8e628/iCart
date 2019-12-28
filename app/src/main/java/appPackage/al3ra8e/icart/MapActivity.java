package appPackage.al3ra8e.icart;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import appPackage.al3ra8e.icart.entities.Department;
import appPackage.al3ra8e.icart.entities.TransactionalItem;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
    ArrayList<TransactionalItem> transactions ;
    ArrayList<Department> departments ;
    LinearLayout map  ;

    TextView itemName;
    TextView transactionNumber ;

    AppCompatActivity activity ;

    int currentTransaction  ;

    double numOfRows ;
    double elementInLast ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        activity = this ;
        initial() ;
        createMap() ;

        transactionNumber.setText("item number : ");
        itemName.setText("item name : ");

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createMap() {
        map.removeAllViews();
        int j = 0 ;
        for(int i = 1 ; i <= numOfRows ; i++){
            View view  = getLayoutInflater().inflate(R.layout.map_view_element , null) ;

            TextView departmentLeft = (TextView) view.findViewById(R.id.departmentLeft);
            TextView departmentCenter = (TextView) view.findViewById(R.id.departmentCenter);
            TextView departmentRight = (TextView) view.findViewById(R.id.departmentRight);
            ImageView arrowLeft = (ImageView) view.findViewById(R.id.arrowLeft);
            ImageView arrowCenter = (ImageView) view.findViewById(R.id.arrowCenter);
            ImageView arrowRight = (ImageView) view.findViewById(R.id.arrowRight);

            if(numOfRows == 1){
                arrowRight.setVisibility(View.INVISIBLE);
            }

            if(i == numOfRows){
            if(elementInLast == 0) {
                departmentLeft.setText(departments.get(j).getName());
                departments.get(j).setDepartmentView(departmentLeft , this);
                departmentCenter.setText(departments.get(j + 1).getName());
                departments.get(j+1).setDepartmentView(departmentCenter, this);
                departmentRight.setText(departments.get(j + 2).getName());
                departments.get(j+2).setDepartmentView(departmentRight, this);
                arrowRight.setVisibility(View.INVISIBLE);
            }
            if(elementInLast == 2) {
                departmentLeft.setText(departments.get(j).getName());
                departments.get(j).setDepartmentView(departmentLeft, this);
                departmentCenter.setText(departments.get(j + 1).getName());
                departments.get(j+1).setDepartmentView(departmentCenter, this);
                departmentRight.setVisibility(View.INVISIBLE);
                arrowCenter.setVisibility(View.INVISIBLE);
                arrowRight.setVisibility(View.INVISIBLE);
            }
            if(elementInLast == 1) {
                departmentLeft.setText(departments.get(j).getName());
                departments.get(j).setDepartmentView(departmentLeft, this);
                departmentCenter.setVisibility(View.INVISIBLE);
                departmentRight.setVisibility(View.INVISIBLE);
                arrowLeft.setVisibility(View.INVISIBLE);
                arrowCenter.setVisibility(View.INVISIBLE);
                arrowRight.setVisibility(View.INVISIBLE);
            }
            }else{
                departmentLeft.setText(departments.get(j).getName());
                departments.get(j).setDepartmentView(departmentLeft, this);
                departmentCenter.setText(departments.get(j + 1).getName());
                departments.get(j + 1).setDepartmentView(departmentCenter, this);
                departmentRight.setText(departments.get(j + 2).getName());
                departments.get(j + 2).setDepartmentView(departmentRight, this);
            }
            map.addView(view);
            j+=3 ;
        }
    }




    private void initial() {
        currentTransaction = 0 ;
        transactionNumber = (TextView) findViewById(R.id.transactionNumber);
        itemName = (TextView) findViewById(R.id.itemName);
        Intent intent = getIntent() ;
        Bundle bundle = intent.getExtras() ;
        map = (LinearLayout) findViewById(R.id.map);
        transactions = (ArrayList<TransactionalItem>) bundle.getSerializable("transactions");


        departments  = (ArrayList<Department>) bundle.getSerializable("departments");

        double x =  departments.size() ;
        numOfRows = Math.ceil(x/3) ;
        elementInLast = x%3 ;

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void createNewMap(View view) {
        Button finish = (Button) findViewById(R.id.button2);
        if(currentTransaction <= transactions.size()-1) {
            finish.setText("next");

            transactionNumber.setText("item number : "+(currentTransaction+1)+"/"+transactions.size());
            itemName.setText("item name : "+transactions.get(currentTransaction).getItemName());

            int department =  transactions.get(currentTransaction).getDepartmentId() ;  //getDepartmentByTransaction();

            for(Department temp :  departments){
                if(temp.getId() == department){
                    if(temp.getDepartmentView() != null)
                    temp.getDepartmentView().setBackground(getDrawable(R.drawable.selected_department_background));
                }else {
                    if(temp.getDepartmentView() != null)
                    temp.getDepartmentView().setBackground(getDrawable(R.drawable.button_background));
                }
                }

            currentTransaction++;
        }else{
            finish.setText("finish");
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
        }
    }
}
