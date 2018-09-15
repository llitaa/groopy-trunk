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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.groopy.groopy.groopy.R;


public class SettingsActivity extends AuthenticationAwareActivity {
    private static final String DOMAIN_NAME = "tabian.ca";

    //widgets
    private EditText mEmail, mCurrentPassword;
    private Button mSave;
    private ProgressBar mProgressBar;
    private TextView mResetPasswordLink;

    @Override
    public String getTag() {
        return "SettingsActivity";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.d(getTag(), "onCreate: started.");
        mEmail = findViewById(R.id.input_email);
        mCurrentPassword = findViewById(R.id.input_password);
        mSave = findViewById(R.id.btn_save);
        mProgressBar = findViewById(R.id.progressBar);
        mResetPasswordLink = findViewById(R.id.change_password);

        setCurrentEmail();

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(getTag(), "onClick: attempting to save settings.");

                //make sure email and current password fields are filled
                if (!isEmpty(mEmail.getText().toString())
                        && !isEmpty(mCurrentPassword.getText().toString())) {

                    /*
                    ------ Change Email Task -----
                     */
                    //if the current email doesn't equal what's in the EditText field then attempt
                    //to edit
                    if (!FirebaseAuth.getInstance().getCurrentUser().getEmail()
                            .equals(mEmail.getText().toString())) {

                        editUserEmail();

                    } else {
                        Toast.makeText(SettingsActivity.this, "no changes were made", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(SettingsActivity.this, "Email and Current Password Fields Must be Filled to Save", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mResetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(getTag(), "onClick: sending password reset link");

                /*
                ------ Reset Password Link -----
                */
                sendResetPasswordLink();
            }
        });


        hideSoftKeyboard();
    }

    private void sendResetPasswordLink() {
        FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(getTag(), "onComplete: Password Reset Email sent.");
                            Toast.makeText(SettingsActivity.this, "Sent Password Reset Link to Email",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(getTag(), "onComplete: No user associated with that email.");

                            Toast.makeText(SettingsActivity.this, "No User Associated with that Email.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void editUserEmail() {
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.

        showDialog();

        AuthCredential credential = EmailAuthProvider
                .getCredential(FirebaseAuth.getInstance().getCurrentUser().getEmail(), mCurrentPassword.getText().toString());
        Log.d(getTag(), "editUserEmail: reauthenticating with:  \n email " + FirebaseAuth.getInstance().getCurrentUser().getEmail()
                + " \n passowrd: " + mCurrentPassword.getText().toString());


        FirebaseAuth.getInstance().getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(getTag(), "onComplete: reauthenticate success.");

                            //make sure the domain is valid

                            ///////////////////now check to see if the email is not already present in the database
                            FirebaseAuth.getInstance().fetchProvidersForEmail(mEmail.getText().toString()).addOnCompleteListener(
                                    new OnCompleteListener<ProviderQueryResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<ProviderQueryResult> task) {

                                            if (task.isSuccessful()) {
                                                ///////// getProviders().size() will return size 1 if email ID is in use.

                                                Log.d(getTag(), "onComplete: RESULT: " + task.getResult().getProviders().size());
                                                if (task.getResult().getProviders().size() == 1) {
                                                    Log.d(getTag(), "onComplete: That email is already in use.");
                                                    hideDialog();
                                                    Toast.makeText(SettingsActivity.this, "That email is already in use", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    Log.d(getTag(), "onComplete: That email is available.");

                                                    /////////////////////add new email
                                                    FirebaseAuth.getInstance().getCurrentUser().updateEmail(mEmail.getText().toString())
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Log.d(getTag(), "onComplete: User email address updated.");
                                                                        Toast.makeText(SettingsActivity.this, "Updated email", Toast.LENGTH_SHORT).show();
                                                                        sendVerificationEmail();
                                                                        FirebaseAuth.getInstance().signOut();
                                                                    } else {
                                                                        Log.d(getTag(), "onComplete: Could not update email.");
                                                                        Toast.makeText(SettingsActivity.this, "unable to update email", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    hideDialog();
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    hideDialog();
                                                                    Toast.makeText(SettingsActivity.this, "unable to update email", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });


                                                }

                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            hideDialog();
                                            Toast.makeText(SettingsActivity.this, "unable to update email", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            Log.d(getTag(), "onComplete: Incorrect Password");
                            Toast.makeText(SettingsActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                            hideDialog();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideDialog();
                        Toast.makeText(SettingsActivity.this, "“unable to update email”", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * sends an email verification link to the user
     */
    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SettingsActivity.this, "Sent Verification Email", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SettingsActivity.this, "Couldn't Verification Send Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void setCurrentEmail() {
        Log.d(getTag(), "setCurrentEmail: setting current email to EditText field");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            Log.d(getTag(), "setCurrentEmail: user is NOT null.");

            String email = user.getEmail();

            Log.d(getTag(), "setCurrentEmail: got the email: " + email);

            mEmail.setText(email);
        }
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

    /**
     * Return true if the @param is null
     *
     * @param string
     * @return
     */
    private boolean isEmpty(String string) {
        return string.equals("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAuthenticationState();
    }

    @Override
    public void onUserSignedOut() {
        Log.d(getTag(), "authenticationState: user is null, navigating back to login screen.");
        Toast.makeText(SettingsActivity.this, "Signed out", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
















