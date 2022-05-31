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
import com.pis.buy2gether.usecases.home.user.friends.FriendsViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public enum UserData {
    INSTANCE;

    private final FirebaseUser firebaseUsers = (FirebaseUser) FirebaseFactory.INSTANCE.getFirebase("FirebaseUser");
    private ArrayList<User> users;
    public ArrayList<User> friends;
    public ArrayList<String> friendIDs;

    UserData() {
        updateListFriends();
        updateListUsers();
    }

    public ArrayList<User> getListUsers(String uid) {
        updateListUsers();
        return users;
    }

    public void setDataUsers(ArrayList<User> data) { this.users.addAll(data); }

    public ArrayList<String> getFriendIDs() { return friendIDs; }

    public void updateListUsers() {
        Task<QuerySnapshot> result = firebaseUsers.getUsers(friends == null? true : false);

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
                users.clear();
                users.addAll(data);
            }
        });
    }

    public ArrayList<User> getListFriends(String uid) {
        updateListFriends();
        return friends;
    }

    public void setDataFriends(ArrayList<User> data) { this.friends.addAll(data); }

    public void updateListFriends() {
        Task<QuerySnapshot> result = firebaseUsers.getFriends(); // TODO: Revisar caso null
        Boolean mh = (firebaseUsers.getFriends() == null);
        result.addOnSuccessListener(task -> {
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
            friends.clear();
            friends.addAll(data);

            friendIDs.clear();
            friendIDs.addAll(ids);
        });
    }


    public ArrayList<User> getListFriends() {
        Task<QuerySnapshot> result = firebaseUsers.getFriends(); // TODO: Revisar caso null
        ArrayList<User> data = new ArrayList<>();

        result.addOnSuccessListener(task -> {
            ArrayList<String> ids = new ArrayList<>();
            ids.add(firebaseUsers.getCurrentUserID());

            for (QueryDocumentSnapshot documentSnapshot : result.getResult()) {
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
        });
        return data;
    }
}
