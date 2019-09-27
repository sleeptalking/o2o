package com.zx.o2o.util;

public class PathUtils {

    private static String  separator = System.getProperty("file.separator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "D:/tomcat9/webapps/o2o/";
        } else {
            basePath = "home/xiangze/";
        }
        basePath = basePath.replace("/", separator);
        return basePath;
    }
    public static String getShopImagePath(Long shopId) {

        String imagePath = "images/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", separator);
    }
}
