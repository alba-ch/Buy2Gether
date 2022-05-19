package com.pis.buy2gether.model.session;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.pis.buy2gether.provider.preferences.PreferencesProvider;
import com.pis.buy2gether.provider.services.FirebaseRDBService;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/* Class for user data persistence and write/read database info (only user session data)*/
public enum Session {
    INSTANCE;

    private FirebaseRDBService RDB = FirebaseRDBService.INSTANCE; // Link with Firestore Firebase database
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser(); // Link with Firebase Authentication
    private FirebaseStorage storage = FirebaseStorage.getInstance(); // Link with Firebase Storage

    /* ----- Current User Data ----- */
    public String getCurrentUserID(){
        return currentUser.getUid();
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

    /**
     * actualitza un sol parametre de user en base de dades
     * @param doc
     * @param field
     * @param value
     */
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
    public void deleteGroupInvite(String id){
        RDB.delete("Invites",id);
    }
    public void deleteFriendRequest(String id){
        RDB.delete("Requests",id);
    }
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

    public Task<QuerySnapshot> getFriendRequests(String user) {
        return RDB.getFriendRequests(user);
    }

    public Task<DocumentSnapshot> getFavorite(String user) {
        return RDB.getFavorite(user);
    }
    public Task<QuerySnapshot> getGroupInvites(String user) {
        return RDB.getGroupInvites(user);
    }

    public void deleteFav(String UserID, String id) {
        RDB.deleteFav(UserID, id);
    }
}
