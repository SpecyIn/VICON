package com.kirg.vicon;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
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


public class SearchUI extends AppCompatActivity implements View.OnClickListener, LoadImageTask.Listener {
    Button btn;
    EditText uname;
    TextView father_name,student_name,student_no,father_no,student_id;
    private Button CallStudentButton,btnhide;
    private Button CallParentButton;
    private TextView CallStudent;
    private ImageView mImageView;
    private EditText idnoimg;
    private Button mBtLoadImage;
    private static int REQUEST_CALL = 1;
    private TextView CallParent;
    private static final String ROOT_URL = "https://kirg.specy.in/vicon_php/SearchUI.php";
    public static final String[] IDNUMBERS = new String[]{
            "17R01A0","18R01A0","19R01A0"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ui);
        btn = findViewById(R.id.submit_btn);
        btnhide=findViewById(R.id.submit_btn);
        uname = findViewById(R.id.uname);
        student_id = findViewById(R.id.tv_student_id);
        student_name = findViewById(R.id.tv_student_name);
        student_no = findViewById(R.id.tv_student_pno);
        father_no = findViewById(R.id.tv_father_pno);
        father_name = findViewById(R.id.father_name);
        CallStudent = findViewById(R.id.tv_student_pno);
        CallParent = findViewById(R.id.tv_father_pno);
        CallStudentButton = findViewById(R.id.btnCallStudent);
        CallParentButton = findViewById(R.id.btnCallParent);
        /** **/
        idnoimg= findViewById(R.id.uname);
        mImageView = (ImageView) findViewById(R.id.image);
        mBtLoadImage = (Button) findViewById(R.id.submit_btn);

        CallStudentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeStudentPhoneCall();
            }
        });
        CallParentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeParentPhoneCall();
            }
        });
        /** THis is Auto Text **/
        AutoCompleteTextView editText= findViewById(R.id.uname);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,IDNUMBERS);
        editText.setAdapter(adapter);
        /** THIS IS WEB VIEW **/
        btn.setOnClickListener(this);
    }

    /** IMAGE LOAD **/
    @Override
    public void onImageLoaded(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error Loading Image !", Toast.LENGTH_SHORT).show();
    }
    /**Phone CAll Student
     */
    private void makeStudentPhoneCall() {
        String number = CallStudent.getText().toString();
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(SearchUI.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SearchUI.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(SearchUI.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeStudentPhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**Phone CAll Parent
     */
    private void makeParentPhoneCall() {
        String number = CallParent.getText().toString();
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(SearchUI.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SearchUI.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(SearchUI.this, "Phone Number Not Retrieved Yet!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        String IMAGE_URL ="https://cmritautonomous.org/beeserp/images/photo/"+idnoimg.getText().toString()+".jpg";
        switch (view.getId()){
            case R.id.submit_btn:
                final String stu_id=uname.getText().toString();
                new LoadImageTask(this).execute(IMAGE_URL);
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        ROOT_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean error = jObj.getBoolean("error");

                            if (!error) {
                                String name=jObj.getString("studentname");
                                String sidnumber=jObj.getString("sidnumber");
                                String message = jObj.getString("message");
                                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
                                String student_pno=jObj.getString("scontactno");
                                String f_name=jObj.getString("parentname");
                                String father_pno=jObj.getString("pcontactno");
                                student_id.setText(""+sidnumber);
                                student_name.setText(""+name);
                                student_no.setText(""+student_pno);
                                father_name.setText(""+f_name);
                                father_no.setText(""+father_pno);


                            } else {

                                String errorMsg = jObj.getString("message");
                                Toast.makeText(getApplicationContext(),
                                        "ID Number Not Found!", Toast.LENGTH_LONG).show();
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
                        params.put("sidnumber",stu_id);


                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
                break;
        }


    }
}


