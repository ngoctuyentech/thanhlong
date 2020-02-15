package vn.techlifegroup.thanhlong.utils;

import android.view.animation.AlphaAnimation;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by toila on 26/12/2016.
 */
public class Constants {
    public static DatabaseReference refDatabase = FirebaseDatabase.getInstance().getReference();
    public static AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    public static DatabaseReference refBlockChain = refDatabase.child("BlockChain");

    public static final String DEVELOPER_KEY = "AIzaSyAbk-8fM2l_OcgsCTuLUJrh1AXmcYGUEgI";

}
