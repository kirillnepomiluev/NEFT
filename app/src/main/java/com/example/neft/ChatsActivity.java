package com.example.neft;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsActivity extends AppCompatActivity {

    private ImageButton findiconbtn, messagebtn, friendsbtn, noticbtn, profilbtn, plusbtn, lupabtn;
    private TextView friendstvbtn;
    private DatabaseReference mydatabase;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;
    private static final String TAG = "RecyclerViewExample";
    private List<com.example.neft.FeedItem> feedsList;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private ArrayList<Chat> chats;
    private MyRecyclerViewAdapter adapter;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        mAuth = FirebaseAuth.getInstance();
        mydatabase= FirebaseDatabase.getInstance().getReference();




        findiconbtn =(ImageButton) findViewById(R.id.findiconbtn);
        messagebtn =(ImageButton) findViewById(R.id.messagebtn);
        friendsbtn = (ImageButton) findViewById(R.id.friendsbtn);
        noticbtn = (ImageButton) findViewById(R.id.noticbtn);
        profilbtn = (ImageButton) findViewById(R.id.profilbtn);
        plusbtn = (ImageButton) findViewById(R.id.plusbtn);
        lupabtn = (ImageButton) findViewById(R.id.lupabtn);

        friendstvbtn = (TextView) findViewById(R.id.friendstvbtn);




        findiconbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatsActivity.this, LookAroundActivity.class);
                startActivity(intent);
                finish();

            }
        });

        messagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        friendsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        noticbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatsActivity.this, NotificationRecActivity.class);
                startActivity(intent);

            }
        });

        profilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatsActivity.this, MyProfileActivity.class);
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


        friendstvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatsActivity.this, ChatsFriends.class);
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

        chats = new ArrayList<>();

        mydatabase.child("chats").child(number).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                chats = new ArrayList<>();

                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 :
                            snapshot.getChildren()) {

                        Chat element = snapshot1.getValue(Chat.class);
                        if (element!= null)

                            chats.add(element);


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

        feedsList = new ArrayList<>();


        adapter = new MyRecyclerViewAdapter(ChatsActivity.this, chats);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItem2ClickListener() {

            @Override
            public void onItemClick(Object item) {

                if(Buffer.checkstatus(ChatsActivity.this)) {
                    Buffer.chat = (Chat) item;

                    Intent intent = new Intent(ChatsActivity.this, ChatBoxActivity.class);

                    startActivity(intent);

                }
            }
        });


    }


}
