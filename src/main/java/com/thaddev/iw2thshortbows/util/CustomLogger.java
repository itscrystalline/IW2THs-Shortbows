package com.thaddev.iw2thshortbows.util;

public record CustomLogger(String id) {
    public void debug(String message) {
        System.out.println(ColorUtils.ANSI_YELLOW + "[" + id + "] " + ColorUtils.ANSI_RESET + message);
    }

    public void error(String message) {
        System.err.println(ColorUtils.ANSI_YELLOW + "[" + id + "] " + ColorUtils.ANSI_RESET + message);
    }
}
