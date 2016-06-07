package com.qs.activitytransitionanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ll
        button = (Button) findViewById(R.id.button);
        //asdd
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                // overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                overridePendingTransition(R.anim.came_in_from_the_right,R.anim.go_to_the_left);

            }
        });

    }
}
