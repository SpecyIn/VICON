package com.kirg.vicon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kirg.vicon.helper.CheckNetworkStatus;
import com.kirg.vicon.helper.HttpJsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddStudentActivity extends AppCompatActivity {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_SIDNUMBER = "sidnumber";
    private static final String KEY_STUDENTNAME = "studentname";
    private static final String KEY_SCONTACTNO = "scontactno";
    private static final String KEY_PARENTNAME = "parentname";
    private static final String KEY_PCONTACTNO = "pcontactno";
    private static final String BASE_URL = "https://kirg.specy.in/NEW_VICON/";
    private static String STRING_EMPTY = "";
    private EditText StudentIDNoEditText;
    private EditText StudentNameEditText;
    private EditText scontactnoEditText;
    private EditText parentnameEditText;
    private EditText pcontactnoEditText;
    private String sidnumber;
    private String studentname;
    private String scontactno;
    private String parentname;
    private String pcontactno;
    private Button addButton;
    private int success;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        StudentIDNoEditText = (EditText) findViewById(R.id.txtsidnumberAdd);
        StudentNameEditText = (EditText) findViewById(R.id.txtStudentNameAdd);
        scontactnoEditText = (EditText) findViewById(R.id.txtscontactnoAdd);
        parentnameEditText = (EditText) findViewById(R.id.txtparentnameAdd);
        pcontactnoEditText = (EditText) findViewById(R.id.txtpcontactnoAdd);
        addButton = (Button) findViewById(R.id.btnAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    addStudent();
                } else {
                    Toast.makeText(AddStudentActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    /**
     * Otherwise displays Toast message informing one or more fields left empty
     */
    private void addStudent() {
        if (!STRING_EMPTY.equals(StudentIDNoEditText.getText().toString()) &&
                !STRING_EMPTY.equals(StudentNameEditText.getText().toString()) &&
                !STRING_EMPTY.equals(scontactnoEditText.getText().toString()) &&
                !STRING_EMPTY.equals(parentnameEditText.getText().toString()) &&
                !STRING_EMPTY.equals(pcontactnoEditText.getText().toString())) {

            sidnumber = StudentIDNoEditText.getText().toString();
            studentname = StudentNameEditText.getText().toString();
            scontactno = scontactnoEditText.getText().toString();
            parentname = parentnameEditText.getText().toString();
            pcontactno = pcontactnoEditText.getText().toString();
            new AddStudentAsyncTask().execute();
        } else {
            Toast.makeText(AddStudentActivity.this,
                    "One or more fields left empty!",
                    Toast.LENGTH_LONG).show();

        }


    }

    /**
     */
    private class AddStudentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display proggress bar
            pDialog = new ProgressDialog(AddStudentActivity.this);
            pDialog.setMessage("Adding Student. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            Map<String, String> httpParams = new HashMap<>();
            //Populating request parameters
            httpParams.put(KEY_SIDNUMBER, sidnumber);
            httpParams.put(KEY_STUDENTNAME, studentname);
            httpParams.put(KEY_SCONTACTNO, scontactno);
            httpParams.put(KEY_PARENTNAME, parentname);
            httpParams.put(KEY_PCONTACTNO, pcontactno);
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "add_student.php", "POST", httpParams);
            try {
                success = jsonObject.getInt(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    if (success == 1) {
                        //Display success message
                        Toast.makeText(AddStudentActivity.this,
                                "Student Added", Toast.LENGTH_LONG).show();
                        Intent i = getIntent();
                        setResult(20, i);
                        //Finish ths activity and go back to listing activity
                        finish();

                    } else {
                        Toast.makeText(AddStudentActivity.this,
                                "Some error occurred while adding Student",
                                Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
}