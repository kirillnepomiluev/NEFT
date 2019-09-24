package com.example.neft;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsFriends extends AppCompatActivity {

    private ImageButton findiconbtn, messagebtn, noticbtn, profilbtn, plusbtn, lupabtn;
    private TextView messagetvbtn;
    private DatabaseReference mydatabase;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;
    private static final String TAG = "RecyclerViewExample";
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private ArrayList<Invite> invites;
    private FriendsRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_friends);

        mAuth = FirebaseAuth.getInstance();
        mydatabase= FirebaseDatabase.getInstance().getReference();


        findiconbtn =(ImageButton) findViewById(R.id.findiconbtn);
        messagebtn =(ImageButton) findViewById(R.id.messagebtn);
        noticbtn = (ImageButton) findViewById(R.id.noticbtn);
        profilbtn = (ImageButton) findViewById(R.id.profilbtn);
        plusbtn = (ImageButton) findViewById(R.id.plusbtn);
        lupabtn = (ImageButton) findViewById(R.id.lupabtn);

        messagetvbtn = (TextView) findViewById(R.id.messagetvbtn);

        findiconbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatsFriends.this, LookAroundActivity.class);
                startActivity(intent);
                finish();

            }
        });

        messagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatsFriends.this, ChatsActivity.class);
                startActivity(intent);
                finish();

            }
        });


        noticbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatsFriends.this, NotificationRecActivity.class);
                startActivity(intent);

            }
        });

        profilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatsFriends.this, MyProfileActivity.class);
                startActivity(intent);

            }
        });

        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        lupabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        messagetvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatsFriends.this, ChatsActivity.class);
                startActivity(intent);
                finish();

            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecView);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        String number = Buffer.user.getUseruid();

        invites = new ArrayList<>();

        mydatabase.child("friends").child(number).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                invites = new ArrayList<>();

                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 :
                            snapshot.getChildren()) {

                        Invite element = snapshot1.getValue(Invite.class);
                        if (element!= null)

                            invites.add(element);


                    }
                }
                updateUI();
                progressBar.setVisibility(View.GONE);

            }

            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "loadPost:onCancelled", databaseError.toException());
            }

        });






    }

    private void updateUI() {




        adapter = new FriendsRecyclerViewAdapter(ChatsFriends.this, invites);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItem2ClickListener() {

            @Override
            public void onItemClick(Object item) {
                if(Buffer.checkstatus(ChatsFriends.this)) {
                    Invite invite = (Invite) item;

                    Buffer.chat = new Chat(invite);

                    Intent intent = new Intent(ChatsFriends.this, ChatBoxActivity.class);

                    startActivity(intent);

                }
            }
        });


    }
}
