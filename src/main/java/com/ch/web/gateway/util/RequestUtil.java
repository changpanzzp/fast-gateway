package com.ch.web.gateway.util;

import org.springframework.format.annotation.DateTimeFormat;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * http请求工具类
 *
 * @author caich
 **/
public class RequestUtil {

    private static final String ACCESS_TOKEN = "AccessToken";

    /**
     * 获取请求参数中的token
     *
     * @param request
     * @return
     **/
    public static String getAccessToken(HttpServletRequest request) {
        String accessToken = request.getHeader(ACCESS_TOKEN);
        if (accessToken == null || accessToken.isEmpty()) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(ACCESS_TOKEN)) {
                        accessToken = cookie.getValue();
                        break;
                    }
                }
            }
        }
        if (accessToken == null || accessToken.isEmpty()) {
            accessToken = request.getParameter(ACCESS_TOKEN);
        }
        return accessToken;
    }

    /**
     * 通过class
     *
     * @param
     * @return
     **/
    public static Object convertByClass(String str, Field field, Class<?> objectClass) {
        if (str == null) {
            return null;
        }
        if (objectClass == String.class) {
            return str;
        }
        if (objectClass == Integer.class) {
            return Integer.valueOf(str);
        }
        if (objectClass == Short.class) {
            return Short.valueOf(str);
        }
        if (objectClass == Long.class) {
            return Long.valueOf(str);
        }
        if (objectClass == Double.class) {
            return Double.valueOf(str);
        }
        if (objectClass == Float.class) {
            return Float.valueOf(str);
        }
        if (objectClass == Boolean.class) {
            return Boolean.valueOf(str);
        }
        if (objectClass == Character.class) {
            return Float.valueOf(str);
        }
        if (objectClass == BigDecimal.class) {
            return new BigDecimal(str);
        }
        if (objectClass == Date.class) {
            DateTimeFormat dateTimeFormat = field.getAnnotation(DateTimeFormat.class);
            if (dateTimeFormat == null) {
                throw new IllegalArgumentException(objectClass.toString() + " is date type, need set DateTimeFormat");
            }
            try {
                return new SimpleDateFormat(dateTimeFormat.pattern()).parse(str);
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }
        }
        throw new IllegalArgumentException(objectClass.toString() + " not support convert");
    }
}
