package com.mj.quickselectapppackagelistview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();


    public final static int APP_LIST_RESULT_ACITIVTYKEY = 100;
    public final static String USE_APP_LIST = "app_list";
    public final static String USE_APP_MODE = "app_allowed_mode";

    Button startButton = null;
    TextView appSelectTextView = null;

    Context activityContext =  null;

    ArrayList<String>  appList = new ArrayList<String>();
    boolean            appMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContext = this;
        setContentView(R.layout.activity_main);

        appSelectTextView = (TextView) findViewById(R.id.textView);

        startButton = (Button) findViewById(R.id.button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "Click Button");

                Intent applist = new Intent(activityContext, AppPackageSelectListVIew.class);
                startActivityForResult(applist, APP_LIST_RESULT_ACITIVTYKEY);
            }
        });


    }

    @Override
    public void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        if (request == APP_LIST_RESULT_ACITIVTYKEY && result == Activity.RESULT_OK) {
            if (data == null) {
                return;  // failed
            }

            ArrayList<String> applist = data.getStringArrayListExtra(USE_APP_LIST);

            if (applist != null && applist.size() != 0) {
                StringBuilder stringBuf = new StringBuilder();
                for (String appPackageName : applist) {
                    Log.d(TAG, "App getpackage name :" + appPackageName);
                    stringBuf.append("App getpackage name :" + appPackageName);
                    stringBuf.append("\r\n");
                }

                // 앱 설정 리스트 획득 // get App list
                appList = (ArrayList<String>) applist.clone();
                // 앱 설정 모드 획득 // get Set App Mode
                appMode = data.getBooleanExtra(USE_APP_MODE, true);


                Log.d(TAG, "App Mode :" + appMode);
                stringBuf.append("\r\n");
                stringBuf.append("App Mode :" + appMode);

                appSelectTextView.setText(stringBuf);

            } else {
                Log.e(TAG, " onAcitivyResult Not Define : " +
                        request + " : " + (result == Activity.RESULT_OK ? "ok" : "cancel"));

            }

            return;
        }
    }
}
