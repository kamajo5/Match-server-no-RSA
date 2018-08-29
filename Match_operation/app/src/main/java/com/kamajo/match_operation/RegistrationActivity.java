package com.kamajo.match_operation;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.util.Log;
import static android.content.ContentValues.TAG;

public class RegistrationActivity extends AppCompatActivity {

    EditText email, pass, name, check_pass;
    String URL = "http://192.168.0.154/Match_server_no_RSA/user.php";
    JSONParser jsonParser = new JSONParser();

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }


    public void Signup(View view) {
        long czasRozpoczecia = System.currentTimeMillis();
        email = (EditText) findViewById(R.id.input_email);
        pass = (EditText) findViewById(R.id.input_password);
        name = (EditText) findViewById(R.id.input_name);
        check_pass = (EditText) findViewById(R.id.input_password_repeat);
        if (CheckFieldValidation() == true && IsPasswordSame() == true) {
            AttemptRegister attemptRegister = new AttemptRegister();
            attemptRegister.execute(name.getText().toString(), pass.getText().toString(), email.getText().toString());
        }
        long czasZakonczenia = System.currentTimeMillis();
        long czasTrwania = czasZakonczenia- czasRozpoczecia;
        Log.e(TAG, "Czas trwania " + czasTrwania + " rejestracji");
    }

    private class AttemptRegister extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            String email = args[2];
            String password = args[1];
            String name = args[0];
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Email", email));
            params.add(new BasicNameValuePair("Login", name));
            params.add(new BasicNameValuePair("Password", password));
            JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
            return json;
        }

        protected void onPostExecute(JSONObject result) {
            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                    Save(name.getText().toString(), pass.getText().toString(), email.getText().toString());
                    Save_id(result.getString("how_id"));
                    Intent i = new Intent(RegistrationActivity.this, TokenActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean CheckFieldValidation() {
        boolean valid = true;
        if (name.getText().toString().equals("")) {
            name.setError("Can't be Empty");
            valid = false;
        } else if (email.getText().toString().equals("")) {
            email.setError("Can't be Empty");
            valid = false;
        } else if (pass.getText().toString().equals("")) {
            pass.setError("Can't be Empty");
            valid = false;
        } else if (check_pass.getText().toString().equals("")) {
            check_pass.setError("Can't be Empty");
            valid = false;
        }
        return valid;
    }

    private boolean IsPasswordSame() {
        if (!pass.getText().toString().equals(check_pass.getText().toString())) {
            check_pass.setError("Password is same");
            return false;
        }
        return true;
    }

    private void Save(String n, String p, String e) {
        SharedPreferences info = getSharedPreferences((String) getText(R.string.File_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = info.edit();
        editor.putString("Login", n);
        editor.putString("Password", p);
        editor.putString("Email", e);
        editor.commit();
    }

    private void Save_id(String t) {
        SharedPreferences info = getSharedPreferences((String) getText(R.string.File_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = info.edit();
        editor.putString("ID", t);
        editor.commit();
    }
}