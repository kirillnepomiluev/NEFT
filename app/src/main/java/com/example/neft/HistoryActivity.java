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

public class HistoryActivity extends AppCompatActivity {

    private ImageButton findiconbtn, messagebtn, friendsbtn, noticbtn, profilbtn, menubtn;
    private TextView moneytv;


    private DatabaseReference mydatabase;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;
    private static final String TAG = "RecyclerViewExample";

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private ArrayList<Operation> history;
    private HistoryRecyclerViewAdapter adapter;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        mAuth = FirebaseAuth.getInstance();
        mydatabase= FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();




        findiconbtn = (ImageButton) findViewById(R.id.findiconbtn);
        messagebtn = (ImageButton) findViewById(R.id.messagebtn);
        friendsbtn = (ImageButton) findViewById(R.id.friendsbtn);
        noticbtn = (ImageButton) findViewById(R.id.noticbtn);
        profilbtn =(ImageButton) findViewById(R.id.profilbtn);
        menubtn = (ImageButton) findViewById(R.id.menubtn);
        moneytv=(TextView) findViewById(R.id.moneytv);
        progressBar=(ProgressBar) findViewById(R.id.progress_bar);


        moneytv.setText("Баллы: "+Buffer.money);



        findiconbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, LookAroundActivity.class);
                startActivity(intent);
                finish();

            }
        });

        messagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, ChatsActivity.class);
                startActivity(intent);
                finish();

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
                Intent intent = new Intent(HistoryActivity.this, NotificationRecActivity.class);
                startActivity(intent);
                finish();


            }
        });

        profilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoryActivity.this, MyProfileActivity.class);
                startActivity(intent);

            }
        });

        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(HistoryActivity.this, SignIn.class);
                startActivity(intent);
                finish();


            }
        });



        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecView);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);





        history = new ArrayList<>();

        mydatabase.child("history").child(Buffer.user.getUseruid()).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                history = new ArrayList<>();

                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 :
                            snapshot.getChildren()) {

                        Operation element = snapshot1.getValue(Operation.class);
                        if (element!= null)

                            history.add(element);


                    }
                }
                updateUI();
                progressBar.setVisibility(View.GONE);
                Buffer.history=history;
                Buffer.moneyupdate();
                moneytv.setText("Баллы: "+Buffer.money);

            }

            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "loadPost:onCancelled", databaseError.toException());
            }

        });

    }


    private void updateUI() {

        if (history.size()==0)  return;






        adapter = new HistoryRecyclerViewAdapter(HistoryActivity.this, history);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItem2ClickListener() {

            @Override
            public void onItemClick( Object item) {
                if(Buffer.checkstatus(HistoryActivity.this)) {



                }



            }


        });



    }
}

