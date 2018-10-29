package com.example.teambc.projectmobiledevelop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SorteerActivity extends AppCompatActivity {

    Button terugButton;
    Button geopendButton;
    Button ratingButton;
    Button afstandButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sorteer_scherm);

        terugButton = (Button) findViewById(R.id.terug_button);
        afstandButton = (Button) findViewById(R.id.afstand_button);
        afstandButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("sortby","distance");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        ratingButton = (Button) findViewById(R.id.rating_button);
        ratingButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("sortby","rating");
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        geopendButton = (Button) findViewById(R.id.geopend_button);
        terugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
