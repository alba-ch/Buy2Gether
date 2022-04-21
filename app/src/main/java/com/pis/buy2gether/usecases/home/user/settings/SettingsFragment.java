package com.pis.buy2gether.usecases.home.user.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentSettingsBinding;
import com.pis.buy2gether.databinding.FragmentUserBinding;
import com.pis.buy2gether.usecases.home.user.UserFragment;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private SettingsViewModel viewModel;
    private FragmentSettingsBinding binding;

    private int TAKE_IMAGE_CODE = 10001;

    Button btn_change_image, btn_check_image, btn_cancel_avatar_dialog,btn_confirm_username,btn_confirm_usercity;
    ShapeableImageView btn_edit_pfp;
    EditText userName,userCity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        viewModel = new SettingsViewModel(getContext());

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.btnReturn.setOnClickListener(this::onClick);
        binding.btnEditPfp.setOnClickListener(this::onClick);
        binding.btnChangePsw.setOnClickListener(this::onClick);
        binding.textSignout.setOnClickListener(this::onClick);
        binding.btnConfirmUsername.setOnClickListener(this::onClick);
        binding.btnConfirmUsercity.setOnClickListener(this::onClick);
        userName = binding.editUsername;
        userCity = binding.editCity;

        setUserPfp();

        return root;
    }

    private void setUserPfp(){
        StorageReference mImageRef =
                FirebaseStorage.getInstance().getReference("profileImages/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+".jpeg");
        final long ONE_MEGABYTE = 1024 * 1024;
        mImageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                DisplayMetrics dm = new DisplayMetrics();
                binding.btnEditPfp.setImageBitmap(bm);
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity(),"Error al carregar l'imatge de perfil\n"+exception,Toast.LENGTH_SHORT).show();
                    }
        });
    }

    /**
     * creem l'alert dialog per configurar la imatge de l'avatar
     * @return
     */
    private AlertDialog setupAvatarConfigurationPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View avatarPopupView = getLayoutInflater().inflate(R.layout.alert_dialog_avatar,null);

        btn_check_image = avatarPopupView.findViewById(R.id.btn_check_image);
        btn_change_image = avatarPopupView.findViewById(R.id.btn_change_image);
        btn_cancel_avatar_dialog = avatarPopupView.findViewById(R.id.btn_cancel_avatar_dialog);

        builder.setView(avatarPopupView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        return dialog;
    }

    private void check_Userimage(){

    }

    private void change_Userimage(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getContext().getPackageManager()) != null){
            startActivityForResult(intent, TAKE_IMAGE_CODE);
        }

    }

    void saveUserImageDB(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);

        String userID = FirebaseAuth.getInstance().getUid();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                .child("profileImages")
                .child(userID+".jpeg");

        storageRef.putBytes(baos.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getDownloadURL(storageRef);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                showFailureAlert(e);
            }
        });
    }

    private void getDownloadURL(StorageReference ref){
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(uri).build();
                user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        FirebaseAuth.getInstance().getCurrentUser().updateProfile(request);
                        Toast.makeText(getActivity(),"Imatge guardada amb èxit",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getActivity(),"Error: Imatge no guardada\n"+e,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == TAKE_IMAGE_CODE){
            switch (resultCode){
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    binding.btnEditPfp.setImageBitmap(bitmap);
                    saveUserImageDB(bitmap);
            }
        }
    }
    /**
     * actualitza eel nom de l'usuari
     * @param nom
     */
    private void change_Username(String nom){
        viewModel.change_Username(nom);
    }

    /**
     * actualitza la localització de l'usuari
     * @param ciutat
     */
    private void change_Usercity(String ciutat){
        viewModel.change_UserCity(ciutat);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void showSuccessAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Sessió tancada amb èxit");
        builder.setPositiveButton("Acceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showFailureAlert(Exception e){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("ERROR: Imatge no guardada\n"+e);
        builder.setPositiveButton("Acceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_signout:
                // Eliminem dades guardades localment de l'usuari (email i contrasenya)
                SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
                prefs.edit().clear().apply();

                viewModel.clearSession();
                // Tanquem sessió
                FirebaseAuth.getInstance().signOut();
                // Mostrem missatge d'èxit per confirmar que s'ha tancat sessió
                showSuccessAlert();
                break;
            case R.id.btn_return:
                Toast.makeText(getActivity(),"RETURN",Toast.LENGTH_SHORT).show();
                /* Canviem de fragment a user options */
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.settings, new UserFragment());
                fragmentTransaction.commit();
                break;
            case R.id.btn_edit_pfp:
                AlertDialog avatar_alert_dialog = setupAvatarConfigurationPopup();
                btn_check_image.setOnClickListener(v -> {
                    avatar_alert_dialog.dismiss();
                });
                btn_change_image.setOnClickListener(v -> {
                    Toast.makeText(getActivity(), "Change image", Toast.LENGTH_SHORT).show();
                    change_Userimage();
                });
                btn_cancel_avatar_dialog.setOnClickListener(v -> {
                    avatar_alert_dialog.dismiss();
                });
                break;
            case R.id.btn_change_psw:
                Toast.makeText(getActivity(), "CANVI CONTRASENYA", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_confirm_username:
                Toast.makeText(getActivity(), "Username changed", Toast.LENGTH_SHORT).show();
                change_Username(userName.getText().toString());
                break;
            case R.id.btn_confirm_usercity:
                Toast.makeText(getActivity(), "City changed", Toast.LENGTH_SHORT).show();
                change_Usercity(userCity.getText().toString());
                break;
            default:
                break;
        }
    }

}