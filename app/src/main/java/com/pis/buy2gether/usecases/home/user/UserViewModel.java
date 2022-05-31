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
import com.pis.buy2gether.model.domain.data.ImageData;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.ProviderType;

import java.security.Provider;

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
    public Boolean checkGuest(){
        return Session.INSTANCE.getProvider() != ProviderType.GUEST;
    }

    public void setup(View view){
        username = view.findViewById(R.id.txt_user);
        description = view.findViewById(R.id.txt_desc);
        img_pfp = view.findViewById(R.id.img_pfp);

        setUserPfp();
        setupUserInfo();
    }

    private void setupUserInfo(){
            username.setText(Session.INSTANCE.getDisplayName());
            description.setText(Session.INSTANCE.getMail());
    }

    private void setUserPfp(){
        if(Session.INSTANCE.getProvider() != ProviderType.GUEST){
        MutableLiveData<Bitmap> lifeData = ImageData.INSTANCE.getProfilePhoto();
        lifeData.observeForever(
                data ->{
                    img_pfp.setImageBitmap(data);
                }
        );
        }
    }
}