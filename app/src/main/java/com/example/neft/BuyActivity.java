package com.example.neft;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BuyActivity extends AppCompatActivity {

    private ImageButton findiconbtn, messagebtn, friendsbtn, noticbtn, profilbtn, menubtn;
    private TextView moneytv;
    private Button testpaybtn, addmoneytest;
    private long time;


    private DatabaseReference mydatabase;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;
    private static final String TAG = "RecyclerViewExample";

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private ArrayList<User> users;
    private PartnersRecyclerViewAdapter adapter;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);


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
        testpaybtn =(Button) findViewById(R.id.testpaybtn);
        progressBar=(ProgressBar) findViewById(R.id.progress_bar);
        addmoneytest=(Button) findViewById(R.id.addmoneytest);

        moneytv.setText("Баллы: "+Buffer.money);

        progressBar.setVisibility(View.GONE);


        addmoneytest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                Operation sss=new Operation("Добавление баллов тест",true,1000,Buffer.user.getUseruid());
                Map<String, Object> childUpdates = new HashMap<>();
                Map<String, Object> sssmap=sss.toMap();
                childUpdates.put("/history/" + Buffer.user.getUseruid() + "/"+sss.getId(),sssmap);
                childUpdates.put("/users/" + Buffer.user.getUseruid() + "/money", Buffer.user.getMoney()+1000);

                mydatabase.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Buffer.user.setMoney(Buffer.user.getMoney()+1000);

                        Toast.makeText(BuyActivity.this, "Операция прошла успешно!",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BuyActivity.this, "Ошибка!",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });



            }
        });


        testpaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((Buffer.money-500)<0) {
                    Toast.makeText(BuyActivity.this, "Недостаточно баллов!", Toast.LENGTH_SHORT).show();
                    return ;
                }
                progressBar.setVisibility(View.VISIBLE);

                Operation sss=new Operation("Оплата абонентской платы",false,500,Buffer.user.getUseruid());

                Map<String, Object> childUpdates = new HashMap<>();

                ArrayList<String> sponsors =  new ArrayList<>(Arrays.asList(Buffer.user.getSponsorid(),Buffer.user.getSponsor2id(),Buffer.user.getSponsor3id(),Buffer.user.getSponsor4id(),Buffer.user.getSponsor5id()));


                for (int i=0; i<5; i++ ) {
                    String s = sponsors.get(i);
                    int w = 0;
                    switch (i) {
                        case (0) :
                            w= 150;
                            break;
                        case (1):
                           w= 75;
                        break;
                        case (2):
                            w= 50;
                            break;
                        case (3):
                            w= 20;
                            break;
                        case (4):
                            w= 5;
                            break;
                    }
                    Operation splus = new Operation("Партнерка", true,w,s);
                    Map<String, Object> splusmap = splus.toMap();
                    childUpdates.put("/history/" + s + "/"+sss.getId(),splusmap);
                }
                if (Buffer.user.getStatusend()==0) Buffer.user.setStatusend(new Date().getTime());
                Calendar instance = Calendar.getInstance();
                instance.setTime(new Date(Buffer.user.getStatusend())); //устанавливаем дату, с которой будет производить операции
                instance.add(Calendar.DAY_OF_MONTH, 30);// прибавляем 30 дней установленной дате
                Date newDate = instance.getTime();
                time=newDate.getTime();

                Map<String, Object> sssmap=sss.toMap();
                childUpdates.put("/history/" + Buffer.user.getUseruid() + "/"+sss.getId(),sssmap);
                childUpdates.put("/users/" + Buffer.user.getUseruid() + "/statusend", time);
                childUpdates.put("/users/" + Buffer.user.getUseruid() + "/status", true);
                childUpdates.put("/partners/" + Buffer.user.getSponsorid() + "/" + Buffer.user.getUseruid() + "/status", true);
                childUpdates.put("/users/" + Buffer.user.getUseruid() + "/money", Buffer.user.getMoney()-500);
                mydatabase.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Buffer.user.setStatus(true);
                        Buffer.user.setStatusend(time);
                        Buffer.user.setMoney(Buffer.user.getMoney()-500);
                        Toast.makeText(BuyActivity.this, "Операция прошла успешно!",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BuyActivity.this, "Ошибка!",Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        });




        findiconbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyActivity.this, LookAroundActivity.class);
                startActivity(intent);
                finish();

            }
        });

        messagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyActivity.this, ChatsActivity.class);
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
                Intent intent = new Intent(BuyActivity.this, NotificationRecActivity.class);
                startActivity(intent);
                finish();


            }
        });

        profilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyActivity.this, MyProfileActivity.class);
                startActivity(intent);

            }
        });

        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(BuyActivity.this, SignIn.class);
                startActivity(intent);
                finish();


            }
        });


        mydatabase.child("history").child(Buffer.user.getUseruid()).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                ArrayList<Operation> history = new ArrayList<>();

                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 :
                            snapshot.getChildren()) {

                        Operation element = snapshot1.getValue(Operation.class);
                        if (element!= null)

                            history.add(element);


                    }
                }

                progressBar.setVisibility(View.GONE);
                Buffer.history=history;
                Buffer.moneyupdate();
                moneytv.setText("Баллы: "+Buffer.money);


            }

            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "loadPost:onCancelled", databaseError.toException());
            }

        });


        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecView);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);


    }

}

