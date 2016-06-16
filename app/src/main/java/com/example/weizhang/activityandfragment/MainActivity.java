package com.example.weizhang.activityandfragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button button = (Button) findViewById(R.id.go_to_b_activity);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    startActivity(intent);
                }
            });
        }

        Button webViewButton = (Button) findViewById(R.id.go_to_web_view_fragment);
        if (webViewButton != null) {
            webViewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, MyWebViewFragment.createInstance("Fragment ONE", "http://lzhangwei.github.io/2016/06/08/Activity-Fragment/"))
                            .addToBackStack(MyWebViewFragment.TAG)
                            .commit();
                }
            });
        }
    }
}
