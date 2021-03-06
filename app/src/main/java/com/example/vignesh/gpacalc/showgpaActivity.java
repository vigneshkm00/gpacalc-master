package com.example.vignesh.gpacalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.InputStreamReader;

public class showgpaActivity extends AppCompatActivity {
    Button b1;
    TextView t1;
    EditText e1;
    String file_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showgpa);
        final CardView card = (CardView) findViewById(R.id.res_card);
        b1 = (Button) findViewById(R.id.gpbtn);
        e1 = (EditText) findViewById(R.id.editText);
        t1 = (TextView) findViewById(R.id.textView6);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                file_name = e1.getText().toString();
                if (!TextUtils.isEmpty(e1.getText().toString()))
                {
                    card.setVisibility(View.VISIBLE);
                    try {
                        String output;
                        File file = new File(file_name);
                        // if(file.exists()) {
                        String mod = getIntent().getStringExtra("mode");
                        if (mod.equals("GPA")) {
//                    file_name = file_name +"c";Toast
                            t1.setText("SEM    GPA");
                        } else {
                            file_name = file_name + "c";
                            t1.setText("SEM    CGPA");
                        }
                        FileInputStream fileInputStream = openFileInput(file_name);
                        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuffer stringBuffer = new StringBuffer();

                        while ((output = bufferedReader.readLine()) != null) {
                            stringBuffer.append("\n  " + output + "\n");
                        }
                        t1.append(stringBuffer.toString());
                        // }
                        //  else
                        // {
                        //     Toast.makeText(getApplicationContext(),"Please enter Appropriate Register Number",Toast.LENGTH_SHORT).show();
                        // }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            else
                Toast.makeText(getApplicationContext(),"Field is empty",Toast.LENGTH_LONG).show();
            }
        });
    }
}
