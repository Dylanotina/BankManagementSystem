package com.example.BankManagementSystem.utils;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class secretKey {
    public static String encryptPassword(String password){
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        return encryptor.encryptPassword(password);
    }
    public static boolean checkPassword(String inputPassword, String encryptedStoredPassword){
        StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
        return encryptor.checkPassword(inputPassword, encryptedStoredPassword);
    }


}
