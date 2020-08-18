package com.example.legame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The Home class is deals with the first scene of the game
 *
 * @author Sabrina Li and Christina Chau
 */

public class Home extends AppCompatActivity {
    Button joinButton;
    Button createButton;

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        database = FirebaseDatabase.getInstance();

        joinButton = findViewById(R.id.button7);
        createButton = findViewById(R.id.button8);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), JoinRoom.class));
            }
        });

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), Create.class));
            }
        });
    }

}
