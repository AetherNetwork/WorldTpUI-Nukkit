package WorldTeleportGui.commands;

import WorldTeleportGui.Main;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.utils.TextFormat;

public class Wtpui extends VanillaCommand {

    public Wtpui(String name) {
        super(name);
    }

    public Main getPlugin() {
        return Main.getInstance();
    }

    @Override
    public boolean execute(CommandSender player, String alias, String[] args) {
        if (player instanceof Player) {
            if (this.getPlugin().getConfig().getBoolean("adminsOnly")) {
                if (player.isOp()) {
                    this.getPlugin().showForm((Player) player);
                } else {
                    player.sendMessage(TextFormat.RED + "You have to be op to use this command!");
                }
            } else {
                this.getPlugin().showForm((Player) player);
            }
        } else {
            player.sendMessage(TextFormat.RED + "You can only use this in-game!");
        }
        return true;
    }


}
