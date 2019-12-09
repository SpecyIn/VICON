package com.thekirg.vicon;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class DeleteFaculty extends AppCompatActivity implements View.OnClickListener {
    Button btn_delete;
    private TextView btngoback;
    TextView delete_id;
    private static final String ROOT_URL = "https://kirg.specy.in/vicon_php/deletefrom.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_faculty);
        btn_delete = findViewById(R.id.btn_delete);
        delete_id= findViewById(R.id.delete_id);
        btn_delete.setOnClickListener(this);
        btngoback= findViewById(R.id.btn_link_back);
        btngoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RemoteMySQLUI.class);
                startActivity(i);

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_delete:
                final String name=delete_id.getText().toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        ROOT_URL, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            if (!error) {
                                String message = jObj.getString("message");
                                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();

                            } else {

                                String errorMsg = jObj.getString("message");
                                Toast.makeText(getApplicationContext(),
                                        "User Has Been Deleted! Return Back to Add New Account!", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Something  went wrong",Toast.LENGTH_LONG).show();


                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        // Posting params to register url
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name",name);


                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
                break;
        }


    }
}


