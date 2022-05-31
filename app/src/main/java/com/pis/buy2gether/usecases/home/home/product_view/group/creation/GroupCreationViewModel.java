package com.pis.buy2gether.usecases.home.home.product_view.group.creation;

import android.graphics.Bitmap;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pis.buy2gether.model.domain.data.FavoriteData;
import com.pis.buy2gether.model.domain.data.ImageData;
import com.pis.buy2gether.model.domain.data.UserData;
import com.pis.buy2gether.model.domain.data.grup.GrupData;
import com.pis.buy2gether.model.domain.pojo.Favorite;
import com.pis.buy2gether.model.domain.pojo.Grup.Category;
import com.pis.buy2gether.model.domain.pojo.Grup.Grup;
import com.pis.buy2gether.model.domain.pojo.User;
import com.pis.buy2gether.model.session.Session;
import com.pis.buy2gether.usecases.home.home.product_view.group.share.FriendListAdapter;

import java.util.ArrayList;
import java.util.Collection;

public class GroupCreationViewModel extends ViewModel {

    private MutableLiveData<ArrayList<User>> mData;

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 0, 0, 20);
        shake.setDuration(1000);
        shake.setInterpolator(new CycleInterpolator(7));
        return shake;
    }

    public String createGroupDB(String productName, Category type, double originalPrice, int groupVisibility, String adminUser){
        Grup grup = new Grup();
        grup.setName(productName);
        grup.setCat(type);
        grup.setPrice((float)originalPrice);
        grup.setVisibility(groupVisibility);
        grup.setOwner(adminUser);

        GrupData.INSTANCE.saveGrup(grup);
        return grup.getId();
    }

    public void SendInvite(String userID, String groupID) {
        GrupData.INSTANCE.sendInvite(userID,getUser(),groupID);
    }

    public String getUser(){
        //Toast.makeText(contex,"Error al carregar l'imatge de perfil\n",Toast.LENGTH_SHORT).show();
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}