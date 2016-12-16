package com.example.floris.firebasetutorial;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class WikiSearch extends AppCompatActivity {

    private TextView tvHello;
    private EditText WikiSearchET;
    private FirebaseAuth mAuth;
    private Button savebutton;
    private EditText mValueField;
    private EditText mKeyValue;
    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_search);
        Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();
        String user_id = mAuth.getCurrentUser().getUid();
        mRef = new Firebase("https://fir-tutorial-a2a99.firebaseio.com/Users/").child(user_id);
        Button btnRequest = (Button) findViewById(R.id.httprequestbtn);
        Button savebutton = (Button) findViewById(R.id.savebutton);
        tvHello = (TextView) findViewById(R.id.tvHelloWorld);
        WikiSearchET = (EditText) findViewById(R.id.wikisearchET);
        WikiSearchET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String value = tvHello.getText().toString();
                String keyvalue = WikiSearchET.getText().toString();
                Firebase childRef = mRef.child(keyvalue);
                childRef.setValue(value);


            }

        });


        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchterm = WikiSearchET.getText().toString();
                searchterm = searchterm.replaceAll(" ", "%20");
//                String searchlog = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=Johnny%20Dawes" + searchterm;
//                Log.d("SEARCHLOG:",searchlog);
                String url = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=" + searchterm;

                new JSONTask().execute(url);


            }

        });
    }


    class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while((line = reader.readLine()) != null){

                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                Log.d("FINALJSON String:", finalJson);

                JSONObject firstObj = new JSONObject(finalJson);
                JSONObject secondObj = firstObj.getJSONObject("query");

                JSONObject thirdObj = secondObj.getJSONObject("pages");

                for(Iterator<String> iter = thirdObj.keys(); iter.hasNext();) {
                    String key = iter.next();
                    JSONObject fourthObj = thirdObj.getJSONObject(key);
                    String pageExtract = fourthObj.getString("extract");


                    return pageExtract;
                }




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tvHello.setText(result);

        }
    }
}
