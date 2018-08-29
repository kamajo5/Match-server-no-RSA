package com.kamajo.match_operation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import android.util.Log;
import static android.content.ContentValues.TAG;

public class CalculationActivity extends AppCompatActivity {

    EditText input;
    TextView output;
    AppCompatButton btn, btn_cal, btn_new;
    JSONParser jsonParser = new JSONParser();
    String option = "1";
    String url = "http://192.168.0.154/Match_server_no_RSA/input_date.php";
    String url2 = "http://192.168.0.154/Match_server_no_RSA/result.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        btn = (AppCompatButton) findViewById(R.id.btn_Result);
        btn.setVisibility(View.GONE);
        input = (EditText) findViewById(R.id.input_mathematical_expression);
        output = (TextView) findViewById(R.id.result);
        btn_new = (AppCompatButton) findViewById(R.id.btn_New);
        btn_new.setVisibility(View.GONE);
        btn_cal = (AppCompatButton) findViewById(R.id.do_the_calculation);
    }

    public void do_the_calculation(View view) {
        long czasRozpoczecia = System.currentTimeMillis();
        if (CheckFieldValidation() == true) {
            AttemptDoCalculation attemptDoCalculation = new AttemptDoCalculation();
            attemptDoCalculation.execute(load_id(), load_input(), option);
            btn.setVisibility(View.VISIBLE);
            btn_new.setVisibility(View.VISIBLE);
        }
        long czasZakonczenia = System.currentTimeMillis();
        long czasTrwania = czasZakonczenia- czasRozpoczecia;
        Log.e(TAG, "Czas trwania " + czasTrwania + " wyslanai wyrazenia");
    }

    public void Show_Result(View view) throws InterruptedException {
        long czasRozpoczecia = System.currentTimeMillis();
        AttemptResult attemptResult = new AttemptResult();
        attemptResult.execute(load_id(), load_input());

        long czasZakonczenia = System.currentTimeMillis();
        long czasTrwania = czasZakonczenia- czasRozpoczecia;
        Log.e(TAG, "Czas trwania " + czasTrwania + " pobrania wyniku");
    }

    public void New_Calculation(View view) {
        input.setText("");
        output.setText("");
        btn_new.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);
        btn_cal.setVisibility(View.VISIBLE);
    }

    private class AttemptDoCalculation extends AsyncTask<String, String, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            String op = args[2];
            String id = args[0];
            String input_pom = args[1];
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("ID_User", id));
            params.add(new BasicNameValuePair("Input_data", input_pom));
            params.add(new BasicNameValuePair("Option", op));
            JSONObject json = jsonParser.makeHttpRequest(url, "POST", params);
            return json;
        }

        protected void onPostExecute(JSONObject result) {
            try {
                if (result != null) {
                    Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class AttemptResult extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            String id = args[0];
            String input_pom = args[1];
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("ID", id));
            param.add(new BasicNameValuePair("Input", input_pom));
            JSONObject jsons = jsonParser.makeHttpRequest(url2, "POST", param);
            return jsons;
        }

        protected void onPostExecute(JSONObject result) {
            try {
                if (result != null) {
                    if (result.getString("success").equals("1")) {
                        output.setText(result.getString("message"));
                    } else {
                        Toast.makeText(getApplicationContext(), "Error input", Toast.LENGTH_LONG).show();
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
        boolean valid = true;
        if (input.getText().toString().equals("")) {
            input.setError("Can't be Empty");
            valid = false;
        } else if (!is_text()) {
            input.setError("Wrong inptut '/n' Delete any text");
            valid = false;
        }
        return valid;
    }

    private Boolean is_text() {
        String pom = input.getText().toString();
       /* char t;
        Boolean is_good = false;
        for (int i = 0; i < pom.length(); i++) {
            t = pom.charAt(i);
            for (int k = 49; k < 72; k++) {
                if (t == (char) k && k != 54 && k != 56) {
                    is_good = true;
                    break;
                } else {
                    is_good = false;
                }
            }
        }*/
        return true;
    }

    private String load_id() {
        SharedPreferences info = getSharedPreferences((String) getText(R.string.File_name), Context.MODE_PRIVATE);
        String t;
        t = info.getString("ID", "");
        return t;
    }

    private String load_input() {
        String text = input.getText().toString();
        return text;
    }
}
