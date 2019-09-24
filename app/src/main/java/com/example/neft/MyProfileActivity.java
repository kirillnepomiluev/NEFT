package com.example.neft;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MyProfileActivity extends AppCompatActivity {

    private ImageButton findiconbtn, messagebtn, friendsbtn, noticbtn, profilbtn;
    private ImageView imageLogin;


    private DatabaseReference mydatabase;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;
    private static final String TAG = "RecyclerViewExample";

    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference usersref;
    private StorageReference picref;
    private ProgressBar progressBar;
    private ArrayList<User> users;
    private LookRecyclerViewAdapter adapter;
    private FirebaseUser user;
    String key;
    String userimageurl;
    private final static int REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE =1;
    static final int GALLERY_REQUEST = 1;
    private TextView userName;
    private TextView premiumtv,moneytv;
    private Button copyid,historybtn,buybtn;
    private LinearLayout partnerslay;
    private LinearLayout exitlay;




    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, MyProfileActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        mAuth = FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();
        userName = (TextView) findViewById(R.id.title);
        userName.setText(user.getDisplayName());



        mAuth = FirebaseAuth.getInstance();
        mydatabase= FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();




        findiconbtn = (ImageButton) findViewById(R.id.findiconbtn);
        messagebtn = (ImageButton) findViewById(R.id.messagebtn);
        friendsbtn = (ImageButton) findViewById(R.id.friendsbtn);
        noticbtn = (ImageButton) findViewById(R.id.noticbtn);
        profilbtn =(ImageButton) findViewById(R.id.profilbtn);
        imageLogin = (ImageView) findViewById(R.id.thumbnail);
        premiumtv =(TextView) findViewById(R.id.premiumtv);

        copyid =(Button) findViewById(R.id.copyidbtn);
        partnerslay=(LinearLayout) findViewById(R.id.partnerslay);
        moneytv=(TextView) findViewById(R.id.moneytv);
        buybtn=(Button)findViewById(R.id.buybtn);
        historybtn=(Button)findViewById(R.id.historybtn);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);
        exitlay=(LinearLayout) findViewById(R.id.exitl);

        moneytv.setText("Баллы: "+Buffer.money);


        copyid.setText("Логин: "+Buffer.user.getLogin());



        exitlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(MyProfileActivity.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });

        historybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfileActivity.this, HistoryActivity.class));
            }
        });

        buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyProfileActivity.this, BuyActivity.class));
            }
        });

        partnerslay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this, PartnersActivity.class);
                startActivity(intent);
                finish();

            }
        });


        copyid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyClipboardManager myClipboardManager = new MyClipboardManager();
                if ( myClipboardManager.copyToClipboard(MyProfileActivity.this,Buffer.user.getLogin()))
                    Toast.makeText(MyProfileActivity.this, "Скопированно в буфер обмена",Toast.LENGTH_SHORT).show();

            }
        });


        SimpleDateFormat df = new SimpleDateFormat(("d-M-yy"));

        premiumtv.setText("PREMIUM до " +df.format(new Date(Buffer.user.getStatusend())));
        if(Buffer.user.getStatus())
            premiumtv.setVisibility(View.VISIBLE);
        else
            premiumtv.setVisibility(View.INVISIBLE);


        findiconbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this, LookAroundActivity.class);
                startActivity(intent);
                finish();

            }
        });

        messagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProfileActivity.this, ChatsActivity.class);
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
                Intent intent = new Intent(MyProfileActivity.this, NotificationRecActivity.class);
                startActivity(intent);

            }
        });

        profilbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                SimpleDateFormat df = new SimpleDateFormat(("d-MM-yy"));
                premiumtv.setText("PREMIUM  до " +df.format(new Date(Buffer.user.getStatusend())));
                if(Buffer.user.getStatus())
                    premiumtv.setVisibility(View.VISIBLE);
                else
                    premiumtv.setVisibility(View.INVISIBLE);


            }

            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "loadPost:onCancelled", databaseError.toException());
            }

        });



        key = user.getUid();

        String sr = Buffer.user.getUseruid();

        try {
            userimageurl = user.getPhotoUrl().toString();
        } catch (Exception ex) {
            userimageurl="R.drawable.loginim";
        }

        storage = FirebaseStorage.getInstance();
        usersref = storage.getReference();

        Picasso.get().load(Buffer.photourlpart1+sr+Buffer.photourlpart2)
                .error(R.drawable.placeholderim)
                .placeholder(R.drawable.placeholderim)
                .transform( new CropCircleTransformation())
                .into(imageLogin);

        imageLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int permissionStatus = ContextCompat.checkSelfPermission(MyProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

                if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                    showimagechoser();
                }
                else {
                    ActivityCompat.requestPermissions(MyProfileActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE);
                }


            }
        });

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showimagechoser();
                }
                break;
        }
    }

    private void showimagechoser() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case GALLERY_REQUEST:
                if (resultCode == RESULT_OK) {

                    Uri selectedImageUri = imageReturnedIntent.getData();

                    Picasso.get()
                            .load(selectedImageUri)
                            .error(R.drawable.placeholderim)
                            .resize(200, 200)
                            .centerCrop()
                            .into(imageLogin, new Callback() {
                                @Override
                                public void onSuccess() {
                                    imageLogin.setVisibility(View.VISIBLE);

                                    uploadpic(key);




                                }

                                @Override
                                public void onError(Exception e) {
                                    Toast.makeText(MyProfileActivity.this, "ошибка.изображение не загруженно",
                                            Toast.LENGTH_SHORT).show();


                                }


                            });




                }
        }
    }

    private void uploadpic(String key) {

        picref= usersref.child("userspics").child(key+".jpg");

        // Get the data from an ImageView as bytes
        imageLogin.setDrawingCacheEnabled(true);
        imageLogin.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageLogin.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();



        UploadTask uploadTask = picref.putBytes(data);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();

                }

                // Continue with the task to get the download URL
                return picref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {

                    userimageurl =task.getResult().toString();
                    getimageURL(userimageurl);




                } else {


                    Toast.makeText(MyProfileActivity.this, "ошибка! Изображение  не обновленно!",
                            Toast.LENGTH_SHORT).show();

                    // Handle failures
                    // ...
                }
            }
        });









    }

    private void getimageURL (String url)  {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(url))
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            mydatabase = FirebaseDatabase.getInstance().getReference();
                            mydatabase.child("users").child(key).child("photoUrl").setValue(userimageurl);



                            Toast.makeText(MyProfileActivity.this, "изображение обновленно",
                                    Toast.LENGTH_SHORT).show();


                            Picasso.get().load(userimageurl)
                                    .error(R.drawable.placeholderim)
                                    .placeholder(R.drawable.placeholderim)
                                    .into(imageLogin);

                        }
                    }
                });

    }



}
