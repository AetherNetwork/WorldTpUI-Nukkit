package Zero.WorldTpUI.events;

import Zero.WorldTpUI.Main;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.Level;
import cn.nukkit.utils.TextFormat;

public class FormEvent implements Listener {

  private Main plugin;

  public FormEvent(Main main){
    plugin = main;
  }

  public Main getPlugin(){
	return plugin;
  }
  
  @EventHandler
  public void FormFormResponse(cn.nukkit.event.player.PlayerFormRespondedEvent event){
    final Player player = event.getPlayer();
    final FormWindow window = event.getWindow();
  if(event.getResponse() != null){
  if(window instanceof FormWindowSimple){
    final String button = ((FormWindowSimple) event.getWindow()).getResponse().getClickedButton().getText();
  if(button != null && !window.wasClosed() && button != "Cancel"){
	Level level = getPlugin().getServer().getLevelByName(button);
  if(getPlugin().getServer().isLevelLoaded(level.getFolderName())){
	player.teleport(level.getSafeSpawn());
  } else {
	player.sendMessage(TextFormat.RED +"The world you are trying to teleport to is not loaded");
   }
  } else {
    window.wasClosed();
     }
    }
   }
  }
}
