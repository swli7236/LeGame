package com.example.legame;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Game extends AppCompatActivity {

    String playerName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        randomAssign();
    }

    private void randomAssign() {

    }
}
