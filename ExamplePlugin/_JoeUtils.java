package ExamplePlugin;

import PluginReference.ChatColor;
import PluginReference.MC_Player;
import PluginReference.RainbowUtils;

import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;

import ExamplePlugin.MyPlugin;

public class _JoeUtils {
    public static SimpleDateFormat shortDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    public static ConcurrentHashMap<String, Long> tooSoon = new ConcurrentHashMap();

    public static String GetDateStringFromLong(long dt) {
        return shortDateFormat.format(dt).toString();
    }

    public static void reply(MC_Player plr, String msg) {
        if (plr == null) {
            System.out.println(ChatColor.StripColor((String)msg));
            return;
        }
        plr.sendMessage(msg);
    }

    public static void ConsoleMsg(String msg) {
        System.out.println("[" + MyPlugin.pluginName + "]: " + ChatColor.StripColor((String)msg));
    }

    public static String TranslateColorString(String parm) {
        return _JoeUtils.TranslateColorString(parm, true, false);
    }

    public static String TranslateColorString(String parm, boolean IsOp, boolean fAllowSpaces) {
        StringBuilder res = new StringBuilder();
        boolean pending = false;
        int i = 0;
        while (i < parm.length()) {
            char ch = parm.charAt(i);
            if (ch == '&') {
                pending = true;
            } else if (pending) {
                pending = false;
                if (ch == '0' && IsOp) {
                    res.append(String.valueOf(ChatColor.BLACK));
                } else if (ch == '1') {
                    res.append(String.valueOf(ChatColor.DARK_BLUE));
                } else if (ch == '2') {
                    res.append(String.valueOf(ChatColor.DARK_GREEN));
                } else if (ch == '3') {
                    res.append(String.valueOf(ChatColor.DARK_AQUA));
                } else if (ch == '4' && IsOp) {
                    res.append(String.valueOf(ChatColor.DARK_RED));
                } else if (ch == '5') {
                    res.append(String.valueOf(ChatColor.DARK_PURPLE));
                } else if (ch == '6') {
                    res.append(String.valueOf(ChatColor.GOLD));
                } else if (ch == '7') {
                    res.append(String.valueOf(ChatColor.GRAY));
                } else if (ch == '8') {
                    res.append(String.valueOf(ChatColor.DARK_GRAY));
                } else if (ch == '9') {
                    res.append(String.valueOf(ChatColor.BLUE));
                } else if (ch == 'a') {
                    res.append(String.valueOf(ChatColor.GREEN));
                } else if (ch == 'b') {
                    res.append(String.valueOf(ChatColor.AQUA));
                } else if (ch == 'c' && IsOp) {
                    res.append(String.valueOf(ChatColor.RED));
                } else if (ch == 'd') {
                    res.append(String.valueOf(ChatColor.LIGHT_PURPLE));
                } else if (ch == 'e') {
                    res.append(String.valueOf(ChatColor.YELLOW));
                } else if (ch == 'f') {
                    res.append(String.valueOf(ChatColor.WHITE));
                } else if (ch == 'l') {
                    res.append(String.valueOf(ChatColor.BOLD));
                } else if (ch == 'm') {
                    res.append(String.valueOf(ChatColor.STRIKETHROUGH));
                } else if (ch == 'k' && IsOp) {
                    res.append(String.valueOf(ChatColor.MAGIC));
                } else if (ch == 'n' && IsOp) {
                    res.append(String.valueOf(ChatColor.UNDERLINE));
                } else if (ch == 'o') {
                    res.append(String.valueOf(ChatColor.ITALIC));
                }
            } else if (IsOp) {
                res.append(ch);
            } else if (_JoeUtils.IsCharLetterOrDigit(ch)) {
                res.append(ch);
            } else if (fAllowSpaces && ch == ' ') {
                res.append(ch);
            }
            ++i;
        }
        return res.toString();
    }

    public static boolean IsCharLetterOrDigit(char ch) {
        if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') {
            return true;
        }
        if (ch >= '0' && ch <= '9') {
            return true;
        }
        return false;
    }

    public static String StringReplace(String src, String key, String val) {
        int idx = src.indexOf(key);
        if (idx < 0) {
            return src;
        }
        return String.valueOf(src.substring(0, idx)) + val + src.substring(idx + key.length());
    }

    public static boolean TooSoon(MC_Player p, String what, int seconds) {
        if (p.isOp()) {
            return false;
        }
        String key = String.valueOf(what) + "." + p.getName();
        Long msBefore = tooSoon.get(key);
        Long curMS = System.currentTimeMillis();
        if (msBefore != null) {
            Long msDelta = curMS - msBefore;
            Long msWaitTime = 1000 * (long)seconds;
            if (msDelta < msWaitTime) {
                p.sendMessage(String.valueOf(ChatColor.RED) + "[" + what + "] Too soon, you must wait: " + ChatColor.AQUA + RainbowUtils.TimeDeltaString_JustMinutesSecs((long)(msWaitTime - msDelta)));
                return true;
            }
        }
        tooSoon.put(key, curMS);
        return false;
    }

    public static void EnsureDirectory(String dirName) {
        File pDir = new File(dirName);
        if (pDir.isDirectory()) {
            return;
        }
        try {
            System.out.println("Creating directory: " + dirName);
            pDir.mkdir();
        }
        catch (Throwable exc) {
            System.out.println("EnsureDirectory " + dirName + ": " + exc.toString());
        }
    }
}

