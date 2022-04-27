package com.pis.buy2gether.usecases.home.user.friends;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pis.buy2gether.R;
import com.pis.buy2gether.databinding.FragmentFriendsBinding;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.ProviderType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FriendsViewModel extends ViewModel{

    private Session session;
    private FragmentFriendsBinding binding;
    private Context context;

    private FriendsListAdapter friendsListAdapter;
    private UsersListAdapter usersListAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ClipData.Item> list;

    android.widget.SearchView searchView;

    FriendsViewModel(Context context, FriendsListAdapter friendsListAdapter, UsersListAdapter usersListAdapter, FragmentFriendsBinding binding){
        this.session = Session.INSTANCE;
        this.context = context;
        this.friendsListAdapter = friendsListAdapter;
        this.usersListAdapter = usersListAdapter;
        this.binding = binding;
    }

    public void setup(View view){
        setupUserInfo();
        setUserPfp();
    }

    private void setupUserInfo(){
        String provider = session.getDataSession(context,"provider");

        if(ProviderType.valueOf(provider) != ProviderType.GUEST) {
            String currentUser = session.getCurrentUserID();
            session.getUserByID(currentUser).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.get("username").toString() != null) binding.txtUser.setText(documentSnapshot.get("username").toString());
                    binding.txtDesc.setText(documentSnapshot.get("email").toString());
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
                        binding.imgPfp.setImageBitmap(bm);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(context,"Error al carregar l'imatge de perfil\n"+exception,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public Task getFriends(){
        return Session.INSTANCE.getFriendsDB(getUserID());
    }

    public Task getUsers(List friends){
        return Session.INSTANCE.getUsers(friends);
    }

    public String getUserID(){
        String userID = "unknown";
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        return userID;
    }

    public Task<DocumentSnapshot> getUserName(String id) {
        return Session.INSTANCE.getUserByID(id);
    }
    /*public Task<DocumentSnapshot> getFriendshipID(String friendID) {
        return Session.INSTANCE.getFriendshipID(getUserID(), friendID);
    }*/
    public void deleteFriend(String id){
        Session.INSTANCE.deleteFriend(id);
    }

    public void sendRequest(String toID) {
        HashMap inviteInfo = new HashMap();
        inviteInfo.put("fromID",getUserID());
        inviteInfo.put("toID",toID);

        Session.INSTANCE.CreateFriendRequest(inviteInfo);
    }

    public void setList(){

        recyclerView.setAdapter(friendsListAdapter);
        binding.friendsList.setAdapter(friendsListAdapter);


        hideSearch();

        Task task = getFriends().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String[] id = documentSnapshot.getData().keySet().toArray(new String[0]);
                    String friendID = id[0].equals(getUserID()) ? id[1] : id[0];
                    Task<DocumentSnapshot> task = getUserName(friendID).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot2) {
                            String username = documentSnapshot2.get("username")== null ? "unknown" : documentSnapshot2.get("username").toString();
                            friendsListAdapter.addFriend(documentSnapshot.getId(),friendID,username);
                        }
                    });
                }
            }
        });
    }

    public void setListUsers(){
        recyclerView = binding.friendsList;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(usersListAdapter);
        binding.friendsList.setAdapter(usersListAdapter);

        showSearch();

        /* Generate list of users available to send Friend request */
        List friendIDs = new ArrayList<>();
        Task t = getFriends().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    String[] id = documentSnapshot.getData().keySet().toArray(new String[0]);
                    String friendID = id[0].equals(getUserID()) ? id[1] : id[0];
                    friendIDs.add(friendID); // Exclude friends
                }
                friendIDs.add(getUserID()); // Exclude current user
                Task task = getUsers(friendIDs).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshotsUsers) {
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshotsUsers) {
                            String username = documentSnapshot.get("username")== null ? "unknown" : documentSnapshot.get("username").toString();
                            usersListAdapter.addUser(documentSnapshot.getId(), username);
                        }
                    }
                });
            }
        });
    }

    public void sendRequest(View view, String toID){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Enviar sol·licitud d'amistad");
        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("SÍ",new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                sendRequest(toID);
                setList();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void queryTextSubmit(String query){
        /* User prem enter */
        searchView.setQuery(query,false);
        searchView.clearFocus();
        if(usersListAdapter.getList().contains(query)){
            usersListAdapter.getFilter().filter(query);
        }
    }

    public void queryTextChange(String newText){
        usersListAdapter.getFilter().filter(newText);
    }

    private void showSearch(){
        binding.searchViewFriends.setVisibility(View.VISIBLE);
        binding.btnAmics.setVisibility(View.GONE);
        binding.name.setVisibility(View.INVISIBLE);

    }

    private void hideSearch(){
        binding.searchViewFriends.setVisibility(View.GONE);
        binding.btnAmics.setVisibility(View.VISIBLE);
        binding.name.setVisibility(View.VISIBLE);
    }
}