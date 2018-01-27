package com.and.yzy.frame.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证
 */
public class MatchStingUtil {

    /**
     * 判断手机号码
     */
    public static boolean isMobile(String mobiles) {
        if (mobiles.length()<11)
            return false;
        Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$");
        Matcher matcher = pattern.matcher(mobiles);
        return matcher.matches();
    }

}
