package com.pis.buy2gether.provider.services;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class FirebaseImages extends Firebase{
    private final long ONE_MEGABYTE = 1024 * 1024;

    public FirebaseImages(){
        super();
    }

    public void saveImageUri(String path, String reference){
        Uri uri = Uri.fromFile(new File(path));
        StorageReference storageReference = st.getReference().child(reference);
        storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
            Log.i("FirebaseImages", "Image saved");
        }).addOnFailureListener(e -> {
            throw new RuntimeException("Error al subir la imagen");
        });
    }

    public void saveImageBitmap(byte[] bytes, String reference){
        StorageReference storageReference = st.getReference().child(reference);
        storageReference.putBytes(bytes).addOnSuccessListener(taskSnapshot -> {
            Log.i("FirebaseImages", "Image saved");
        }).addOnFailureListener(e -> {
            throw new RuntimeException("Error al subir la imagen");
        });
    }


    public byte[] getImage(String path){
        StorageReference ref = st.getReference().child(path);
        final byte[][] data = new byte[1][1];
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                data[0] = bytes;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        return data[0];
    }
}
