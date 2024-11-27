package lk.ijse.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean userIdValidate(String userId) {
        String userRegex = "^US-\\d{4}$";
        Pattern pattern = Pattern.compile(userRegex);
        Matcher matcher = pattern.matcher(userId);
        return matcher.matches();
    }

    public static boolean studentIdValidate(String studentId) {
        String studentRegex = "^STU-\\d{4}$";
        Pattern pattern = Pattern.compile(studentRegex);
        Matcher matcher = pattern.matcher(studentId);
        return matcher.matches();
    }

    public static boolean programIdValidate(String programId) {
        String programRegex = "^PRO-\\d{4}$";
        Pattern pattern = Pattern.compile(programRegex);
        Matcher matcher = pattern.matcher(programId);
        return matcher.matches();
    }

    public static boolean nameValidate(String name) {
        String nameRegex = "^[A-z\\s]{4,15}$";
        Pattern pattern = Pattern.compile(nameRegex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static boolean passwordValidate(String password) {
        String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean addressValidate(String address) {
        String addressRegex = "[A-z @ 0-9]{4,20}";
        Pattern pattern = Pattern.compile(addressRegex);
        Matcher matcher = pattern.matcher(address);
        return matcher.matches();
    }

    public static boolean mobileValidate(String mobile) {
        String mobileRegex = "^(?:7|0|(?:\\\\\\\\+94))[0-9]{9,10}$";
        Pattern pattern = Pattern.compile(mobileRegex);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    public static boolean emailCheck(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean qtyValidate(String qty) {
        String programRegex = "^[1-9][0-9]*$";
        Pattern pattern = Pattern.compile(programRegex);
        Matcher matcher = pattern.matcher(qty);
        return matcher.matches();
    }

    public static boolean feeValidate(String fee) {
        String priceRegex = "^(\\d+\\.?\\d{0,2})$";
        Pattern pattern = Pattern.compile(priceRegex);
        Matcher matcher = pattern.matcher(fee);
        return matcher.matches();
    }

    public static boolean durationValidate(String duration) {
        String durationMonthsRegex = "^(\\d+)(?:\\.?\\d{0,2})?$";
        Pattern pattern = Pattern.compile(durationMonthsRegex);
        Matcher matcher = pattern.matcher(duration);
        return matcher.matches();
    }
}
