package com.pis.buy2gether.model.domain.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.services.FirebaseFactory;
import com.pis.buy2gether.provider.services.FirebaseImages;
import java.io.ByteArrayOutputStream;

public enum ImageData {
    INSTANCE;

    private final String path_grup = "grupImages";
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

    public Bitmap getGrupPhoto(String grupID){
        String path = path_grup + "/" + grupID + ".jpg";
        byte[] data = firebaseImages.getImage(path);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        return bitmap;
    }

    public Bitmap getProfilePhoto(){
        String path = path_profile + "/" + Session.INSTANCE.getCurrentUserID() + ".jpg";
        byte[] data = firebaseImages.getImage(path);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        return bitmap;
    }

    public Bitmap getProfilePhoto(String pathPhoto){
        String path = path_profile + "/" + pathPhoto + ".jpeg";
        byte[] data = firebaseImages.getImage(path);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        return bitmap;
    }



}
