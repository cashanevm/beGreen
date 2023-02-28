package ua.knu.beGreen.service.api;

import java.awt.image.BufferedImage;

public interface QRCodeService {
    BufferedImage generateEAN13BarcodeImage(String barcodeText);

    byte[] toByteArray(BufferedImage bi, String format);
}
