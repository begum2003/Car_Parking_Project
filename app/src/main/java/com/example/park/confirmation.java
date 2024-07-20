package com.example.park;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import org.opencv.android.OpenCVLoader;


public class confirmation extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        if(OpenCVLoader.initLocal()) Log.d("Loaded", "sucess");
        else Log.d("Loaded", "err");

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Python py = Python.getInstance();
                PyObject module = py.getModule("sample");
                System.out.println(module);

            }
        });

    }
}