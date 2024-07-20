package freezy.utils;

import org.springframework.stereotype.Service;

@Service
public class StringUtils {

    public static String replaceSpaces(String input){
        return input.replace(" ", "_");
    }

}
