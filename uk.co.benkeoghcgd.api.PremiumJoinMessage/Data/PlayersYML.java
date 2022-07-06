package uk.co.benkeoghcgd.api.PremiumJoinMessage.Data;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.benkeoghcgd.api.AxiusCore.API.DataHandler;

public class PlayersYML extends DataHandler {
    public PlayersYML(JavaPlugin instance) {
        super(instance, "Players");
    }

    @Override
    protected void saveDefaults() {}

    public boolean hasJoin(Player p) {
        return data.get(p.getUniqueId().toString() + ".join") != null;
    }

    public String getJoin(Player p) {
        if(hasJoin(p)) return (String) data.get(p.getUniqueId().toString() + ".join");
        else return "";
    }

    public boolean hasQuit(Player p) {
        return data.get(p.getUniqueId().toString() + ".quit") != null;
    }

    public String getQuit(Player p) {
        if(hasQuit(p)) return (String) data.get(p.getUniqueId().toString() + ".quit");
        else return "";
    }

    public void setJoin(Player p, String joinMsg) {
        setData(p.getUniqueId().toString() + ".join", joinMsg);
    }

    public void setQuit(Player p, String quitMsg) {
        setData(p.getUniqueId().toString() + ".quit", quitMsg);
    }

    public void clearJoin(Player p) {
        setData(p.getUniqueId().toString() + ".join", null);
    }

    public void clearQuit(Player p) {
        setData(p.getUniqueId().toString() + ".quit", null);
    }
}
