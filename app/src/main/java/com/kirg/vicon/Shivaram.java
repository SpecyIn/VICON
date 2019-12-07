package com.kirg.vicon;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Shivaram extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shivaram);

        ImageView shivaramli =findViewById(R.id.shivaramli);
        ImageView specyin =findViewById(R.id.specyin);
        ImageView janakili =findViewById(R.id.janakili);
        ImageView shreyali =findViewById(R.id.shreyali);
        ImageView saikiranli =findViewById(R.id.saikiranli);
        ImageView bhavanali =findViewById(R.id.bhavanali);
        ImageView jahnavili =findViewById(R.id.jahnavili);
        ImageView amulli =findViewById(R.id.amulli);
        ImageView luma =findViewById(R.id.luma);

        /** Shivaram Linked In **/
        shivaramli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check for network connectivity
                Uri uri = Uri.parse("https://www.linkedin.com/in/shivaramjimada/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        /** Shivaram Website In **/
        specyin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check for network connectivity
                Uri uri = Uri.parse("https://www.specy.in"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        /** janaki Li In **/
        janakili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check for network connectivity
                Uri uri = Uri.parse("https://www.linkedin.com/in/mellacheruvu-janakiram-a1bbbb178/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        shreyali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check for network connectivity
                Uri uri = Uri.parse("https://www.linkedin.com/in/indukuri-shreya-b4062b187"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        saikiranli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check for network connectivity
                Uri uri = Uri.parse("https://www.linkedin.com/in/vududala-sai-kiran/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        bhavanali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check for network connectivity
                Uri uri = Uri.parse("https://www.linkedin.com/in/doulaghar-bhavana-332561199/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        jahnavili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check for network connectivity
                Uri uri = Uri.parse("https://www.linkedin.com/in/jahanavi-sanda-170562199"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        amulli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check for network connectivity
                Uri uri = Uri.parse("https://www.linkedin.com/in/praneet-amul-akash-cherukuri/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

        luma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check for network connectivity
                Uri uri = Uri.parse("https://sites.google.com/view/praneet-amul-cherukuri/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

    }
}
