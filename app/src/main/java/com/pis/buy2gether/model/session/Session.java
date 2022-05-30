package com.pis.buy2gether.model.session;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.model.domain.pojo.Profile;
import com.pis.buy2gether.provider.ProviderType;
import com.pis.buy2gether.provider.preferences.PreferencesProvider;
import com.pis.buy2gether.provider.services.FirebaseAuthentification;
import com.pis.buy2gether.provider.services.FirebaseFactory;
import com.pis.buy2gether.provider.services.FirebaseRDBService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/* Class for user data persistence and write/read database info (only user session data)*/
public enum Session {
    INSTANCE, DocumentSnapshot;

    private FirebaseRDBService RDB = FirebaseRDBService.INSTANCE; // Link with Firestore Firebase database
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser(); // Link with Firebase Authentication
    private FirebaseStorage storage = FirebaseStorage.getInstance(); // Link with Firebase Storage
    private String email;
    private String displayName;
    private ProviderType type;
    private String uuid;

    private FirebaseAuthentification firebaseAuth = (FirebaseAuthentification) FirebaseFactory.INSTANCE.getFirebase("FirebaseAuthentification");

    /* ----- Current User Data ----- */
    public String getCurrentUserID(){
        return uuid;
    }

    public FirebaseUser getCurrentUser(){
        return currentUser;
    }

    public Task<DocumentSnapshot> getUserInformation(String user){
        return RDB.get("users",user);
    }

    /* ----- Storage Data ----- */
    public StorageReference getPfpImageRef(String id){
        return storage.getReference("profileImages/"+id+".jpeg");
    }

    public StorageReference getCurrentUserPfpImageRef(){
        return storage.getReference("profileImages/"+getCurrentUserID()+".jpeg");
    }

    /* ----- Preferences Data ----- */
    public String getDataSession(Context context, String key){
        return PreferencesProvider.string(context, key);
    }

    public void setDataSession(Context context, String key, String value){
        PreferencesProvider.set(context, key, value);
    }

    public void clearDataSession(Context context){
        PreferencesProvider.clear(context);
    }

    /* ----- Database ----- */
    public void saveDB(String coll, String doc, HashMap data){
        RDB.save(coll,doc,data);
    }


    public void updateUser(String doc,String field, String value){
        RDB.update(doc,field,value);
    }

    public String CreateGroupDB(HashMap data){
        String UUIDString = UUID.randomUUID().toString();
        this.SaveGroupDB(UUIDString,data);
        return UUIDString;
    }
    public void SaveGroupDB(String UUIDString, HashMap data){
        RDB.saveGroup(UUIDString,data);
    }
    public void deleteDB(String coll, String doc){
        RDB.delete(coll,doc);
    }
    /*
    * public void deleteGroupInvite(String id){
        RDB.delete("Invites",id);
    }
    public void deleteFriendRequest(String id){
        RDB.delete("Requests",id);
    }
    * */

    public void deleteFriend(String id){
        RDB.delete("Friendships",id);
    }

    public Task<DocumentSnapshot> getDB(String coll, String doc){
        return RDB.get(coll,doc);
    }

    public Task<QuerySnapshot> getUsers(List friends){
        return RDB.getUsers(friends);
    }
    public Task<DocumentSnapshot> getUserByID(String id){
        return RDB.getUserByID(id);
    }
    public Task<DocumentSnapshot> getGroup(String id){
        return RDB.getGroup(id);
    }

    public Task<QuerySnapshot> getFriendsDB(String userID){
        return RDB.getFriends(userID);
    }

    public void CreateInvite(HashMap inviteInfo) {
        String UUIDString = UUID.randomUUID().toString();
        RDB.SaveInviteDB(UUIDString,inviteInfo);
    }

    public void CreateFriendRequest(HashMap inviteInfo) {
        String UUIDString = UUID.randomUUID().toString();
        RDB.SaveFriendRequestDB(UUIDString,inviteInfo);
    }

