package freezy.utils;

import org.springframework.stereotype.Service;

@Service
public class FreazyStringUtils {

    public static String replaceSpaces(String input){
        return input.replace(" ", "_");
    }

}
