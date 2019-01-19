package com.example.userr.remindme.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.userr.remindme.Helpers.Dailogs;
import com.example.userr.remindme.Helpers.ErrorHandling;
import com.example.userr.remindme.R;
import com.example.userr.remindme.Helpers.URLS;
import com.example.userr.remindme.Helpers.ValidationFields;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.activity_login_createTV)
    TextView createTV;
    @BindView(R.id.et_login_email)
    EditText emailEditText;
    @BindView(R.id.activity_login_passwordET)
    EditText login_passwordET;
    @BindView(R.id.activity_login_loginBTN)
    Button login_activity_loginBTN;
    String email, password;
    Dialog loadingDialog = null;
    private RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        emailEditText.setText(getIntent().getStringExtra("email"));
        login_passwordET.setText(getIntent().getStringExtra("pass"));
        initlize();
        setEvents();
        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE);
        if(sharedPreferences!=null&&!sharedPreferences.getString("email","").equals("")){
            email = sharedPreferences.getString("email","");
            password = sharedPreferences.getString("password","");
            loginRequest();
        }


    }


    private void initlize() {

        mRequestQueue = Volley.newRequestQueue(this);
    }

    private void setEvents() {

        createTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        login_activity_loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    loginRequest();
                }
            }
        });
    }


    private boolean validate() {

        email = emailEditText.getText().toString();
        password = login_passwordET.getText().toString();

        if (ValidationFields.isEmpty(email)) {
            emailEditText.setError("Enter Email");
            return false;
        } else if (!ValidationFields.isValidEmail(email)) {
            emailEditText.setError("Invalid Email");
            return false;
        }
        //-------------------------------------------------
        else if (ValidationFields.isEmpty(password)) {
            login_passwordET.setError("Enter Password");
            return false;
        } else if (!ValidationFields.isValidPassword(password)) {
            login_passwordET.setError("Invalid Password");
            return false;
        }


        return true;
    }

    public JSONObject getMapData() {
        JSONObject postParam = new JSONObject();
        try {
            postParam.put("email", email);
            postParam.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postParam;
    }


    public void loginRequest() {

        loadingDialog = Dailogs.createLoadingBar(this);
        loadingDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URLS.LOGIN_URL, getMapData(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String check = (String) response.get("check");
                    if (check.equals("Success")) {
                        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email",email);
                        editor.putString("password",password);
                        editor.apply();
                        loadingDialog.dismiss();
                    } else {
                        Toast.makeText(LoginActivity.this, "" + check, Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(LoginActivity.this, "" + ErrorHandling.getRequestError(volleyError), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
        mRequestQueue.add(request);
    }


}
