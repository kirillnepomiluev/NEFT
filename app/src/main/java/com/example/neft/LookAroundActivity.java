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
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class LookAroundActivity extends AppCompatActivity {

    private ImageButton findiconbtn, messagebtn, friendsbtn, noticbtn, profilbtn, menubtn;
    private TextView clubtvbtn;

    private DatabaseReference mydatabase;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;
    private static final String TAG = "RecyclerViewExample";

    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private ProgressBar progressBar;
    private ArrayList<User> users;
    private LookRecyclerViewAdapter adapter;
    private FirebaseUser user;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        user = mAuth.getCurrentUser();
        if (user == null) {
            mAuth.signOut();
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_around);

        mAuth = FirebaseAuth.getInstance();
        mydatabase= FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();
        storage = FirebaseStorage.getInstance();




        findiconbtn = (ImageButton) findViewById(R.id.findiconbtn);
        messagebtn = (ImageButton) findViewById(R.id.messagebtn);
        friendsbtn = (ImageButton) findViewById(R.id.friendsbtn);
        noticbtn = (ImageButton) findViewById(R.id.noticbtn);
        profilbtn =(ImageButton) findViewById(R.id.profilbtn);
        menubtn = (ImageButton) findViewById(R.id.menubtn);

        clubtvbtn =(TextView) findViewById(R.id.clubtvbtn);

        findiconbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        messagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LookAroundActivity.this, ChatsActivity.class);
                startActivity(intent);

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
                Intent intent = new Intent(LookAroundActivity.this, NotificationRecActivity.class);
                startActivity(intent);

            }
        });

        profilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LookAroundActivity.this, MyProfileActivity.class);
                startActivity(intent);


            }
        });

        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });

        clubtvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecView);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);





        users = new ArrayList<>();

        mydatabase.child("users").addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                users = new ArrayList<>();

                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 :
                            snapshot.getChildren()) {

                        User element = snapshot1.getValue(User.class);
                        if (element!= null)

                            users.add(element);


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
        if (users.size()==0)  return;

        for (int i=0; i <users.size(); i++ ) {

            if (users.get(i).getUseruid().equals(Buffer.user.getUseruid()))  users.remove(i);
        }




        adapter = new LookRecyclerViewAdapter(LookAroundActivity.this, users);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItem2ClickListener() {

            @Override
            public void onItemClick(Object item) {
                if(Buffer.checkstatus(LookAroundActivity.this)) {

                    Buffer.curuser = (User) item;
                    Buffer.chat = new Chat(Buffer.user, Buffer.curuser);
                    Buffer.back = LookAroundActivity.class;

                    Intent intent = new Intent(LookAroundActivity.this, ChatBoxActivity.class);

                    startActivity(intent);

                }


            }
        });


    }


}
