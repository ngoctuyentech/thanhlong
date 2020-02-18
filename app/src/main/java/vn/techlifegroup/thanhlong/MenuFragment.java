package vn.techlifegroup.thanhlong;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import vn.techlifegroup.thanhlong.model.UserModel;
import vn.techlifegroup.thanhlong.ui.menufragment2.MenuFragment2;

import static vn.techlifegroup.thanhlong.MainActivity.userUid;
import static vn.techlifegroup.thanhlong.utils.Constants.buttonClick;
import static vn.techlifegroup.thanhlong.utils.Constants.refDatabase;

public class MenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static String userUid = FirebaseAuth.getInstance().getUid();
    private String userPhone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View menu = inflater.inflate(R.layout.menu_fragment, container, false);
        final ImageView ivAccount = menu.findViewById(R.id.iv_menu_account);
        final ImageView ivTxList  = menu.findViewById(R.id.iv_tx_list);
        final ImageView ivCall    = menu.findViewById(R.id.iv_call);

        ivTxList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                //viewTransaction();
            }
        });

        ivAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), ivAccount);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.account, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.logout:
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(getActivity(),LoginActivity.class));
                                return true;

                            case R.id.profile:
                                dialogAddProfile();
                                return true;

                            default:
                                return false;
                        }
                    }
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListene

        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refDatabase.child("1-System/adminContact").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String phone = dataSnapshot.getValue().toString();
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });




        return menu;
    }

    private void dialogAddProfile(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View dialogView = inflater.inflate(R.layout.dialog_add_profile, null);
        builder.setView(dialogView);

        final Dialog dialog = builder.create();
        dialog.show();

        final EditText edtName     = dialogView.findViewById(R.id.edt_name_user);
        final EditText edtCmnd     = dialogView.findViewById(R.id.edt_cmnd_user);
        final EditText edtPhone    = dialogView.findViewById(R.id.edt_phone_user);
        final EditText edtAdddress = dialogView.findViewById(R.id.edt_address_user);

        edtPhone.setText(userPhone);

        final Button btnOk = dialogView.findViewById(R.id.btn_ok);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.startAnimation(buttonClick);

                final String userPhone   = edtPhone.getText().toString();

                final String userName    = edtName.getText().toString();
                final String userCmnd    = edtCmnd.getText().toString();
                final String userAddress = edtAdddress.getText().toString();

                if(TextUtils.isEmpty(userPhone)){
                    Toast.makeText(getActivity(),"Vui lòng nhập số điện thoại",Toast.LENGTH_LONG).show();

                }if(TextUtils.isEmpty(userName)){
                    Toast.makeText(getActivity(),"Vui lòng nhập Họ và tên",Toast.LENGTH_LONG).show();

                }if(TextUtils.isEmpty(userCmnd)){
                    Toast.makeText(getActivity(),"Vui lòng nhập CMND",Toast.LENGTH_LONG).show();

                }if(TextUtils.isEmpty(userAddress)){
                    Toast.makeText(getActivity(),"Vui lòng nhập địa chỉ",Toast.LENGTH_LONG).show();

                }else {

                    UserModel userModel = new UserModel(userName, userCmnd, userPhone, userAddress);
                    refDatabase.child("Profile").child(userUid).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Cập nhật hồ sơ thành công", Toast.LENGTH_SHORT).show();

                        }
                    });




                }
            }
        });


    }

/*
    private void dialogProfile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_user_profile,null);
        builder.setView(dialogView);

        final Dialog dialog = builder.create();
        dialog.show();

        final EditText edtName = dialogView.findViewById(R.id.edt_profile_name);
        final EditText edtId = dialogView.findViewById(R.id.edt_profile_id);
        final EditText edtPhone = dialogView.findViewById(R.id.edt_profile_phone);

        refUserUid.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Contact")){
                    refUserUid.child("Contact").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Contact contact = dataSnapshot.getValue(Contact.class);
                            String userName = contact.getUserName();
                            String userPhone = contact.getUserPhone();
                            String userId = contact.getUserId();

                            edtName.setText(userName);
                            edtPhone.setText(userPhone);
                            edtId.setText(userId);
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

        Button btnUpdate = dialogView.findViewById(R.id.btn_profile_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);

                String name = edtName.getText().toString();
                String id = edtId.getText().toString();
                String phone = edtPhone.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(id) || TextUtils.isEmpty(phone)){
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_LONG).show();

                }else{
                    dialog.dismiss();
                    Contact updateContact = new Contact(name,id,phone);
                    refUserUid.child("Contact").setValue(updateContact);
                    Toast.makeText(getContext(), "Cập nhật thành công!", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

 */
//dialogProfile t3g


