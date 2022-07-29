package com.thaddev.coolideas.util;

import com.thaddev.coolideas.CoolIdeasMod;
import net.minecraft.text.Text;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ColorUtils {
    public static String black = "\u00A70";
    public static String dark_blue = "\u00A71";
    public static String dark_green = "\u00A72";
    public static String dark_aqua = "\u00A73";
    public static String dark_red = "\u00A74";
    public static String dark_purple = "\u00A75";
    public static String gold = "\u00A76";
    public static String gray = "\u00A77";
    public static String dark_gray = "\u00A78";
    public static String blue = "\u00A79";
    public static String green = "\u00A7a";
    public static String aqua = "\u00A7b";
    public static String red = "\u00A7c";
    public static String light_purple = "\u00A7d";
    public static String yellow = "\u00A7e";
    public static String white = "\u00A7f";
    public static String obfuscated = "\u00A7k";
    public static String bold = "\u00A7l";
    public static String strikethrough = "\u00A7m";
    public static String underline = "\u00A7n";
    public static String italic = "\u00A7o";
    public static String reset = "\u00A7r";

    public static String getColorFromCode(String color) {
        try {
            Field colorField = ColorUtils.class.getField(color.substring(2));
            return colorField.get("").toString();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            CoolIdeasMod.LOGGER.error(Arrays.toString(e.getStackTrace()));
            return "";
        }
    }

    public static String from(String fromText) {
        return convert(fromText, dark_aqua + "[" + gold + "CoolIdeas" + dark_aqua + "] " + reset);
    }

    public static String fromNoTag(String fromText) {
        return convert(fromText, "");
    }

    public static Text component(String fromText) {
        return Text.of(fromText);
    }

    public static String convert(String fromText, String initial) {
        char[] chars = fromText.toCharArray();
        StringBuilder builder = new StringBuilder(initial);
        int startIndex = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(' && chars[i + 1] == '%' && chars[i + 2] == '$') {
                startIndex = i + 1;
            } else if (chars[i] == ')' && startIndex != 0) {
                builder.append(getColorFromCode(fromText.substring(startIndex, i)));
                startIndex = 0;
            } else if (startIndex == 0) {
                builder.append(chars[i]);
            }
        }
        return builder.toString();
    }

    public static int rgbToInteger(int red, int green, int blue, int alpha) {
        return (alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF);
    }

    public static int rgbToInteger(int red, int green, int blue) {
        return rgbToInteger(red, green, blue, 255);
    }

    public static Color integerToColor(int color) {
        return new Color(color);
    }
}