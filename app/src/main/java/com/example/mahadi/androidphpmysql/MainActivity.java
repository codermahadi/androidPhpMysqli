package com.example.mahadi.androidphpmysql;

import android.app.ProgressDialog;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText uname, pass, email;
    private Button btn_res;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uname = (EditText) findViewById(R.id.uname);
        pass = (EditText) findViewById(R.id.pass);
        email = (EditText) findViewById(R.id.email);

        btn_res = (Button) findViewById(R.id.btn_rg);
        btn_res.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_rg) {

            registerUser();
        }
    }

    private void registerUser() {
        final String username = uname.getText().toString().trim();
        final String password = pass.getText().toString().trim();
        final String u_email = email.getText().toString().trim();

        progressDialog.setMessage("Registering User..");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ROOT_REGISTER,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),e.toString()+"Error msg",Toast.LENGTH_LONG).show();

                        }

                    }
                },
                new Response.ErrorListener() {


                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> stringMap = new HashMap<>();

                stringMap.put("username", username);
                stringMap.put("password", password);
                stringMap.put("email", u_email);
                return stringMap;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
