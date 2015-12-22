package tk.shadowcube.snowball;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class SnowMan implements Listener{

	private main plugin;
	public SnowMan(main p){
		this.plugin = p;
		
		p.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e){
		Player p = e.getPlayer();
		
		if(e.getRightClicked() instanceof org.bukkit.entity.Snowman){
			Snowman snow = (Snowman)e.getRightClicked();
			try{
				if(snow.getCustomName().equalsIgnoreCase("Snowball Fight")){
					Inventory inv = Bukkit.createInventory(null, 18, "�6>�e> �bSnowball �e<�6<");
					File file = new File("plugins//SnowballFight//players.yml");
					YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
			
					ArrayList<String> lore2 = new ArrayList<>();
					ItemStack item = new ItemStack(Material.SNOW_BALL);
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName("�8Snowball Fight!");
					lore2.add("�5Click...");
					lore2.add("�5Players: �7" + Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname")).getPlayers().size());
					meta.setLore(lore2);
					item.setItemMeta(meta);
					inv.setItem(13, item);
			
					int kills = players.getInt("Players." + p.getName() + ".Kills");
					int deaths = players.getInt("Players." + p.getName() + ".Deaths");
					double KD = (double) kills / deaths;
			
					ArrayList<String> lore = new ArrayList<>();
					ItemStack item1 = new ItemStack(Material.SKULL_ITEM,1,(short)3);
					SkullMeta meta1 = (SkullMeta) item1.getItemMeta();
					meta1.setOwner(p.getName());
					meta1.setDisplayName("�eProfile: �5" + p.getName());
					lore.add("�5Kills: �7" + kills);
					lore.add("�5Deaths: �7" + deaths);
					lore.add("�5K/D: �7" + KD);
					meta1.setLore(lore);
					item1.setItemMeta(meta1);
					inv.setItem(4, item1);
			
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
					p.openInventory(inv);
				}
			}catch(NullPointerException ex){
			}
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		Player p = (Player)e.getWhoClicked();
		try{
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�8Snowball Fight!")){
				e.setCancelled(true);
				if(p.hasPermission("snowball.join")){
					p.performCommand("snowball join");
					plugin.updateScoreboard(p);
				}else{
					p.sendMessage("�4Sorry! You don't have enough permission!");
				}
			}
			} catch(NullPointerException ex){
			}
	}
}
