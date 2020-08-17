package com.example.legame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Wait extends AppCompatActivity {

    Button button;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait);
        button = findViewById(R.id.button3);
        listView = findViewById(R.id.ListView2);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                button.setText(R.string.waiting);
                button.setEnabled(false);
            }
        });
    }
}
