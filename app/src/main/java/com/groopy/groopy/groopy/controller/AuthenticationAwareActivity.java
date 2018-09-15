package com.groopy.groopy.groopy.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationAwareActivity extends AppCompatActivity {
    // User authentication listener in Firebase
    protected FirebaseAuth.AuthStateListener _authListener;

    private void onUserSignedIn(FirebaseUser user) {
        Log.d(getTag(), "authenticationState: user is authenticated.");

        boolean isEmailVerified = user.isEmailVerified();
        if (isEmailVerified) {
            onUserSignedInVerified(user);
        } else // Email is not yet verified
        {
            onUserSignedInNotVerified(user);
        }
    }

    private void initializeDataBase() {
        FirebaseApp.initializeApp(this);
        setupFirebaseAuth();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeDataBase();
    }

    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        initializeDataBase();
    }

    private void setupFirebaseAuth() {
        Log.d(getTag(), "authenticationState: started Firebase setup.");

        _authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d(getTag(), "authenticationState: on authentication change event fired.");
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    onUserSignedIn(user);
                } else {
                    onUserSignedOut();
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(_authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (_authListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(_authListener);
        }
    }

    public void checkAuthenticationState() {
        Log.d(getTag(), "checkAuthenticationState: force user authentication changed event.");
        if (_authListener != null) {
            _authListener.onAuthStateChanged(FirebaseAuth.getInstance());
        }
    }

    public void onUserSignedInVerified(FirebaseUser user) {
        Log.d(getTag(), "authenticationState: signed_in:" + user.getUid());
        Toast.makeText(AuthenticationAwareActivity.this, "Authenticated with: " + user.getEmail(), Toast.LENGTH_SHORT).show();
    }

    public void onUserSignedInNotVerified(FirebaseUser user) {
        Toast.makeText(AuthenticationAwareActivity.this, "Email is not Verified\nCheck your Inbox", Toast.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().signOut();
    }

    public void onUserSignedOut() {
        Log.d(getTag(), "authenticationState:signed_out");
    }

    public void signOut() {
        Log.d(getTag(), "authenticationState: executing sign out");
        FirebaseAuth.getInstance().signOut();
    }

    public String getTag() {
        return "AuthenticationAwareActivity";
    }
}
