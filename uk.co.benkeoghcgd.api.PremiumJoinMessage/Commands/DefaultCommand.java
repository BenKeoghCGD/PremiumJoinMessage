package uk.co.benkeoghcgd.api.PremiumJoinMessage.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.co.benkeoghcgd.api.AxiusCore.API.AxiusCommand;
import uk.co.benkeoghcgd.api.AxiusCore.API.AxiusPlugin;
import uk.co.benkeoghcgd.api.PremiumJoinMessage.PremiumJoinMessage;

import java.util.List;

public class DefaultCommand extends AxiusCommand {

    PremiumJoinMessage instance;

    public DefaultCommand(PremiumJoinMessage instance) {
        super(instance, true,
                "premiumjoinmessage",
                "generic command for PremiumJoinMessage",
                "Incorrect Syntax: /premiumjoinmessage help", "pjm");
        this.instance = instance;

        setPermission("premiumjoinmessage.admin");
    }

    @Override
    public boolean onCommand(CommandSender sndr, Command command, String s, String[] args) {
        if(args.length == 0) {
            showHelp(sndr);
            return true;
        }

        switch(args[0].toLowerCase()) {
            case "reload":
                instance.ReloadAll();
                sndr.sendMessage(instance.getNameFormatted() + "Reloaded all configurations.");
                return true;

            case "user":
                // /pjm user <username> <set/clear> <join/quit> "new join msg"
                if(args.length < 4) {
                    sndr.sendMessage(instance.getNameFormatted() + "Invalid Syntax: /" + s + " user <username> <set/clear> <join/quit> [\"new join msg\"]");
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if(target == null) {
                    sndr.sendMessage(instance.getNameFormatted() + "Player does not exist, online or offline.");
                    return true;
                }


                if(args[2].equalsIgnoreCase("set")) {
                    if(args.length > 4) {
                        StringBuilder txt = new StringBuilder();
                        for(int i = 4; i < args.length; i++) {
                            txt.append(args[i] + " ");
                        }

                        if(args[3].equalsIgnoreCase("join")) instance.pyml.setJoin(target, txt.toString());
                        else if(args[3].equalsIgnoreCase("quit")) instance.pyml.setQuit(target, txt.toString());
                        else {
                            sndr.sendMessage(instance.getNameFormatted() + "Invalid Syntax: /" + s + " user <username> <set/clear> <join/quit> [\"new join msg\"]");
                            return true;
                        }

                        sndr.sendMessage(instance.getNameFormatted() + "Updated player data.");
                        instance.ReloadEvents();
                        instance.pyml.loadData();
                        return true;
                    }
                }
                else if(args[2].equalsIgnoreCase("clear")) {
                    if(args[3].equalsIgnoreCase("join")) instance.pyml.clearJoin(target);
                    else if(args[3].equalsIgnoreCase("quit")) instance.pyml.clearQuit(target);
                    else {
                        sndr.sendMessage(instance.getNameFormatted() + "Invalid Syntax: /" + s + " user <username> <set/clear> <join/quit> [\"new join msg\"]");
                        return true;
                    }

                    sndr.sendMessage(instance.getNameFormatted() + "Updated player data.");
                    instance.ReloadEvents();
                    instance.pyml.loadData();
                    return true;
                }

                sndr.sendMessage(instance.getNameFormatted() + "Invalid Syntax: /" + s + " user <username> <set/clear> <join/quit> [\"new join msg\"]");
                break;

            case "group":
                // /pjm user <username> <set/clear> <join/quit> "new join msg"
                if(args.length < 4) {
                    sndr.sendMessage(instance.getNameFormatted() + "Invalid Syntax: /" + s + " group <group> <set/clear> <join/quit/weight/all> [\"new join msg\"]");
                    return true;
                }

                if(args[2].equalsIgnoreCase("set")) {
                    if(args.length >= 5) {
                        StringBuilder txt = new StringBuilder();
                        for(int i = 4; i < args.length; i++) {
                            txt.append(args[i] + " ");
                        }

                        if(args[3].equalsIgnoreCase("join")) {
                            instance.gyml.setJoin(args[1], txt.toString());
                            if(!instance.gyml.hasWeight(args[1])) instance.gyml.setWeight(args[1], 1);
                        }
                        else if(args[3].equalsIgnoreCase("quit")) {
                            instance.gyml.setQuit(args[1], txt.toString());
                            if(!instance.gyml.hasWeight(args[1])) instance.gyml.setWeight(args[1], 1);
                        }
                        else if(args[3].equalsIgnoreCase("weight")) {
                            instance.gyml.setWeight(args[1], Integer.parseInt(args[4]));
                        }
                        else {
                            sndr.sendMessage(instance.getNameFormatted() + "Invalid Syntax: /" + s + " group <group> <set/clear> <join/quit/weight/all> [\"new join msg\"]");
                            return true;
                        }

                        sndr.sendMessage(instance.getNameFormatted() + "Updated group data.");
                        instance.ReloadEvents();
                        instance.gyml.loadData();
                        return true;
                    }
                }
                else if(args[2].equalsIgnoreCase("clear")) {
                    if(args[3].equalsIgnoreCase("join")) instance.gyml.clearJoin(args[1]);
                    else if(args[3].equalsIgnoreCase("quit")) instance.gyml.clearQuit(args[1]);
                    else {
                        sndr.sendMessage(instance.getNameFormatted() + "Invalid Syntax: /" + s + " group <group> <set/clear> <join/quit/weight/all> [\"new join msg\"]");
                        return true;
                    }

                    sndr.sendMessage(instance.getNameFormatted() + "Updated group data.");
                    instance.gyml.loadData();
                    instance.ReloadEvents();
                    return true;
                }

                sndr.sendMessage(instance.getNameFormatted() + "Invalid Syntax: /" + s + " group <group> <set/clear> <join/quit/weight/all> [\"new join msg\"]");
                return true;

            case "gui":
                sndr.sendMessage(instance.getNameFormatted() + "GUI only available with Premium.");
                break;

            case "groups":
                sndr.sendMessage(instance.getNameFormatted() + "All Groups");
                List<String> allGroups = instance.gyml.loadGroupNames();
                if(allGroups.isEmpty()) {
                    sndr.sendMessage("§7This is awkward! you have no groups defined!");
                }
                else {
                    for(String s1 : allGroups) {
                        sndr.sendMessage("§5" + s1 + "§7:");
                        if(instance.gyml.hasJoin(s1)) sndr.sendMessage("§8-§d Join MSG: §7" + ChatColor.translateAlternateColorCodes('&', instance.gyml.getJoin(s1)));
                        if(instance.gyml.hasQuit(s1)) sndr.sendMessage("§8-§d Quit MSG: §7" + ChatColor.translateAlternateColorCodes('&', instance.gyml.getQuit(s1)));
                        if(instance.gyml.hasWeight(s1)) sndr.sendMessage("§8-§d Weight: §7" + instance.gyml.getWeight(s1));
                    }
                }
                break;

            default:
                showHelp(sndr);
                break;
        }
        return true;
    }

    void showHelp(CommandSender sndr) {
        sndr.sendMessage(instance.getNameFormatted() + "Plugin Commands - General");
        sndr.sendMessage("§9/pjm reload §d-§7 Reload all plugin files.");
        sndr.sendMessage("§9/pjm user §d-§7 View user-specific commands.");
        sndr.sendMessage("§9/pjm group §d-§7 View group-specific commands.");
        sndr.sendMessage("§9/pjm groups §d-§7 View all groups.");
        sndr.sendMessage("§9/pjm gui §d-§7 Load editing GUI.");
    }
}
