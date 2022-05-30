package com.pis.buy2gether.model.domain.data;

import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.model.domain.pojo.Address;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.model.domain.pojo.User;
import com.pis.buy2gether.provider.services.FirebaseUser;
import com.pis.buy2gether.provider.services.FirebaseUser;
import com.pis.buy2gether.provider.services.FirebaseFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public enum UserData {
    INSTANCE;

    private final FirebaseUser firebaseUsers = (FirebaseUser) FirebaseFactory.INSTANCE.getFirebase("FirebaseUser");
    private MutableLiveData<ArrayList<User>> users = new MutableLiveData<>();
    public MutableLiveData<ArrayList<User>> friends = new MutableLiveData<>();
    public MutableLiveData<ArrayList<String>> friendIDs = new MutableLiveData<>();

    public MutableLiveData<ArrayList<User>> getListUsers(String uid) {
        updateListUsers(uid);
        return users;
    }

    public void setDataUsers(ArrayList<User> data) { this.users.setValue(data); }

    public ArrayList<String> getFriendIDs() { return friendIDs.getValue(); }

    public void updateListUsers(String uid) {
        Task<QuerySnapshot> result = firebaseUsers.getUsers(friends.getValue()==null? true : false);

        result.addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                ArrayList<User> data = new ArrayList<>();
                for(QueryDocumentSnapshot documentSnapshot : result.getResult()){
                    User a = new User();
                    Map<String, Object> info = documentSnapshot.getData();
                    a.setId(documentSnapshot.getId());
                    a.setUsername((String) info.get("username"));
                    a.setEmail((String) info.get("email"));
                    a.setProvider((String) info.get("provider"));
                    a.setProfileImage(documentSnapshot.getId()); // Cargar imágenes no funciona.
                    data.add(a);
                }
                users.setValue(data);
            }
        });
    }

    public MutableLiveData<ArrayList<User>> getListFriends(String uid) {
        updateListFriends(uid);
        return friends;
    }

    public void setDataFriends(ArrayList<User> data) { this.friends.setValue(data); }

    public void updateListFriends(String uid) {
        Task<QuerySnapshot> result = firebaseUsers.getFriends(uid);

        result.addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                ArrayList<User> data = new ArrayList<>();
                ArrayList<String> ids = new ArrayList<>();
                ids.add(firebaseUsers.getCurrentUserID());

                for(QueryDocumentSnapshot documentSnapshot : result.getResult()){
                    String[] id = documentSnapshot.getData().keySet().toArray(new String[0]);
                    String friendID = id[0].equals(firebaseUsers.getCurrentUserID()) ? id[1] : id[0];
                    Task<DocumentSnapshot> task2 = firebaseUsers.getUserByID(friendID).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot2) {
                            User a = new User();
                            Map<String, Object> info = documentSnapshot2.getData();

                            a.setId(documentSnapshot2.getId());
                            a.setUsername((String) info.get("username"));
                            a.setEmail((String) info.get("email"));
                            a.setProvider((String) info.get("provider"));
                            //a.setProfileImage(documentSnapshot2.getId()); // Cargar imágenes no funciona.
                            ids.add(documentSnapshot2.getId());
                            data.add(a);
                        }
                    });
                }
                friends.setValue(data);
                friends.postValue(data);

                friendIDs.setValue(ids);
                friendIDs.postValue(ids);
            }
        });
    }
}
