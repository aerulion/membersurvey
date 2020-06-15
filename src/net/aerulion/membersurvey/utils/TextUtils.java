package net.aerulion.membersurvey.utils;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class TextUtils {

    private final static int CHAT_CENTER_PX = 154;

    public static String CenteredChatMessage(String message) {
        String[] lines = message.split("\n", 40);
        StringBuilder returnMessage = new StringBuilder();
        for (String line : lines) {
            int messagePxSize = 0;
            boolean previousCode = false;
            boolean isBold = false;
            for (char c : line.toCharArray()) {
                if (c == '§') {
                    previousCode = true;
                } else if (previousCode) {
                    previousCode = false;
                    isBold = c == 'l';
                } else {
                    DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                    messagePxSize = isBold ? messagePxSize + dFI.getBoldLength() : messagePxSize + dFI.getLength();
                    messagePxSize++;
                }
            }
            int toCompensate = CHAT_CENTER_PX - messagePxSize / 2;
            int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
            int compensated = 0;
            StringBuilder sb = new StringBuilder();
            while (compensated < toCompensate) {
                sb.append(" ");
                compensated += spaceLength;
            }
            returnMessage.append(sb.toString()).append(line).append("\n");
        }
        return returnMessage.toString();
    }

    public static void sendCenteredChatMessage(Player player, String message) {
        player.sendMessage(CenteredChatMessage(message));
    }

    public static void sendChatSpacerLine(Player player, String chatcolor) {
        player.sendMessage(chatcolor + "§m                                                                                ");
    }

    public static void sendColoredConsoleMessage(final String msg) {
        final ConsoleCommandSender sender = Bukkit.getConsoleSender();
        sender.sendMessage(msg);
    }

    public static List<String> WrapString(String input, int width) {
        List<String> WrappedString = new ArrayList<>();
        String wrapped = WordUtils.wrap(input, width, "\n", true);
        Collections.addAll(WrappedString, wrapped.split("\n"));
        return WrappedString;
    }

    public static ArrayList<String> filterForTabCompleter(ArrayList<String> commandList, String filter) {
        if (filter != null) {
            for (Iterator<String> iterator = commandList.iterator(); iterator.hasNext(); ) {
                String value = iterator.next();
                if (!value.toLowerCase().startsWith(filter.toLowerCase())) {
                    {
                        iterator.remove();
                    }
                }
            }
        }
        return commandList;
    }
}