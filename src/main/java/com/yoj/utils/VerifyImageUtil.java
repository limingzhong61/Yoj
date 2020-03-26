package com.yoj.utils;

import com.yoj.utils.verification_code.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

@Component
@Slf4j
public class VerifyImageUtil {
    /**
     * 1、验证码工具
     */
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    private static final String VERIFY_CODE_NAME = "rightCode";
    /**
     * 3、校对验证码
     *
     * @param httpServletRequest
     * @return
     */
    public boolean verify(HttpServletRequest httpServletRequest) {
        String tryCode = httpServletRequest.getParameter("tryCode");
        return verify(httpServletRequest, tryCode);
    }

    /**
     * 3、校对验证码
     *
     * @param httpServletRequest
     * @param tryCode
     * @return
     */
    public boolean verify(HttpServletRequest httpServletRequest, String tryCode) {
        String rightCode = (String) httpServletRequest.getSession().getAttribute(VERIFY_CODE_NAME);
        log.info(VERIFY_CODE_NAME + rightCode + " ———— tryCode:" + tryCode);
        if (rightCode == null || tryCode == null) {
            return false;
        }
        if (rightCode.equals(tryCode)) {
            setVerifyCode(httpServletRequest,null);
            return true;
        }
        return false;
    }

    private void setVerifyCode(HttpServletRequest httpServletRequest, String verifyCode){
        httpServletRequest.getSession().setAttribute(VERIFY_CODE_NAME, verifyCode);
    }

    /**
     * 2、生成验证码
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @throws Exception
     */
    public void getImage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws Exception {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // 生产验证码字符串并保存到session中
            String createText = defaultKaptcha.createText();
            this.setVerifyCode(httpServletRequest,createText);
            // 使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
