package ua.softgroup.hw_1_bilichuksv;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ActivityTree extends AppCompatActivity {

    TextView showMsg;
    String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threelayout);

        showMsg = (TextView) findViewById(R.id.showMsg);

        Intent intent = getIntent();

        login = intent.getStringExtra("login");
        showMsg.setText("Hello " + login + ".  You are welcome to my application privat zone!");
    }
}