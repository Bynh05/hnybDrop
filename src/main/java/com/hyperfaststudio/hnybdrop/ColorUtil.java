package com.hyperfaststudio.hnybdrop;

import net.md_5.bungee.api.ChatColor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {
    private static final Pattern HEX_PATTERN = Pattern.compile("(?i)&(#(?:[0-9a-f]{6}))");

    public static String translateColors(String text) {
        if (text == null) return "";
        text = text.replace('§', '&');

        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String hexColor = matcher.group(1);
            String replacement = ChatColor.of(hexColor).toString();
            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);

        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }
}
