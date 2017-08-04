package kr.ac.ssu.closet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


/**
 * Created by soeun on 2017. 7. 17..
 */

public class LoginActivity extends AppCompatActivity {

    static HashMap<String, Info> member = new HashMap<String, Info>();
    private Info info;
    private String email;
//    static int memberCount;

//    public static String[][] member = {
//            {"soeun@somin.com", "12345678"},
//            {"jongmin@somin.com", "12345678"},
//            {"apple@pie.com", "12345678"},
//            {"banana@bread.com", "12345678"},
//            {"cup@cake.com", "12345678"},
//            {"doughnut@naver.com", "12345678"},
//            {"eclair@naver.com", "12345678"},
//            {"froyo@naver.com", "12345678"},
//            {"ginger@bread.com", "12345678"},
//            {"honey@comb.com", "12345678"},
//            {"icecream@sandwich.com", "12345678"}
//    };

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private CheckBox chkSigned;
    private TextView btnSignup;

    private CallbackManager callbackManager;

    private Button btnFacebook;
    private Button btnGoogle;
    private Button btnTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext()); // 페이스북 SDK 초기화

        setContentView(R.layout.activity_login);

        email = "soeun@somin.com";
        info = new Info("Soeun", "Lee", Info.hash("12345678"+email), 1995, 4, 11, 1);
        member.put(email, info);

        email = "jongmin@somin.com";
        info = new Info("Jongmin", "Chae", Info.hash("12345678"+email), 1992, 9, 24, 2);
        member.put(email, info);

//        memberCount = member.size();

        etEmail = (EditText) findViewById(R.id.input_email);
        etPassword = (EditText) findViewById(R.id.input_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        chkSigned = (CheckBox) findViewById(R.id.check_signed_in);
        btnSignup = (TextView)findViewById(R.id.btn_signup);
        btnFacebook = (Button)findViewById(R.id.btn_facebook);
        btnGoogle = (Button)findViewById(R.id.btn_google);
        btnTwitter = (Button)findViewById(R.id.btn_twitter);

        /* Facebook Login */
        callbackManager = CallbackManager.Factory.create();
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(
                        LoginActivity.this,
                        Arrays.asList("public_profile", "user_friends", "email")
                );
                LoginManager.getInstance().registerCallback(
                        callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        /* TODO: id 멤버 DB에 저장 */
                        /* TODO: 페이스북 공개 프로필에서 필요한 정보 못 찾으면 따로 요청 */
                        Log.e("Facebook", "onSuccess");
                        Log.d("Facebook", "user id : " + AccessToken.getCurrentAccessToken().getUserId());
                        startActivity(new Intent(LoginActivity.this, BinderActivity.class));
                    }

                    @Override
                    public void onCancel() {
                        Log.e("Facebook", "onCancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.e("Facebook", "onError " + error.getLocalizedMessage());
                    }
                });
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* TODO: Save hashed password */
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if(member.get(email) == null) return;

                String inputHash = Info.hash(password + email);
                String originHash = member.get(email).getPasswordHashed();

//                try {
//                    MessageDigest inputMd = MessageDigest.getInstance("SHA-256");
//                    inputMd.update(input.getBytes());
//
//                    byte inputByteData[] = inputMd.digest();
//                    StringBuffer inputSb = new StringBuffer();
//
//                    for(int j = 0; j < inputByteData.length; j++)
//                        inputSb.append(Integer.toString((inputByteData[j]&0xff) + 0x100, 16).substring(1));
//
//                    inputHash = inputSb.toString();

                if(inputHash.compareTo(originHash) == 0)
                    startActivity(new Intent(LoginActivity.this, BinderActivity.class));
                else
                    Toast.makeText(LoginActivity.this, "origin: " + originHash + "\n"
                            + "input: " + inputHash, Toast.LENGTH_SHORT).show();

//                }catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
