package com.vtr.camera;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {
    private static final BigDecimal SIZE_TO_MB_CONVERT = BigDecimal.valueOf(1024 * 1024);

    static public double convertBytesToMB(long num) {
        return BigDecimal.valueOf(num)
                .divide(SIZE_TO_MB_CONVERT, 3, RoundingMode.FLOOR)
                .doubleValue();
    }
}
