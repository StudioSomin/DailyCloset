package kr.ac.ssu.closet;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by soeun on 2017. 8. 13..
 * ref: http://cholol.tistory.com/397
 */

class RegistDB extends AsyncTask<Void, Integer, Void> {
    String first_name, last_name, id, password, birthday, v_key;
    int sex;

    RegistDB(String first_name, String last_name, String id,
             String password, String birthday, int sex) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.id = id;
        this.password = password;
        this.birthday = birthday;
        this.sex = sex;
        this.v_key = String.format("%s%04d",
                new SimpleDateFormat("yyyyMMddHHmmss").
                        format(new Date(System.currentTimeMillis())),
                (int)(Math.random()*10000));
    }

    @Override
    protected Void doInBackground(Void... unused) {

        /* 인풋 파라메터값 생성 */
        String param =
                "&u_id=" + id + "&u_first_name=" + first_name + "&u_last_name=" + last_name
                        + "&u_password=" + password + "&u_birthday=" + birthday +"&u_sex=" + sex
                        + "&u_key=" + v_key + "";

        try {
            /* 서버연결 */
            URL url = new URL("http://52.78.168.31/new_member.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//            conn.setRequestMethod("POST");
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
            String data = "";

            is = conn.getInputStream();
            in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
            String line = null;
            StringBuffer buff = new StringBuffer();
            while ((line = in.readLine()) != null) {
                buff.append(line + "\n");
            }
            data = buff.toString().trim();
            Log.e("RECV DATA", data);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
