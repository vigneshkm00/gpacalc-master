package com.example.vignesh.gpacalc;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About_us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        TextView tv = (TextView )findViewById(R.id.email_text);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","chesmotechnologies@email.com", null));
              //  intent.putExtra(Intent.EXTRA_SUBJECT, subject);
              //  intent.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

    }
}
