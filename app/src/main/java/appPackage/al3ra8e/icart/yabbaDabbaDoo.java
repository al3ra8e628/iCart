package appPackage.al3ra8e.icart;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class yabbaDabbaDoo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yabba_dabba_doo);
        getSupportActionBar().setTitle("Developed By");
    }

    public void openWebSite(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.YabbaDabba-Doo.com"));
        startActivity(browserIntent);
    }
}
