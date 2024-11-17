package edu.northeastern.group_10_lyricshuffle.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IdenticonGenerator {
    private static final int GRID_SIZE = 5;
    private static final int BLOCK_SIZE = 70;
    private static final int MARGIN = 20;
    private static final int IMAGE_SIZE = BLOCK_SIZE * GRID_SIZE + (MARGIN * 2);

    // Predefined hue ranges for vibrant colors
    private static final float[][] COLOR_RANGES = {
            {0f, 60f},    // Red to Yellow
            {180f, 240f}, // Cyan to Blue
            {270f, 330f}  // Purple to Pink
    };

    public static Bitmap generate(String name) {
        Bitmap bitmap = Bitmap.createBitmap(IMAGE_SIZE, IMAGE_SIZE, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);

        byte[] hash = generateHash(name);

        // Generate vibrant color using HSV
        float[] hsv = new float[3];
        int rangeIndex = Math.abs(hash[0] % COLOR_RANGES.length);
        float[] range = COLOR_RANGES[rangeIndex];

        // Hue: Use hash to select within the chosen range
        hsv[0] = range[0] + (Math.abs(hash[1]) % (range[1] - range[0]));
        // Saturation: Keep it high for vibrant colors (0.7-0.9)
        hsv[1] = 0.7f + (Math.abs(hash[2]) % 20) / 100f;
        // Value: Keep it high for brightness (0.8-1.0)
        hsv[2] = 0.8f + (Math.abs(hash[3]) % 20) / 100f;

        Paint paint = new Paint();
        paint.setColor(Color.HSVToColor(hsv));

        boolean[][] pattern = generatePattern(hash);

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (pattern[i][j]) {
                    float x = MARGIN + (j * BLOCK_SIZE);
                    float y = MARGIN + (i * BLOCK_SIZE);
                    canvas.drawRect(x, y, x + BLOCK_SIZE, y + BLOCK_SIZE, paint);
                }
            }
        }

        return createCircularBitmap(bitmap);
    }

    private static byte[] generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[32];
        }
    }

    private static boolean[][] generatePattern(byte[] hash) {
        boolean[][] pattern = new boolean[GRID_SIZE][GRID_SIZE];

        // Generate only half of the pattern (it will be mirrored)
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE/2 + 1; j++) {
                pattern[i][j] = ((hash[(i*GRID_SIZE + j) % hash.length] & 0xFF) > 127);

                // Mirror the pattern for symmetry (except the middle column)
                if (j < GRID_SIZE/2) {
                    pattern[i][GRID_SIZE-1-j] = pattern[i][j];
                }
            }
        }

        return pattern;
    }

    public static Bitmap createCircularBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        canvas.drawCircle(width/2f, height/2f, width/2f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return output;
    }
}
