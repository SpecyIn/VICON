package com.kirg.vicon;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.AlertDialog;

import com.kirg.vicon.helper.CheckNetworkStatus;
import com.kirg.vicon.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudentUpdateDeleteActivity extends AppCompatActivity {
    private static String STRING_EMPTY = "";
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_SIDNUMBER = "sidnumber";
    private static final String KEY_DATA = "data";
    private static final String KEY_STUDENTNAME = "studentname";
    private static final String KEY_SCONTACTNO = "scontactno";
    private static final String KEY_PARENTNAME = "parentname";
    private static final String KEY_PCONTACTNO = "pcontactno";
    private static final String BASE_URL = "https://kirg.specy.in/NEW_VICON/";
    private String sidnumber;
    private EditText StudentIDEditText;
    private EditText StudentNameEditText;
    private EditText scontactnoEditText;
    private EditText parentnameEditText;
    private EditText pcontactnoEditText;
    private String studentname;
    private String scontactno;
    private String parentname;
    private String pcontactno;
    private ProgressDialog pDialog;
    private static int REQUEST_CALL = 1;
    private EditText CallStudent;
    private EditText CallParent;
    private Button CallStudentButton;
    private Button CallParentButton;
    private Button SmsStudentButton;
    private Button SmsParentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_update_delete);
        Intent intent = getIntent();
        StudentIDEditText =  findViewById(R.id.txtstudentIDUpdate);
        StudentNameEditText =  findViewById(R.id.txtStudentNameUpdate);
        scontactnoEditText =  findViewById(R.id.txtscontactnoUpdate);
        parentnameEditText =  findViewById(R.id.txtparentnameUpdate);
        pcontactnoEditText =  findViewById(R.id.txtpcontactnoUpdate);
        CallStudent =  findViewById(R.id.txtscontactnoUpdate);
        CallParent =  findViewById(R.id.txtpcontactnoUpdate);
        CallStudentButton =  findViewById(R.id.btnCallStudent);
        CallParentButton =  findViewById(R.id.btnCallParent);

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

        StudentIDEditText.setEnabled(false);
        StudentNameEditText.setEnabled(false);
        parentnameEditText.setEnabled(false);
        scontactnoEditText.setEnabled(false);
        pcontactnoEditText.setEnabled(false);

        sidnumber = intent.getStringExtra(KEY_SIDNUMBER);
        new FetchStudentDetailsAsyncTask().execute();



    }
    /**Phone CAll Student
     */
    private void makeStudentPhoneCall() {
        String number = CallStudent.getText().toString();
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(StudentUpdateDeleteActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(StudentUpdateDeleteActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(StudentUpdateDeleteActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
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

            if (ContextCompat.checkSelfPermission(StudentUpdateDeleteActivity.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(StudentUpdateDeleteActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(StudentUpdateDeleteActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }




    /**
     * Fetches single movie details from the server
     */
    private class FetchStudentDetailsAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(StudentUpdateDeleteActivity.this);
            pDialog.setMessage("Loading Student Details. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            httpParams.put(KEY_SIDNUMBER, sidnumber);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_student_details.php", "GET", httpParams);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONObject movie;
                if (success == 1) {
                    //Parse the JSON response
                    movie = jsonObject.getJSONObject(KEY_DATA);
                    studentname = movie.getString(KEY_STUDENTNAME);
                    scontactno = movie.getString(KEY_SCONTACTNO);
                    parentname = movie.getString(KEY_PARENTNAME);
                    pcontactno = movie.getString(KEY_PCONTACTNO);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    //Populate the Edit Texts once the network activity is finished executing
                    StudentIDEditText.setText(sidnumber);
                    StudentNameEditText.setText(studentname);
                    scontactnoEditText.setText(scontactno);
                    parentnameEditText.setText(parentname);
                    pcontactnoEditText.setText(pcontactno);

                }
            });
        }


    }
}