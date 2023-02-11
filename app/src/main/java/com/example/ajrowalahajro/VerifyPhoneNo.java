package com.example.ajrowalahajro;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneNo extends AppCompatActivity {
    String verificationCodeBySystem;
    private FirebaseAuth mAuth;
    Button verify_btn;
    EditText phoneNoEnteredByTheUser;
    ProgressBar progressBar;
    String userType ;
    String type ;
    String phoneNo ;
    String name ;
    String email ;
    String address ;
    String password ;
    String waterbill ;
    String salary ;
    String nfm ;
    Bundle args;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_no);
        args = getIntent().getExtras();
        userType = args.getString("userType");
        Toast.makeText(this, userType, Toast.LENGTH_SHORT).show();
        type = args.getString("type");
        phoneNo = args.getString("phoneNo");
        if(type.equals("register")){
            name = args.getString("name");
            email = args.getString("email");
            address = args.getString("address");
            password = args.getString("password");
            if(userType.equals("2")){
                waterbill = args.getString("waterbill");
                salary = args.getString("salary");
                nfm = args.getString("nfm");
        }}

        Toast.makeText(VerifyPhoneNo.this, phoneNo, Toast.LENGTH_SHORT).show();
        verify_btn = findViewById(R.id.verify_btn);
        phoneNoEnteredByTheUser = findViewById(R.id.verification_code_entered_by_user);
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        sendVerificationCodeToUser("+962"+phoneNo.substring(1));
        mAuth = FirebaseAuth.getInstance();
        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = phoneNoEnteredByTheUser.getText().toString();

                if (code.isEmpty() || code.length() < 6) {
                    phoneNoEnteredByTheUser.setError("Wrong OTP...");
                    phoneNoEnteredByTheUser.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        });
    }

    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks  mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
           public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
               super.onCodeSent(s, forceResendingToken);
//               Toast.makeText(VerifyPhoneNo.this, s, Toast.LENGTH_SHORT).show();

               verificationCodeBySystem = s;
           }

           @Override
           public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//               Toast.makeText(VerifyPhoneNo.this, "onVerificationCompleted", Toast.LENGTH_SHORT).show();

               String code = phoneAuthCredential.getSmsCode();
               if (code != null) {
                   Toast.makeText(VerifyPhoneNo.this, code, Toast.LENGTH_SHORT).show();
                   progressBar.setVisibility(View.VISIBLE);
                   verifyCode(code);
               }
           }

           @Override
           public void onVerificationFailed(FirebaseException e) {
               Toast.makeText(VerifyPhoneNo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
           }
    };
    private void verifyCode(String codeByUser) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhoneNo.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            //Perform Your required action here to either let the user sign In or do something required
//                            Intent intent = new Intent(getApplicationContext(), Login.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                            startActivity(intent);
                            if(type.equals("register")){
                                if(userType.equals("1")){
                                    Register r=new Register(name,email,phoneNo,address,password);
                                    r.donorRegister(VerifyPhoneNo.this);
                                    Toast.makeText(VerifyPhoneNo.this, "لقد تم انشاء حسابك بنجاح", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else if(userType.equals("2")){
                                    Register r=new Register(name,email,phoneNo,address,password,waterbill,salary,nfm);
                                    r.needyRegister(VerifyPhoneNo.this);
                                    Toast.makeText(VerifyPhoneNo.this, "لقد تم انشاء حسابك بنجاح", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }else if(type.equals("restPassword")){
                                Intent intent = new Intent(VerifyPhoneNo.this, restPass.class);
                                intent.putExtra("phone", phoneNo);
                                intent.putExtra("id", "0");
                                intent.putExtra("type", userType);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            Toast.makeText(VerifyPhoneNo.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}