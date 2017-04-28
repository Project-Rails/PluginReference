package ExamplePlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import PluginReference.ChatColor;
import PluginReference.MC_Command;
import PluginReference.MC_Player;

public class MotdCmd implements MC_Command {

	@Override
	public List<String> getAliases() {
		return null;
	}

	@Override
	public String getCommandName() {
		return "motd";
	}

	@Override
	//Please, please, PLEASE use this formatting guide for command help lines! Link: http://bit.do/dp8QK 
	public String getHelpLine(MC_Player arg0) {
		return ChatColor.AQUA + "/motd " + ChatColor.WHITE + "--- Server MOTD";
	}

	@Override
	public List<String> getTabCompletionList(MC_Player arg0, String[] arg1) {
		return null;
	}

	@Override
	public void handleCommand(MC_Player plr, String[] args) {
		if(hasPermissionToUse(plr) == true) {
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
		
	}

	@Override
	public boolean hasPermissionToUse(MC_Player plr) {
		return plr.hasPermission("motd.command");
	}

}
