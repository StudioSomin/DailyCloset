package kr.ac.ssu.closet;

import android.app.DatePickerDialog;
import android.os.Bundle;
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
import android.widget.Toast;

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

    private String nameFirst, nameLast, email, password, passwordConfirm;
    private int birthY=1990, birthM=0, birthD=1;
    private boolean isBirthSet;
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
                /* TODO: Check validity of email address */
                if(s.toString().length()<1)
                    etEmail.setError("What's your email address?");
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
                int len = s.toString().length();
                if(len < 7) {
                    /* TODO: Check combination condition of password */
                    /* TODO: password = s.toString().hashing */
                    etPassword.setError("Enter a combination of at least seven numbers, letters, and punctuation marks.");
                }
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
                /* TODO: Compare hashed PW and PW(Confirm) */
                password = s.toString();
                passwordConfirm = s.toString();
                if(password.compareTo(passwordConfirm) != 0)
                    etPasswordConfirm.setError("Password does not match the confirm password.");
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
                        birthM = month;
                        birthD = dayOfMonth;
                        btnBirth.setText(year+"-"+(month+1)+"-"+dayOfMonth);
                        isBirthSet = true;
                    }
                }, birthY, birthM, birthD).show();
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
                gender = rgGender.getCheckedRadioButtonId();

                /* TODO: Save data to server DB */
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
}

