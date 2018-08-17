package com.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
    public static int getInt(HttpServletRequest request, String key){
        try{
            return Integer.valueOf(request.getParameter(key));
        } catch (Exception e){
            return -1;
        }
    }

    public static long getLong(HttpServletRequest request, String key){
        try{
            return Long.valueOf(request.getParameter(key));
        } catch (Exception e){
            return -1;
        }
    }

    public static boolean getBolean(HttpServletRequest request, String key){
        try{
            return Boolean.valueOf(request.getParameter(key));
        } catch (Exception e){
            return false;
        }
    }

    public static double getDouble(HttpServletRequest request, String key){
        try{
            return Double.parseDouble(request.getParameter(key));
        } catch (Exception e){
            return -1d;
        }
    }
    public static String getString(HttpServletRequest request, String key){
        try{
           String result = request.getParameter(key);
           if(result!=null){
               result = result.trim();
           }
           if("".equals(result)){
               result = null;
           }
           return result;
        } catch (Exception e){
            return "";
        }
    }

}
