package com.pis.buy2gether.model.domain.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.services.FirebaseFactory;
import com.pis.buy2gether.provider.services.FirebaseImages;
import java.io.ByteArrayOutputStream;

public enum ImageData {
    INSTANCE;
    private final long ONE_MEGABYTE = 1024 * 1024;
    private final String path_grup = "groupImages";
    private final String path_profile = "profileImages";
    private final FirebaseImages firebaseImages = (FirebaseImages) FirebaseFactory.INSTANCE.getFirebase("FirebaseImages");

    public void saveGrupPhoto(String grupID, String photo_path){
        String path = path_grup + "/" + grupID + ".jpg";
        firebaseImages.saveImageUri(photo_path, path);
    }

    public void saveGrupPhoto(String grupID, Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        String path = path_grup + "/" + grupID + ".jpg";
        firebaseImages.saveImageBitmap(baos.toByteArray(), path);
    }

    public void saveProfilePhoto(String photo_path){
        String path = path_grup + "/" + Session.INSTANCE.getCurrentUserID() + ".jpg";
        firebaseImages.saveImageUri(photo_path, path);
    }

    public void saveProfilePhoto(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        String path = path_grup + "/" + Session.INSTANCE.getCurrentUserID() + ".jpg";
        firebaseImages.saveImageBitmap(baos.toByteArray(), path);
    }

    public MutableLiveData<Bitmap> getGrupPhoto(String grupID){
        String path = path_grup + "/" + grupID + ".jpg";
        StorageReference data = firebaseImages.getImage(path);
        MutableLiveData<Bitmap> liveData = new MutableLiveData<Bitmap>();

        data.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                byte[] dataPx = bytes;
                Bitmap bitmap = BitmapFactory.decodeByteArray(dataPx, 0, dataPx.length);
                liveData.setValue(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        return liveData;
    }

    public MutableLiveData<Bitmap> getProfilePhoto(){
        String path = path_profile +"/"+ Session.INSTANCE.getCurrentUserID() + ".jpg";
        StorageReference data = firebaseImages.getImage(path);
        MutableLiveData<Bitmap> liveData = new MutableLiveData<Bitmap>();

        data.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                byte[] dataPx = bytes;
                Bitmap bitmap = BitmapFactory.decodeByteArray(dataPx, 0, dataPx.length);
                liveData.setValue(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                getUnknown().getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        byte[] dataPx = bytes;
                        Bitmap bitmap = BitmapFactory.decodeByteArray(dataPx, 0, dataPx.length);
                        liveData.setValue(bitmap);
                    }
                });
            }
        });
        return liveData;
    }

    public StorageReference getUnknown(){
        String path = path_profile + "/unknown.jpg";
        return firebaseImages.getImage(path);
    }



    public MutableLiveData<Bitmap> getProfilePhoto(String pathPhoto){
        String path = path_profile + "/" + pathPhoto + ".jpg";
        StorageReference data = firebaseImages.getImage(path);
        MutableLiveData<Bitmap> liveData = new MutableLiveData<Bitmap>();

        data.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                byte[] dataPx = bytes;
                Bitmap bitmap = BitmapFactory.decodeByteArray(dataPx, 0, dataPx.length);
                liveData.setValue(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                getUnknown().getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        byte[] dataPx = bytes;
                        Bitmap bitmap = BitmapFactory.decodeByteArray(dataPx, 0, dataPx.length);
                        liveData.setValue(bitmap);
                    }
                });
            }
        });
        return liveData;
    }



}
