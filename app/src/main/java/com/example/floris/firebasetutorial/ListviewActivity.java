package com.example.floris.firebasetutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class ListviewActivity extends AppCompatActivity {
    private Firebase mRef;
    private ArrayList<String> mUsernames = new ArrayList<>();
    private ListView mListView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        mRef = new Firebase("https://fir-tutorial-a2a99.firebaseio.com/Users").child(user_id);

        mListView = (ListView) findViewById(R.id.listview);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, simple_list_item_1, mUsernames);

        mListView.setAdapter(arrayAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 String name = mUsernames.get(i);
                Log.d("ClickLog",name);
                Intent displayIntent = new Intent(ListviewActivity.this,WikiDisplay.class);
                displayIntent.putExtra("pagename",name);
                startActivity(displayIntent);
            }
        });

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            String value = dataSnapshot.getKey();

                mUsernames.add(value);
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}


//    private Button msendData;
//    private EditText mValueField;
//    private EditText mKeyValue;
//    private Firebase mRef;
//    private TextView mRetrieveView;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Firebase.setAndroidContext(this);
//        mRef = new Firebase("https://fir-tutorial-a2a99.firebaseio.com/");
//        mValueField = (EditText) findViewById(R.id.addValueET);
//        mKeyValue = (EditText) findViewById(R.id.keyValue);
//        msendData = (Button) findViewById(R.id.addString);
////        mRetrieveView = (TextView) findViewById(R.id.retrieveTextView);
//        msendData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String value = mValueField.getText().toString();
//                String keyvalue = mKeyValue.getText().toString();
//                Firebase childRef = mRef.child(keyvalue);
//                childRef.setValue(value);
//
//
//            }
//
//        });
//
////        mRef.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////
////                Map<String, String> map = dataSnapshot.getValue(Map.class);
////                String age = map.get("Age");
////                String name = map.get("Name");
////                String profession = map.get("Profession");
////                Log.v("E_VALUE", "Name :" + name);
////                Log.v("E_VALUE", "Age :" + age);
////                Log.v("E_VALUE", "Profession :" + profession);
////            }
////
////            @Override
////            public void onCancelled(FirebaseError firebaseError) {
////
////
////
////
////            }
////        });
//    }
//
//    public void onClickButton2(View view) {
//
//        Intent intent =  new Intent(this,ListviewActivity.class);
//        startActivity(intent);
//
//    }
//}

