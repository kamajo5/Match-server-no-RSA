package com.kamajo.match_operation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.util.Log;
import static android.content.ContentValues.TAG;

public class TokenActivity extends AppCompatActivity {

    EditText token;
    JSONParser jsonParser = new JSONParser();
    String url = "http://192.168.0.154/Match_server_no_RSA/token.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        token = (EditText) findViewById(R.id.input_token);
    }

    public void Active(View view) {
        long czasRozpoczecia = System.currentTimeMillis();
        if (CheckFieldValidation() == true) {
            AttemptToken attemptToken = new AttemptToken();
            attemptToken.execute(Load_id().toString(), token.getText().toString());
        }
        long czasZakonczenia = System.currentTimeMillis();
        long czasTrwania = czasZakonczenia- czasRozpoczecia;
        Log.e(TAG, "Czas trwania " + czasTrwania + " aktywacji tokenu");
    }

    private class AttemptToken extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            String id = args[0];
            String token = args[1];
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ID", id));
            params.add(new BasicNameValuePair("Token", token));
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
            return json;
        }

        protected void onPostExecute(JSONObject result) {
            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                    if (result.getString("isActive") == "1") {
                        Intent i = new Intent(TokenActivity.this, LoginActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean CheckFieldValidation() {
        if (token.getText().toString().equals("")) {
            token.setError("Can't be Empty");
            return false;
        }
        return true;
    }

    private String Load_id() {
        SharedPreferences info = getSharedPreferences((String) getText(R.string.File_name), Context.MODE_PRIVATE);
        String t;
        t = info.getString("ID", "");
        return t;
    }
}
