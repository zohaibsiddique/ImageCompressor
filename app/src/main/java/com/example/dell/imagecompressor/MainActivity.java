package com.example.dell.imagecompressor;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Button phoneStorageButton, sdCardStorageButton;
    String directory = Environment.getExternalStoragePublicDirectory("Image Compressor").toString();
    final String preferenceKey = "direc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        makeDirectory();

        getReferences();
        phoneStorageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.startAnActivity(MainActivity.this, Albums.class, "1");
            }
        });

        sdCardStorageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.startAnActivity(MainActivity.this, Albums.class, "2");
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    void getReferences() {
        phoneStorageButton = (Button) findViewById(R.id.phonestorage);
        sdCardStorageButton = (Button) findViewById(R.id.sdcard);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_preferences) {
            Util.startAnActivity(MainActivity.this, Preferences.class, "");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void makeDirectory() {
        File file = new File(directory);
        if (file.mkdirs()) {

        } else {

        }
        SessionManager sessionManager = new SessionManager();
        String value = sessionManager.getPreferences(getApplicationContext(), preferenceKey);
        if(value.equals("")) {
            sessionManager.setPreferences(getApplicationContext(), preferenceKey, directory);
        } else {
        }
    }
}
