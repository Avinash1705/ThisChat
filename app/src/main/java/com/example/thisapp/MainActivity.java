package com.example.thisapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.Toast;

import com.example.thisapp.Adapters.FragmentsAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        init();
        getSupportActionBar().hide();

        viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        tableLayout.setupWithViewPager(viewPager);
    }
    private void init(){
    viewPager=findViewById(R.id.viewpager_id);
    tableLayout=findViewById(R.id.tablayout_id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_dots,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Signup.class));
                break;
            case R.id.menu_groupChat:
                startActivity(new Intent(getApplicationContext(),GroupChat.class));
                break;
            case R.id.menu_settings:
                startActivity(new Intent(getApplicationContext(),settingActivity.class));
                break;
            default:
                Toast.makeText(getApplicationContext(), " Default working", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }


}