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

/**
 * Join Room class allows users to join a room in the game
 *
 * @author Sabrina Li and Christina Chau
 */

public class JoinRoom extends AppCompatActivity {
    ListView listView;

    List<String> roomsList;

    EditText nameText;
    String roomName = "";
    String playerName = "";
    int countPlayers=0;

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
                addRoomEventListener();

            }
        });

        //show if room available
        addRoomsEventListener();
    }

    private void addRoomEventListener() {

        roomRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                countPlayers = (int) snapshot.getChildrenCount() + 1;
                roomPlayerRef = database.getReference("rooms/"+roomName+"/player"+countPlayers);
                nameRef = database.getReference("rooms/"+roomName+"/player"+countPlayers+"/name");
                roleRef = database.getReference("rooms/"+roomName+"/player"+countPlayers+"/role");
                cardRef = database.getReference("rooms/"+roomName+"/player"+countPlayers+"/card");
                nameRef.setValue(playerName);
                roleRef.setValue("");
                cardRef.setValue("");
                addCreateEventListener();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //error
                Toast.makeText(JoinRoom.this,"Error!", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public int getPlayerNum(){
        return countPlayers;
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
                intent.putExtra("playerNum", getPlayerNum());
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
