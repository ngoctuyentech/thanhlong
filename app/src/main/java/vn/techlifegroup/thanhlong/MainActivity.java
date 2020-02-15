package vn.techlifegroup.thanhlong;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import vn.techlifegroup.thanhlong.utils.Utils;

import static vn.techlifegroup.thanhlong.utils.Constants.refDatabase;

public class MainActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener {

    public static String userUid = FirebaseAuth.getInstance().getUid();

    public static DatabaseReference refUserUid = refDatabase.child("UserPoint").child(userUid);

    Uri mInvitationUrl;

    private String referrerUser,userPhone;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent it = this.getIntent();
        referrerUser = it.getStringExtra("ReferrerlUser");

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float a = 10000;

                refDatabase.child("UserPoint").child(userUid).setValue(a);
            }
        });

        TextView tvPoint = findViewById(R.id.tv_point_cash);

        userPhone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
/*
        if((referrerUser != null) && (!referrerUser.equals(userUid))){

            refDatabase.child("1-System/Referrals/point").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final String point = dataSnapshot.getValue().toString();

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(false);
                    final EditText edtName = new EditText(MainActivity.this);
                    edtName.setInputType(InputType.TYPE_CLASS_TEXT);
                    edtName.setHint("Nhập tên của bạn");
                    builder.setView(edtName);

                    builder.setMessage("Xin chúc mừng, bạn được tặng " + Utils.convertNumber(Float.parseFloat(point)*POINT_VALUE+"") + " vào túi.");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String name = edtName.getText().toString();
                            if(TextUtils.isEmpty(name)){
                                Toast.makeText(getApplicationContext(), "Vui lòng nhập tên", Toast.LENGTH_LONG).show();
                            }else{
                                Utils.addPoint(Float.parseFloat(point), getApplicationContext(), PointActivity.this,"Túi 3 Gang");

                                Account account = new Account(userUid, userPhone, name);
                                refDatabase.child("Referral").child(referrerUser).child(userUid).setValue(account);

                            }

                        }
                    }).setNegativeButton("Từ chối", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        refDatabase.child("Referral").child(userUid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                refDatabase.child("1-System/Referrals/pointReferrer").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String point = dataSnapshot.getValue().toString();
                        Utils.addPoint(Float.parseFloat(point), getApplicationContext(), PointActivity.this,"MyCompany");

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

 */
//code t3g
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
