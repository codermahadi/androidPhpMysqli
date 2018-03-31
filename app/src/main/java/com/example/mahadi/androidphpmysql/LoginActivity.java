package com.example.mahadi.androidphpmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText uname, pass;
    private Button btn_login;
    private TextView result, text_reister;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (SharePrefMng.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
        }

        result = (TextView) findViewById(R.id.result);
        text_reister = (TextView) findViewById(R.id.res);

        uname = (EditText) findViewById(R.id.uname);
        pass = (EditText) findViewById(R.id.pass);

        btn_login = (Button) findViewById(R.id.btn_login);


        btn_login.setOnClickListener(this);
        text_reister.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_login) {

            userLogin();

        } else if (view.getId() == R.id.res) {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void userLogin() {

        final String username = uname.getText().toString().trim();
        final String password = pass.getText().toString().trim();

        progressDialog.show();
        StringRequest stringRequest = new StringRequest(

                Request.Method.POST,
                Constants.ROOT_LOGIN,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        try {
                            JSONObject object = new JSONObject(response);
                            if (!object.getBoolean("error")) {

                                SharePrefMng.getInstance(getApplicationContext()).userLogin(
                                        object.getInt("id"),
                                        object.getString("username"),
                                        object.getString("email")

                                );
                                Toast.makeText(getApplicationContext(), "User Login Successfully", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                                finish();

                            } else {

                                Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Tets", Toast.LENGTH_LONG).show();

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
}
