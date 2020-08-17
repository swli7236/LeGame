package com.example.legame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Create extends AppCompatActivity {
    Button createButton;
    EditText nameText;
    EditText roomNameText;
    TextView createErrorMsg;

    String playerName = "";
    String roomName = "";

    FirebaseDatabase database;
    DatabaseReference roomRef;
    DatabaseReference roomPlayerRef;
    DatabaseReference nameRef;
    DatabaseReference roleRef;
    DatabaseReference cardRef;

    int numChildren = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);

        database = FirebaseDatabase.getInstance();
        createButton = findViewById(R.id.button6);
        nameText = findViewById(R.id.editText6);
        roomNameText = findViewById(R.id.editText2);
        createErrorMsg = findViewById(R.id.textView);

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                playerName = nameText.getText().toString();
                createButton.setText(R.string.creating);
                createButton.setEnabled(false);
                roomName = roomNameText.getText().toString();
                roomRef = database.getReference("rooms/"+roomName);
                addRoomEventListener();
            }
        });
    }

    /*
    public String getPlayerName() {
        return playerName;
    }

     */


    private void addRoomEventListener() {
        /*
        roomRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                numChildren++;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                numChildren--;
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //error
                createButton.setText("CREATE");
                createButton.setEnabled(true);
                createErrorMsg.setVisibility(View.GONE);
                Toast.makeText(Create.this,"Error!", Toast.LENGTH_SHORT).show();
            }
        });

         */

        roomRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //room name exists, not unique
                    createErrorMsg.setVisibility(View.VISIBLE);
                    createButton.setText(R.string.create);
                    createButton.setEnabled(true);
                } else {
                    //room name unique, create room
                    createButton.setText(R.string.creating);
                    createButton.setEnabled(false);
                    roomPlayerRef = database.getReference("rooms/"+roomName+"/player1");
                    nameRef = database.getReference("rooms/"+roomName+"/player1/name");
                    roleRef = database.getReference("rooms/"+roomName+"/player1/role");
                    cardRef = database.getReference("rooms/"+roomName+"/player1/card");
                    nameRef.setValue(playerName);
                    roleRef.setValue("");
                    cardRef.setValue("");
                    addCreateEventListener();

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //error
                createButton.setText(R.string.create);
                createButton.setEnabled(true);
                createErrorMsg.setVisibility(View.GONE);
                Toast.makeText(Create.this,"Error!", Toast.LENGTH_SHORT).show();
            }

        });
    }

    /*
    public int getNumChildren() {
        return numChildren;
    }
     */

    private void addCreateEventListener(){
        roomPlayerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //join room
                createButton.setText(R.string.create);
                createButton.setEnabled(true);
                createErrorMsg.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), Wait.class);
                intent.putExtra("roomName",roomName);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error
                createButton.setText(R.string.create);
                createButton.setEnabled(true);
                createErrorMsg.setVisibility(View.GONE);
                Toast.makeText(Create.this,"Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
