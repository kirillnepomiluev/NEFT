package com.example.neft;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationRecActivity extends AppCompatActivity {

    private ImageButton findiconbtn, messagebtn, friendsbtn, noticbtn, profilbtn, menubtn;
    private TextView clubtvbtn;

    private DatabaseReference mydatabase;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;
    private static final String TAG = "RecyclerViewExample";

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private ArrayList<Invite> users;
    private InviteRecyclerViewAdapter adapter;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_rec);


        mAuth = FirebaseAuth.getInstance();
        mydatabase= FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();




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
                Intent intent = new Intent(NotificationRecActivity.this, LookAroundActivity.class);
                startActivity(intent);
                finish();

            }
        });

        messagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationRecActivity.this, ChatsActivity.class);
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

            }
        });

        profilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationRecActivity.this, MyProfileActivity.class);
                startActivity(intent);

            }
        });

        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(NotificationRecActivity.this, SignIn.class);
                startActivity(intent);
                finish();


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

        mydatabase.child("invites").child(Buffer.user.getUseruid()).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                users = new ArrayList<>();

                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 :
                            snapshot.getChildren()) {

                        Invite element = snapshot1.getValue(Invite.class);
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

        if (users.size()==0) {
            mRecyclerView.removeAllViews();
            return;
        }

        for (int i=0; i <users.size(); i++ ) {

            if (users.get(i).equals(Buffer.user))  users.remove(i);
        }




        adapter = new InviteRecyclerViewAdapter(NotificationRecActivity.this, users);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(View v, Object item) {
                if (v.getId()==R.id.okbtn) addfriend((Invite)item);
                if (v.getId()==R.id.cancelbtn) deleteinvite((Invite)item);
                if (v.getId()==R.id.title|(v.getId()==R.id.thumbnail)) {
                    Buffer.curuser=new User((Invite)item);
                    Intent intent = new Intent(NotificationRecActivity.this, ProfileAboutActivity.class);
                    startActivity(intent);
                    finish();

                }



            }


        });



    }

    private void deleteinvite(Invite item) {
        mydatabase.child("invites").child(Buffer.user.getUseruid()).child(item.getUser2uid()).removeValue();
    }

    private void addfriend(final Invite item) {
        Invite item2 = new Invite(item);
        Map<String, Object> neweventValues = item.toMap();
        Map<String, Object> neweventValues2 = item2.toMap();

        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/friends/" + item.getUser2uid()+"/"+Buffer.user.getUseruid(), neweventValues);
        childUpdates.put("/friends/" + Buffer.user.getUseruid()+"/"+item.getUser2uid(), neweventValues2);


        mydatabase.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Write was successful!
                // ...
                Toast.makeText(NotificationRecActivity.this, "Приглашение в друзья отправленно!",
                        Toast.LENGTH_SHORT).show();
                deleteinvite(item);




            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                        Toast.makeText(NotificationRecActivity.this, "ошибка.Приглашение не отправленно",
                                Toast.LENGTH_SHORT).show();
                    }
                });





    }

}
