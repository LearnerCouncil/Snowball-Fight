package tk.shadowcube.snowball;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class TokenShop implements Listener{
	
	private main plugin;
	public TokenShop(main p){
		this.plugin = p;
		
		p.getServer().getPluginManager().registerEvents(this, plugin);
	}
	private HashMap<String, Integer> tasks = new HashMap<>();
	
	int slot = 0;
	@SuppressWarnings("deprecation")
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onClick(PlayerInteractEvent e){
		final Player p = e.getPlayer();
		World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
		World w2 = p.getWorld();
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
			if(w == w2){
				if(p.getItemInHand().getType() == Material.NETHER_STAR){
							File file = new File("plugins//SnowballFight//players.yml");
							final YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
							int tokens = players.getInt("Players." + p.getName() + ".Tokens");
							final Inventory inv = Bukkit.createInventory(null, 27, "§8Shop | §eYour Tokens: §5" + tokens);
							
							this.tasks.put(p.getName(), Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
					               public void run() {
					            	   if(slot < 9){
					            	   		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 13);
					            	   		ItemMeta meta = item.getItemMeta();
					            	   		meta.setDisplayName("§1");
					            	   		item.setItemMeta(meta);
					            	   		inv.setItem(slot, item);
					            	   		p.playSound(p.getLocation(), Sound.DIG_STONE, 1F, 1F);
					            	   		if(slot == 1){
					            	   			ItemStack item1 = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 14);
							            	   	ItemMeta meta1 = item1.getItemMeta();
							            	   	meta1.setDisplayName("§1");
							            	   	item1.setItemMeta(meta1);
							            	   	inv.setItem(0, item1);
							            	   	
							            	   	ItemStack item1111 = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 13);
							            	   	ItemMeta meta1111 = item1111.getItemMeta();
							            	   	meta1111.setDisplayName("§1");
							            	   	item1111.setItemMeta(meta1111);
							            	   	inv.setItem(9, item1111);
					            	   		}else if(slot == 3){
					            	   			ItemStack item1 = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 14);
							            	   	ItemMeta meta1 = item1.getItemMeta();
							            	   	meta1.setDisplayName("§1");
							            	   	item1.setItemMeta(meta1);
							            	   	inv.setItem(2, item1);
							            	   	
							            	   	ItemStack item11 = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 14);
							            	   	ItemMeta meta11 = item11.getItemMeta();
							            	   	meta11.setDisplayName("§1");
							            	   	item11.setItemMeta(meta11);
							            	   	inv.setItem(18, item11);
							            	   	p.playSound(p.getLocation(), Sound.DIG_STONE, 1F, 1F);
					            	   		}else if(slot == 5){
					            	   			ItemStack item1 = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 14);
							            	   	ItemMeta meta1 = item1.getItemMeta();
							            	   	meta1.setDisplayName("§1");
							            	   	item1.setItemMeta(meta1);
							            	   	inv.setItem(4, item1);
					            	   		}else if(slot == 7){
					            	   			ItemStack item1 = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 14);
							            	   	ItemMeta meta1 = item1.getItemMeta();
							            	   	meta1.setDisplayName("§1");
							            	   	item1.setItemMeta(meta1);
							            	   	inv.setItem(6, item1);
							            	   	
					            	   		}else if(slot == 8){
					            	   			ItemStack item1 = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 14);
							            	   	ItemMeta meta1 = item1.getItemMeta();
							            	   	meta1.setDisplayName("§1");
							            	   	item1.setItemMeta(meta1);
							            	   	inv.setItem(8, item1);
							            	   	
							            	   	ItemStack item11111 = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 13);
							            	   	ItemMeta meta11111 = item11111.getItemMeta();
							            	   	meta11111.setDisplayName("§1");
							            	   	item11111.setItemMeta(meta11111);
							            	   	inv.setItem(17, item11111);
							            	   	
							            	   	ItemStack item111 = new ItemStack(Material.STAINED_GLASS_PANE, 1,(byte) 14);
							            	   	ItemMeta meta111 = item111.getItemMeta();
							            	   	meta111.setDisplayName("§1");
							            	   	item111.setItemMeta(meta111);
							            	   	inv.setItem(26, item111);
							            	   	p.playSound(p.getLocation(), Sound.DIG_STONE, 1F, 1F);
					            	   		}
					            	   		slot++;
					            	   }else{
					            		   Bukkit.getScheduler().cancelTask(tasks.get(p.getName()));
					            		   tasks.remove(p.getName());
					            		   slot = 0;
					            	   }
					               }
					        }, 30, 3));
							
							Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable(){
								@Override
								public void run() {
									File file = new File("plugins//SnowballFight//shop.yml");
									YamlConfiguration shop = YamlConfiguration.loadConfiguration(file);
									
									ArrayList<String> lore31 = new ArrayList<>();
									ItemStack item31 = new ItemStack(Material.TNT);
									ItemMeta meta31 = item31.getItemMeta();
									meta31.setDisplayName("§eGrenade");
									lore31.add("§5Boooom!");
									meta31.setLore(lore31);
									item31.setItemMeta(meta31);
									inv.setItem(10, item31);
									
									ArrayList<String> lore3 = new ArrayList<>();
									ItemStack item3 = new ItemStack(Material.IRON_SWORD);
									ItemMeta meta3 = item3.getItemMeta();
									meta3.setDisplayName("§eKnife");
									lore3.add("§5Killed instantly - right-click on a player!");
									meta3.setLore(lore3);
									item3.setItemMeta(meta3);
									inv.setItem(11, item3);
									
									ArrayList<String> lore311 = new ArrayList<>();
									ItemStack item311 = new ItemStack(Material.FIREWORK_CHARGE);
									ItemMeta meta311 = item311.getItemMeta();
									meta311.setDisplayName("§82x §eBlindnessBall");
									lore311.add("§5Makes your enemies blind!");
									meta311.setLore(lore311);
									item311.setAmount(2);
									item311.setItemMeta(meta311);
									inv.setItem(12, item311);
									
									ArrayList<String> lore = new ArrayList<>();
									ItemStack item = new ItemStack(Material.LEATHER_HELMET);
									LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
									meta.setColor(Color.RED);
									meta.setDisplayName("§eWoolly hat");
									lore.add("§5So soft!");
									lore.add("§5Reduced 10% of the suffered damage!");
									meta.setLore(lore);
									item.setItemMeta(meta);
									inv.setItem(13, item);
									
									ArrayList<String> lore1 = new ArrayList<>();
									ItemStack item1 = new ItemStack(Material.LEATHER_CHESTPLATE);
									LeatherArmorMeta meta1 = (LeatherArmorMeta) item1.getItemMeta();
									meta1.setColor(Color.GREEN);
									meta1.setDisplayName("§eWinter coat");
									lore1.add("§5Oh...so warm!");
									lore1.add("§5Reduced 10% of the suffered damage!");
									meta1.setLore(lore1);
									item1.setItemMeta(meta1);
									inv.setItem(14, item1);
									
									ArrayList<String> lore11 = new ArrayList<>();
									ItemStack item11 = new ItemStack(Material.LEATHER_LEGGINGS);
									LeatherArmorMeta meta11 = (LeatherArmorMeta) item11.getItemMeta();
									meta11.setColor(Color.WHITE);
									meta11.setDisplayName("§eTrousers");
									lore11.add("§5Reduced 10% of the suffered damage!");
									meta11.setLore(lore11);
									item11.setItemMeta(meta11);
									inv.setItem(15, item11);
									
									ArrayList<String> lore111 = new ArrayList<>();
									ItemStack item111 = new ItemStack(Material.LEATHER_BOOTS);
									LeatherArmorMeta meta111 = (LeatherArmorMeta) item111.getItemMeta();
									meta111.setColor(Color.RED);
									meta111.setDisplayName("§eWinter boots");
									lore111.add("§5Reduced 10% of the suffered damage!");
									meta111.setLore(lore111);
									item111.setItemMeta(meta111);
									inv.setItem(16, item111);
									
									if(players.getInt("Players." + p.getName() + ".Tokens") == shop.getInt("Shop.Price.Grenade") || players.getInt("Players." + p.getName() + ".Tokens") > shop.getInt("Shop.Price.Grenade")){
										ArrayList<String> lore2111 = new ArrayList<>();
										ItemStack item2111 = new ItemStack(Material.EMERALD);
										ItemMeta meta2111 = item2111.getItemMeta();
										meta2111.setDisplayName("§8Buy: §eGrenade");
										lore2111.add("§5This item costs " + shop.getInt("Shop.Price.Grenade") +" tokens!");
										meta2111.setLore(lore2111);
										item2111.setAmount(shop.getInt("Shop.Price.Grenade"));
										item2111.setItemMeta(meta2111);
										inv.setItem(19, item2111);
									}else{
										ArrayList<String> lore2111 = new ArrayList<>();
										ItemStack item2111 = new ItemStack(Material.INK_SACK, 8, (short)0, (byte)(15 - DyeColor.GRAY.getData()));
										ItemMeta meta2111 = item2111.getItemMeta();
										meta2111.setDisplayName("§8Buy: §eGrenade");
										lore2111.add("§5This item costs " + shop.getInt("Shop.Price.Grenade") +" tokens!");
										meta2111.setLore(lore2111);
										item2111.setAmount(shop.getInt("Shop.Price.Grenade"));
										item2111.setItemMeta(meta2111);
										inv.setItem(19, item2111);
									}
									
									if(players.getInt("Players." + p.getName() + ".Tokens") == shop.getInt("Shop.Price.Knife") || players.getInt("Players." + p.getName() + ".Tokens") > shop.getInt("Shop.Price.Knife")){
										ArrayList<String> lore3111 = new ArrayList<>();
										ItemStack item3111 = new ItemStack(Material.EMERALD);
										ItemMeta meta3111 = item3111.getItemMeta();
										meta3111.setDisplayName("§8Buy: §eKnife");
										lore3111.add("§5This item costs " + shop.getInt("Shop.Price.Knife") + " tokens!");
										meta3111.setLore(lore3111);
										item3111.setAmount(shop.getInt("Shop.Price.Knife"));
										item3111.setItemMeta(meta3111);
										inv.setItem(20, item3111);
									}else{
										ArrayList<String> lore3111 = new ArrayList<>();
										ItemStack item3111 = new ItemStack(Material.INK_SACK, 8, (short)0, (byte)(15 - DyeColor.GRAY.getData()));
										ItemMeta meta3111 = item3111.getItemMeta();
										meta3111.setDisplayName("§8Buy: §eKnife");
										lore3111.add("§5This item costs " + shop.getInt("Shop.Price.Knife") + " tokens!");
										meta3111.setLore(lore3111);
										item3111.setAmount(shop.getInt("Shop.Price.Knife"));
										item3111.setItemMeta(meta3111);
										inv.setItem(20, item3111);
									}
									
									if(players.getInt("Players." + p.getName() + ".Tokens") == shop.getInt("Shop.Price.BlindnessBall") || players.getInt("Players." + p.getName() + ".Tokens") > shop.getInt("Shop.Price.BlindnessBall")){
										ArrayList<String> lore31111 = new ArrayList<>();
										ItemStack item31111 = new ItemStack(Material.EMERALD);
										ItemMeta meta31111 = item31111.getItemMeta();
										meta31111.setDisplayName("§8Buy: §eBlindnessBall");
										lore31111.add("§5This item costs " + shop.getInt("Shop.Price.BlindnessBall") + " tokens!");
										meta31111.setLore(lore31111);
										item31111.setAmount(shop.getInt("Shop.Price.BlindnessBall"));
										item31111.setItemMeta(meta31111);
										inv.setItem(21, item31111);
									}else{
										ArrayList<String> lore31111 = new ArrayList<>();
										ItemStack item31111 = new ItemStack(Material.INK_SACK, 8, (short)0, (byte)(15 - DyeColor.GRAY.getData()));
										ItemMeta meta31111 = item31111.getItemMeta();
										meta31111.setDisplayName("§8Buy: §eBlindnessBall");
										lore31111.add("§5This item costs " + shop.getInt("Shop.Price.BlindnessBall") + " tokens!");
										meta31111.setLore(lore31111);
										item31111.setAmount(shop.getInt("Shop.Price.BlindnessBall"));
										item31111.setItemMeta(meta31111);
										inv.setItem(21, item31111);
									}
									
									if(players.getInt("Players." + p.getName() + ".Tokens") == shop.getInt("Shop.Price.WoolyHat") || players.getInt("Players." + p.getName() + ".Tokens") > shop.getInt("Shop.Price.WoolyHat")){
										ArrayList<String> lore2 = new ArrayList<>();
										ItemStack item2 = new ItemStack(Material.EMERALD);
										ItemMeta meta2 = item2.getItemMeta();
										meta2.setDisplayName("§8Buy: §eWoolly hat");
										lore2.add("§5This item costs " + shop.getInt("Shop.Price.WoolyHat") + " tokens!");
										meta2.setLore(lore2);
										item2.setAmount(shop.getInt("Shop.Price.WoolyHat"));
										item2.setItemMeta(meta2);
										inv.setItem(22, item2);
									}else{
										ArrayList<String> lore2 = new ArrayList<>();
										ItemStack item2 = new ItemStack(Material.INK_SACK, 8, (short)0, (byte)(15 - DyeColor.GRAY.getData()));
										ItemMeta meta2 = item2.getItemMeta();
										meta2.setDisplayName("§8Buy: §eWoolly hat");
										lore2.add("§5This item costs " + shop.getInt("Shop.Price.WoolyHat") + " tokens!");
										meta2.setLore(lore2);
										item2.setAmount(shop.getInt("Shop.Price.WoolyHat"));
										item2.setItemMeta(meta2);
										inv.setItem(22, item2);
									}
									
									if(players.getInt("Players." + p.getName() + ".Tokens") == shop.getInt("Shop.Price.WinterCoat") || players.getInt("Players." + p.getName() + ".Tokens") > shop.getInt("Shop.Price.WinterCoat")){
										ArrayList<String> lore21 = new ArrayList<>();
										ItemStack item21 = new ItemStack(Material.EMERALD);
										ItemMeta meta21 = item21.getItemMeta();
										meta21.setDisplayName("§8Buy: §eWinter coat");
										lore21.add("§5This item costs " + shop.getInt("Shop.Price.WinterCoat") + " tokens!");
										meta21.setLore(lore21);
										item21.setAmount(shop.getInt("Shop.Price.WinterCoat"));
										item21.setItemMeta(meta21);
										inv.setItem(23, item21);
									}else{
										ArrayList<String> lore21 = new ArrayList<>();
										ItemStack item21 = new ItemStack(Material.INK_SACK, 8, (short)0, (byte)(15 - DyeColor.GRAY.getData()));
										ItemMeta meta21 = item21.getItemMeta();
										meta21.setDisplayName("§8Buy: §eWinter coat");
										lore21.add("§5This item costs " + shop.getInt("Shop.Price.WinterCoat") + " tokens!");
										meta21.setLore(lore21);
										item21.setAmount(shop.getInt("Shop.Price.WinterCoat"));
										item21.setItemMeta(meta21);
										inv.setItem(23, item21);
									}
									
									if(players.getInt("Players." + p.getName() + ".Tokens") == shop.getInt("Shop.Price.Trousers") || players.getInt("Players." + p.getName() + ".Tokens") > shop.getInt("Shop.Price.Trousers")){
										ArrayList<String> lore211 = new ArrayList<>();
										ItemStack item211 = new ItemStack(Material.EMERALD);
										ItemMeta meta211 = item211.getItemMeta();
										meta211.setDisplayName("§8Buy: §eTrousers");
										lore211.add("§5This item costs " + shop.getInt("Shop.Price.Trousers") + " tokens!");
										meta211.setLore(lore211);
										item211.setAmount(shop.getInt("Shop.Price.Trousers"));
										item211.setItemMeta(meta211);
										inv.setItem(24, item211);
									}else{
										ArrayList<String> lore211 = new ArrayList<>();
										ItemStack item211 = new ItemStack(Material.INK_SACK, 8, (short)0, (byte)(15 - DyeColor.GRAY.getData()));
										ItemMeta meta211 = item211.getItemMeta();
										meta211.setDisplayName("§8Buy: §eTrousers");
										lore211.add("§5This item costs " + shop.getInt("Shop.Price.Trousers") + " tokens!");
										meta211.setLore(lore211);
										item211.setAmount(shop.getInt("Shop.Price.Trousers"));
										item211.setItemMeta(meta211);
										inv.setItem(24, item211);
									}
									
									if(players.getInt("Players." + p.getName() + ".Tokens") == shop.getInt("Shop.Price.WinterBoots") || players.getInt("Players." + p.getName() + ".Tokens") > shop.getInt("Shop.Price.WinterBoots")){
										ArrayList<String> lore21111 = new ArrayList<>();
										ItemStack item21111 = new ItemStack(Material.EMERALD);
										ItemMeta meta21111 = item21111.getItemMeta();
										meta21111.setDisplayName("§8Buy: §eWinter boots");
										lore21111.add("§5This item costs " + shop.getInt("Shop.Price.WinterBoots") + " tokens!");
										meta21111.setLore(lore21111);
										item21111.setAmount(shop.getInt("Shop.Price.WinterBoots"));
										item21111.setItemMeta(meta21111);
										inv.setItem(25, item21111);
									}else{
										ArrayList<String> lore21111 = new ArrayList<>();
										ItemStack item21111 = new ItemStack(Material.INK_SACK, 8, (short)0, (byte)(15 - DyeColor.GRAY.getData()));
										ItemMeta meta21111 = item21111.getItemMeta();
										meta21111.setDisplayName("§8Buy: §eWinter boots");
										lore21111.add("§5This item costs " + shop.getInt("Shop.Price.WinterBoots") + " tokens!");
										meta21111.setLore(lore21111);
										item21111.setAmount(shop.getInt("Shop.Price.WinterBoots"));
										item21111.setItemMeta(meta21111);
										inv.setItem(25, item21111);
									}
										p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
								}
							}, 15);
							
							p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1F, 1F);
							p.openInventory(inv);
					}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e){
		Player p = (Player) e.getPlayer();
		World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
		World w2 = p.getWorld();
		File file = new File("plugins//SnowballFight//players.yml");
		YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
		int tokens = players.getInt("Players." + p.getName() + ".Tokens");
		if(w == w2){
			try{
				if(e.getInventory().getName().equalsIgnoreCase("§8Shop | §eYour Tokens: §5" + tokens)){
					Bukkit.getScheduler().cancelTask(tasks.get(p.getName()));
					tasks.remove(p.getName());
					slot = 0;
				}
			}catch(NullPointerException ex){
			}
		}
	}
}
