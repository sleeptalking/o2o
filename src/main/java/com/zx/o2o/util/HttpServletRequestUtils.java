package com.zx.o2o.util;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtils {
    public static int getInt(HttpServletRequest request, String key) {

        try {
            return Integer.decode(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }

    }

    public static Long getLong(HttpServletRequest request, String key) {
        try {
            return Long.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1l;
        }

    }
    public static Double getDouble(HttpServletRequest request, String key) {
        try {
            return Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1d;
        }

    }
    public static Boolean getBoolean(HttpServletRequest request, String key) {
        try {
            return Boolean.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return false;
        }

    }
    public static String getString(HttpServletRequest request, String name) {
        try {
            String result = request.getParameter(name);
            if (result != null) {
                result = result.trim();
            }
            if ("".equals(result))
                result = null;
            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
