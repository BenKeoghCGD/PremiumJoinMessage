package uk.co.benkeoghcgd.api.PremiumJoinMessage.Data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import uk.co.benkeoghcgd.api.AxiusCore.API.AxiusPlugin;
import uk.co.benkeoghcgd.api.AxiusCore.API.DataHandler;

import java.util.ArrayList;
import java.util.List;

public class GroupsYML extends DataHandler {
    public GroupsYML(AxiusPlugin instance) {
        super(instance, "Groups");
    }

    @Override
    protected void saveDefaults() {
        setData("exampleGroup.join", "&5&lExampleJoin&9 %username% joined the game");
        setData("exampleGroup.quit", "&5&lExampleQuit&9 %username% left the game");
        setData("exampleGroup.weight", -1);
    }

    public boolean hasJoin(String groupName) {
        return data.get(groupName + ".join") != null;
    }

    public boolean hasQuit(String groupName) {
        return data.get(groupName + ".quit") != null;
    }

    public void setJoin(String groupName, String joinMsg) {
        setData(groupName + ".join", joinMsg);
    }

    public void setQuit(String groupName, String quitMsg) {
        setData(groupName + ".quit", quitMsg);
    }

    public String getJoin(String groupName) {
        return (String) data.get(groupName + ".join");
    }

    public String getQuit(String groupName) {
        return (String) data.get(groupName + ".quit");
    }

    public int getWeight(String groupName) {
        return (Integer) data.get(groupName + ".weight");
    }

    public void setWeight(String groupName, int newWeight) {
        setData(groupName + ".weight", newWeight);
    }

    public void clearJoin(String groupName) {
        setData(groupName + ".join", null, true);
    }

    public void clearQuit(String groupName) {
        setData(groupName + ".quit", null, true);
    }

    public List<String> loadGroupNames() {
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(file);
        List<String> s = new ArrayList<>();
        for(String key : fileConfig.getConfigurationSection("").getKeys(false))
            s.add(key);

        return s;
    }

    public boolean hasWeight(String arg) {
        return data.get(arg + ".weight") != null;
    }
}
