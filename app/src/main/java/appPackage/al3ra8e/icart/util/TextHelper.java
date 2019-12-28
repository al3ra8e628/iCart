package appPackage.al3ra8e.icart.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by al3ra8e on 3/12/2018.
 */

public class TextHelper {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }



    /*
      ^                 # start-of-string
     (?=.*[0-9])       # a digit must occur at least once
     (?=.*[a-z])       # a lower case letter must occur at least once
     (?=.*[A-Z])       # an upper case letter must occur at least once
     (?=.*[@#$%^&+=])  # a special character must occur at least once
     (?=\S+$)          # no whitespace allowed in the entire string
     .{8,}             # anything, at least eight places though
     $                 # end-of-string
     * */


    public static final Pattern VALID_PASSWORD_ADDRESS_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", Pattern.CASE_INSENSITIVE);

    public static boolean validatePassword(String passStr) {
        Matcher matcher = VALID_PASSWORD_ADDRESS_REGEX.matcher(passStr);
        return matcher.find();
    }



}
