package ua.softgroup.hw_1_bilichuksv;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView showMsg;
    EditText logEdText, passEdText;
    Button regBtn, toLogBtn;
    String login, password, ecnrPassw;
    SharedPreferences sPref;
    int encryptKey = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showMsg = (TextView) findViewById(R.id.showMsg);
        logEdText = (EditText) findViewById(R.id.logEdText);
        passEdText = (EditText) findViewById(R.id.passEdText);
        regBtn = (Button) findViewById(R.id.regBtn);
        toLogBtn = (Button) findViewById(R.id.toLogBtn);

        View.OnClickListener onclBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMsg.setText("");
                switch (v.getId()) {
                    case R.id.regBtn:
                        registerUser();
                        break;
                    case R.id.toLogBtn:
                        goToLoginForm();
                        break;
                }
            }
        };

        regBtn.setOnClickListener(onclBtn);
        toLogBtn.setOnClickListener(onclBtn);
    }

    void goToLoginForm () {
        Intent intent = new Intent(this, ActivityTwo.class);
        startActivity(intent);
    }

    void registerUser(){
        readFormData();
        if (!chkEmpty()) {
            if (chkDupl()) {
                showMsg.setText("This login is ocupied, please enter another login");
            } else {
                saveRegUser();
                showMsg.setText("You succesfull registered!");
            };
        } else {
            showMsg.setText("Enter please both login and password");
        }
    };

    void readFormData() {
        login = logEdText.getText().toString();
        password = passEdText.getText().toString();
    }

    boolean chkEmpty() {
        return (login.equals("") || password.equals(""));
    }

    boolean chkDupl() {
        if (loadText()) {
            return true;
        } else return false;
    }

    void saveRegUser() {
        ecnrPassw = encryptPassw(password);
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(login, ecnrPassw);
        ed.commit();
        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    String encryptPassw(String password) {
        byte[] encryptedPassw = password.getBytes();
        for (int i = 0; i < encryptedPassw.length; i++) {
            encryptedPassw[i] += encryptKey;
        }
        return new String(encryptedPassw);
    }

    boolean loadText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedText = sPref.getString(login, "");
        if (savedText != "") {
            showMsg.setText(savedText);
            return true;
        } else{
            return false;
        }
    }
}
