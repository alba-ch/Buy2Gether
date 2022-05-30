package com.pis.buy2gether.provider.services;

import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.pis.buy2gether.model.domain.pojo.Profile;
import com.pis.buy2gether.model.session.Session;

public class FirebaseAuthentification extends Firebase{

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public FirebaseAuthentification(){
        super();
    }

    public Task<AuthResult> googleLogIn(GoogleSignInAccount account){
        if(account != null) {
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
            return FirebaseAuth.getInstance().signInWithCredential(credential);
        }
        return null;
    }

    public void saveUser(Profile p){
        db.collection("users").document(Session.INSTANCE.getCurrentUserID()).set(p);
    }


    public String getUUID() {
        return firebaseAuth.getCurrentUser().getUid();
    }

    public boolean checkSession(){
        return firebaseAuth.getCurrentUser() == null;
    }

    public Task<AuthResult> emailLogIn(String email, String psw){
        return firebaseAuth.signInWithEmailAndPassword(email,psw);
    }

    public Task<AuthResult> emailSignIn(String email, String psw){
        return firebaseAuth.createUserWithEmailAndPassword(email,psw);
    }

}
