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

import android.widget.ArrayAdapter;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentGroupCreationBinding;
import com.pis.buy2gether.model.domain.data.ImageData;
import com.pis.buy2gether.model.domain.pojo.Grup.Category;
import com.pis.buy2gether.usecases.home.home.product_view.group.share.FriendListAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CLIPBOARD_SERVICE;


public class GroupCreationFragment extends Fragment implements View.OnClickListener, FriendListAdapter.ItemClickListener {
    private FriendListAdapter friendListAdapter;
    private GroupCreationViewModel groupCreationViewModel;
    private FragmentGroupCreationBinding binding;

    private final int TAKE_IMAGE_CODE = 10001;
    private final int GALLERY_IMAGE_CODE = 1000;
    private int groupVisibility = 0;
    private String groupID = "";
    private String defaultMessage = "Acabo de crear un grup de Buy2Gether i m'agradaria que compréssim el producte junts!";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        groupCreationViewModel =
                new ViewModelProvider(this).get(GroupCreationViewModel.class);

        binding = FragmentGroupCreationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.submitButton.setOnClickListener(this);
        root.setClickable(true);
        root.setOnClickListener(this);

        binding.groupPopup.otherUsers.setClickable(true);
        binding.groupPopup.otherUsers.setFocusable(true);
        binding.groupPopup.otherUsers.setOnClickListener(this);

        binding.imageView.setClickable(true);
        binding.imageView.setOnClickListener(this);


        String[] items = new String[Category.values().length];

