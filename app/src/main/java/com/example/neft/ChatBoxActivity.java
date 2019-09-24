package com.example.neft;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ChatBoxActivity extends AppCompatActivity {

    private ImageButton iconsmile, iconmicro,iconplus,arrowicon,pointsicon;
    private EditText textfield;
    private Button greenbtn;
    private ImageView imageLogin;
    private TextView name, online;
    private DatabaseReference mydatabase;
    private FirebaseAuth mAuth;
    private LinearLayout mlayout;
    private String id;
    private ArrayList<Message> messages;
    private ProgressBar progressBar;
    private static final String TAG = "RecyclerViewExample";
    private SimpleDateFormat df;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        mAuth = FirebaseAuth.getInstance();
        mydatabase= FirebaseDatabase.getInstance().getReference();


        Buffer.curuser = new User(Buffer.chat);
        df= new SimpleDateFormat("HH:mm");


        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mlayout = (LinearLayout) findViewById(R.id.messageboxla);
        iconsmile = (ImageButton) findViewById(R.id.iconsmile);
        iconmicro = (ImageButton) findViewById(R.id.iconmicro);
        iconplus = (ImageButton) findViewById(R.id.iconplus);
        arrowicon = (ImageButton) findViewById(R.id.arrowicon);
        pointsicon =(ImageButton) findViewById(R.id.pointsicon);

        textfield =(EditText) findViewById(R.id.textfield);

        greenbtn = (Button) findViewById(R.id.greenbtn);
        imageLogin = (ImageView) findViewById(R.id.thumbnail);
        name = (TextView) findViewById(R.id.title);
        online= (TextView) findViewById(R.id.phone);

        arrowicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });





        View.OnClickListener listentoprofile = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatBoxActivity.this, ProfileAboutActivity.class);

                startActivity(intent);
                finish();
            }
        };

        String sr = Buffer.curuser.getUseruid();

        Picasso.get().load(Buffer.photourlpart1+sr+Buffer.photourlpart2)
                .error(R.drawable.placeholderim)
                .placeholder(R.drawable.placeholderim)
                .transform( new CropCircleTransformation())
                .into(imageLogin);

        name.setText(Buffer.curuser.getLogin());


        imageLogin.setOnClickListener(listentoprofile);
        name.setOnClickListener(listentoprofile);
        online.setOnClickListener(listentoprofile);


        iconsmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        iconmicro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        iconplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        pointsicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        textfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        greenbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendmessage();

            }
        });

        id = Buffer.chat.getUser1uid().compareTo(Buffer.chat.getUser2uid())>0 ?Buffer.chat.getUser1uid()+Buffer.chat.getUser2uid():Buffer.chat.getUser2uid()+Buffer.chat.getUser1uid();

        messages = new ArrayList<>();

        mydatabase.child("messages").child(id).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                messages = new ArrayList<>();

                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 :
                            snapshot.getChildren()) {

                        Message element = snapshot1.getValue(Message.class);
                        if (element!= null)

                            messages.add(element);


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void updateUI() {
        mlayout.removeAllViews();
        for (Message mes: messages) {

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);


            TextView tv = new TextView(this );
            tv.setText(mes.getText());
            TextView time = new TextView(this);
            time.setText(df.format(new Date(mes.getTime())));


            LinearLayout.LayoutParams lParamstv = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            LinearLayout.LayoutParams lParamstime = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            lParamstv.gravity=Gravity.LEFT;
            lParamstime.gravity=Gravity.RIGHT;


            int dip = 270;
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,  getResources().getDisplayMetrics());
            dip= (int)px;

            int pad =18;
            px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pad,  getResources().getDisplayMetrics());
            pad= (int)px;

            lParamstv.setMargins(pad,pad,pad,0);
            lParamstime.setMargins(0,0,pad/4,pad/4);

            layout.addView(tv,lParamstv);
            layout.addView(time,lParamstime);




           // try {
           //     Typeface typeface = getResources().getFont(R.font.montserratregular);
            //    tv.setTypeface(typeface);
          //  } catch (Exception e) {}


            tv.setTextColor(ContextCompat.getColor(this,R.color.verydarkgreytext));
            tv.setTextSize(15);
            time.setTextSize(10);



            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            mlayout.addView(layout,lParams);


            if (Buffer.user.getUseruid().equals(mes.getAftorid())) {
                lParams.gravity= Gravity.RIGHT;
                lParams.setMargins(4*pad,pad,pad,0);
                layout.setLayoutParams(lParams);
                layout.setBackgroundResource(R.drawable.roundedlight);

            }
            else {
                lParams.setMargins(pad,pad,4*pad,0);
                lParams.gravity= Gravity.START;
                layout.setLayoutParams(lParams);
                layout.setBackgroundResource(R.drawable.roundedlightwhite);
            }
            tv.setFocusableInTouchMode(true);
            tv.requestFocus();
            textfield.setFocusableInTouchMode(true);
            textfield.requestFocus();
        }

    }

    private void sendmessage() {
        Message message = new Message(Buffer.user.getUseruid() ,textfield.getText().toString());
        Map<String, Object> messagemap = message.toMap();

        Chat chat = new Chat(Buffer.user, Buffer.curuser, message);
        Chat chat1 = new Chat(Buffer.curuser , Buffer.user, message);
        Map<String, Object> chatmap = chat.toMap();
        Map<String, Object> chat1map = chat1.toMap();

        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/messages/" + id+"/"+message.getId(), messagemap);
        childUpdates.put("/chats/" +Buffer.user.getUseruid()+"/"+ id, chatmap);
        childUpdates.put("/chats/" +Buffer.curuser.getUseruid()+"/"+ id, chat1map);

        mydatabase.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                textfield.setText("");
                // Write was successful!
                // ...

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                        Toast.makeText(ChatBoxActivity.this, "ошибка.Сообщение не отправленно",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
