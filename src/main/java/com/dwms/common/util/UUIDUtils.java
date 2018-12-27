package com.dwms.common.util;

import java.util.UUID;

/**
 * @author windy
 * @date 2016-12-15
 */
public class UUIDUtils {

    /**
     * 生成32位的UUID.
     * @return
     * @author windy
     * @date 2016-12-07
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
