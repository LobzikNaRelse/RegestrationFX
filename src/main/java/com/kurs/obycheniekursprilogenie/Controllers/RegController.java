package com.kurs.obycheniekursprilogenie.Controllers;

// если не запускается, есть баг, нужно перенести в основную папку, запустить, потом вернуть в эту папку и снова запустить и пофиксится

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.kurs.obycheniekursprilogenie.DB;
import com.kurs.obycheniekursprilogenie.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegController
{

    @FXML
    private Button reg_btn;

    @FXML
    private TextField reg_email;

    @FXML
    private TextField reg_login;

    @FXML
    private PasswordField reg_password;

    private DB db = new DB();

    @FXML
    void initialize()
    {
      User user = db.getUser();
        reg_login.setText(user.getLogin());
       reg_email.setText(user.getEmail());
        reg_password.setText(user.getPassword());
        reg_btn.setOnAction(actionEvent ->
        {
            registrationUser();
        });
    }

    private void registrationUser()
    {
        String login = reg_login.getCharacters().toString();
        String email = reg_email.getCharacters().toString();
        String password = reg_password.getCharacters().toString();

        reg_login.setStyle("-fx-border-color: #fafafa;");
        reg_email.setStyle("-fx-border-color: #fafafa;");
        reg_password.setStyle("-fx-border-color: #fafafa;");
        reg_btn.setText("Изменить данные");

        if (login.length() <= 1)
            reg_login.setStyle("-fx-border-color: #e06249;");
        else if (email.length() <= 5 || !email.contains("@") || !email.contains("."))
            reg_email.setStyle("-fx-border-color: #e06249;");
        else if (password.length() <= 3)
            reg_password.setStyle("-fx-border-color: #e06249;");
        else if (db.thisExistUser(login))
            reg_btn.setText("Логин занят");
        else
        {
            db.changeUser(login, email, md5String(password));
            reg_login.setText("");
            reg_email.setText("");
            reg_password.setText("");
            reg_btn.setText("Все готово :)");
        }
    }

    public static String md5String(String password)
    {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try
        {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {e.printStackTrace();}

        BigInteger bigInteger = new BigInteger(1, digest);
        String m5dHex = bigInteger.toString(16);

        while (m5dHex.length() < 32)
            m5dHex = "0" + m5dHex;

        return m5dHex;
    }

}

