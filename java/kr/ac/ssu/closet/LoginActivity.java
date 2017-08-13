package kr.ac.ssu.closet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthException;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import io.fabric.sdk.android.Fabric;

/**
 * Created by soeun on 2017. 7. 17..
 */

public class LoginActivity extends AppCompatActivity {
    private final static int O_SIGN_IN=0, F_SIGN_IN=1, G_SIGN_IN=2, T_SIGN_IN=3, ERR=-1;
    private static final String TWT_KEY="NqBNWXQFezaeC0aKoBBXhVF8t",
            TWT_SEC="h9ASZ4nDOrXidRQzvXvaA7AyRMMXUNrMBeNoS5rykijjy6d8QZ";

//    static HashMap<String, Info> member = new HashMap<String, Info>();
//    private Info info;
//    private String email;
    private String result;
//    TwitterAuthClient mTwitterAuthClient;
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

    private Button btnFacebook;
    private Button btnGoogle;
    private Button btnTwitter;

    private CallbackManager callbackManager; // for facebook
    private GoogleApiClient mGoogleApiClient; // for google
    private TwitterAuthClient mTwitterClient; // for twitter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext()); // 페이스북 SDK 초기화
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

//        email = "soeun@somin.com";
//        info = new Info("Soeun", "Lee", Info.hash("12345678"+email), 1995, 4, 11, 1);
//        member.put(email, info);
//
//        email = "jongmin@somin.com";
//        info = new Info("Jongmin", "Chae", Info.hash("12345678"+email), 1992, 9, 24, 2);
//        member.put(email, info);

//        memberCount = member.size();

        etEmail = (EditText) findViewById(R.id.input_email);
        etPassword = (EditText) findViewById(R.id.input_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        chkSigned = (CheckBox) findViewById(R.id.check_signed_in);
        btnSignup = (TextView)findViewById(R.id.btn_signup);
        btnFacebook = (Button)findViewById(R.id.btn_facebook);
        btnGoogle = (Button)findViewById(R.id.btn_google);
        btnTwitter = (Button)findViewById(R.id.btn_twitter);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
//                if(member.get(email) == null) return;

                LoginDB loginDB = new LoginDB(email, Info.hash(password + email));
                loginDB.execute();

//                String inputHash = Info.hash(password + email);
//                String originHash = member.get(email).getPasswordHashed();

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

//                if(inputHash.compareTo(originHash) == 0) {
//                    startActivity(new Intent(LoginActivity.this, BinderActivity.class));
//                    finish();
//                } else
//                    Toast.makeText(LoginActivity.this, "origin: " + originHash + "\n"
//                            + "input: " + inputHash, Toast.LENGTH_SHORT).show();

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
//                        finish();
                    }
                });


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
                                finish();
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

        /* Google Login */
        // ref: https://developers.google.com/identity/sign-in/android/start
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, G_SIGN_IN);
            }
        });

        /* Twitter Login */
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWT_KEY, TWT_SEC);
        Fabric.with(this, new Twitter(authConfig));
//        if (Fabric.isInitialized()) {
//            Log.e("Fabric", "initialized");
//        } else Log.e("Fabric", "uninitialized");
        mTwitterClient = new TwitterAuthClient();

        // final TwitterAuthClient mTwitterAuthClient= new TwitterAuthClient();
        btnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTwitterClient.authorize(LoginActivity.this,
                        new com.twitter.sdk.android.core.Callback<TwitterSession>() {
                            @Override
                            public void success(Result<TwitterSession> twitterSessionResult) {
                                // Success
                                TwitterSession session = twitterSessionResult.data;
                                Long userId = session.getUserId();
                                final String userName = session.getUserName();

                                Log.e("Twitter", "onSuccess");
                                Log.d("Twitter", "user id: " + userId.toString());
                                Log.d("Twitter", "user name: " + userName);
                                startActivity(new Intent(LoginActivity.this, BinderActivity.class));
                                finish();
                            }

                            @Override
                            public void failure(TwitterException e) {
                                e.printStackTrace();
                            }
                        });
            }
        });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("Google", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.d("Google", acct.getId());
//            Log.d("Google", acct.getIdToken());
            Log.d("Google", acct.getEmail());
            startActivity(new Intent(LoginActivity.this, BinderActivity.class));
            finish();
//            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
//            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case O_SIGN_IN: break;
            case G_SIGN_IN:
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
                break;
            case TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE:
                mTwitterClient.onActivityResult(requestCode, resultCode, data);
            default:
                if(requestCode == FacebookSdk.getCallbackRequestCodeOffset())
                    callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    class LoginDB extends AsyncTask<Void, Integer, Void> {
        String id, password;
        String data;

        LoginDB(String id, String password) {
            this.id = id;
            this.password = password;
        }

        String getData() {
            return data;
        }

        @Override
        protected Void doInBackground(Void... unused) {

        /* 인풋 파라메터값 생성 */
            String param = "&u_id=" + id + "&u_password=" + password + "";
            Log.e("POST",param);
            try {
            /* 서버연결 */
                URL url = new URL("http://52.78.168.31/member.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();

            /* 안드로이드 -> 서버 파라메터값 전달 */
                OutputStream outs = conn.getOutputStream();
                outs.write(param.getBytes("UTF-8"));
                outs.flush();
                outs.close();

            /* 서버 -> 안드로이드 파라메터값 전달 */
                InputStream is = null;
                BufferedReader in = null;

                is = conn.getInputStream();
                in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff = new StringBuffer();
                while ( ( line = in.readLine() ) != null )
                {
                    buff.append(line + "\n");
                }
                data = buff.toString().trim();

            /* 서버에서 응답 */
                Log.e("RECV DATA",data);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            AlertDialog.Builder alertDialogBuilder =
                    new AlertDialog.Builder(LoginActivity.this);

            if(data.equals("\uFEFF1")) {
                Log.e("RESULT","성공적으로 처리되었습니다!");
                startActivity(new Intent(LoginActivity.this, BinderActivity.class));

            } else if(data.equals("\uFEFF2")) { // if(data.equals("\uFEFF0")) {
                alertDialogBuilder
                        .setTitle("알림")
                        .setMessage("이메일 회원 인증이 완료되지 않았습니다.")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                            }
                        });
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();

            } else {
                Log.e("RESULT","존재하지 않는 이메일이거나 비밀번호가 일치하지 않습니다.");

                alertDialogBuilder
                        .setTitle("알림")
                        .setMessage("존재하지 않는 이메일이거나 비밀번호가 일치하지 않습니다.")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                            }
                        });
                AlertDialog dialog = alertDialogBuilder.create();
                dialog.show();

//            } else {
//                Log.e("RESULT","에러 발생! ERRCODE = " + data);
            }
        }


    }
}
