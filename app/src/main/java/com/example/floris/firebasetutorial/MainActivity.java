package com.example.floris.firebasetutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

import static android.R.layout.simple_list_item_1;

public class MainActivity extends AppCompatActivity {

    private Button msendData;
    private EditText mValueField;
    private EditText mKeyValue;
    private Firebase mRef;
    private TextView mRetrieveView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://fir-tutorial-a2a99.firebaseio.com/Users/");
        mValueField = (EditText) findViewById(R.id.addValueET);
        mKeyValue = (EditText) findViewById(R.id.keyValue);
        msendData = (Button) findViewById(R.id.addString);
//        mRetrieveView = (TextView) findViewById(R.id.retrieveTextView);
        msendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value = mValueField.getText().toString();
                String keyvalue = mKeyValue.getText().toString();
                Firebase childRef = mRef.child(keyvalue);
                childRef.setValue(value);


            }

        });

//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Map<String, String> map = dataSnapshot.getValue(Map.class);
//                String age = map.get("Age");
//                String name = map.get("Name");
//                String profession = map.get("Profession");
//                Log.v("E_VALUE", "Name :" + name);
//                Log.v("E_VALUE", "Age :" + age);
//                Log.v("E_VALUE", "Profession :" + profession);
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//
//
//
//            }
//        });
    }

    public void onClickButton2(View view) {

        Intent intent =  new Intent(this,UserLogin.class);
        startActivity(intent);

    }
}

