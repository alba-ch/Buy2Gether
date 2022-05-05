package com.pis.buy2gether.usecases.home.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pis.buy2gether.R;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.ProviderType;

public class UserViewModel extends ViewModel {

    TextView username;
    TextView description;
    ShapeableImageView img_pfp;

    private Session session;
    private Context context;

    UserViewModel(Context context){
        this.session = Session.INSTANCE;
        this.context = context;
    }

    public void setup(View view){
        username = view.findViewById(R.id.txt_user);
        description = view.findViewById(R.id.txt_desc);
        img_pfp = view.findViewById(R.id.img_pfp);

        setUserPfp();
        setupUserInfo();
    }

    private void setupUserInfo(){
        String provider = session.getDataSession(context,"provider");

        if(ProviderType.valueOf(provider) != ProviderType.GUEST) {
            String currentUser = session.getCurrentUserID();
            session.getUserByID(currentUser).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.get("username").toString() != null) username.setText(documentSnapshot.get("username").toString());
                    description.setText(documentSnapshot.get("email").toString());
                }
            });
        }
    }

    private void setUserPfp(){
        session.getCurrentUserPfpImageRef().getBytes(1024 * 1024)
          .addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                img_pfp.setImageBitmap(bm);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(context,"Error al carregar l'imatge de perfil\n"+exception,Toast.LENGTH_SHORT).show();
            }
        });
    }
}