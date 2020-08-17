package com.example.legame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JoinRoom extends AppCompatActivity {
    ListView listView;

    List<String> roomsList;

    EditText nameText;
    String roomName = "";
    String playerName = "";

    FirebaseDatabase database;
    DatabaseReference roomPlayerRef;
    DatabaseReference roomsRef;
    DatabaseReference roomRef;
    DatabaseReference nameRef;
    DatabaseReference roleRef;
    DatabaseReference cardRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joinroom);

        database = FirebaseDatabase.getInstance();

        //get player name and assign room name to player name

        listView = findViewById(R.id.ListView);

        //all existing available rooms
        roomsList = new ArrayList<>();

        nameText = findViewById(R.id.editText9);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //join existing room and add as player2
                playerName = nameText.getText().toString();
                roomName = roomsList.get(position);
                roomRef = database.getReference("rooms/"+roomName);
                roomPlayerRef = database.getReference("rooms/"+roomName+"/player2");
                nameRef = database.getReference("rooms/"+roomName+"/player2/name");
                roleRef = database.getReference("rooms/"+roomName+"/player2/role");
                cardRef = database.getReference("rooms/"+roomName+"/player2/card");
                addCreateEventListener();
                nameRef.setValue(playerName);
                roleRef.setValue("");
                cardRef.setValue("");
                //System.out.print(Create.getNumChildren());
            }
        });

        //show if room available
        addRoomsEventListener();
    }


    private void addRoomsEventListener(){
        roomsRef = database.getReference("rooms");
        roomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //show list of rooms
                roomsList.clear();
                Iterable<DataSnapshot> rooms = snapshot.getChildren();
                for(DataSnapshot snap:rooms) {
                    roomsList.add(snap.getKey());

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(JoinRoom.this, android.R.layout.simple_list_item_1,roomsList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error - nothing
            }
        });
    }

    private void addCreateEventListener(){
        roomPlayerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //join room
                Intent intent = new Intent(getApplicationContext(), Wait.class);
                intent.putExtra("roomName",roomName);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error
                Toast.makeText(JoinRoom.this,"Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
