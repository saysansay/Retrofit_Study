package com.aissoft.wfh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class MainActivity extends AppCompatActivity {
    private ApiService mAPIService;
    private TextView mResponseTv;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText edtemail = (EditText) findViewById(R.id.edt_email);
        final EditText edtpassword = (EditText) findViewById(R.id.edt_password);
        Button btnsubmit = (Button) findViewById(R.id.btnsubmit);
        Button btnget=(Button) findViewById(R.id.btnget);
        Button btnemail=(Button)findViewById(R.id.btnemail);
        mAPIService = ApiUtils.getAPIService();
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtemail.getText().toString().trim();
                String password = edtpassword.getText().toString().trim();
                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                    sendPost(email,password);
                    Log.i("Retrofit", "post submit to API.");
                }
            }
        });
        btnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences app = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                String token = app.getString("token", "");
                getUsers(token);
            }
        });

        btnemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // SharedPreferences app = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
               // String email = app.getString("email", "");
               // String token = app.getString("token", "");
                ProgressDialog pd = new ProgressDialog(MainActivity.this);
                pd.setMessage("loading");
                pd.show();
                //getSingle("1",token);
                Intent myIntent = new Intent(MainActivity.this, TakePhoto.class);
               // myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
                pd.dismiss();
            }
        });
    }
    public  void ShowProses(){
        ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("loading");
        pd.show();
    }
    public void sendPost(final String email, String password) {
        mAPIService.postLogin(email, password).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                       String token = jsonObject.getString("token");
                       String token_type = jsonObject.getString("token_type");
                       Integer expire =jsonObject.getInt("expires_in");
                       SharedPreferences preferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE);
                       SharedPreferences.Editor editor = preferences.edit();
                       editor.putString("email",email);
                       editor.putString("token",token);
                       editor.putString("token_type",token_type);
                       editor.putInt("expire",expire);
                       editor.apply();
                       Log.i("Retrofit", "Respond submitted to API." + token);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getUsers(String token) {
        mAPIService.getAllUser("Bearer " + token).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));

                        Log.i("Retrofit", "Respond Get All Users." + jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    public void getProfile(String token) {
        mAPIService.getProfile("Bearer " + token).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));

                        Log.i("Retrofit", "Respond Profile." + jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getSingle(String id,String token) {
        mAPIService.getSingleUser(id,"Bearer " + token).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    try {
                       // JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        JsonElement questions = response.body();
                       // JsonObject check = new Gson().fromJson(questions.getAsJsonObject(), JsonObject.class);
                        Log.i("Retrofit", "Respond Profile." + questions.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
