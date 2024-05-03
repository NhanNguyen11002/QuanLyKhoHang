package org.example.quanlykhohang.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static final String PHONE_NUMBER_REGEX = "^0\\d{9}$";
    // Biểu thức chính quy cho địa chỉ email phổ biến
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static boolean validatePhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    // Phương thức validate địa chỉ email
    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
