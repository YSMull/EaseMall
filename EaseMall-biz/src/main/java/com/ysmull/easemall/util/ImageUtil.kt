package com.ysmull.easemall.util

import org.springframework.web.multipart.MultipartFile

import javax.imageio.ImageIO
import java.awt.*
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.IOException

/**
 * @author maoyusu
 */
class ImageUtil private constructor() {

    init {
        throw IllegalStateException("Utility class")
    }

    companion object {

        private fun resize(img: BufferedImage, height: Int, width: Int): BufferedImage {
            val tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH)
            val resized = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
            val g2d = resized.createGraphics()
            g2d.drawImage(tmp, 0, 0, null)
            g2d.dispose()
            return resized
        }


        @Throws(IOException::class)
        fun getPicBytes(pic: MultipartFile): ByteArray {
            val originalImage = ImageIO.read(pic.inputStream)
            val width = originalImage.width
            val height = originalImage.height
            val l = if (width < height) height else width
            val newImage = resize(originalImage, l, l)
            val out = ByteArrayOutputStream()
            ImageIO.write(newImage, "png", out)
            out.flush()
            return out.toByteArray()
        }
    }
}
