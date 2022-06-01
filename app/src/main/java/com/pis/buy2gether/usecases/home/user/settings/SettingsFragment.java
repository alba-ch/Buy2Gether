package com.pis.buy2gether.usecases.home.user.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentSettingsBinding;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.ProviderType;
import com.pis.buy2gether.usecases.home.MainActivity;
import com.pis.buy2gether.usecases.home.user.UserFragment;
import com.pis.buy2gether.usecases.onboarding.log_in.LoginActivity;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private SettingsViewModel viewModel;
    private FragmentSettingsBinding binding;

    private final int TAKE_IMAGE_CODE = 10001;
    private final int GALLERY_IMAGE_CODE = 1000;

    Button btn_change_image, btn_check_image, btn_cancel_avatar_dialog;
    ImageButton btn_cancel_changepsw_dialog,btn_save_change_psw,btn_cancel_check_img;
    ShapeableImageView btn_edit_pfp;
    EditText userName,userCity,new_psw,confirm_psw;
    ImageView img_check_avatar;

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
        binding.btnChangePsw.setOnClickListener(this::onClick);

        userName = binding.editUsername;
        userCity = binding.editCity;

        setUserPfp();
        update_Username();
        update_UserCity();

        return root;
    }

    /**
     * configura la imatge de perfil del boto UserPfp
     */
    private void setUserPfp(){
        MutableLiveData<Bitmap> data = viewModel.getProfilePhoto();
        data.observe(requireActivity(), bm ->{
            binding.btnEditPfp.setImageBitmap(bm);
        });
    }

    /**
     * creem l'alert dialog per configurar la imatge de l'avatar
     * @return avatar dialog
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

    /**
     * creem l'alert dialog per configurar la contrasenya
     * @return change_psw dialog
     */
    private AlertDialog setupChangePswPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View changePswPopupView = getLayoutInflater().inflate(R.layout.alert_dialog_change_psw,null);

        btn_cancel_changepsw_dialog = changePswPopupView.findViewById(R.id.btn_cancel_psw_dialog);
        btn_cancel_changepsw_dialog = changePswPopupView.findViewById(R.id.btn_cancel_psw_dialog);
        new_psw = changePswPopupView.findViewById(R.id.edtxt_nova_psw);
        confirm_psw = changePswPopupView.findViewById(R.id.edtxt_confirm_nova_psw);
        btn_save_change_psw = changePswPopupView.findViewById(R.id.btn_save_change_psw);

        builder.setView(changePswPopupView);

        AlertDialog changePsw_dialog = builder.create();
        changePsw_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        changePsw_dialog.show();

        return changePsw_dialog;
    }

    private AlertDialog setupCheckImagePopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View checkImgPopupView = getLayoutInflater().inflate(R.layout.activity_check_image,null);

        btn_cancel_check_img = checkImgPopupView.findViewById(R.id.btn_cancel_checkimg_dialog);
        img_check_avatar = checkImgPopupView.findViewById(R.id.img_check_avatar);

        check_Userimage(img_check_avatar);

        builder.setView(checkImgPopupView);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        return dialog;
    }

    private void check_Userimage(ImageView avatar){
        //get drawable from imageButton
         Drawable image = binding.btnEditPfp.getDrawable();
        //set image_view of alert dialog to image
        avatar.setImageDrawable(image);
    }

    /**
     * CHECK IMAGE CREANT +NOVA ACTIVITAT
     * Pass extraData containing image URI to CheckImageActivity and start it
     */
    private void showCheckImage(){
        Intent intent = new Intent(getActivity(), CheckImageActivity.class);
        intent.putExtra("imageUri",binding.btnEditPfp.toString());
        startActivity(intent);
    }

    /**
     * mètode per canviar la imatge de perfil de l'usuari
     */
    private void change_Userimage(){
        showMediaDialog();
    }

    private void showMediaDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setNeutralButton("CÀMERA",new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                //ACTION_IMAGE_CAPTURE:intent action to have the camera application to capture image and return to it
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(intent.resolveActivity(getContext().getPackageManager()) != null){
                    startActivityForResult(intent, TAKE_IMAGE_CODE);
                }
            }
        });

        builder.setPositiveButton("GALERIA",new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                //ACTION PICK: pick the item in data and return
                Intent intent = new Intent(Intent.ACTION_PICK);
                //we choose a concret data to sent
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if(intent.resolveActivity(getContext().getPackageManager()) != null){
                    startActivityForResult(intent, GALLERY_IMAGE_CODE);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(700, 300);
    }

    void saveUserImageDB(Bitmap bitmap){
        viewModel.saveImage(bitmap);
        Toast.makeText(getActivity(),"Imatge guardada amb èxit",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //foto des de càmera
        if(requestCode == TAKE_IMAGE_CODE){
            switch (resultCode){
                case RESULT_OK:
                    //get image captured
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    binding.btnEditPfp.setImageBitmap(bitmap);
                    saveUserImageDB(bitmap);
                    break;
            }
        }
        //foto des de galeria
        if(requestCode == GALLERY_IMAGE_CODE){
            binding.btnEditPfp.setImageURI(data.getData());
            try {
                saveUserImageDB((MediaStore.Images.Media.getBitmap(this.requireContext().getContentResolver(), data.getData())));
            } catch (IOException e) {
                Toast.makeText(getActivity(),"Error: La imatge no s'ha pogut carregar\n"+e,Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * actualitza el nom de l'usuari si auqest existeix en base de dades
     */
    private void update_Username(){
        Task<DocumentSnapshot> task = viewModel.update_UserInformation().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.get("username").toString() != null) {
                    binding.editUsername.setText(documentSnapshot.get("username").toString());
                }
            }
        });
    }

    private void update_UserCity(){
        Task<DocumentSnapshot> task = viewModel.update_UserInformation().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.get("city") != null) {
                    binding.editCity.setText(documentSnapshot.get("city").toString());
                }
            }
        });
    }

    /**
     * actualitza el nom de l'usuari
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

    /**
     * actualitza la contrasenya del compte de l'usuari
     * @param nova_psw
     */
    private void change_UserPassword(String nova_psw){
        FirebaseAuth.getInstance().getCurrentUser().updatePassword(nova_psw).addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Password Update", task.getException() + "");
                    Toast.makeText(getActivity(), "Password update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Tanquem sessió
     */
    private void sign_out(){
        // Eliminem dades guardades localment de l'usuari (email i contrasenya)
        SharedPreferences prefs = getActivity().getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
        viewModel.clearSession();
        // Tanquem sessió
        FirebaseAuth.getInstance().signOut();
        // Mostrem missatge d'èxit per confirmar que s'ha tancat sessió
        showSuccessAlert();
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        this.getActivity().finish();

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
                sign_out();
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
                    AlertDialog check_img_alert_dialog = setupCheckImagePopup();
                    btn_cancel_check_img.setOnClickListener(view1 -> {
                        check_img_alert_dialog.dismiss();
                    });
                });

                btn_change_image.setOnClickListener(v -> {
                    change_Userimage();
                });
                btn_cancel_avatar_dialog.setOnClickListener(v -> {
                    avatar_alert_dialog.dismiss();
                });
                break;
            case R.id.btn_change_psw:
                AlertDialog changePsw_alert_dialog = setupChangePswPopup();
                btn_cancel_changepsw_dialog.setOnClickListener(v->{
                    changePsw_alert_dialog.dismiss();
                });
                btn_save_change_psw.setOnClickListener(v->{

                    String nova_contrasenya = new_psw.getText().toString();
                    String confirm_contrasenya = confirm_psw.getText().toString();

                    //comprovem si algun dels camps està buit
                    if(nova_contrasenya.isEmpty() && confirm_contrasenya.isEmpty()){
                        Toast.makeText(getActivity(), "Els camps no poden estar buits!", Toast.LENGTH_SHORT).show();
                    }else{
                        //si les dues contrasenyes no coincideixen
                        if(!nova_contrasenya.equals(confirm_contrasenya)){
                            Toast.makeText(getActivity(), "Les contrasenyes han de ser les mateixes!", Toast.LENGTH_SHORT).show();
                        //canviem la contrasenya
                        }else{
                            change_UserPassword(confirm_contrasenya);
                            //Tanquem sessio
                            sign_out();
                        }
                    }
                    //?
                    changePsw_alert_dialog.dismiss();
                });

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