        for (Category category : Category.values()) {
            items[category.ordinal()] = category.toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        binding.type.setAdapter(adapter);
        // set up the RecyclerView
        setList();
        //recyclerView.setAdapter(friendListAdapter);
        //binding.groupPopup.friendList.setAdapter(friendListAdapter);
        binding.publicButton.setOnClickListener(this);
        binding.privateButton.setOnClickListener(this);
        binding.hiddenButton.setOnClickListener(this);
        binding.groupPopup.codiImage.setOnClickListener(this);
        binding.groupPopup.moreinformation.setOnClickListener(this);
        binding.groupPopup.sharemessages.setOnClickListener(this);

        binding.privateButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));


        binding.groupPopup.shareDummy.setOnClickListener(this);
        return root;
    }
    @Override
    public void onItemClick(View view, int position) {
        groupCreationViewModel.SendInvite(friendListAdapter.getUserID(position),groupID);
        Toast.makeText(getActivity(), friendListAdapter.getUserID(position),Toast.LENGTH_SHORT).show();
    }

    private void setList(){
        RecyclerView recyclerView = binding.groupPopup.friendList;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        friendListAdapter = new FriendListAdapter(getContext(),this);
        recyclerView.setAdapter(friendListAdapter);
        binding.groupPopup.friendList.setAdapter(friendListAdapter);
        Task<QuerySnapshot> task = groupCreationViewModel.getFriends().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String[] id = documentSnapshot.getData().keySet().toArray(new String[0]);
                    String friendID = id[0].equals(groupCreationViewModel.getUser()) ? id[1] : id[0];
                    Task<DocumentSnapshot> task = groupCreationViewModel.getUserName(friendID).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                friendListAdapter.addUser(friendID,documentSnapshot.get("username").toString());

                        }
                    });
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        getActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //foto des de càmera
        if(requestCode == TAKE_IMAGE_CODE){
            switch (resultCode){
                case RESULT_OK:
                    //get image captured
                    Bitmap bitmap = Bitmap.createScaledBitmap((Bitmap) data.getExtras().get("data"), binding.imageView.getWidth(), binding.imageView.getHeight(), true);
                    binding.imageView.setImageBitmap(bitmap);
                    break;
            }
        }
        //foto des de galeria
        if(requestCode == GALLERY_IMAGE_CODE){
            try {
                binding.imageView.setImageBitmap(Bitmap.createScaledBitmap((MediaStore.Images.Media.getBitmap(this.requireContext().getContentResolver(), data.getData())), binding.imageView.getWidth(), binding.imageView.getHeight(), true));
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
        groupCreationViewModel.saveImage(bitmap, groupID);
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
        switch (view.getId()){
            case R.id.privateButton:
                binding.privateButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));
                binding.hiddenButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.publicButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                groupVisibility = 0;
                process = false;
                break;
            case R.id.hiddenButton:
                binding.hiddenButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));
                binding.publicButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.privateButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                groupVisibility = 1;
                process = false;
                break;
            case R.id.publicButton:
                binding.hiddenButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                binding.publicButton.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));
                binding.privateButton.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                groupVisibility = 2;
                process = false;
                break;
            case R.id.submitButton:
                String prodName = binding.Name.getText().toString();
                String prodLink = binding.link.getText().toString();
                String userLimit = binding.usersLimit.getText().toString();
                String originalPrice = binding.originalPrice.getText().toString();
                if(!(prodName.isEmpty()) && !(prodLink.isEmpty()) && !(userLimit.isEmpty()) && !(originalPrice.isEmpty()) ){
                    groupID = groupCreationViewModel.createGroupDB(prodName, prodLink, binding.type.getSelectedItem().toString(),Integer.parseInt(userLimit), Double.parseDouble(originalPrice),groupVisibility,groupCreationViewModel.getUser());
                    saveGroupImageDB(((BitmapDrawable) binding.imageView.getDrawable()).getBitmap());
                }else {
                    if(prodName.isEmpty())
                        binding.Name.startAnimation(groupCreationViewModel.shakeError());
                    if(prodLink.isEmpty())
                        binding.link.startAnimation(groupCreationViewModel.shakeError());
                    if(userLimit.isEmpty())
                        binding.usersLimit.startAnimation(groupCreationViewModel.shakeError());
                    if(originalPrice.isEmpty())
                        binding.originalPrice.startAnimation(groupCreationViewModel.shakeError());
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
            if (view.getId() == binding.getRoot().getId()) {
                binding.groupPopup.otherUsers.setClickable(false);
                binding.groupPopup.otherUsers.setFocusable(false);

                binding.groupPopup.sharemessages.setClickable(false);
                binding.groupPopup.sharemessages.setFocusable(false);
                binding.groupPopup.codiImage.setClickable(false);
                binding.groupPopup.codiImage.setFocusable(false);
                binding.groupPopup.codiInvitacio.setClickable(false);
                binding.groupPopup.codiInvitacio.setFocusable(false);
                binding.groupPopup.linkText.setClickable(false);
                binding.groupPopup.linkText.setFocusable(false);
                binding.groupPopup.moreinformation.setClickable(false);
                binding.groupPopup.moreinformation.setFocusable(false);
                binding.groupPopup.friendList.setFocusable(false);
                binding.groupPopup.friendList.setClickable(false);
                binding.submitButton.setVisibility(View.VISIBLE);
                binding.groupPopup.shareDummy.setClickable(false);
                binding.groupPopup.shareDummy.setFocusable(false);
                binding.groupPopup.getRoot().setVisibility(View.INVISIBLE);
            } else if (view.getId() == binding.groupPopup.otherUsers.getId()) {
                binding.groupPopup.codiImage.setVisibility(View.INVISIBLE);
                binding.groupPopup.codiImage.setClickable(false);
                binding.groupPopup.codiImage.setFocusable(false);
                binding.groupPopup.codiInvitacio.setVisibility(View.INVISIBLE);
                binding.groupPopup.codiInvitacio.setClickable(false);
                binding.groupPopup.codiInvitacio.setFocusable(false);
                binding.groupPopup.linkText.setVisibility(View.INVISIBLE);
                binding.groupPopup.linkText.setClickable(false);
                binding.groupPopup.linkText.setFocusable(false);
                binding.groupPopup.moreinformation.setVisibility(View.INVISIBLE);
                binding.groupPopup.moreinformation.setClickable(false);
                binding.groupPopup.moreinformation.setFocusable(false);
                binding.groupPopup.sharemessages.setVisibility(View.INVISIBLE);
                binding.groupPopup.sharemessages.setClickable(false);
                binding.groupPopup.sharemessages.setFocusable(false);
                binding.groupPopup.otherUsers.setVisibility(View.INVISIBLE);
                binding.groupPopup.otherUsers.setClickable(false);
                binding.groupPopup.otherUsers.setFocusable(false);
                binding.groupPopup.friendList.setVisibility(View.VISIBLE);
                binding.groupPopup.friendList.setFocusable(true);
                binding.groupPopup.friendList.setClickable(true);
            } else if (!groupID.isEmpty()){
                binding.groupPopup.shareDummy.setClickable(true);
                binding.groupPopup.shareDummy.setFocusable(true);

                binding.groupPopup.otherUsers.setVisibility(View.VISIBLE);
                binding.groupPopup.otherUsers.setClickable(true);
                binding.groupPopup.otherUsers.setFocusable(true);

                binding.groupPopup.sharemessages.setVisibility(View.VISIBLE);
                binding.groupPopup.sharemessages.setClickable(true);
                binding.groupPopup.sharemessages.setFocusable(true);
                binding.groupPopup.codiImage.setVisibility(View.VISIBLE);
                binding.groupPopup.codiImage.setClickable(true);
                binding.groupPopup.codiImage.setFocusable(true);
                binding.groupPopup.codiInvitacio.setVisibility(View.VISIBLE);
                binding.groupPopup.codiInvitacio.setClickable(true);
                binding.groupPopup.codiInvitacio.setFocusable(true);
                binding.groupPopup.linkText.setText(groupID);
                binding.groupPopup.linkText.setVisibility(View.VISIBLE);
                binding.groupPopup.linkText.setClickable(true);
                binding.groupPopup.linkText.setFocusable(true);
                binding.groupPopup.moreinformation.setVisibility(View.VISIBLE);
                binding.groupPopup.moreinformation.setClickable(true);
                binding.groupPopup.moreinformation.setFocusable(true);
                binding.groupPopup.friendList.setVisibility(View.INVISIBLE);
                binding.groupPopup.friendList.setFocusable(false);
                binding.groupPopup.friendList.setClickable(false);
                binding.submitButton.setVisibility(View.INVISIBLE);
                binding.groupPopup.getRoot().setVisibility(View.VISIBLE);
            }
        }
    }

}