package com.yoj.storage;

import com.yoj.utils.auth.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class StorageService {
//    @Value("${spring.servlet.multipart.location}")
    private final Path storeRootLocation;
    @Autowired
    private UserUtil currentUserUtil;

    @Autowired
    public StorageService(StorageProperties properties) {
        this.storeRootLocation = Paths.get(properties.getLocation());
    }
//    //存储文件
//    public String store(MultipartFile uploadFile) {
//        String newName = null;
//        try {
//            if (uploadFile.isEmpty()) {
//                throw new IOException("Failed to store empty file.");
//            }
//            String oldName = uploadFile.getOriginalFilename();
//            newName = UUID.randomUUID().toString()
//                    + oldName.substring(oldName.lastIndexOf("."), oldName.length());
//            uploadFile.transferTo(new File(storeLocation, newName));
//            Files.copy(inputStream, destinationFile,
//                    StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return newName;
//    }

    /**
     * @param uploadFile
     * @return is or not upload successfully
     */
    public boolean storeAvatar(MultipartFile uploadFile, HttpServletRequest req) {
        // empty file data
        if (uploadFile == null || uploadFile.isEmpty()) {
            log.info("not have uploadFile data");
            return false;
        }
        // 组成新的文件名
        String oldName = uploadFile.getOriginalFilename();
        //不是图片后缀名
        if (!oldName.endsWith(".jpg") && !oldName.endsWith(".png") && !oldName.endsWith(".gif")) {
            log.info("not have a image suffix");
            return false;
        }
        //related path : static/uploadFile
        // newFile is composed of saveDirectory + useId.originSuffix
        String newFileName = currentUserUtil.getUserDetail().getUsername() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        Path storePath = Paths.get(storeRootLocation.normalize().toAbsolutePath().toString(), "avatar", newFileName);
        try (InputStream inputStream = uploadFile.getInputStream()) {
            Files.copy(inputStream, storePath,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // judge is or not a image file
        File savedImage = new File(storePath.toString());
        if (!isImage(savedImage)) {
            log.info("is not a image file");
            savedImage.delete();
            return false;
        }
        return true;
    }

    /**
     * 通过读取文件并获取其width及height的方式，来判断判断当前文件是否图片，这是一种非常简单的方式。
     * 这种方式较安全！
     *
     * @param imageFile
     * @return
     */
    public static boolean isImage(File imageFile) {
        if (!imageFile.exists()) {
            return false;
        }
        Image img = null;
        try {
            img = ImageIO.read(imageFile);
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            img = null;
        }
    }
}
