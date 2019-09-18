package com.zx.o2o.web.shopadmin;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/code")
public class KaptchaContoller {
    private Producer kaptchaProducer = null;

    @Autowired
    public void setCaptchaProducer(Producer kaptchaProducer) {
        this.kaptchaProducer = kaptchaProducer;
    }

    @RequestMapping(value = "/kaptcha",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getCode(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<>();
        String capText = kaptchaProducer.createText();
        request.getSession().setAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = kaptchaProducer.createImage(capText);
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", out);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(out.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
            modelMap.put("success",true);
            modelMap.put("codeImage",captchaBase64);
            modelMap.put("sessionId",request.getSession().getId());
        } catch (IOException e) {
            modelMap.put("success",false);
            modelMap.put("errMsg",e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                e.printStackTrace();
            }
        }
        return modelMap;
    }

}
