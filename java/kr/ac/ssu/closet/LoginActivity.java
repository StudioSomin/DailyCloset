package kr.ac.ssu.closet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by soeun on 2017. 7. 17..
 */

public class LoginActivity extends AppCompatActivity {

    TextView btn_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_signup = (TextView)findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(
                new TextView.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                        startActivity(intent);
                    }
                });
    }
}
