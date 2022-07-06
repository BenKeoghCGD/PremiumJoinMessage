package uk.co.benkeoghcgd.api.PremiumJoinMessage.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import uk.co.benkeoghcgd.api.AxiusCore.Utils.Logging;
import uk.co.benkeoghcgd.api.PremiumJoinMessage.Data.ConfigYML;
import uk.co.benkeoghcgd.api.PremiumJoinMessage.Data.GroupsYML;
import uk.co.benkeoghcgd.api.PremiumJoinMessage.Data.PlayersYML;
import uk.co.benkeoghcgd.api.PremiumJoinMessage.PremiumJoinMessage;

public class JoinLeaveListeners implements Listener {

    PremiumJoinMessage plugin;
    ConfigYML cyml;
    PlayersYML pyml;
    GroupsYML gyml;

    public JoinLeaveListeners(PremiumJoinMessage premiumJoinMessage) {
        plugin = premiumJoinMessage;
        cyml = premiumJoinMessage.cyml;
        pyml = premiumJoinMessage.pyml;
        gyml = premiumJoinMessage.gyml;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if((boolean) cyml.data.get("config.hiddenEnabled") && e.getPlayer().hasPermission("premiumjoinmessage.hidden")) {
            e.setJoinMessage(null);
            Bukkit.getOnlinePlayers().forEach(a -> {if(a.hasPermission("premiumjoinmessage.hidden")) a.sendMessage(ChatColor.translateAlternateColorCodes('&', cyml.data.get("hidden.join").toString().replaceAll("%username%", a.getName())));});
            return;
        }
        else if(pyml.hasJoin(e.getPlayer())) {
            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', pyml.getJoin(e.getPlayer()).replaceAll("%username%", e.getPlayer().getName())));
            return;
        }
        else if(!gyml.loadGroupNames().isEmpty()) {
            int bestWeight = 0;
            String bestGuess = "";
            for(String s : gyml.loadGroupNames()) {
                if(!gyml.hasJoin(s)) continue;
                if(e.getPlayer().hasPermission("premiumjoinmessage.group." + s)) {
                    if(gyml.getWeight(s) > bestWeight) {
                        bestGuess = ChatColor.translateAlternateColorCodes('&', gyml.getJoin(s).replaceAll("%username%", e.getPlayer().getName()));
                        bestWeight = gyml.getWeight(s);
                    }
                }
            }

            if(bestWeight != 0) {
                e.setJoinMessage(bestGuess);
                return;
            }
        }

        if((boolean) cyml.data.get("config.showDefaultJoin"))
            e.setJoinMessage(cyml.data.get("defaults.join").toString().replaceAll("%username%", e.getPlayer().getName()));
        else
            e.setJoinMessage(null);

        if(e.getJoinMessage() != null) e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', e.getJoinMessage()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if((boolean) cyml.data.get("config.hiddenEnabled") && e.getPlayer().hasPermission("premiumjoinmessage.hidden")) {
            e.setQuitMessage(null);
            Bukkit.getOnlinePlayers().forEach(a -> {if(a.hasPermission("premiumjoinmessage.hidden")) a.sendMessage(ChatColor.translateAlternateColorCodes('&', cyml.data.get("hidden.quit").toString().replaceAll("%username%", a.getName())));});
            return;
        }
        else if(pyml.hasQuit(e.getPlayer())){
            e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', pyml.getQuit(e.getPlayer()).replaceAll("%username%", e.getPlayer().getName())));
            return;
        }
        else if(!gyml.loadGroupNames().isEmpty()) {
            int bestWeight = 0;
            String bestGuess = "";
            for(String s : gyml.loadGroupNames()) {
                if(!gyml.hasQuit(s)) continue;
                if(e.getPlayer().hasPermission("premiumjoinmessage.group." + s)) {
                    if(gyml.getWeight(s) > bestWeight) {
                        bestGuess = ChatColor.translateAlternateColorCodes('&', gyml.getQuit(s).replaceAll("%username%", e.getPlayer().getName()));
                        bestWeight = gyml.getWeight(s);
                    }
                }
            }

            if(bestWeight != 0) {
                e.setQuitMessage(bestGuess);
                return;
            }
        }

        if((boolean) cyml.data.get("config.showDefaultQuit"))
            e.setQuitMessage(cyml.data.get("defaults.quit").toString().replaceAll("%username%", e.getPlayer().getName()));
        else
            e.setQuitMessage(null);

        if(e.getQuitMessage() != null) e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', e.getQuitMessage()));
    }
}
