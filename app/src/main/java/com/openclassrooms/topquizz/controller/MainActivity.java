package com.openclassrooms.topquizz.controller;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import com.openclassrooms.topquizz.R;

public class MainActivity extends AppCompatActivity {
    public static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    public static final String SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO";
    public static final String SHARED_PERF_USER_INFO_NAME = "SHARED_PERF_USER_INFO_NAME";

//    private AppBarConfiguration appBarConfiguration;
//    private ActivityMainBinding binding;

    TextView mGreetingTextView;
    EditText mNameEditText;
    Button mPlayButton;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            // fetch score from intent
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE,0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String firstName=getSharedPreferences(SHARED_PREF_USER_INFO,MODE_PRIVATE)
                .getString(SHARED_PERF_USER_INFO_NAME,null);

        mGreetingTextView = findViewById(R.id.main_textview_greeting);
        mNameEditText = findViewById(R.id.main_edittext_greeting);
        mPlayButton = findViewById(R.id.main_button_greeting);

        mPlayButton.setEnabled(false);
        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPlayButton.setEnabled(!s.toString().isEmpty());
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences(SHARED_PREF_USER_INFO,MODE_PRIVATE)
                        .edit()
                        .putString(SHARED_PERF_USER_INFO_NAME,mNameEditText.getText().toString())
                        .commit();
                //Toast.makeText(MainActivity.this, "testing testing ", Toast.LENGTH_LONG).show();
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
                
            }
        });

//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        setSupportActionBar(binding.toolbar);
//
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//
//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAnchorView(R.id.fab)
//                        .setAction("Action", null).show();
//            }
//        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}