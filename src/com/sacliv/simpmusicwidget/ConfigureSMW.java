package com.sacliv.simpmusicwidget;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ConfigureSMW extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.configure);

        

        // don't call 'this', use 'getApplicationContext()', the activity-object
        // is bigger than just the
        // context because the activity also stores the UI elemtents
        Toast.makeText(getApplicationContext(), "We are in Configure",
                Toast.LENGTH_SHORT).show();
    }

}
