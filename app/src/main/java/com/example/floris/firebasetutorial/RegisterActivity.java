package com.example.floris.firebasetutorial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPasswordfield;
    private Button mRegisterButton;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mProgress = new ProgressDialog(this);
        mNameField = (EditText) findViewById(R.id.namefield);
        mEmailField = (EditText) findViewById(R.id.emailfield);
        mPasswordfield = (EditText) findViewById(R.id.passwordfield);
        mRegisterButton = (Button) findViewById(R.id.registerbutton);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });

    }

    private void startRegister() {
        final String name = mNameField.getText().toString();
        String email = mEmailField.getText().toString();
        String password = mPasswordfield.getText().toString();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mProgress.setMessage("Signing up ...");
            mProgress.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){

                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = mDatabase.child(user_id);

                        current_user_db.child("name").setValue(name);
                        current_user_db.child("image").setValue("default");
                        mProgress.dismiss();
                        Intent accountIntent = new Intent(RegisterActivity.this,AccountActivity.class);
                        accountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(accountIntent);

                    }
                }
            });
        }
    }
}
