package com.example.thisapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thisapp.Models.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    private static final int RC_SIGN_IN =65 ;
    private static final String TAG = "raw";
    Button bt_SignUp,bt_googleLogin;
    private EditText mName,mPass,mEmail;
    String st_email,st_pass;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient ;
    private String st_name;
    FirebaseDatabase database;
//    DatabaseReference myRef;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
        //only dec
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(),gso);

        bt_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(Signup.this, "Sign up Touched", Toast.LENGTH_SHORT).show();
                SignUpEmailPass();
            }
        });
        bt_googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpGoogleAccount();
            }
        });
    }
    private void init(){
        bt_SignUp=findViewById(R.id.bt_SignUp);
        mName=findViewById(R.id.et_name);
        mPass=findViewById(R.id.et_pass);
        mEmail=findViewById(R.id.et_email);
        bt_googleLogin=findViewById(R.id.bt_googleLogin);
    }
    private void SignUpEmailPass(){
        st_name=mName.getText().toString();
        st_email=mEmail.getText().toString();
        st_pass=mPass.getText().toString();
        mAuth.createUserWithEmailAndPassword(st_email,st_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Users users=new Users(st_name,st_email,st_pass);
                    //users data not set
//                    myRef.child("Users").child(users.getuId()).setValue(users);

                    database.getReference().child("Users")
                            .child(task.getResult().getUser().getUid()).setValue(users);
                    Toast.makeText(Signup.this, "SignUp successful done", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                else{
                    Toast.makeText(Signup.this, " SignUp failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void SignUpGoogleAccount(){
        signIn();

    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(Signup.this, "SignUp Wih Google done", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();
                            Users users=new Users();
                            users.setuId(user.getUid());
                            users.setuName(user.getDisplayName());
                            users.setuProfilePic(user.getPhotoUrl().toString());
                            database.getReference().child("Users").child(user.getUid()).setValue(users);
//                            updateUI(user);
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }
                    }
                });
    }
}
