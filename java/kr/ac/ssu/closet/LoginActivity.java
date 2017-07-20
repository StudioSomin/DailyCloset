package kr.ac.ssu.closet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by soeun on 2017. 7. 17..
 */

public class LoginActivity extends AppCompatActivity {

    private String[][] member = {
            {"soeun@somin.com", "12345678"},
            {"jongmin@somin.com", "12345678"},
//            {"apple@pie.com", "12345678"},
//            {"banana@bread.com", "12345678"},
//            {"cup@cake.com", "12345678"},
//            {"doughnut@naver.com", "12345678"},
//            {"eclair@naver.com", "12345678"},
//            {"froyo@naver.com", "12345678"},
//            {"ginger@bread.com", "12345678"},
//            {"honey@comb.com", "12345678"},
//            {"icecream@sandwich.com", "12345678"}
    };
    private int memberCount = member.length;
    private EditText etEmail, etPassword;
    private Button btnLogin;
    private CheckBox chkSigned;
    private TextView btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.input_email);
        etPassword = (EditText) findViewById(R.id.input_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        chkSigned = (CheckBox) findViewById(R.id.check_signed_in);
        btnSignup = (TextView)findViewById(R.id.btn_signup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* TODO: Make searching(email) faster */
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                for(int i=0; i<memberCount; i++)
                    if(email.compareTo(member[i][0]) == 0 && password.compareTo(member[i][1]) == 0)
                        startActivity(new Intent(LoginActivity.this, BinderActivity.class));
            }
        });

        btnSignup.setOnClickListener(
                new TextView.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                        startActivity(intent);
                    }
                });
    }

//    private int getUserID(String email) {
//        for(int i=0; i<memberCount; i++)
//            if(email.compareTo(member[i][0]) == 0) return i;
//        return -1;
//    }
}