    public void addFriendship(HashMap<String,String> friendship) {
        String UUIDString = UUID.randomUUID().toString();
        RDB.addFriend(UUIDString,friendship);
    }

    public void joinGroup(HashMap<String, String> membership) {
        String UUIDString = UUID.randomUUID().toString();
        RDB.joinGroup(UUIDString,membership);
    }

    public Task<DocumentSnapshot> getFavorite(String user) {
        return RDB.getFavorite(user);
    }


    public void deleteFav(String UserID, String id) {
        RDB.deleteFav(UserID, id);
    }



    //Refactoring

    public MutableLiveData<String> googleLogIn(Intent data){
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        GoogleSignInAccount account = task.getResult();
        Task<AuthResult> result = firebaseAuth.googleLogIn(account);
        if (result == null)
            return null;
        MutableLiveData<String> lifeUuid = new MutableLiveData<>();
        result.addOnCompleteListener(
                user ->{
                    email = account.getEmail();
                    displayName = account.getDisplayName();
                    type = ProviderType.GOOGLE;
                    uuid = firebaseAuth.getUUID();

                    Task<DocumentSnapshot> user_info = firebaseAuth.getUserInfo();

                    user_info.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            if (document.getData() == null){
                                Profile pf = new Profile();
                                pf.setEmail(email);
                                pf.setUsername(displayName);
                                pf.setProvider(type);
                                firebaseAuth.saveUser(pf);
                                lifeUuid.setValue(uuid);
                                return;
                            }
                            lifeUuid.setValue(uuid);
                        }
                    });
                }
        );
        return lifeUuid;
    }

    public boolean checkSession(){
            Boolean result = firebaseAuth.checkSession();
            return result;
    }

    public MutableLiveData<String> chargeUserInfo(){
        uuid = firebaseAuth.getUUID();
        Task<DocumentSnapshot> userInfo = firebaseAuth.getUserInfo();
        MutableLiveData<String> lifeUuid = new MutableLiveData<>();
        userInfo.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                uuid = firebaseAuth.getUUID();
                //Log.e("UUID", firebaseAuth.getUUID());
                DocumentSnapshot document = task.getResult();
                email = (String) document.getData().get("email");
                displayName = (String) document.getData().get("username");
                type = ProviderType.valueOf((String) document.getData().get("provider"));
                lifeUuid.setValue(uuid);
            }
        });
        return lifeUuid;
    }

    public MutableLiveData<String> emailLogIn(String m, String p){
        Task<AuthResult> result = firebaseAuth.emailLogIn(m,p);
        MutableLiveData<String> lifeUuid = new MutableLiveData<>();
        result.addOnCompleteListener(
                task -> {
                    if(task.isSuccessful()){
                        email = task.getResult().getUser().getEmail();
                        type = ProviderType.BASIC;
                        uuid = firebaseAuth.getUUID();
                        lifeUuid.setValue(uuid);
                    }else{
                        lifeUuid.setValue("Error 404");
                    }
                }
        );
        return lifeUuid;
    }

    public MutableLiveData<String> emailSignIn(String email, String psw, String username){
        Task<AuthResult> result = firebaseAuth.emailSignIn(email, psw);
        MutableLiveData<String> lifeUuid = new MutableLiveData<>();
        result.addOnCompleteListener(
                task -> {
                    if(task.isSuccessful()){
                        this.email = email;
                        type = ProviderType.BASIC;
                        displayName = username;
                        uuid = firebaseAuth.getUUID();
                        Profile pf = new Profile();
                        pf.setEmail(email);
                        pf.setUsername(displayName);
                        pf.setProvider(type);
                        firebaseAuth.saveUser(pf);
                        lifeUuid.setValue(uuid);
                    }else{
                        Log.e("dedewd", "no suss");
                        lifeUuid.setValue("Error 404");
                    }
                }
        );
        return lifeUuid;
    }




    public String getMail() {
        return email;
    }
    public String getProvider(){
        return String.valueOf(type);
    }
}
