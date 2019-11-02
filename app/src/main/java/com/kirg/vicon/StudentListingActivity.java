package com.kirg.vicon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.kirg.vicon.helper.CheckNetworkStatus;
import com.kirg.vicon.helper.HttpJsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentListingActivity extends AppCompatActivity {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_SIDNUMBER = "sidnumber";
    private static final String KEY_STUDENTNAME = "studentname";
    private static final String BASE_URL = "https://kirg.specy.in/NEW_VICON/";
    private ArrayList<HashMap<String, String>> studentList;
    private ListView studentListView;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_listing);
        studentListView = (ListView) findViewById(R.id.studentList);
        new FetchStudentAsyncTask().execute();

    }

    /**
     * Fetches the list of Students from the server
     */
    private class FetchStudentAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(StudentListingActivity.this);
            pDialog.setMessage("Loading Students. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "fetch_all_students.php", "GET", null);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray Students;
                if (success == 1) {
                    studentList = new ArrayList<>();
                    Students = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate Students list
                    for (int i = 0; i < Students.length(); i++) {
                        JSONObject student = Students.getJSONObject(i);
                        String sidnumber = student.getString(KEY_SIDNUMBER);
                        String studentname = student.getString(KEY_STUDENTNAME);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_SIDNUMBER, sidnumber);
                        map.put(KEY_STUDENTNAME, studentname);
                            studentList.add(map);
                    }
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
                    populateStudentList();
                }
            });
        }

    }

    /**
     * Updating parsed JSON data into ListView
     * */
    private void populateStudentList() {
        ListAdapter adapter = new SimpleAdapter(
                StudentListingActivity.this, studentList,
                R.layout.list_item, new String[]{KEY_SIDNUMBER, KEY_STUDENTNAME},
                new int[]{R.id.sidnumber, R.id.studentname});
        // updating listview
        studentListView.setAdapter(adapter);
        //Call StudentUpdateDeleteActivity when a Student is clicked
        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity
                if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
                    String sidnumber = ((TextView) view.findViewById(R.id.sidnumber)).getText().toString();
                    Intent intent = new Intent(getApplicationContext(), StudentUpdateDeleteActivity.class);
                    intent.putExtra(KEY_SIDNUMBER, sidnumber);
                    startActivityForResult(intent, 20);

                } else {
                    Toast.makeText(StudentListingActivity.this,
                            "Unable to connect to internet",
                            Toast.LENGTH_LONG).show();

                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

}
