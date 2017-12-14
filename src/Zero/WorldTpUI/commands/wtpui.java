package Zero.WorldTpUI.commands;

import java.util.Map;

import Zero.WorldTpUI.Main;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.defaults.VanillaCommand;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.Level;
import cn.nukkit.utils.TextFormat;

public class wtpui extends VanillaCommand {

  private Main plugin;

  public wtpui(Main main, String name){
  super(name);
    this.plugin = main;
  }
  
  public Main getPlugin() {
    return plugin;
  }

  @Override
  public boolean execute(CommandSender player, String alias, String[] args){	
  if(player instanceof Player){
    final FormWindowSimple window = new FormWindowSimple("World Teleport UI", "Teleport to any world");
    Map<Integer, Level> level = getPlugin().getServer().getLevels();
    window.addButton(new ElementButton("Cancel"));
  for(Level lvl : level.values()){
    window.addButton(new ElementButton(lvl.getFolderName()));
    ((Player) player).showFormWindow(window);
   }
  } else {
	player.sendMessage(TextFormat.RED +"You can only use /hub in-game!");  
  }
    return true;
  }
}
