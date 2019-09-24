package com.example.neft;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private EditText sponsorlogin, userlogin;
    private Button registbtn;
    private DatabaseReference mydatabase;
    private FirebaseAuth mAuth;
    private TextView testlogin;
    private boolean idchecked;
    private String spronsorid;
    private RadioGroup malegr;
    private boolean male;
    private boolean malecheked;
    private User sponsor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mydatabase = FirebaseDatabase.getInstance().getReference();

        sponsorlogin = (EditText) findViewById(R.id.sponsorloginet);
        userlogin = (EditText) findViewById(R.id.createloginedtext);
        registbtn = (Button) findViewById(R.id.button_reg);
        testlogin = (TextView) findViewById(R.id.testlogin);
        malegr = (RadioGroup) findViewById(R.id.malerg);
        male = false;
        malecheked = false;
        malegr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        malecheked = false;
                        break;
                    case R.id.men:
                        male = true;
                        malecheked = true;
                        break;
                    case R.id.women:
                        male = false;
                        malecheked = true;

                        break;


                    default:
                        break;
                }
            }
        });


        idchecked = false;
        spronsorid = "";


        testlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userlogin.getText().toString().isEmpty())
                    Toast.makeText(SignUp.this, "Поле логин пустое",
                            Toast.LENGTH_SHORT).show();
                else
                    mydatabase.child("logins").child(userlogin.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String s = dataSnapshot.getValue(String.class);
                            if (s == null)
                                Toast.makeText(SignUp.this, "Логин уникален",
                                        Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(SignUp.this, "Логин занят",
                                        Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(SignUp.this, "Ошибка соединения",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
            }
        });


        registbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userreg();
            }
        });
    }

    private void userreg() {


        if (chekpassword()) {

            if (userlogin.getText().toString().isEmpty())
                Toast.makeText(SignUp.this, "Поле логин пустое",
                        Toast.LENGTH_SHORT).show();
            else if (sponsorlogin.getText().toString().isEmpty())
                Toast.makeText(SignUp.this, "Поле Логин спонсора пустое",
                        Toast.LENGTH_SHORT).show();
            else


                mydatabase.child("logins").child(userlogin.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String s = dataSnapshot.getValue(String.class);
                        if (s == null) {

                            userregcontinue();
                        } else {
                            Toast.makeText(SignUp.this, "Логин занят",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(SignUp.this, "Ошибка соединения",
                                Toast.LENGTH_SHORT).show();

                    }
                });


        }
    }

    private void userregcontinue() {

        mydatabase.child("logins").child(sponsorlogin.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                if (s == null) {
                    Toast.makeText(SignUp.this, "Спонсор с таким логином не найден",
                            Toast.LENGTH_SHORT).show();

                } else {
                    spronsorid = s;
                    mydatabase.child("users").child(spronsorid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            sponsor = dataSnapshot.getValue(User.class);
                            if (sponsor == null) {
                                Toast.makeText(SignUp.this, "Спонсор с таким логином не найден",
                                        Toast.LENGTH_SHORT).show();

                            } else

                                userregfinish();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(SignUp.this, "Ошибка соединения",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });

                }
        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SignUp.this, "Ошибка соединения",
                        Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void userregfinish() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Buffer.user = new User(currentUser.getUid(), currentUser.getPhoneNumber(), userlogin.getText().toString());
        Buffer.user.setSponsorlogin(sponsorlogin.getText().toString());
        Buffer.user.setMale(male);
        Buffer.user.setSponsorid(spronsorid);
        Buffer.user.setSponsor2id(sponsor.getSponsorid());
        Buffer.user.setSponsor3id(sponsor.getSponsor2id());
        Buffer.user.setSponsor4id(sponsor.getSponsor3id());
        Buffer.user.setSponsor5id(sponsor.getSponsor4id());


        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(Buffer.user.getLogin()).build();


        currentUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            updateUI();


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }


    private void updateUI() {

        Map<String, Object> neweventValues = Buffer.user.toMap();

        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/users/" + Buffer.user.getUseruid(), neweventValues);
        childUpdates.put("/partners/" + spronsorid + "/" + Buffer.user.getUseruid(), neweventValues);
        childUpdates.put("/logins/" + Buffer.user.getLogin(), Buffer.user.getUseruid());


        mydatabase.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Write was successful!
                // ...
                Toast.makeText(SignUp.this, "Успешно создан профиль!",
                        Toast.LENGTH_SHORT).show();

                startActivity(new Intent(SignUp.this, LookAroundActivity.class));


                finish();


            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                        Toast.makeText(SignUp.this, "Ошибка.Профиль не создан",
                                Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private boolean chekpassword() {
        if (malecheked) {
            return true;

        } else {
            Toast.makeText(SignUp.this, "Пол не указан",
                    Toast.LENGTH_SHORT).show();
            return false;

        }

    }
}
