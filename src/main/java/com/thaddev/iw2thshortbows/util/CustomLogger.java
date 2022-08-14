package com.thaddev.iw2thshortbows.util;

public record CustomLogger(String id) {
    public void debug(String message) {
        System.out.println(Utils.ANSI_YELLOW + "[" + id + "] " + Utils.ANSI_RESET + message);
    }

    public void error(String message) {
        System.err.println(Utils.ANSI_YELLOW + "[" + id + "] " + Utils.ANSI_RESET + message);
    }
}
