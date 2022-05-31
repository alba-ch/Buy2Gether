package com.pis.buy2gether.usecases.home.home.product_view.group.creation;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.R;
import com.pis.buy2gether.model.domain.data.ImageData;
import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.domain.pojo.Grup.Category;
import com.pis.buy2gether.usecases.home.home.product_view.group.share.FriendListAdapter;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CLIPBOARD_SERVICE;


public class GroupCreationFragment extends Fragment implements View.OnClickListener, FriendListAdapter.ItemClickListener {
    private FriendListAdapter friendListAdapter;
    private GroupCreationViewModel groupCreationViewModel;

    private final int TAKE_IMAGE_CODE = 10001;
    private final int GALLERY_IMAGE_CODE = 1000;
    private int groupVisibility = 0;
    private String groupID = "";
    private String defaultMessage = "Acabo de crear un grup de Buy2Gether i m'agradaria que compréssim el producte junts!";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        groupCreationViewModel =
                new ViewModelProvider(this).get(GroupCreationViewModel.class);

        View view = inflater.inflate(R.layout.fragment_group_creation, container, false);
        Button submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);
        view.setClickable(true);
        view.setOnClickListener(this);

        ImageView otherUsers = view.findViewById(R.id.otherUsers);

        otherUsers.setClickable(true);
        otherUsers.setFocusable(true);
        otherUsers.setOnClickListener(this);

        ImageView groupFoto = view.findViewById(R.id.imageView);
        groupFoto.setClickable(true);
        groupFoto.setOnClickListener(this);
        Spinner type = view.findViewById(R.id.type);
        Button publicButton = view.findViewById(R.id.publicButton);
        Button hiddenButton = view.findViewById(R.id.hiddenButton);
        Button privateButton = view.findViewById(R.id.privateButton);


        String[] items = new String[Category.values().length];

        for (Category category : Category.values()) {
            items[category.ordinal()] = category.toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        type.setAdapter(adapter);
        // set up the RecyclerView
        setList(view);
        //recyclerView.setAdapter(friendListAdapter);
        //binding.groupPopup.friendList.setAdapter(friendListAdapter);
        publicButton.setOnClickListener(this);
        privateButton.setOnClickListener(this);
        hiddenButton.setOnClickListener(this);

        ImageView codiImage = view.findViewById(R.id.codi_image);
        ImageView moreInformation = view.findViewById(R.id.moreinformation);
        ImageView shareMessages = view.findViewById(R.id.sharemessages);

        RelativeLayout shr = view.findViewById(R.id.shareDummy);
        shr.setOnClickListener(this);
        codiImage.setOnClickListener(this);
        moreInformation.setOnClickListener(this);
        shareMessages.setOnClickListener(this);

        privateButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));

        return view;
    }
    @Override
    public void onItemClick(View view, int position) {
        groupCreationViewModel.SendInvite(friendListAdapter.getUser(position).getId(),groupID);
    }

    private void setList(View view){
        RecyclerView recyclerView = view.findViewById(R.id.friendList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        friendListAdapter = new FriendListAdapter(getContext(),this);
        recyclerView.setAdapter(friendListAdapter);

    }

    @Override
    public void onDestroyView() {
        getActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //foto des de càmera
        if(requestCode == TAKE_IMAGE_CODE){
            switch (resultCode){
                case RESULT_OK:
                    ImageView imageView = getView().findViewById(R.id.imageView);
                    //get image captured
                    Bitmap bitmap = Bitmap.createScaledBitmap((Bitmap) data.getExtras().get("data"), imageView.getWidth(), imageView.getHeight(), true);
                    imageView.setImageBitmap(bitmap);
                    break;
            }
        }
        //foto des de galeria
        if(requestCode == GALLERY_IMAGE_CODE){
            try {
                ImageView imageView = getView().findViewById(R.id.imageView);
                imageView.setImageBitmap(Bitmap.createScaledBitmap((MediaStore.Images.Media.getBitmap(this.requireContext().getContentResolver(), data.getData())), imageView.getWidth(), imageView.getHeight(), true));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void showFailureAlert(Exception e){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("ERROR: Imatge no guardada\n"+e);
        builder.setPositiveButton("Acceptar",null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveGroupImageDB(Bitmap bitmap){
        ImageData.INSTANCE.saveGrupPhoto(groupID,bitmap);
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
    @Override
    public void onClick(View view) {
        boolean process = view.getId() != R.id.shareDummy;


        Spinner type = view.findViewById(R.id.type);
        EditText name = view.findViewById(R.id.Name);
        EditText link = view.findViewById(R.id.link);
        EditText usersLimit = view.findViewById(R.id.usersLimit);
        EditText originalPriceET = view.findViewById(R.id.originalPrice);
        Button privateButton = view.findViewById(R.id.privateButton);
        Button hiddenButton = view.findViewById(R.id.hiddenButton);
        Button publicButton = view.findViewById(R.id.publicButton);
        View groupPopUp = view.findViewById(R.id.group_popup);
        Button submitButton = view.findViewById(R.id.submitButton);
        ImageView otherUsers = view.findViewById(R.id.otherUsers);
        ImageView sharemessages = view.findViewById(R.id.sharemessages);
        ImageView moreInformation = view.findViewById(R.id.moreinformation);
        RelativeLayout shareDummy = view.findViewById(R.id.shareDummy);

        RecyclerView friendList = view.findViewById(R.id.friendList);

        ImageView codiImage = view.findViewById(R.id.codi_image);
        TextView codiInvitacio = view.findViewById(R.id.codi_invitacio);
        EditText linkText = view.findViewById(R.id.linkText);

        ImageView groupFoto = view.findViewById(R.id.imageView);
        switch (view.getId()){
            case R.id.privateButton:
                privateButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));
                hiddenButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                publicButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                groupVisibility = 0;
                process = false;
                break;
            case R.id.hiddenButton:
                hiddenButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));
                publicButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                privateButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                groupVisibility = 1;
                process = false;
                break;
            case R.id.publicButton:
                hiddenButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                publicButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));
                privateButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                groupVisibility = 2;
                process = false;
                break;
            case R.id.submitButton:
                String prodName = name.getText().toString();
                String prodLink = link.getText().toString();
                String userLimit = usersLimit.getText().toString();
                String originalPrice = originalPriceET.getText().toString();
                if(!(prodName.isEmpty()) && !(prodLink.isEmpty()) && !(userLimit.isEmpty()) && !(originalPrice.isEmpty()) ){
                    groupID = groupCreationViewModel.createGroupDB(prodName, Category.valueOf(type.getSelectedItem().toString()), Double.parseDouble(originalPrice),groupVisibility,groupCreationViewModel.getUser());
                    saveGroupImageDB(((BitmapDrawable) groupFoto.getDrawable()).getBitmap());
                }else {
                    if(prodName.isEmpty())
                        name.startAnimation(groupCreationViewModel.shakeError());
                    if(prodLink.isEmpty())
                        link.startAnimation(groupCreationViewModel.shakeError());
                    if(userLimit.isEmpty())
                        usersLimit.startAnimation(groupCreationViewModel.shakeError());
                    if(originalPrice.isEmpty())
                        originalPriceET.startAnimation(groupCreationViewModel.shakeError());
                }

                break;
            case R.id.codi_image:
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("GroupCode", groupID);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getActivity(),"Codi copiat",Toast.LENGTH_SHORT).show();
                break;
            case R.id.moreinformation:

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, defaultMessage+groupID);
                startActivity(Intent.createChooser(intent, "Share via"));
                break;
            case R.id.sharemessages:

                Intent intentSMS = new Intent();
                intentSMS.setAction(Intent.ACTION_SENDTO);

                intentSMS.setType("text/plain");
                intentSMS.setData(Uri.parse("smsto:"));
                intentSMS.putExtra("sms_body",defaultMessage+groupID);
                startActivity(intentSMS);
                break;
            case R.id.imageView:
                showMediaDialog();
                break;
            default:
                break;
        }
        if(process) {
            if (view.getId() == getView().getId()) {
                otherUsers.setClickable(false);
                otherUsers.setFocusable(false);

                sharemessages.setClickable(false);
                sharemessages.setFocusable(false);
                codiImage.setClickable(false);
                codiImage.setFocusable(false);
                codiInvitacio.setClickable(false);
                codiInvitacio.setFocusable(false);
                linkText.setClickable(false);
                linkText.setFocusable(false);
                moreInformation.setClickable(false);
                moreInformation.setFocusable(false);
                friendList.setFocusable(false);
                friendList.setClickable(false);
                submitButton.setVisibility(View.VISIBLE);
                shareDummy.setClickable(false);
                shareDummy.setFocusable(false);
                groupPopUp.setVisibility(View.INVISIBLE);
            } else if (view.getId() == otherUsers.getId()) {
                codiImage.setVisibility(View.INVISIBLE);
                codiImage.setClickable(false);
                codiImage.setFocusable(false);
                codiInvitacio.setVisibility(View.INVISIBLE);
                codiInvitacio.setClickable(false);
                codiInvitacio.setFocusable(false);
                linkText.setVisibility(View.INVISIBLE);
                linkText.setClickable(false);
                linkText.setFocusable(false);
                moreInformation.setVisibility(View.INVISIBLE);
                moreInformation.setClickable(false);
                moreInformation.setFocusable(false);
                sharemessages.setVisibility(View.INVISIBLE);
                sharemessages.setClickable(false);
                sharemessages.setFocusable(false);
                otherUsers.setVisibility(View.INVISIBLE);
                otherUsers.setClickable(false);
                otherUsers.setFocusable(false);
                friendList.setVisibility(View.VISIBLE);
                friendList.setFocusable(true);
                friendList.setClickable(true);
            } else if (!groupID.isEmpty()){
                shareDummy.setClickable(true);
                shareDummy.setFocusable(true);

                otherUsers.setVisibility(View.VISIBLE);
                otherUsers.setClickable(true);
                otherUsers.setFocusable(true);

                sharemessages.setVisibility(View.VISIBLE);
                sharemessages.setClickable(true);
                sharemessages.setFocusable(true);
                codiImage.setVisibility(View.VISIBLE);
                codiImage.setClickable(true);
                codiImage.setFocusable(true);
                codiInvitacio.setVisibility(View.VISIBLE);
                codiInvitacio.setClickable(true);
                codiInvitacio.setFocusable(true);
                linkText.setText(groupID);
                linkText.setVisibility(View.VISIBLE);
                linkText.setClickable(true);
                linkText.setFocusable(true);
                moreInformation.setVisibility(View.VISIBLE);
                moreInformation.setClickable(true);
                moreInformation.setFocusable(true);
                friendList.setVisibility(View.INVISIBLE);
                friendList.setFocusable(false);
                friendList.setClickable(false);
                submitButton.setVisibility(View.INVISIBLE);
                groupPopUp.setVisibility(View.VISIBLE);
            }
        }
    }

}