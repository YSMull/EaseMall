package com.ysmull.easeshop.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author maoyusu
 */
public class ImageUtil {

    private ImageUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }


    public static byte[] getPicBytes(MultipartFile pic) throws IOException {
        BufferedImage originalImage = ImageIO.read(pic.getInputStream());
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        int l = width < height ? height : width;
        BufferedImage newImage = resize(originalImage, l, l);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(newImage, "png", out);
        out.flush();
        return out.toByteArray();
    }
}
