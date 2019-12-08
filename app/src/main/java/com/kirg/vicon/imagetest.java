package com.kirg.vicon;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class imagetest extends AppCompatActivity implements View.OnClickListener, LoadImageTask.Listener {

    private ImageView mImageView;
    private EditText idno;
    private Button mBtLoadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagetest);
        idno= findViewById(R.id.idno);
        mImageView = (ImageView) findViewById(R.id.image);
        mBtLoadImage = (Button) findViewById(R.id.btn_load_image);
        mBtLoadImage.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        String IMAGE_URL ="https://cmritautonomous.org/beeserp/images/photo/"+idno.getText().toString()+".jpg";
        switch (view.getId()) {
            case R.id.btn_load_image:
                new LoadImageTask(this).execute(IMAGE_URL);
                break;
        }
    }

    @Override
    public void onImageLoaded(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Error Loading Image !", Toast.LENGTH_SHORT).show();
    }
}