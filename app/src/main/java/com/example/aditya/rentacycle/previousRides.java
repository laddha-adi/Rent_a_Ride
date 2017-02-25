package com.example.aditya.rentacycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class previousRides extends AppCompatActivity {
public static TextView pasteText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_previous_rides);
        pasteText = (TextView) findViewById(R.id.pasteText);


    }

}