/*
    private void viewTransaction() {
        final List<TransactionModel> transactions = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_view_transaction,null);
        builder.setView(dialogView);

        Dialog dialog = builder.create();
        dialog.show();

        final RecyclerView rvTx = dialogView.findViewById(R.id.recycler_view_transaction);
        rvTx.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTx.setLayoutManager(linearLayoutManager);

        Spinner spinFilter = dialogView.findViewById(R.id.spin_view_transaction_filter);
        spinFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String choosenTypeView = (String) parent.getItemAtPosition(position);

                if(choosenTypeView.equals("Tất cả")){
                    transactions.clear();

                    refUserUid.child("Transaction").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> txSnap = dataSnapshot.getChildren();
                            for(DataSnapshot itemTx:txSnap){
                                TransactionModel transaction = itemTx.getValue(TransactionModel.class);
                                String parentBlock = transaction.getParentBlock();
                                final String blockKey = transaction.getBlockKey();
                                final String type = transaction.getType();

                                refBlockChain.child(parentBlock).child(blockKey).child("transaction")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                TransactionModel blockTx = dataSnapshot.getValue(TransactionModel.class);
                                                final String amount = blockTx.getAmount();
                                                final String timeStamp = blockTx.getTimeStamp();

                                                final String from = type.equals("sender")? blockTx.getReceiver():blockTx.getSender();

                                                TransactionModel listTx = new TransactionModel(amount,timeStamp,type,from);
                                                transactions.add(listTx);

                                                AdapterTransaction adapterTransaction = new AdapterTransaction(getContext(),transactions);
                                                rvTx.setAdapter(adapterTransaction);
                                                adapterTransaction.notifyDataSetChanged();
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
                if(choosenTypeView.equals("10 giao dịch gần nhất")){
                    transactions.clear();

                    refUserUid.child("Transaction").limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> txSnap = dataSnapshot.getChildren();
                            for(DataSnapshot itemTx:txSnap){
                                TransactionModel transaction = itemTx.getValue(TransactionModel.class);
                                String parentBlock = transaction.getParentBlock();
                                final String blockKey = transaction.getBlockKey();
                                final String type = transaction.getType();

                                refBlockChain.child(parentBlock).child(blockKey).child("transaction")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                TransactionModel blockTx = dataSnapshot.getValue(TransactionModel.class);
                                                final String amount = blockTx.getAmount();
                                                final String timeStamp = blockTx.getTimeStamp();

                                                final String from = type.equals("sender")? blockTx.getReceiver():blockTx.getSender();

                                                TransactionModel listTx = new TransactionModel(amount,timeStamp,type,from);
                                                transactions.add(listTx);

                                                AdapterTransaction adapterTransaction = new AdapterTransaction(getContext(),transactions);
                                                rvTx.setAdapter(adapterTransaction);
                                                adapterTransaction.notifyDataSetChanged();
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

                if(choosenTypeView.equals("50 giao dịch gần nhất")){
                    transactions.clear();

                    refUserUid.child("Transaction").limitToLast(50).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> txSnap = dataSnapshot.getChildren();
                            for(DataSnapshot itemTx:txSnap){
                                TransactionModel transaction = itemTx.getValue(TransactionModel.class);
                                String parentBlock = transaction.getParentBlock();
                                final String blockKey = transaction.getBlockKey();
                                final String type = transaction.getType();

                                refBlockChain.child(parentBlock).child(blockKey).child("transaction")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                TransactionModel blockTx = dataSnapshot.getValue(TransactionModel.class);
                                                final String amount = blockTx.getAmount();
                                                final String timeStamp = blockTx.getTimeStamp();

                                                final String from = type.equals("sender")? blockTx.getReceiver():blockTx.getSender();

                                                TransactionModel listTx = new TransactionModel(amount,timeStamp,type,from);
                                                transactions.add(listTx);

                                                AdapterTransaction adapterTransaction = new AdapterTransaction(getContext(),transactions);
                                                rvTx.setAdapter(adapterTransaction);
                                                adapterTransaction.notifyDataSetChanged();
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

                if(choosenTypeView.equals("100 giao dịch gần nhất")){
                    transactions.clear();

                    refUserUid.child("Transaction").limitToLast(100).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> txSnap = dataSnapshot.getChildren();
                            for(DataSnapshot itemTx:txSnap){
                                TransactionModel transaction = itemTx.getValue(TransactionModel.class);
                                String parentBlock = transaction.getParentBlock();
                                final String blockKey = transaction.getBlockKey();
                                final String type = transaction.getType();

                                refBlockChain.child(parentBlock).child(blockKey).child("transaction")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                TransactionModel blockTx = dataSnapshot.getValue(TransactionModel.class);
                                                final String amount = blockTx.getAmount();
                                                final String timeStamp = blockTx.getTimeStamp();

                                                final String from = type.equals("sender")? blockTx.getReceiver():blockTx.getSender();

                                                TransactionModel listTx = new TransactionModel(amount,timeStamp,type,from);
                                                transactions.add(listTx);

                                                AdapterTransaction adapterTransaction = new AdapterTransaction(getContext(),transactions);
                                                rvTx.setAdapter(adapterTransaction);
                                                adapterTransaction.notifyDataSetChanged();
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

 */

//viewTransaction

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
