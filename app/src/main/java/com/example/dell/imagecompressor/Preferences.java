package com.example.dell.imagecompressor;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class Preferences extends AppCompatActivity {
    TextView storageDirectoryTextView;
    SessionManager sessionManager = new SessionManager();
    final String preferenceKey = "direc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        getReference();

        storageDirectoryTextView.setText(sessionManager.getPreferences(getApplicationContext(), preferenceKey));

    }

    void getReference() {
        storageDirectoryTextView = (TextView) findViewById(R.id.storage_directory);
        storageDirectoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> list = Util.getAllDirectories(new File("/storage/sdcard0/"));
                final String[] array = list.toArray(new String[list.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(Preferences.this);
                builder.setTitle("Choose path")
                        .setItems(array, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sessionManager.setPreferences(Preferences.this, preferenceKey, array[which]);
                                storageDirectoryTextView.setText(sessionManager.getPreferences(Preferences.this, preferenceKey));
                            }
                        });
                builder.create();
                builder.show();
            }
        });
    }
}
