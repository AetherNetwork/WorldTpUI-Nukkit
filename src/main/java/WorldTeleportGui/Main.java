package WorldTeleportGui;

import WorldTeleportGui.commands.Wtpui;
import WorldTeleportGui.events.GuiEvent;
import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.Level;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import java.util.Map;

public class Main extends PluginBase {

    private static Main instance;
    private Config settings = null;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {//TODO Add Multi Lang Support.
        instance = this;
        this.getServer().getPluginManager().registerEvents(new GuiEvent(), this);
        this.getServer().getCommandMap().register("wtpui", new Wtpui("wtpui"));
        this.getConfig();
        if (this.getConfig().exists("version")) {
            if (!this.getConfig().getString("version").equals(this.getDescription().getVersion())) {
                this.info(TextFormat.YELLOW + "It seems your on an older version, Updating Config...");
                this.updateConfig();
                this.info(TextFormat.GREEN + "Updated Config!");
            } else {
                //do nothing
            }
        } else {
            this.updateConfig();
        }
        if (!this.getConfig().getBoolean("adminsOnly")) {
            this.info(TextFormat.RED + "You are running this without op protection");
        }
        this.info("v" + this.getConfig().getString("version") + TextFormat.GREEN + " Has Loaded");
    }

    @Override
    public void onDisable() {
        this.info(TextFormat.RED + "Goodbye!");
    }

    public Config getConfig() {
        if (this.settings == null) {
            this.info(TextFormat.YELLOW + "Loading Config...");
            this.settings = new Config(this.getDataFolder() + "/settings.yml", Config.YAML);
            this.settings.reload();
            this.info(TextFormat.YELLOW + "Config Loaded.");
        }
        return this.settings;
    }

    public void updateConfig() {
        this.getConfig().set("version", this.getDescription().getVersion());
        if (!this.getConfig().exists("adminsOnly")) {
            this.getConfig().set("adminsOnly", true);
        }
        if (!this.getConfig().exists("loadWorldWithGui")) {
            this.getConfig().set("loadWorldWithGui", true);
        }
        if (!this.getConfig().exists("useItemToOpenGUI")) {
            this.getConfig().set("useItemToOpenGUI", false);

            if (!this.getConfig().exists("item")) {
                this.getConfig().set("item", 339);
            }
        }
        this.getConfig().save();
    }

    public void showForm(Player player){
        final FormWindowSimple window = new FormWindowSimple("World Teleport GUI", "Teleport to any world");
        Map<Integer, Level> level = this.getServer().getLevels();

        if(this.getConfig().getBoolean("loadWorldWithGui")){
            window.addButton(new ElementButton("Load All Worlds."));
        }
        for (Level lvl : level.values()) {
            window.addButton(new ElementButton(lvl.getFolderName()));
        }
        player.showFormWindow(window);
    }

    public void info(String msg) {
        this.getServer().getLogger().info(TextFormat.DARK_GRAY + "[" + TextFormat.AQUA + getDescription().getName() + TextFormat.DARK_GRAY + "] " + TextFormat.RESET + msg);
    }
}
