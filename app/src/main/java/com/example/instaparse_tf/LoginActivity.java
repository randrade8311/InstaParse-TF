package com.example.instaparse_tf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }

        pd = new ProgressDialog(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignup);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "OnClick login button ");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username,password);

            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                createUser(username, password);
            }
        });
    }

    private void createUser(String username, String password) {
        new ProgressDialog(LoginActivity.this);
        pd.setMessage("Creating New User...");
        pd.show();

        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    goMainActivity();
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this,"Account made!", Toast.LENGTH_SHORT);
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e(TAG, "issue with login", e);
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this,"Account couldn't be made!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private void loginUser(String username, String password) {
        new ProgressDialog(LoginActivity.this);
        pd.setMessage("Logging in...");
        pd.show();
        Log.i(TAG,"Attempting to log in "+ username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, com.parse.ParseException e) {
                if(e != null){
                    Toast.makeText(LoginActivity.this,"Error with log in!", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "issue with login", e);
                    pd.dismiss();
                    return;
                }
                goMainActivity();
                pd.dismiss();
                Toast.makeText(LoginActivity.this,"Success!", Toast.LENGTH_SHORT);
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
