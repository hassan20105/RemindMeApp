package com.example.userr.remindme.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.userr.remindme.Helpers.Dailogs;
import com.example.userr.remindme.Helpers.URLS;
import com.example.userr.remindme.R;
import com.example.userr.remindme.Helpers.ValidationFields;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.activity_register_registerBTN)
    Button registerBTN;
    @BindView(R.id.activity_register_emailET)
    EditText emailET;
    @BindView(R.id.activity_register_passwordET)
    EditText passwordET;
    @BindView(R.id.activity_register_confirmPasswordET)
    EditText confirmPasswordET;
    @BindView(R.id.activity_register_phoneET)
    EditText phoneET;
   public static String email, password, confirmPassword, phone;
    Dialog loadingDialog = null;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mRequestQueue = Volley.newRequestQueue(this);
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callData();
                if (validate()) {
                    registerRequest(URLS.REGISTER_URL);
                }


            }
        });

    }


    public boolean validate() {
        if (ValidationFields.isEmpty(email)) {
            emailET.setError("Enter Email");
            return false;
        } else if (!ValidationFields.isValidEmail(email)) {
            emailET.setError("Invalid Email");
            return false;
        }
        //-------------------------------------------------
        else if (ValidationFields.isEmpty(password)) {
            passwordET.setError("Enter Password");
            return false;
        } else if (!ValidationFields.isValidPassword(password)) {
            passwordET.setError("Invalid Password");
            return false;
        }
        //----------------------------------------------------
        else if (ValidationFields.isEmpty(confirmPassword)) {
            confirmPasswordET.setError("Enter Confirmation Password");
            return false;
        } else if (!ValidationFields.isValidConfirmPassword(confirmPassword, password)) {
            confirmPasswordET.setError("Password doesnt match");
            return false;
        }
        //---------------------------------------------------------
        else if (ValidationFields.isEmpty(phone)) {
            phoneET.setError("Enter Phone");
            return false;
        } else if (!ValidationFields.isPhoneValid(phone)) {
            phoneET.setError("Phone Invalid");
            return false;
        }

        return true;
    }

    public void callData() {
        email = emailET.getText().toString();
        password = passwordET.getText().toString();
        confirmPassword = confirmPasswordET.getText().toString();
        phone = phoneET.getText().toString();
    }

    public Map<String, String> getMapData() {
        callData();
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("email", email);
        postParam.put("password", password);
        postParam.put("confirmPassword", confirmPassword);
        postParam.put("phone", phone);
        return postParam;
    }


    public void registerRequest(String url) {
        loadingDialog = Dailogs.createLoadingBar(this);
        loadingDialog.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(getMapData()), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String dbStatus = (String) response.get("DBStatus");
                    if (dbStatus.equals("Data Inserted")) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("pass", password);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Error Happened", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mRequestQueue.add(request);
    }


}
