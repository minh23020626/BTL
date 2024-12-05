//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.library.book;

import com.google.zxing.WriterException;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QRCodeGeneratorTest {
    public QRCodeGeneratorTest() {
    }

    @Test
    public void testGenerateQRCodeBytes() throws WriterException, IOException {
        String content = "https://www.example.com";
        byte[] qrCodeBytes = QRCodeGenerator.generateQRCodeBytes(content);
        Assertions.assertNotNull(qrCodeBytes, "QR code bytes should not be null");
        Assertions.assertTrue(qrCodeBytes.length > 0, "QR code byte array should have content");
        Assertions.assertTrue(qrCodeBytes.length > 100, "QR code byte array should have reasonable size");
    }
}
