package vn.techlifegroup.thanhlong.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.print.PageRange;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Pattern;


/**
 * Created by toila on 08/01/2017.
 */

public class Utils {

    private static ProgressDialog mProgressDialog;
/*
    public static void addPoint(final float accPoint, final Context context,
                                final Activity activiy, final String sender ) {

        showProgressDialog(activiy);

        refUserUid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Transaction")){
                    Query lastUserTx = refUserUid.child("Transaction").limitToLast(1);
                    lastUserTx.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> txSnap = dataSnapshot.getChildren();
                            TransactionModel tx = txSnap.iterator().next().getValue(TransactionModel.class);
                            String blockKey = tx.getBlockKey();
                            String parentBlock = tx.getParentBlock();
                            String txAgent = tx.getAgent();
                            String type = tx.getType();
                            if(type.equals("receiver")){
                                refBlockChain.child(parentBlock).child(blockKey).child("transaction/receiverBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final String userBL = dataSnapshot.getValue().toString();

                                        refBlockChain.child("AdminAccount").child("beingCalled").setValue("true");
                                        refBlockChain.child("AdminAccount").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.hasChild("Transaction")){
                                                    Query querySender = refBlockChain.child("AdminAccount").child("Transaction").limitToLast(1);
                                                    querySender.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            Iterable<DataSnapshot> txSnap = dataSnapshot.getChildren();
                                                            for (DataSnapshot txItem:txSnap){
                                                                TransactionModel txBlockInfo =txItem.getValue(TransactionModel.class);
                                                                final String blockKey = txBlockInfo.getBlockKey();

                                                                refBlockChain.child("CurrentSubChain/name").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                        String subName = dataSnapshot.getValue().toString();

                                                                        refBlockChain.child(subName).child(blockKey).child("transaction").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                TransactionModel adminTx = dataSnapshot.getValue(TransactionModel.class);
                                                                                final String senderBalance = adminTx.getSenderBalance();

                                                                                updateSubChain(senderBalance,userBL,accPoint,context,activiy,sender);
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(DatabaseError databaseError) {

                                                                            }
                                                                        });

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
                                                }else{
                                                    updateSubChain("9999999999",userBL,accPoint,context,activiy,sender);

                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }else {
                                refBlockChain.child(parentBlock).child(blockKey).child("transaction/senderBalance").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final String userBL = dataSnapshot.getValue().toString();

                                        refBlockChain.child("AdminAccount").child("beingCalled").setValue("true");
                                        refBlockChain.child("AdminAccount").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.hasChild("Transaction")){
                                                    Query querySender = refBlockChain.child("AdminAccount").child("Transaction").limitToLast(1);
                                                    querySender.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            Iterable<DataSnapshot> txSnap = dataSnapshot.getChildren();
                                                            for (DataSnapshot txItem:txSnap){
                                                                TransactionModel txBlockInfo =txItem.getValue(TransactionModel.class);
                                                                final String blockKey = txBlockInfo.getBlockKey();

                                                                refBlockChain.child("CurrentSubChain/name").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                                        String subName = dataSnapshot.getValue().toString();

                                                                        refBlockChain.child(subName).child(blockKey).child("transaction").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                                                TransactionModel adminTx = dataSnapshot.getValue(TransactionModel.class);
                                                                                final String senderBalance = adminTx.getSenderBalance();

                                                                                updateSubChain(senderBalance,userBL,accPoint,context,activiy,sender);
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(DatabaseError databaseError) {

                                                                            }
                                                                        });

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
                                                }else{
                                                    updateSubChain("9999999999",userBL,accPoint,context,activiy,sender);

                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

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
                }else{

                    refBlockChain.child("AdminAccount").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("Transaction")){
                                Query querySender = refBlockChain.child("AdminAccount").child("Transaction").limitToLast(1);
                                querySender.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Iterable<DataSnapshot> txSnap = dataSnapshot.getChildren();
                                        for (DataSnapshot txItem:txSnap){
                                            TransactionModel txBlockInfo =txItem.getValue(TransactionModel.class);
                                            final String blockKey = txBlockInfo.getBlockKey();

                                            refBlockChain.child("CurrentSubChain/name").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    String subName = dataSnapshot.getValue().toString();

                                                    refBlockChain.child(subName).child(blockKey).child("transaction").addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                                            TransactionModel adminTx = dataSnapshot.getValue(TransactionModel.class);
                                                            final String senderBalance = adminTx.getSenderBalance();

                                                            updateSubChain(senderBalance,"0",accPoint,context,activiy,sender);
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });

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
                            }else{
                                updateSubChain("9999999999","0",accPoint,context,activiy,sender);

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

 */
//addPoint

/*
    private static void updateSubChain(final String adminBalance,
      final String receiverBalance, final float accPoint, final Context context,
            final Activity  activity,final String sender) {

        refBlockChain.child("CurrentSubChain").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SubBlock subBlock = dataSnapshot.getValue(SubBlock.class);
                final String subname = subBlock.getName();
                final int txTotal = Integer.parseInt(subBlock.getTxTotal());

                Query queryLast = refBlockChain.child(subname).limitToLast(1);
                queryLast.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> blockSnap = dataSnapshot.getChildren();

                        for(DataSnapshot itemBlock:blockSnap){
                            SubBlock lastBlock = itemBlock.getValue(SubBlock.class);
                            final String timeStamp = Calendar.getInstance().getTime().getTime()+"";

                            String updateAdminBL = (Double.parseDouble(adminBalance)-accPoint)+"";
                            String updateUserBL = (Double.parseDouble(receiverBalance)+accPoint)+"";

                            final TransactionModel transaction = new TransactionModel(
                                    sender,
                                    userUid,
                                    accPoint+"",
                                    updateAdminBL,
                                    updateUserBL,
                                    timeStamp
                            );

                            final String updateBlockHash = new SubBlock(
                                    lastBlock.getHash(),transaction
                            ).calculateHash();

                            final SubBlock newBlock = new SubBlock(
                                    lastBlock.getIndex()+1,
                                    lastBlock.getHash(),
                                    transaction,
                                    updateBlockHash,
                                    timeStamp
                            );

                            if(txTotal<BLOCK_HEIGHT){

                                refBlockChain.child("CurrentSubChain").child("txTotal").setValue(txTotal+1+"");

                                final DatabaseReference blockPush = refBlockChain.child(subname).push();
                                final TransactionModel blockInfo = new TransactionModel("SubChain",updateBlockHash,blockPush.getKey(),"MyCompany","receiver");

                                blockPush.setValue(newBlock).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                        builder.setCancelable(false);

                                        builder.setMessage("Xin chúc mừng! Bạn vừa được nhận " + Utils.convertNumber(accPoint*POINT_VALUE +"") + " vào túi!");
                                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                float currentGain = sharedPref.getFloat("SessionGain", 0);
                                                float updateGain = currentGain + accPoint*POINT_VALUE;
                                                editor.putFloat("SessionGain",updateGain).apply();
                                            }
                                        }).show();

                                        refUserUid.child("Transaction").child(blockPush.getKey()).setValue(blockInfo);
                                        refBlockChain.child("AdminAccount/Transaction").child(blockPush.getKey()).setValue(blockInfo);
                                        refBlockChain.child("AdminAccount").child("beingCalled").setValue("false");
                                        hideProgressDialog();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context,"Giao dịch lỗi, vui lòng liên hệ với Xom Nho!",Toast.LENGTH_LONG).show();
                                    }
                                });

                            }else{
                                refBlockChain.child("SubChainList").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        long itemCount = dataSnapshot.getChildrenCount();
                                        String subName = "SubChain"+(itemCount+1);

                                        Chain chain = new Chain (subName,"1");

                                        refBlockChain.child("SubChainList").child(subName).setValue(timeStamp);

                                        refBlockChain.child("CurrentSubChain").setValue(chain);

                                        final DatabaseReference blockPush = refBlockChain.child(subName).push();

                                        final TransactionModel blockInfo = new TransactionModel(subName,updateBlockHash,blockPush.getKey(),"MyCompany","receiver");

                                        blockPush.setValue(newBlock).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                                builder.setCancelable(false);

                                                builder.setMessage("Xin chúc mừng! Bạn vừa được nhận " + Utils.convertNumber(accPoint*POINT_VALUE +"") + " vào túi!");
                                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        float currentGain = sharedPref.getFloat("SessionGain", 0);
                                                        float updateGain = currentGain + accPoint*POINT_VALUE;
                                                        editor.putFloat("SessionGain",updateGain).apply();
                                                    }
                                                }).show();

                                                refUserUid.child("Transaction").child(blockPush.getKey()).setValue(blockInfo);
                                                refBlockChain.child("AdminAccount/Transaction").child(blockPush.getKey()).setValue(blockInfo);
                                                refBlockChain.child("AdminAccount").child("beingCalled").setValue("false");

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context,"Giao dịch lỗi, vui lòng liên hệ với Xom Nho!",Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

 */
//updateSubChain

/*    public static void updateSubChainTx(final Context context,
      final Activity activiy,final String senderBL, final String senderUid,
            final String receiverBalance, final String receiverUid, final float v) {
        showProgressDialog(activiy);


        refBlockChain.child("CurrentSubChain").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                SubBlock subBlock = dataSnapshot.getValue(SubBlock.class);

                final String subName = subBlock.getName();
                final int txTotal = Integer.parseInt(subBlock.getTxTotal());

                Query queryLast = refBlockChain.child(subName).limitToLast(1);
                queryLast.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> blockSnap = dataSnapshot.getChildren();
                        for(DataSnapshot itemBlock:blockSnap){
                            SubBlock lastBlock = itemBlock.getValue(SubBlock.class);
                            final String timeStamp = Calendar.getInstance().getTime().getTime()+"";

                            String updateAdminBL = (Double.parseDouble(senderBL)-v)+"";
                            String updateUserBL = (Double.parseDouble(receiverBalance)+v)+"";

                            final TransactionModel transaction = new TransactionModel(
                                    senderUid,
                                    receiverUid,
                                    v+"",
                                    updateAdminBL,
                                    updateUserBL,
                                    timeStamp
                            );

                            final String updateBlockHash = new SubBlock(
                                    lastBlock.getHash(),transaction
                            ).calculateHash();

                            final SubBlock newBlock = new SubBlock(
                                    lastBlock.getIndex()+1,
                                    lastBlock.getHash(),
                                    transaction,
                                    updateBlockHash,
                                    timeStamp
                            );
                            if(txTotal<BLOCK_HEIGHT){

                                int updateTxTotal = txTotal + 1;
                                refBlockChain.child("CurrentSubChain").child("txTotal").setValue(updateTxTotal+"");

                                DatabaseReference blockPush = refBlockChain.child(subName).push();
                                final TransactionModel blockInfoReceiver = new TransactionModel(subName,updateBlockHash,blockPush.getKey(),"thanhlong","receiver");
                                final TransactionModel blockInfoSender = new TransactionModel(subName,updateBlockHash,blockPush.getKey(),"thanhlong","sender");

                                blockPush.setValue(newBlock).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context,"Giao dịch thành công!",Toast.LENGTH_LONG).show();
                                        refUserUid.child("Transaction").push().setValue(blockInfoSender);

                                        refDatabase.child("Users").child(receiverUid).child("Transaction").push().setValue(blockInfoReceiver);
                                        refDatabase.child("Users").child(receiverUid).child("NewTransaction").push().setValue(blockInfoReceiver);
                                        hideProgressDialog();


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context,"Giao dịch lỗi, vui lòng liên hệ qua hotline!",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else{
                                refBlockChain.child("SubChainList").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        long itemCount = dataSnapshot.getChildrenCount();
                                        String subName = "SubChain"+(itemCount+1);

                                        Chain chain = new Chain (subName,"1");

                                        refBlockChain.child("SubChainList").child(subName).setValue(timeStamp);
                                        refBlockChain.child("CurrentSubChain").setValue(chain);


                                        DatabaseReference blockPush = refBlockChain.child(subName).push();

                                        final TransactionModel blockInfo = new TransactionModel(subName,updateBlockHash,blockPush.getKey(),"thanhlong","receiver");
                                        final TransactionModel blockInfoReceiver = new TransactionModel(subName,updateBlockHash,blockPush.getKey(),"thanhlong","receiver");

                                        blockPush.setValue(newBlock).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(context,"Giao dịch thành công!",Toast.LENGTH_LONG).show();
                                                refUserUid.child("Transaction").push().setValue(blockInfo);

                                                refDatabase.child("Users").child(receiverUid).child("Transaction").push().setValue(blockInfoReceiver);
                                                refDatabase.child("Users").child(receiverUid).child("NewTransaction").push().setValue(blockInfoReceiver);
                                                hideProgressDialog();


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context,"Giao dịch lỗi, vui lòng liên hệ với Papa!",Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

 */
//updateSubChainTx

/*
    public abstract static class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

        public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

        private int previousTotal = 0; // The total number of items in the dataset after the last load
        private boolean loading = true; // True if we are still waiting for the last set of data to load.
        private int visibleThreshold = 3; // The minimum amount of items to have below your current scroll position before loading more.
        int firstVisibleItem, visibleItemCount, totalItemCount;

        private int current_page = 1;

        private LinearLayoutManager mLinearLayoutManager;

        public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
            this.mLinearLayoutManager = linearLayoutManager;
        }


        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached

                // Do something
                current_page++;

                onLoadMore(current_page);

                loading = true;
            }
        }

        public abstract void onLoadMore(int current_page);


    }
*/
//EndlessRecyclerOnScrollListener

    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            //Applies sha256 to our input,
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer(); // This will contain hash as hexidecimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth() , view.getHeight() ,Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        view.layout(0, 0, view.getWidth(), view.getHeight());

        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    public static class NumberTextWatcherForThousand implements TextWatcher {

        EditText editText;

        public NumberTextWatcherForThousand(EditText editText) {
            this.editText = editText;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                editText.removeTextChangedListener(this);
                String value = editText.getText().toString();


                if (value != null && !value.equals("")) {

                    if (value.startsWith(".")) {
                        editText.setText("0.");
                    }
                    if (value.startsWith("0") && !value.startsWith("0.")) {
                        editText.setText("");

                    }


                    String str = editText.getText().toString().replaceAll(",", "");
                    if (!value.equals(""))
                        editText.setText(getDecimalFormattedString(str));
                    editText.setSelection(editText.getText().toString().length());
                }
                editText.addTextChangedListener(this);
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
                editText.addTextChangedListener(this);
            }

        }

    }

    public static String getDecimalFormattedString(String value)     {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = ".";
        }
        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3)
            {
                str3 = "," + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }

    }


    public static String getDate(String timeStampStr){

        try{
            @SuppressLint("SimpleDateFormat") DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date netDate = (new Date(Long.parseLong(timeStampStr)));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "";
        }
    }

    public static String getHourDateFromTimeStamp(String timeStampStr) {

        try{
            @SuppressLint("SimpleDateFormat") DateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yy");
            Date netDate = (new Date(Long.parseLong(timeStampStr)));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "";
        }

    }

    public static String convertNumber(String numString){
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        float numStringFloat = Float.parseFloat(numString);
        String covertNum = numberFormat.format(numStringFloat);
        return covertNum;
    }

    public static Bitmap getScaledBitmap(Bitmap b, int reqWidth, int reqHeight)   {

        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, b.getWidth(), b.getHeight()), new RectF(0, 0, reqWidth, reqHeight), Matrix.ScaleToFit.CENTER);

        return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
    }

    public static String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(timestamp);
            Date currenTimeZone = c.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }

    //@RequiresApi(api = Build.VERSION_CODES.KITKAT)

    public static void createFile(final String outputFile,
                                  final Context context, final Integer[] inputRawResources)
            throws IOException {

        final OutputStream outputStream = new FileOutputStream(outputFile);

        final Resources resources = context.getResources();
        final byte[] largeBuffer = new byte[1024 * 4];
        int totalBytes = 0;
        int bytesRead = 0;

        for (Integer resource : inputRawResources) {
            final InputStream inputStream = resources.openRawResource(resource
                    .intValue());
            while ((bytesRead = inputStream.read(largeBuffer)) > 0) {
                if (largeBuffer.length == bytesRead) {
                    outputStream.write(largeBuffer);
                } else {
                    final byte[] shortBuffer = new byte[bytesRead];
                    System.arraycopy(largeBuffer, 0, shortBuffer, 0, bytesRead);
                    outputStream.write(shortBuffer);
                }
                totalBytes += bytesRead;
            }
            inputStream.close();
        }

        outputStream.flush();
        outputStream.close();
    }

    public static  String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    public static Bitmap getImageFromAssetsFile(Context ctx, String fileName) {
        Bitmap image = null;
        AssetManager am = ctx.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;

    }

    public static File getFileFromAsset(Context ctx, String fileName) {

        try{
            InputStream is = ctx.getAssets().open(fileName);
            File f = File.createTempFile("","NestArt");
            //File f = new File(getCac);
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while((length=is.read(buffer)) > 0) {
                outputStream.write(buffer,0,length);
            }

            outputStream.close();
            is.close();

            return f;
        }catch (IOException e) {
            //Logging exception
        }

        return null;
    }

    public static void copyFdToFile(FileDescriptor src, File dst) throws IOException {
        FileChannel inChannel = new FileInputStream(src).getChannel();
        FileChannel outChannel = new FileOutputStream(dst).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null)
                inChannel.close();
            outChannel.close();
        }
    }

    public static boolean copyAsset(AssetManager assetManager,
                                     String fromAssetPath, String toPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(fromAssetPath);
            new File(toPath).createNewFile();
            out = new FileOutputStream(toPath);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
/*
    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

 */
//setBadgeCount
    public static String capSentences( final String text ) {

        return text.substring( 0, 1 ).toUpperCase() + text.substring( 1 ).toLowerCase();
    }

    public static String wordFirstCap(String str)
    {
        String[] words = str.trim().split(" ");
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < words.length; i++)
        {
            if(words[i].trim().length() > 0)
            {
                ret.append(Character.toUpperCase(words[i].trim().charAt(0)));
                ret.append(words[i].trim().substring(1));
                if(i < words.length - 1) {
                    ret.append(' ');
                }
            }
        }

        return ret.toString();
    }

    public static String covertStringToURL(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll("đ", "d");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
/*
    public static void showProgressDialog(Activity context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(context.getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();

    }

 */
//showProgressDialog

    public static void  hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }



    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    /*
     public static class NumberTextWatcherForThousand implements TextWatcher {

        EditText editText;


        public NumberTextWatcherForThousand(EditText editText) {
            this.editText = editText;


        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try {
                editText.removeTextChangedListener(this);
                String value = editText.getText().toString();


                if (value != null && !value.equals("")) {

                    if (value.startsWith(".")) {
                        editText.setText("0.");
                    }
                    if (value.startsWith("0") && !value.startsWith("0.")) {
                        editText.setText("");

                    }


                    String str = editText.getText().toString().replaceAll(",", "");
                    if (!value.equals(""))
                        editText.setText(getDecimalFormattedString(str));
                    editText.setSelection(editText.getText().toString().length());
                }
                editText.addTextChangedListener(this);
                return;
            } catch (Exception ex) {
                ex.printStackTrace();
                editText.addTextChangedListener(this);
            }

        }


    }

    public static String getDecimalFormattedString(String value)     {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";
        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }
        String str3 = "";
        int i = 0;
        int j = -1 + str1.length();
        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = ".";
        }
        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if (str2.length() > 0)
                    str3 = str3 + "." + str2;
                return str3;
            }
            if (i == 3)
            {
                str3 = "," + str3;
                i = 0;
            }
            str3 = str1.charAt(k) + str3;
            i++;
        }

    }
     */



}
