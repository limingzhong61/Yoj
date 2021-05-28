package com.yoj.storage;

import com.yoj.utils.AppInfo;
import com.yoj.utils.auth.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class StorageService {

    private final Path storeRootLocation;

    @Autowired
    private UserUtil currentUserUtil;

    @Autowired
    public StorageService(AppInfo appInfo) {
        this.storeRootLocation = Paths.get(appInfo.getStoreRootLocationStr());
    }

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
        String newFileName = currentUserUtil.getUserDetail().getUserId() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        Path storePath = Paths.get(storeRootLocation.normalize().toAbsolutePath().toString(), "avatar", newFileName);
        try (InputStream inputStream = uploadFile.getInputStream()) {
            Files.copy(inputStream, storePath,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File savedImage = new File(storePath.toString());// judge is or not a image file
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

    public Path load(String filename) {
        return storeRootLocation.resolve(filename);
    }

    /**
     * 加载图片
     *
     * @param filename
     * @return
     */
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
//                throw new StorageFileNotFoundException(
//                        "Could not read file: " + filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
