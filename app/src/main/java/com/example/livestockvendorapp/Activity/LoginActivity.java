package com.example.livestockvendorapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.livestockvendorapp.Model.Common;
import com.example.livestockvendorapp.Model.User;
import com.example.livestockvendorapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    EditText mphone, mpassword;
    AlertDialog dialog;
    ImageView img;
    Button login;
    String p_no;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener fiAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Paper.init(this);
        mphone = findViewById(R.id.useremail);
        mpassword = findViewById(R.id.userpass);
        img=findViewById(R.id.Login_image);

        dialog=new SpotsDialog.Builder().setContext(LoginActivity.this).setCancelable(true).build();
        mAuth = FirebaseAuth.getInstance();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("Butcher");
        //Picasso.get().load(R.drawable.loginimage).into(img);
        login = findViewById(R.id.loginbtn);

        if (haveNetwork()) {
            Toast.makeText(getApplicationContext(), "Network connection is available", Toast.LENGTH_SHORT).show();
        } else if (!haveNetwork()) {
            Toast.makeText(getApplicationContext(), "Network connection is not available", Toast.LENGTH_SHORT).show();
        }


        fiAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    String phone_no = user.getPhoneNumber();
                    insetuserinfo(phone_no);
                    Toast.makeText(LoginActivity.this, "Loging", Toast.LENGTH_SHORT).show();
                    Intent mainintent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainintent);
                    finish();
                    //return;
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                p_no = mphone.getText().toString().trim();
                String pass = mpassword.getText().toString().trim();


                if (p_no.isEmpty() && pass.isEmpty()) {
                    mphone.setError("Please enter the details");
                    mphone.requestFocus();
                    mpassword.setError("Please enter the details");
                    mpassword.requestFocus();
                } else {
                    p_no = p_no.replaceFirst("0", "+92");
                    dialog.show();
                    if (haveNetwork()) {

                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                          Toast.makeText(getApplicationContext(), "Network connection is available", Toast.LENGTH_SHORT).show();
                                if (dataSnapshot.child(p_no).exists()) {

                                    User user = dataSnapshot.child(p_no).getValue(User.class);
                                    user.setPhone(p_no);
                                    if (user.getPassword().equals(mpassword.getText().toString())) {
                                        Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                                        Common.currentuser = user;
                                        Paper.book().write(Common.login,user);
                                        Paper.book().write(Common.checklogin,"1");
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                        dialog.dismiss();
                                        finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Invalid password or phone", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "User Not exits", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(LoginActivity.this, "Logged unSuccessfully", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(getApplicationContext(),Mainpage.class));

                            }
                        });

                    } else if (!haveNetwork()) {
                        Toast.makeText(getApplicationContext(), "Network connection is not available", Toast.LENGTH_SHORT).show();

                    }


                }

            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(fiAuthStateListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(fiAuthStateListener);
    }


    public void insetuserinfo(String phone) {
        User obj = new User();
        obj.setPhone(phone);
        Common.currentuser = obj;

    }

    private boolean haveNetwork() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
