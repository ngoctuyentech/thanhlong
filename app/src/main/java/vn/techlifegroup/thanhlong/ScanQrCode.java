package vn.techlifegroup.thanhlong;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;

import java.util.Calendar;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import vn.techlifegroup.thanhlong.model.QrCodeModel;
import vn.techlifegroup.thanhlong.utils.Constants;

import static vn.techlifegroup.thanhlong.utils.Constants.refDatabase;

public class ScanQrCode extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    private static int MY_REQUEST_CODE = 1;
    private String key;
    private String timeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_code);

        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
    }

    @Override
    protected void onResume() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(),android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        MY_REQUEST_CODE);
            }

        }else{
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();          // Start camera on resume
        }

        super.onResume();
    }

    @Override
    public void handleResult(Result result) {
        key = result.toString();

        if(key.contains(".") || key.contains("#") || key.contains("$") || key.contains("[") || key.contains("]")){
            Toast.makeText(getApplicationContext(), "Mã không hợp lệ", Toast.LENGTH_LONG).show();
        }else{
            refDatabase.child("QrCode/CodeUsed").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(key)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ScanQrCode.this);
                        builder.setMessage("Rất tiếc, mã QR đã được sử dụng, vui lòng sử dụng mã QR khác.");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent it = new Intent(getApplicationContext(),MainActivity.class);
                                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                                startActivity(it);

                            }
                        }).show();

                    }else{

                        refDatabase.child("QrCode").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if(dataSnapshot.hasChildren()){
                                    QrCodeModel qrCode = dataSnapshot.getValue(QrCodeModel.class);
                                    String codeType = qrCode.getCodeType();


                                    if(codeType.equals("Theo sản phẩm")){
                                        String productName = qrCode.getProductName();
                                        final String discountRate = qrCode.getDisCountRate();
/*
                                        Constants.refProductList.child(productName).child("productPrice").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                final float codeValue = Float.parseFloat(dataSnapshot.getValue().toString())*Float.parseFloat(discountRate)/100;

                                                Intent it = new Intent(getApplicationContext(),MainActivity.class);
                                                it.putExtra("CashAdded", codeValue+"");
                                                it.putExtra("CodeKey", key);
                                                startActivity(it);

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
 */
//Theo sản phẩm

                                    }else{
                                        final String cashValue = qrCode.getCashValue();
                                        Intent it = new Intent(getApplicationContext(),MainActivity.class);
                                        it.putExtra("CashAdded", cashValue);
                                        it.putExtra("CodeKey", key);
                                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                                        updateQrCode();

                                        startActivity(it);

                                    }
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



    }

    public void updateQrCode(){

        timeStamp = (Calendar.getInstance().getTime().getTime())+"";

        refDatabase.child("QrCode").child("CodeUsed").child(key).setValue(timeStamp).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                refDatabase.child("QrCode").child(key).setValue(null);

            }
        });

    }

    @Override
    public void onBackPressed() {
        mScannerView.stopCamera();          // Stop camera on pause

        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
                mScannerView.startCamera();          // Start camera on resume
            }

        }
    }
}
