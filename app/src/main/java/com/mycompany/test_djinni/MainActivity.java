package com.mycompany.test_djinni;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.mycompany.helloworld.ApiWrapping;
import com.mycompany.helloworld.HelloWorld;
public class MainActivity
        extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {
    private HelloWorld cppApi;
    private int counter = 1;
    private boolean permGrantedReadPhoneState = true;
    private static final int REQ_CODE_PERM_READ_PHONE_STATE = 1001;

    static {
        System.loadLibrary("helloworld");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cppApi = HelloWorld.create();
        checkPermission();
    }

    public void buttonWasPressed(View view) {
        cppApi.setMsg(permGrantedReadPhoneState ? ApiWrapping.getDeviceId(this) +  " == counter:" + (counter++) + " == " : "Permission not granted to read phone state");

        String myString = cppApi.getMsg() + "\n";
        TextView t = (TextView) findViewById(R.id.helloWorldText);
        t.setText(myString + t.getText());
    }

    private void checkPermission(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQ_CODE_PERM_READ_PHONE_STATE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case REQ_CODE_PERM_READ_PHONE_STATE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permGrantedReadPhoneState = true;
                } else {
                    permGrantedReadPhoneState = false;
                }
                break;
            default:
                break;
        }
    }
}