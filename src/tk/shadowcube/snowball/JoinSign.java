package tk.shadowcube.snowball;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class JoinSign implements Listener{

	private main plugin;
	public JoinSign(main p){
		this.plugin = p;
		p.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onCreateSign(SignChangeEvent e){
		Player p = e.getPlayer();
		if(e.getLine(0).equalsIgnoreCase("[Snowball]")){
			if(e.getLine(1).equalsIgnoreCase("Join")){
				if(p.hasPermission("snowball.admin")){
					e.setLine(0, "§8[§bSnowball§8]");
					e.setLine(1, "§6>§e> §aJoin §e<§6<");
					p.sendMessage("§6>§e> §bSnowball §e<§6< §7Sign successfully created!");
				}
			}
		}
	}
	
	@EventHandler
	public void onSignInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(e.getClickedBlock().getState() instanceof Sign){
				Sign sign = (Sign)e.getClickedBlock().getState();
				if(sign.getLine(0).equalsIgnoreCase("§8[§bSnowball§8]")){
					if(sign.getLine(1).equalsIgnoreCase("§6>§e> §aJoin §e<§6<")){
						if(p.hasPermission("snowball.join")){
							p.performCommand("snowball join");
							plugin.updateScoreboard(p);
						}else{
							p.sendMessage("§4Sorry! You don't have enough permission!");
						}
					}
				}
			}
		}
	}
}
