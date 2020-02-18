package vn.techlifegroup.thanhlong;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import vn.techlifegroup.thanhlong.model.Cash;
import vn.techlifegroup.thanhlong.model.PointModel;
import vn.techlifegroup.thanhlong.model.UserModel;
import vn.techlifegroup.thanhlong.utils.Utils;

import static vn.techlifegroup.thanhlong.utils.Constants.buttonClick;
import static vn.techlifegroup.thanhlong.utils.Constants.refDatabase;

public class MainActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener {

    public static String userUid = FirebaseAuth.getInstance().getUid();

    private String userPhone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

    private String referrerUser;

    private String cashAdded,codeKey;

    private String totalUserPoint, totalPoint;

    private TextView tvPointUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent it = this.getIntent();
        cashAdded = it.getStringExtra("CashAdded");
        codeKey = it.getStringExtra("CodeKey");

        //Toast.makeText(getApplicationContext(), cashAdded+"", Toast.LENGTH_LONG).show();

        tvPointUser = findViewById(R.id.tv_point_cash);
        final TextView tvPointAll  = findViewById(R.id.tv_point_cash_all);


        refDatabase.child("UserPoint").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(userUid)){
                    refDatabase.child("UserPoint").child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            String totalUserPoint = dataSnapshot.getValue()+"";

                            tvPointUser.setText(Utils.convertNumber(totalUserPoint));

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        refDatabase.child("1-System/totalPointAllUser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String totalPoint = dataSnapshot.getValue()+"";

                tvPointAll.setText(Utils.convertNumber(totalPoint));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button btnScan       = findViewById(R.id.btn_scan);
        Button btnWithdrawal = findViewById(R.id.btn_withdrawal);


        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(buttonClick);
                startActivity(new Intent(getApplicationContext(),ScanQrCode.class));

            }
        });
/*
        btnWithdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdrawal();

            }
        });

 */
//btnWithdrawal


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
    protected void onResume() {

        if(cashAdded != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(false);
            builder.setMessage("Xin chúc mừng, Quý khách được tăng tích luỹ thêm " + Utils.convertNumber(cashAdded) + " đ. Vui lòng bấm Đồng ý để xác nhận!");

            builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //scanToPoint();
                    addPoint();

                    cashAdded = null;
                }
            }).show();
        }

        super.onResume();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void addPoint(){

        final long addPoint = Long.parseLong(cashAdded);

        refDatabase.child("UserPoint").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(userUid)){

                    refDatabase.child("UserPoint").child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            long oldPoint = Long.parseLong(dataSnapshot.getValue()+"");
                            long newPoint = oldPoint + addPoint;

                            refDatabase.child("UserPoint").child(userUid).setValue(newPoint+"");
                            tvPointUser.setText(newPoint+"");


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    refDatabase.child("1-System").child("totalPointAllUser").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            long oldPoint = Long.parseLong(dataSnapshot.getValue()+"");
                            long newPoint = oldPoint + addPoint;

                            refDatabase.child("1-System/totalPointAllUser").setValue(newPoint+"");

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else {

                    tvPointUser.setText(addPoint+"");

                    refDatabase.child("UserPoint").child(userUid).setValue(addPoint+"");

                    refDatabase.child("1-System").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild("totalPointAllUser")) {

                                refDatabase.child("1-System").child("totalPointAllUser")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                        long oldPoint = Long.parseLong(dataSnapshot.getValue()+"");
                                        long newPoint = oldPoint + addPoint;

                                        refDatabase.child("1-System/totalPointAllUser").setValue(newPoint+"");

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }else {
                                refDatabase.child("1-System/totalPointAllUser").setValue(addPoint+"");

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {

        //startActivity(new Intent(getApplicationContext(),MainActivity.class));


        super.onBackPressed();
    }

/*
    private void withdrawal(){

        refDatabase.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(userUid)){
                    refDatabase.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Toast.makeText(getApplicationContext(), "Hồ sở đã sẳn sàng", Toast.LENGTH_SHORT).show();

                            withdrawCash();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else {

                    dialogAddProfile();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

 */
//withdrawal

    private void withdrawCash() {

        final long userBalance = Long.parseLong(totalUserPoint);

        if(userBalance<100000){

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Quý khách cần tích luỹ đủ 100,000 đồng để thực hiện rút tiền.");
            builder.setCancelable(false);

            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.show();

        }else{

            refDatabase.child("1-System/Admin/CashRequest").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.hasChild(userUid)){
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Quý khách đã gửi yêu cầu đổi tiền. Chúng tôi đang xử lý và phản hồi sớm nhất. Trân trọng cám ơn!");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

                    }else{
                        final EditText edtCash = new EditText(MainActivity.this);
                        edtCash.setHint("Nhập số tiền muốn đổi");
                        edtCash.setInputType(InputType.TYPE_CLASS_NUMBER);
                        edtCash.addTextChangedListener(new Utils.NumberTextWatcherForThousand(edtCash));

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Bạn muốn gửi yêu cầu đổi tiền? (khoản tiền thực nhận sẽ được trừ 10% trên số tiền dự định rút)");
                        builder.setCancelable(false);
                        builder.setView(edtCash);

                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String cash = edtCash.getText().toString().replace(",","");
                                if(TextUtils.isEmpty(cash)){
                                    Toast.makeText(getApplicationContext(), "Vui lòng nhập số tiền muốn đổi!", Toast.LENGTH_LONG).show();
                                }else if(Float.parseFloat(cash) > userBalance){
                                    Toast.makeText(getApplicationContext(), "Số tiền rút nhiều hơn tiền hiện có.", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    final Cash request = new Cash(userUid, userPhone, cash.replace(",",""));
                                    refDatabase.child("1-System/Admin/CashRequest").child(userUid).setValue(request);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("Chúng tôi đã nhận được yêu cầu đổi thưởng của Quý Khách. Chúng tôi sẽ phản hồi trong thời gian sớm nhất. Trân trọng cám ơn.");
                                    builder.setPositiveButton("Xong", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    }).show();

                                }
                            }
                        }).setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        builder.show();

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }

/*
    private void dialogAddProfile(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        final View dialogView = inflater.inflate(R.layout.dialog_add_profile, null);
        builder.setView(dialogView);

        final Dialog dialog = builder.create();
        dialog.show();

        final EditText edtName     = dialogView.findViewById(R.id.edt_name_user);
        final EditText edtPhone    = dialogView.findViewById(R.id.edt_phone_user);
        final EditText edtAdddress = dialogView.findViewById(R.id.edt_address_user);

        edtPhone.setText(userPhone);

        final Button   btnOk = dialogView.findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(buttonClick);

                final String userPhone   = edtPhone.getText().toString();

                final String userName    = edtName.getText().toString();
                final String userAddress = edtAdddress.getText().toString();

                if(TextUtils.isEmpty(userPhone)){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập số điện thoại",Toast.LENGTH_LONG).show();

                }if(TextUtils.isEmpty(userName)){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập Họ và tên",Toast.LENGTH_LONG).show();

                }if(TextUtils.isEmpty(userAddress)){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập địa chỉ",Toast.LENGTH_LONG).show();

                }else {

                    UserModel userModel = new UserModel(userName, userPhone, userAddress);
                    refDatabase.child("Profile").child(userUid).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Vui lòng ấn rút tiền lại", Toast.LENGTH_SHORT).show();

                        }
                    });




                }
            }
        });


    }

 */
//dialogAddProfile
}
