package com.example.legame;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 *
 *
 * @author Sabrina Li and Christina Chau
 */

public class Wait extends AppCompatActivity {

    Button button;
    ListView listView;
    List<String> playersList;
    String roomName;

    FirebaseDatabase database;
    DatabaseReference playersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait);
        button = findViewById(R.id.button3);
        listView = findViewById(R.id.ListView2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText(R.string.waiting);
                button.setEnabled(false);
            }
        });

        addPlayersEventListener();
    }

    private void addPlayersEventListener(){
        playersRef = database.getReference("rooms/"+roomName);
        playersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //show list of players
                playersList.clear();
                Iterable<DataSnapshot> rooms = snapshot.getChildren();
                for(DataSnapshot snap:rooms) {
                    playersList.add(snap.getKey());

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(Wait.this, android.R.layout.simple_list_item_1,playersList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error - nothing
            }
        });
    }

}
