package com.mycompany.test_djinni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.mycompany.helloworld.HelloWorld;

public class MainActivity extends AppCompatActivity {

    private HelloWorld cppApi;
    private int counter = 1;

    static {
        System.loadLibrary("helloworld");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cppApi = HelloWorld.create();
    }

    public void buttonWasPressed(View view) {
        cppApi.setMsg("Hello, Djinni + " + (counter++) + " == ");

        String myString = cppApi.getMsg() + "\n";
        TextView t = (TextView) findViewById(R.id.helloWorldText);
        t.setText(myString + t.getText());
    }
}