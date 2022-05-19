package com.pis.buy2gether.provider.services;


import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.pis.buy2gether.model.session.Session;

import java.io.IOException;
import java.util.List;

// Use the application default credentials


public class Firebase {
    protected final static FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected final static FirebaseStorage st = FirebaseStorage.getInstance();

    public Firebase() {

    }

    public Task<DocumentSnapshot> getUserInfo() {
        Task<DocumentSnapshot> documents = db.collection("users").document(Session.INSTANCE.getCurrentUserID()).get();
        return documents;
    }



}

