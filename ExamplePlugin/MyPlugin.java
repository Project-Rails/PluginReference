package ExamplePlugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import PluginReference.ChatColor;
import PluginReference.MC_EventInfo;
import PluginReference.MC_Location;
import PluginReference.MC_Player;
import PluginReference.MC_Server;
import PluginReference.PluginBase;
import PluginReference.PluginInfo;

public class MyPlugin extends PluginBase {
	//None of this next stuff is necessary, it's just how I like to format it
    public static MC_Server server = null;
    public static String pluginName = "ExamplePlugin";
    public static String version = "17w17a";
    public static String PluginDir = "plugins_mod" + File.separator + "ExamplePlugin" + File.separator;
    public static String PluginDataFilename = String.valueOf(PluginDir) + "motd.txt";
    private static Logger logger = Logger.getLogger(pluginName);

    public void onStartup(MC_Server server) {
    	System.out.println(String.format("%s %s starting up...", pluginName, version));
    	//Register and commands this way:
    	server.registerCommand((PluginReference.MC_Command)new MotdCmd());
    	//I recommend installing "_JoeUtils" on every plugin you make. Super easy. This creates the plugin's folder
    	_JoeUtils.EnsureDirectory(PluginDir);
    	File yourFile = new File(PluginDataFilename);
    	try {
			yourFile.createNewFile();
			System.out.println("[ExamplePlugin]: Setup a default MOTD file.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    //Register the plugin information -- that's all you need
    public PluginInfo getPluginInfo() {
        PluginInfo info = new PluginInfo();
        info.name = pluginName;
        info.description = "A plugin example for Project Rails (" + version + ")";
        return info;
    }

    public void onShutdown() {
    	System.out.println(String.format("%s %s shutting down...", pluginName, version));
    }
    
    //Send an MOTD
    public void onPlayerJoin(MC_Player plr) {
		try {
			File file = new File(MyPlugin.PluginDataFilename);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				stringBuffer.append("\n" + ChatColor.GRAY);
			}
			fileReader.close();
			plr.sendMessage(" ");
			plr.sendMessage(ChatColor.GRAY + stringBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
			plr.sendMessage(ChatColor.RED + "Internal exception! Please check console for stack trace.");
		}
    }
    
    //Block break event
    public void onAttemptBlockBreak(MC_Player plr, MC_Location loc, MC_EventInfo ei) {
    	//Check if a player has a permission
    	if(plr.hasPermission("exampleplugin.break") == true) {
    		//Send a message to the player if they can break it
    		plr.sendMessage("You can break this block!");
    	} else {
    		//Cancel the event
    		ei.isCancelled = true;
    		//Send a message to the player
    		plr.sendMessage(ChatColor.RED + "You can't break this block!");
    		//Damage the player by half of a heart
    		plr.setHealth(plr.getHealth() - 1);
    	}
    }
    
    //Kick event
    public void onPlayerKick(MC_Player player, String reason, MC_EventInfo ei) {
    	if(reason.contains("banned")) {
    		//Change the kick message
    		ei.tag = ChatColor.GOLD + "You were banned :(";
    		ei.isCancelled = false;
    		ei.isModified = true;
    	}
    }
}
