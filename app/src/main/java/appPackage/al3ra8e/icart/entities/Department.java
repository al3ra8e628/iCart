package appPackage.al3ra8e.icart.entities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

public class Department implements Serializable{

    private int id;
    private int society_id;
    private String name;
    private int numberOfItems;
    private String departmentDetails ;
    TextView departmentView ;




    public Department() {
    }

    public Department(int id) {
        this.id = id;
    }

    public Department(int id, int society_id, String name, int numberOfItems) {
        this.id = id;
        this.society_id = society_id;
        this.name = name;
        this.numberOfItems = numberOfItems;
    }

    public String getDepartmentDetails() {
        return departmentDetails;
    }

    public void setDepartmentDetails(String departmentDetails) {
        this.departmentDetails = departmentDetails;
    }

    public TextView getDepartmentView() {
        return departmentView;
    }

    public void setDepartmentView(TextView departmentView , final AppCompatActivity activity) {
        this.departmentView = departmentView;


        this.getDepartmentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new AlertDialog.Builder(activity)
                       .setTitle("Department Details")
                       .setMessage(getName()+" : "+getDepartmentDetails())
                       .setCancelable(true)
                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                           }
                       })
                       .show() ;
            }
        });

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSociety_id() {
        return society_id;
    }

    public void setSociety_id(int society_id) {
        this.society_id = society_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    @Override
    public boolean equals(Object obj) {
        Department department = (Department) obj  ;
        return this.getId() == department.getId();
    }
}