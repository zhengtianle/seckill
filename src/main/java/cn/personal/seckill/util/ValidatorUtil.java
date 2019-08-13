package cn.personal.seckill.util;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @Time 19-8-9
 * @Author ZhengTianle
 * Description:
 */
public class ValidatorUtil {
    private static final Pattern mobile_pattern= Pattern.compile("1\\d{10}");//1开头，然后10个数字，那么正确的手机号
    //验证手机号格式
    public static boolean isMobile(String src) {
        if(StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher m=mobile_pattern.matcher(src);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(isMobile("17863113969"));
    }
}
