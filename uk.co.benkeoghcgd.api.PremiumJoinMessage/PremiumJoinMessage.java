package uk.co.benkeoghcgd.api.PremiumJoinMessage;

import org.bukkit.event.HandlerList;
import uk.co.benkeoghcgd.api.AxiusCore.API.AxiusPlugin;
import uk.co.benkeoghcgd.api.AxiusCore.API.GUI;
import uk.co.benkeoghcgd.api.AxiusCore.Exceptions.MissingDependException;
import uk.co.benkeoghcgd.api.AxiusCore.Utils.GUIAssets;
import uk.co.benkeoghcgd.api.AxiusCore.Utils.Logging;
import uk.co.benkeoghcgd.api.PremiumJoinMessage.Commands.DefaultCommand;
import uk.co.benkeoghcgd.api.PremiumJoinMessage.Data.ConfigYML;
import uk.co.benkeoghcgd.api.PremiumJoinMessage.Data.GroupsYML;
import uk.co.benkeoghcgd.api.PremiumJoinMessage.Data.PlayersYML;
import uk.co.benkeoghcgd.api.PremiumJoinMessage.Listeners.JoinLeaveListeners;

public class PremiumJoinMessage extends AxiusPlugin {

    public ConfigYML cyml;
    public PlayersYML pyml;
    public GroupsYML gyml;

    @Override
    protected void Preregister() {
        Logging.Log(this, "Running plugin pre-registry tasks");
        EnableUpdater(103077);

        Logging.Log(this, "Registering Data Files");
        cyml = new ConfigYML(this);
        pyml = new PlayersYML(this);
        gyml = new GroupsYML(this);

        Logging.Log(this, "Collecting Commands");
        commands.add(new DefaultCommand(this));
    }

    @Override
    protected void Postregister() {
        setIcon(GUI.createGuiItem(GUIAssets.DECORHEADS.get("arrowdown"), "§d§lPremiumJoinMessage"));
        setFormattedName("&x&f&6&0&0&f&b&lP&x&f&1&0&8&f&b&lr&x&e&c&1&0&f&b&le&x&e&7&1&8&f&b&lm&x&e&2&2&0&f&b&li&x&d&d&2&9&f&c&lu&x&d&8&3&1&f&c&lm&x&d&3&3&9&f&c&lJ&x&c&e&4&1&f&c&lo&x&c&9&4&9&f&c&li&x&c&4&5&1&f&c&ln&x&b&f&5&9&f&c&lM&x&b&a&6&1&f&c&le&x&b&5&6&a&f&d&ls&x&b&0&7&2&f&d&ls&x&a&b&7&a&f&d&la&x&a&6&8&2&f&d&lg&x&a&1&8&a&f&d&le§7 ");

        Logging.Log(this, "Registering Commands");
        registerCommands();

        Logging.Log(this, "Registering Listeners");
        getServer().getPluginManager().registerEvents(new JoinLeaveListeners(this), this);
    }

    @Override
    protected void Stop() {

    }

    @Override
    protected void FullStop() {

    }

    public void ReloadAll() {
        gyml.loadData();
        pyml.loadData();
        cyml.loadData();
    }

    public void ReloadEvents() {
        Logging.Log(this, "Reloading Events.");
        HandlerList.unregisterAll(this);
        getServer().getPluginManager().registerEvents(new JoinLeaveListeners(this), this);
    }
}
