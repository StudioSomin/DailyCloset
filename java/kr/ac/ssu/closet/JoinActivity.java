package kr.ac.ssu.closet;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by soeun on 2017. 7. 18..
 */

public class JoinActivity extends AppCompatActivity {

    private EditText etNameFirst, etNameLast, etEmail, etPassword, etPasswordConfirm;
    private Button btnBirth, btnJoin;
    private RadioGroup rgGender;
    private TextView txtAgree;

    private String nameFirst, nameLast, email;
    private String password = null, passwordConfirm = null;
    private String birthday;
    private int birthY=1990, birthM=1, birthD=1;
    private boolean isBirthSet;
    private boolean isPassConfEnabled = false;
    private int gender; // Female:1, Male:2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        etNameFirst = (EditText)findViewById(R.id.new_first_name);
        etNameLast = (EditText)findViewById(R.id.new_last_name);
        etEmail = (EditText)findViewById(R.id.new_email);
        etPassword = (EditText)findViewById(R.id.new_password);
        etPasswordConfirm = (EditText)findViewById(R.id.new_password_confirm);
        btnBirth = (Button)findViewById(R.id.new_birthday);
        rgGender = (RadioGroup)findViewById(R.id.new_gender);
        txtAgree = (TextView)findViewById(R.id.new_agree);
        btnJoin = (Button)findViewById(R.id.btn_join_confirm);

        btnJoin.setEnabled(false);

        etPasswordConfirm.setEnabled(isPassConfEnabled);

        etNameFirst.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()<1)
                    etNameFirst.setError("What's your first name?");
                checkAllContentsFilled();
            }
        });
        etNameLast.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()<1)
                    etNameLast.setError("What's your last name?");
                checkAllContentsFilled();
            }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final String regex = "^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$";
                // ref: http://emailregex.com/
                if(s.toString().length()<1 || !s.toString().matches(regex))
                    etEmail.setError("Please enter a valid address.");

//                for(int i = 0; i < LoginActivity.memberCount; i++) {
                /* TODO: check if email address existed. if(LoginActivity.member.get(s.toString()) != null) */
//                    if(s.toString().compareTo(LoginActivity.member[i][0]) == 0)
//                    etEmail.setError("Email address already in use.");
//              }
                checkAllContentsFilled();
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                final String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{7,20}$";
                // ref: https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
//                int len = s.toString().length();
//                if(len < 7) {
                if(!s.toString().matches(regex)) {
                    etPassword.setError("Enter a combination of at least seven numbers," +
                            "upper and lower case letters, and special characters.");
                    isPassConfEnabled = false;
                    etPasswordConfirm.setText(null);
                    etPasswordConfirm.setError(null);

                } else {
                    isPassConfEnabled = true;
                    password = s.toString();
                    passwordConfirm = etPasswordConfirm.getText().toString();
                    checkPassword(password, passwordConfirm);
                }
                etPasswordConfirm.setEnabled(isPassConfEnabled);
                checkAllContentsFilled();
            }
        });

        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                password = etPassword.getText().toString();
                passwordConfirm = s.toString();
                checkPassword(password, passwordConfirm);
                checkAllContentsFilled();
            }
        });

        btnBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(JoinActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        birthY = year;
                        birthM = month+1;
                        birthD = dayOfMonth;
                        btnBirth.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                        isBirthSet = true;
                        checkAllContentsFilled();
                    }
                }, birthY, birthM-1, birthD).show();
                checkAllContentsFilled();
            }
        });

        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkAllContentsFilled();
            }
        });

        String notice = "By clicking Create Account, you agree to our Terms and that you have read our Data Policy, including our Cookie Use. You may receive SMS Notifications from Mycloset and can opt out at any time.";
        txtAgree.setText(notice);

        Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return "";
            }
        };

        Pattern patternTerms = Pattern.compile("Terms");
        Pattern patternDataPolicy = Pattern.compile("Data Policy");
        Pattern patternCookieUse = Pattern.compile("Cookie Use");

        /* TODO: Change url to appropriate dialog. */
        Linkify.addLinks(txtAgree, patternTerms, "http://google.com", null, transformFilter);
        Linkify.addLinks(txtAgree, patternDataPolicy, "http://daum.net", null, transformFilter);
        Linkify.addLinks(txtAgree, patternCookieUse, "http://ssu.ac.kr", null, transformFilter);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameFirst = etNameFirst.getText().toString();
                nameLast = etNameLast.getText().toString();
                email = etEmail.getText().toString();
                birthday = String.format("%04d-%02d-%02d", birthY, birthM, birthD);
                gender = rgGender.getCheckedRadioButtonId();

                CheckDB checkDB = new CheckDB(email, Info.hash(password + email));
                checkDB.execute();

//                Toast.makeText(JoinActivity.this, nameFirst +"/"+ nameLast +"/"+
//                        email +"/"+ password+"/"+ gender, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAllContentsFilled() {
        if(etNameFirst.getError() != null || etNameLast.getError() != null || etEmail.getError() != null
                || etPassword.getError() != null || etPasswordConfirm.getError() != null

                || etNameFirst.getText() == null || etNameLast.getText() == null || etEmail.getText() == null
                || etPassword.getText() == null || etPasswordConfirm.getText() == null
                || rgGender.getCheckedRadioButtonId() == -1 || !isBirthSet) {

            btnJoin.setEnabled(false);
        } else btnJoin.setEnabled(true);
    }

    private void checkPassword(String pw, String pwc) {
        if((pw == null && pwc == null) || pw.compareTo(pwc) != 0)
            etPasswordConfirm.setError("Password does not match the confirm password.");
        else
            etPasswordConfirm.setError(null);
    }

    class CheckDB extends AsyncTask<Void, Integer, Void> {
        String id, password;
        String data;

        CheckDB(String id, String password) {
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

        /* TODO: Catch exception - network */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            AlertDialog.Builder alertDialogBuilder =
                    new AlertDialog.Builder(JoinActivity.this);
            Log.e("DATA", data);

            if(data.equals("\uFEFF-1")) {
                Log.e("RESULT","성공적으로 처리되었습니다!");
                new RegistDB(nameFirst, nameLast, email, Info.hash(password+email), birthday, gender).execute();
                finish();

            } else { // if(data.equals("\uFEFF0")) {
                Log.e("RESULT","존재하는 아이디입니다.");

                alertDialogBuilder
                        .setTitle("알림")
                        .setMessage("존재하는 아이디입니다.")
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

