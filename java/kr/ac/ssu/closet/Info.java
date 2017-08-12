package kr.ac.ssu.closet;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by soeun on 2017. 8. 5..
 */

class Info {
    private String nameFirst, nameLast, passwordHashed;
    private int birthY, birthM, birthD;
    private int gender; // Female:1, Male:2

    Info(String nameFirst, String nameLast, String passwordHashed,
         int birthY, int birthM, int birthD, int gender) {
        this.nameFirst = nameFirst;
        this.nameLast = nameLast;
        this.passwordHashed = passwordHashed;
        this.birthY = birthY;
        this.birthM = birthM;
        this.birthD = birthD;
        this.gender = gender;
    }

    Info() {
        new Info("", "", "", 0, 0, 0, 0);
    }

    static String hash(String input) {
        MessageDigest inputMd = null;
        try {
            inputMd = MessageDigest.getInstance("SHA-256");
            inputMd.update(input.getBytes());

            byte inputByteData[] = inputMd.digest();
            StringBuffer inputSb = new StringBuffer();

            for(int j = 0; j < inputByteData.length; j++)
                inputSb.append(Integer.toString((inputByteData[j]&0xff) + 0x100, 16).substring(1));

            input = inputSb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Log.i("HASH", input);
        return input;
    }

    void setNameFirst(String nameFirst) {
        this.nameFirst = nameFirst;
    }

    void setNameLast(String nameLast) {
        this.nameLast = nameLast;
    }

    void setPasswordHashed(String passwordHashed) {
        this.passwordHashed = passwordHashed;
    }

    void setBirthY(int birthY) {
        this.birthY = birthY;
    }

    void setBirthM(int birthM) {
        this.birthM = birthM;
    }

    void setBirthD(int birthD) {
        this.birthD = birthD;
    }

    void setGender(int gender) {
        this.gender = gender;
    }

    String getNameFirst() {
        return nameFirst;
    }

    String getNameLast() {
        return nameLast;
    }

    String getPasswordHashed() {
        return passwordHashed;
    }

    int getBirthY() {
        return birthY;
    }

    int getBirthM() {
        return birthM;
    }

    int getBirthD() {
        return birthD;
    }

    int getGender() {
        return gender;
    }
}
