package com.zx.o2o.util;

import com.zx.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtils {
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random random = new Random();

    public static String generateThumbnail(ImageHolder imageHolder, String targetAddr){
        String realFileName = getRandomFileName();
        String extension = getFileExtension(imageHolder.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtils.getImgBasePath()+relativeAddr);
        System.out.println(basePath);

        try{
            Thumbnails.of(imageHolder.getImage()).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.png")),0.25f).outputQuality(0.8f).toFile(dest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return relativeAddr;
    }


    public static void deleteFileOrpat(String path){
        File fileOrPath = new File(PathUtils.getImgBasePath()+path);
        if(fileOrPath.exists()){
            if(fileOrPath.isDirectory()){
                File[] files = fileOrPath.listFiles();
                for(int i=0;i<files.length;i++){
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }


    public static String generateThumbnail(CommonsMultipartFile thumbnail,String targetAddr){
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail);
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtils.getImgBasePath()+relativeAddr);
        try{
            Thumbnails.of(thumbnail.getInputStream()).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.png")),0.25f).outputQuality(0.8f).toFile(dest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return relativeAddr;

    }

    /**
     * 创建目标路径说涉及到的目录
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtils.getImgBasePath() + targetAddr;
        File file = new File(realFileParentPath);
        if(!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 获取输入文件的扩展名
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }
    private static String getFileExtension(CommonsMultipartFile cFile) {
        String originalFileName = cFile.getOriginalFilename();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒+随机五位数
     * @return
     */
    private static String getRandomFileName() {
        //获取随机五位数
        int rannum = random.nextInt(89999)+10000;
        String nowTimeStr = simpleDateFormat.format(new Date());
        return  nowTimeStr+rannum;
    }



}
