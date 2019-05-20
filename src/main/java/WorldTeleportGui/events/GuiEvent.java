package WorldTeleportGui.events;

import WorldTeleportGui.Main;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;

import java.io.File;
import java.util.Objects;

public class GuiEvent implements Listener {

    private Main getPlugin() {
        return Main.getInstance();
    }

    @EventHandler
    public void formRespond(cn.nukkit.event.player.PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        FormWindow window = event.getWindow();
        if (event.getResponse() == null) return;
        if (window instanceof FormWindowSimple) {
            String title = ((FormWindowSimple) event.getWindow()).getTitle();
            String button = ((FormResponseSimple) event.getResponse()).getClickedButton().getText();
            if (!event.wasClosed()) {
                if (title.equals("World Teleport GUI")) {
                    if (button.equals("Load All Worlds.")) {
                        File worlds = new File(this.getPlugin().getServer().getDataPath() + "/worlds");
                        if (worlds.exists()) {
                            for (String world : Objects.requireNonNull(worlds.list())) {
                                if (!this.getPlugin().getServer().isLevelLoaded(world)) {
                                    this.getPlugin().getServer().loadLevel(world);
                                }
                            }
                        }
                        this.getPlugin().showForm(player);
                    } else {
                        Level level = getPlugin().getServer().getLevelByName(button);
                        if (getPlugin().getServer().isLevelLoaded(level.getFolderName())) {
                            player.teleport(level.getSafeSpawn());
                        } else {
                            player.sendMessage(TextFormat.RED + "The world you are trying to teleport does not exist or isn't loaded");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (this.getPlugin().getConfig().getBoolean("useItemToOpenGUI")) {
            long currentTime = System.currentTimeMillis();
            Item item = new Item(this.getPlugin().getConfig().getInt("item"));
            item.setCustomName(TextFormat.AQUA + "World Teleport");
            item.setCustomBlockData(new CompoundTag().putString("wtp", "teleport").putLong("Last Used", currentTime));
            if(this.getPlugin().getConfig().getBoolean("adminsOnly")){
                if(player.isOp()){
                    player.getInventory().addItem(item);
                }
            } else {
                player.getInventory().addItem(item);
            }
        }
    }

    @EventHandler
    public void onInteract(cn.nukkit.event.player.PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Item item = event.getItem();
        if (item.hasCustomBlockData()) {
            if (item.getCustomBlockData().contains("wtp") && item.getCustomBlockData().contains("Last Used")) {
                String customBlockData = item.getCustomBlockData().getString("wtp");
                long currentTime = System.currentTimeMillis();
                int cooldown = 1000;//1 second
                if (customBlockData.equals("teleport")) {
                    if ((currentTime - item.getCustomBlockData().getLong("Last Used")) >= cooldown) {
                        this.getPlugin().showForm(player);
                    }
                }
            }
        }
    }
}
