package com.qs.activitytransitionanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void onClick(View view){
        finish();
        overridePendingTransition(R.anim.came_in_from_the_left,R.anim.go_to_the_right);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.came_in_from_the_left,R.anim.go_to_the_right);


    }
}
