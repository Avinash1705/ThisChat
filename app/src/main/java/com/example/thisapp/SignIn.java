package com.example.thisapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {
    Button bt_SignUp,bt_googleLogin;
    private EditText mPass,mEmail;
    String st_email,st_pass;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
        auth=FirebaseAuth.getInstance();
    }

    public void SignupActivityFn(View view) {
        startActivity(new Intent(SignIn.this,Signup.class));
    }
    private void init(){
        bt_SignUp=findViewById(R.id.bt_SignUp_signIn);
//        mName=findViewById(R.id.et_name_signIn);
        mPass=findViewById(R.id.et_pass_signIn);
        mEmail=findViewById(R.id.et_email_signIn);
        bt_googleLogin=findViewById(R.id.bt_googleLogin_signIn);
    }

    public void SignInActivityFn(View view) {
        st_email=mEmail.getText().toString();
        st_pass=mPass.getText().toString();
        auth.signInWithEmailAndPassword(st_email,st_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(SignIn.this,MainActivity.class));
                }
                else{
                    Toast.makeText(SignIn.this, "SignIn failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}