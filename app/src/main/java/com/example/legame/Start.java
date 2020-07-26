package com.example.legame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Start extends AppCompatActivity {

    Button button;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        button = findViewById(R.id.button3);
        listView = findViewById(R.id.ListView2);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                button.setText("Waiting for other players...");
                button.setEnabled(false);
            }
        });
    }
}
