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

public class ActivityTwo extends AppCompatActivity {

    TextView showMsg;
    EditText logEdText, passEdText;
    Button logBtn;
    String login, password, savedPassword, ecnrPassw;;
    SharedPreferences sPref;
    int encryptKey = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twolayout);

        showMsg = (TextView) findViewById(R.id.showMsg);
        logEdText = (EditText) findViewById(R.id.logEdText);
        passEdText = (EditText) findViewById(R.id.passEdText);
        logBtn = (Button) findViewById(R.id.logBtn);

        View.OnClickListener onclLogBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMsg.setText("");
                loginUser();
            }
        };

        logBtn.setOnClickListener(onclLogBtn);
    }

    void goToInviteForm () {
        Intent intent = new Intent(this, ActivityTree.class);
        intent.putExtra("login", login);
        startActivity(intent);
    }

    void loginUser(){
        readFormData();
        if (!chkEmpty()) {
            if (chkLogin()) {
                Toast.makeText(this, "You succesfully logined as " + login, Toast.LENGTH_SHORT).show();
                goToInviteForm();
            } else {
                showMsg.setText("typed login or password is incorrect");
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

    String decryptPassw(String encrPassw) {
        byte[] decryptedPassw = encrPassw.getBytes();
        for (int i = 0; i < decryptedPassw.length; i++) {
            decryptedPassw[i] -= encryptKey;
        }
        return new String(decryptedPassw);
    }

    boolean chkLogin() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        ecnrPassw = sPref.getString(login, "");
        savedPassword = decryptPassw(ecnrPassw);
        if (savedPassword.equals(password)) {
            return true;
        } else{
            return false;
        }
    }
}