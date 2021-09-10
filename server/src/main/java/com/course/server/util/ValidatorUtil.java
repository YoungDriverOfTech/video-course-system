package com.course.server.util;

import com.course.server.exception.ValidatorException;
import org.springframework.util.StringUtils;

public class ValidatorUtil {

    /**
     * require check
     */
    public static void require(String str, String fieldName) {
        if (StringUtils.isEmpty(str)) {
            throw new ValidatorException(fieldName + " is required.");
        }
    }

    /**
     * length check
     */
    public static void length(String str, String fieldName, int min, int max) {
        int length = 0;
        if (!StringUtils.isEmpty(str)) {
            length = str.length();
        }

        if (length < min || length > max) {
            throw new ValidatorException(fieldName + " length range is " + min + "~" +max);
        }
    }
}
