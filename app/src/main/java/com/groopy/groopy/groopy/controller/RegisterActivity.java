package com.groopy.groopy.groopy.controller;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.groopy.groopy.groopy.R;
import com.groopy.groopy.groopy.dataBase.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private EditText mEmail, mPassword, mConfirmPassword;
    private Button mRegister;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        mConfirmPassword = findViewById(R.id.input_confirm_password);
        mRegister = findViewById(R.id.btn_register);
        mProgressBar = findViewById(R.id.progressBar);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: attempting to register.");

                //check for null valued EditText fields
                if (!isEmpty(mEmail.getText().toString())
                        && !isEmpty(mPassword.getText().toString())
                        && !isEmpty(mConfirmPassword.getText().toString())) {

                    //check if user has a valid email address
                    if (isEmailValid(mEmail.getText().toString())) {

                        //check if passwords match
                        if (doStringsMatch(mPassword.getText().toString(), mConfirmPassword.getText().toString())) {

                            //Initiate registration task
                            registerNewEmail(mEmail.getText().toString(), mPassword.getText().toString());
                        } else {
                            Toast.makeText(RegisterActivity.this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Please Specify valid E-Mail address", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "You must fill out all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        hideSoftKeyboard();
    }

    /**
     * Register a new email and password to Firebase Authentication
     *
     * @param email
     * @param password
     */
    public void registerNewEmail(final String email, String password) {

        showDialog();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: AuthState: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                            sendVerificationEmail();

                            //insert some default data
                            String currentUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            User user = createDefaultUser(email, currentUserUID);
                            FirebaseDatabase.getInstance().getReference()
                                    .child(getString(R.string.dbnode_users))
                                    .child(currentUserUID)
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.d(TAG, "data base: successfully updated user data.");
                                            FirebaseAuth.getInstance().signOut();
                                            redirectLoginScreen();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "data base: failed to update user data.");
                                    Log.d(TAG, e.toString());
                                    Toast.makeText(RegisterActivity.this, "something went wrong.", Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    redirectLoginScreen();
                                }
                            });
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Unable to Register",
                                    Toast.LENGTH_SHORT).show();
                        }
                        hideDialog();
                    }
                });
    }

    /**
     * sends an email verification link to the user
     */
    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "Couldn't Verification Send Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    /**
     * Returns True if the user's email string is results in valid email address
     * @param emailStr
     * @return
     */
    static private boolean isEmailValid(String emailStr) {
        Log.d(TAG, "isEmailValid: verifying email string is valid: " + emailStr);

        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        boolean isValid = matcher.find();

        Log.d(TAG, "isEmailValid: is valid: " + isValid);
        return isValid;
    }

    /**
     * Redirects the user to the login screen
     */
    private void redirectLoginScreen() {
        Log.d(TAG, "redirectLoginScreen: redirecting to login screen.");

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Return true if @param 's1' matches @param 's2'
     *
     * @param s1
     * @param s2
     * @return
     */
    private boolean doStringsMatch(String s1, String s2) {
        return s1.equals(s2);
    }

    /**
     * Return true if the @param is null
     *
     * @param string
     * @return
     */
    private boolean isEmpty(String string) {
        return string.equals("");
    }

    private void showDialog() {
        mProgressBar.setVisibility(View.VISIBLE);

    }

    private void hideDialog() {
        if (mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    static private User createDefaultUser(final String email, final String uid)
    {
        User user = new User();
        user.setName(email.substring(0, email.indexOf("@")));
        user.setPhone("1");
        user.setProfile_image("");
        user.setUser_id(uid);
        return user;
    }
}
















