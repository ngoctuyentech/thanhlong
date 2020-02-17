package vn.techlifegroup.thanhlong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.util.concurrent.TimeUnit;

import static vn.techlifegroup.thanhlong.utils.Constants.buttonClick;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String mVerificationId,referrerUid;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private boolean mVerificationInProgress = false;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                            Toast.makeText(getApplicationContext(), deepLink.toString(), Toast.LENGTH_LONG).show();
                        }

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user == null
                                && deepLink != null
                                && deepLink.getBooleanQueryParameter("invitedby", false)) {
                            referrerUid = deepLink.getQueryParameter("invitedby");

                            createAnonymousAccountWithReferrerInfo(referrerUid);

                            Toast.makeText(getApplicationContext(), referrerUid, Toast.LENGTH_LONG).show();

                        }
                    }
                });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                mVerificationInProgress = false;

                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                mVerificationInProgress = false;
                // [END_EXCLUDE]
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();

                //Toast.makeText(getApplicationContext(),"Lỗi gửi mã xác thực. Vui lòng đăng nhập bằng cách khác",Toast.LENGTH_LONG).show();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(getApplicationContext(),"Số điện thoại không đúng.",Toast.LENGTH_LONG).show();

                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(getApplicationContext(),"Vuot quota",Toast.LENGTH_LONG).show();

                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    //Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                    //        Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

            }
            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                Toast.makeText(getApplicationContext(),"Vui lòng chờ nhận mã xác thực",Toast.LENGTH_LONG).show();


                // ...
            }
        };

        final EditText edtPhone = findViewById(R.id.edt_phone_user);

        Button btnLogin = (Button)findViewById(R.id.btn_ok);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(buttonClick);
                v.startAnimation(buttonClick);
                final String phoneNumber =  "+84"+(edtPhone.getText().toString()).substring(1);
                if(TextUtils.isEmpty(phoneNumber)){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập số điện thoại",Toast.LENGTH_LONG).show();
                }else{
                    startPhoneNumberVerification(phoneNumber);

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    View dialogView = getLayoutInflater().inflate(R.layout.dialog_enter_code,null);
                    builder.setView(dialogView);
                    final Dialog dialog2 = builder.create();
                    dialog2.show();

                    final EditText edtCodeInput = (EditText)dialogView.findViewById(R.id.edt_enter_code_number);
                    Button btnCodeOk = (Button)dialogView.findViewById(R.id.btn_enter_code_ok);
                    Button btnCodeResend = (Button)dialogView.findViewById(R.id.btn_enter_code_resend);
                    final TextView tvRemain = (TextView)dialogView.findViewById(R.id.tv_enter_code_timer);

                    new CountDownTimer(3000, 1000) {
                        public void onTick(long millisUntilFinished) {
                            tvRemain.setText((millisUntilFinished / 1000)+"");
                        }

                        public void onFinish() {
                            Toast.makeText(getApplicationContext(),"Nếu chưa nhận được mã, hãy nhấn Gửi lại!",Toast.LENGTH_LONG).show();
                        }
                    }.start();

                    btnCodeResend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(buttonClick);
                            resendVerificationCode(phoneNumber, mResendToken);

                        }
                    });

                    btnCodeOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String code = edtCodeInput.getText().toString();

                            v.startAnimation(buttonClick);
                            if(TextUtils.isEmpty(code)){
                                Toast.makeText(getApplicationContext(),"Vui lòng nhập mã xác thực",Toast.LENGTH_LONG).show();

                            }else{
                                verifyPhoneNumberWithCode(mVerificationId, code);

                            }
                        }
                    });


                }
            }
        });

    }

    private void createAnonymousAccountWithReferrerInfo(final String referrerUid) {
        FirebaseAuth.getInstance()
                .signInAnonymously()
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference userRecord =
                                FirebaseDatabase.getInstance().getReference()
                                        .child("users")
                                        .child(user.getUid());
                        userRecord.child("referred_by").setValue(referrerUid);
                    }
                });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent it = new Intent(getApplicationContext(),MainActivity.class);
                            it.putExtra("ReferrerlUser", referrerUid);
                            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                            startActivity(it);
                        }
                    }
                });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void resendVerificationCode(String phoneNumber,PhoneAuthProvider.ForceResendingToken token) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }
}
