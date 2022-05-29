package com.pis.buy2gether.usecases.home.user.friends;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.databinding.FragmentFriendsBinding;
import com.pis.buy2gether.model.domain.data.AddressData;
import com.pis.buy2gether.model.domain.data.UserData;
import com.pis.buy2gether.model.domain.pojo.Address;
import com.pis.buy2gether.model.domain.pojo.User;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.provider.ProviderType;
import com.pis.buy2gether.usecases.home.notifications.NotiType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FriendsViewModel extends ViewModel{

    /* Mutable Live Data for Friends and User List autoupdate */
    public MutableLiveData<ArrayList<User>> userList;
    public MutableLiveData<ArrayList<User>> friendList;

    private Session session;
    private FragmentFriendsBinding binding;
    private Context context;

    private FriendsListAdapter friendsListAdapter;
    private UsersListAdapter usersListAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ClipData.Item> list;

    android.widget.SearchView searchView;

    FriendsViewModel(Context context, FragmentFriendsBinding binding){
        this.userList = new MutableLiveData<>();
        this.friendList = new MutableLiveData<>();

        this.session = Session.INSTANCE;
        this.context = context;
        this.binding = binding;

        init();
    }

    private void init(){
        userList = UserData.INSTANCE.getListUsers(getUserID());
        friendList = UserData.INSTANCE.getListFriends(getUserID());
    }

    public void setup(){
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

    public MutableLiveData<ArrayList<User>> getFriends(){  return friendList; }

    public MutableLiveData<ArrayList<User>> getUsers(){ return userList; }

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
        //Session.INSTANCE.deleteFriend(id);
        Toast.makeText(context, "Amic eliminat", Toast.LENGTH_SHORT).show();
    }

    public void sendRequest(String toID) {
        Task task = Session.INSTANCE.getUserByID(getUserID()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                HashMap inviteInfo = new HashMap();
                String username = documentSnapshot.get("username").toString();
                Log.e("FRIEND REQUEST","actual username is: " + username);
                inviteInfo.put("FromUsername",username);
                inviteInfo.put("fromID",getUserID());
                inviteInfo.put("groupname","");
                inviteInfo.put("toID",toID);
                inviteInfo.put("notiType", NotiType.FRIEND_REQUEST);
                Session.INSTANCE.CreateFriendRequest(inviteInfo);
            }
        });
    }

    public void showFriendList(){
        recyclerView = binding.friendsList;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(friendsListAdapter);
        binding.friendsList.setAdapter(friendsListAdapter);
        hideSearch();
        //friendsListAdapter = new FriendsListAdapter(context,getFriends().getValue());
    }

    public void showUserList(){
        recyclerView = binding.friendsList;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(usersListAdapter);
        binding.friendsList.setAdapter(usersListAdapter);

        //usersListAdapter = new UsersListAdapter(context,getUsers().getValue());
    }

    public void setList(ArrayList<User> list){
        friendsListAdapter = new FriendsListAdapter(context,list);
        //friendList=list;
        /*Task task = getFriends().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
        });*/
    }

    public void setListUsers(ArrayList<User> list){
        usersListAdapter = new UsersListAdapter(context,list);

        /* Generate list of users available to send User request */
        /*List friendIDs = new ArrayList<>();
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
        });*/
    }

    public void sendRequest(View view, String toID){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Enviar sol·licitud d'amistad");
        builder.setNegativeButton("NO",null);
        builder.setPositiveButton("SÍ",new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                sendRequest(toID);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void queryTextSubmit(String query){
        /* User prem enter */
        searchView.setQuery(query,false);
        searchView.clearFocus();
        //if(usersListAdapter.getList().contains(query)){
        usersListAdapter.getFilter().filter(query);
        //}
    }

    public void queryTextChange(String newText){
        usersListAdapter.getFilter().filter(newText);
    }

    public void showSearch(){
        binding.searchViewFriends.setVisibility(View.VISIBLE);
        binding.btnAmics.setVisibility(View.GONE);
        binding.name.setVisibility(View.INVISIBLE);

    }

    public void hideSearch(){
        binding.searchViewFriends.setVisibility(View.GONE);
        binding.btnAmics.setVisibility(View.VISIBLE);
        binding.name.setVisibility(View.VISIBLE);
    }
}