package tk.shadowcube.snowball;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopTheme implements Listener{

	private main plugin;
	public ShopTheme(main p){
		this.plugin = p;
		p.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onShopOpen(InventoryOpenEvent e){
		if(e.getPlayer() instanceof Player){
			final Player p = (Player) e.getPlayer();
			File file = new File("plugins//SnowballFight//players.yml");
			final YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
			int tokens = players.getInt("Players." + p.getName() + ".Tokens");
			if(e.getInventory().getTitle().equalsIgnoreCase("§8Shop | §eYour Tokens: §5" + tokens)){
				final Inventory inv = e.getInventory();
				
				Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable(){
					@Override
					public void run() {
						ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName("§1");
						item.setItemMeta(meta);
						inv.setItem(0, item);
						
						ItemStack item1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
						ItemMeta meta1 = item1.getItemMeta();
						meta1.setDisplayName("§1");
						item1.setItemMeta(meta1);
						inv.setItem(8, item1);
						p.playSound(p.getLocation(), Sound.DIG_STONE, 1F, 1F);
						
						Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable(){
							@Override
							public void run() {
								ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13);
								ItemMeta meta = item.getItemMeta();
								meta.setDisplayName("§1");
								item.setItemMeta(meta);
								inv.setItem(1, item);
								
								ItemStack item1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13);
								ItemMeta meta1 = item1.getItemMeta();
								meta1.setDisplayName("§1");
								item1.setItemMeta(meta1);
								inv.setItem(7, item1);
								
								ItemStack item11 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13);
								ItemMeta meta11 = item11.getItemMeta();
								meta11.setDisplayName("§1");
								item11.setItemMeta(meta11);
								inv.setItem(9, item11);
								
								ItemStack item111 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13);
								ItemMeta meta111 = item111.getItemMeta();
								meta111.setDisplayName("§1");
								item111.setItemMeta(meta111);
								inv.setItem(17, item111);
								p.playSound(p.getLocation(), Sound.DIG_STONE, 1F, 1F);
								
								Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable(){
									@Override
									public void run() {
										ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
										ItemMeta meta = item.getItemMeta();
										meta.setDisplayName("§1");
										item.setItemMeta(meta);
										inv.setItem(2, item);
										
										ItemStack item1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
										ItemMeta meta1 = item1.getItemMeta();
										meta1.setDisplayName("§1");
										item1.setItemMeta(meta1);
										inv.setItem(6, item1);
										
										ItemStack item11 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
										ItemMeta meta11 = item11.getItemMeta();
										meta11.setDisplayName("§1");
										item11.setItemMeta(meta11);
										inv.setItem(18, item11);
										
										ItemStack item111 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
										ItemMeta meta111 = item111.getItemMeta();
										meta111.setDisplayName("§1");
										item111.setItemMeta(meta111);
										inv.setItem(26, item111);
										p.playSound(p.getLocation(), Sound.DIG_STONE, 1F, 1F);
										
										Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable(){
											@Override
											public void run() {
												ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13);
												ItemMeta meta = item.getItemMeta();
												meta.setDisplayName("§1");
												item.setItemMeta(meta);
												inv.setItem(3, item);
												
												ItemStack item1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 13);
												ItemMeta meta1 = item1.getItemMeta();
												meta1.setDisplayName("§1");
												item1.setItemMeta(meta1);
												inv.setItem(5, item1);
												p.playSound(p.getLocation(), Sound.DIG_STONE, 1F, 1F);
												
												Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable(){
													@Override
													public void run() {
														ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 4);
														ItemMeta meta = item.getItemMeta();
														meta.setDisplayName("§1");
														item.setItemMeta(meta);
														inv.setItem(4, item);
														p.playSound(p.getLocation(), Sound.DIG_STONE, 1F, 1F);
													}
												}, 5);
											}
										}, 5);
									}
								}, 5);
							}
						}, 5);
					}
				}, 22);
			}
		}
	}
}
