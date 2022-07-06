package uk.co.benkeoghcgd.api.PremiumJoinMessage.Data;

import uk.co.benkeoghcgd.api.AxiusCore.API.AxiusPlugin;
import uk.co.benkeoghcgd.api.AxiusCore.API.DataHandler;

public class ConfigYML extends DataHandler {
    public ConfigYML(AxiusPlugin instance) {
        super(instance, "Config");
    }

    @Override
    protected void saveDefaults() {
        setData("config.showDefaultJoin", true, false);
        setData("config.showDefaultQuit", true, false);
        setData("config.hiddenEnabled", true, false);
        setData("defaults.join", "&7[&a+&7] %username% has joined the game.", false);
        setData("defaults.quit", "&7[&c-&7] %username% has left the game.", false);
        setData("hidden.join", "&8&lHIDDEN&7 %username% has joined the game.", false);
        setData("hidden.quit", "&8&lHIDDEN&7 %username% has left the game.", false);
    }
}
