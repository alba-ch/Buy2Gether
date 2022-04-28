package com.pis.buy2gether.usecases.home.user.settings;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.ActivityCheckImageBinding;

public class CheckImageActivity extends AppCompatActivity {
    private ActivityCheckImageBinding binding;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancelCheckimgDialog.setOnClickListener(view -> {
            //tanquem l'activitat
            finish();
        });
        //set image
        check_Userimage(binding.imgCheckAvatar);
    }

    /**
     * Get the extradata holding the Uri and set ImageView source
     * @param avatar
     */
    private void check_Userimage(ImageView avatar){
        //get data from imageButton from SettingsFragment
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String uriFromSettings = bundle.getString("imageUri");
            if(uriFromSettings != null){
                Uri imageUri = Uri.parse(uriFromSettings);
                    //set image_view source of alert dialog to image
                    //avatar.setImageURI(imageUri);
            }else{
                Toast.makeText(this,"uri nula",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"data nula",Toast.LENGTH_SHORT).show();
        }
    }
}
