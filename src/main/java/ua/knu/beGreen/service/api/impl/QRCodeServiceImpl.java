package ua.knu.beGreen.service.api.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import ua.knu.beGreen.service.api.QRCodeService;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QRCodeServiceImpl implements QRCodeService {
    private static final Integer QR_CODE_WIDTH = 400;
    private static final Integer QR_CODE_HEIGHT = 400;

    @Override
    public BufferedImage generateEAN13BarcodeImage(String barcodeText) {
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BufferedImage qr;

        try {
            BitMatrix bitMatrix = barcodeWriter
                    .encode(barcodeText, BarcodeFormat.QR_CODE, QR_CODE_WIDTH, QR_CODE_HEIGHT);
            qr = MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            qr = new BufferedImage(QR_CODE_WIDTH, QR_CODE_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        }

        return qr;
    }

    @Override
    public byte[] toByteArray(BufferedImage bi, String format) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bi, format, stream);
        } catch (IOException e) {
            log.error("IOException while to byte array", e);
        }
        return stream.toByteArray();
    }
}
