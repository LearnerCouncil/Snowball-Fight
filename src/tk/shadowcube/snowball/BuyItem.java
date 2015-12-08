package tk.shadowcube.snowball;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class BuyItem implements Listener{

	private main plugin;
	public BuyItem(main p){
		this.plugin = p;
		
		p.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onBuy(InventoryClickEvent e){
		File file2 = new File("plugins//SnowballFight//shop.yml");
		YamlConfiguration shop = YamlConfiguration.loadConfiguration(file2);
		try{
			if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Buy: §eWoolly hat")){
				e.setCancelled(true);
				File file = new File("plugins//SnowballFight//players.yml");
				YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
				Player p = (Player) e.getWhoClicked();
				
				if(players.getInt("Players." + p.getName() + ".Tokens") == shop.getInt("Shop.Price.WoolyHat") && p.getInventory().getHelmet() == null || players.getInt("Players." + p.getName() + ".Tokens") > shop.getInt("Shop.Price.WoolyHat") && p.getInventory().getHelmet() == null){
					players.set("Players." + p.getName() + ".Tokens", players.getInt("Players." + p.getName() + ".Tokens") - shop.getInt("Shop.Price.WoolyHat"));
					try {
						players.save(file);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
					
					ArrayList<String> lore = new ArrayList<>();
					ItemStack item = new ItemStack(Material.LEATHER_HELMET);
					LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
					meta.setColor(Color.RED);
					meta.setDisplayName("§eWoolly hat");
					lore.add("§5So soft!");
					lore.add("§5Reduced 10% of the suffered damage!");
					meta.setLore(lore);
					meta.addEnchant(Enchantment.DURABILITY, 10, true);
					item.setItemMeta(meta);
					
					p.getInventory().setHelmet(item);
				}else{
					p.sendMessage("§cYou don't have enough tokens to buy that or you already have helmet!");
				}
			}else{
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Buy: §eWinter coat")){
					e.setCancelled(true);
					File file = new File("plugins//SnowballFight//players.yml");
					YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
					Player p = (Player) e.getWhoClicked();
					
					if(players.getInt("Players." + p.getName() + ".Tokens") == shop.getInt("Shop.Price.WinterCoat") && p.getInventory().getChestplate() == null || players.getInt("Players." + p.getName() + ".Tokens") > shop.getInt("Shop.Price.WinterCoat") && p.getInventory().getChestplate() == null){
						players.set("Players." + p.getName() + ".Tokens", players.getInt("Players." + p.getName() + ".Tokens") - shop.getInt("Shop.Price.WinterCoat"));
						try {
							players.save(file);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
						
						ArrayList<String> lore1 = new ArrayList<>();
						ItemStack item1 = new ItemStack(Material.LEATHER_CHESTPLATE);
						LeatherArmorMeta meta1 = (LeatherArmorMeta) item1.getItemMeta();
						meta1.setColor(Color.GREEN);
						meta1.setDisplayName("§eWinter coat");
						lore1.add("§5Oh...so warm!");
						lore1.add("§5Reduced 10% of the suffered damage!");
						meta1.setLore(lore1);
						meta1.addEnchant(Enchantment.DURABILITY, 10, true);
						item1.setItemMeta(meta1);
						
						p.getInventory().setChestplate(item1);
					}else{
						p.sendMessage("§cYou don't have enough tokens to buy that or you already have a chestplate!");
					}
				}else{
					if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Buy: §eTrousers")){
						e.setCancelled(true);
						File file = new File("plugins//SnowballFight//players.yml");
						YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
						Player p = (Player) e.getWhoClicked();
						
						if(players.getInt("Players." + p.getName() + ".Tokens") == shop.getInt("Shop.Price.Trousers") && p.getInventory().getLeggings() == null || players.getInt("Players." + p.getName() + ".Tokens") > shop.getInt("Shop.Price.Trousers") && p.getInventory().getLeggings() == null){
							players.set("Players." + p.getName() + ".Tokens", players.getInt("Players." + p.getName() + ".Tokens") - shop.getInt("Shop.Price.Trousers"));
							try {
								players.save(file);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
							
							ArrayList<String> lore11 = new ArrayList<>();
							ItemStack item11 = new ItemStack(Material.LEATHER_LEGGINGS);
							LeatherArmorMeta meta11 = (LeatherArmorMeta) item11.getItemMeta();
							meta11.setColor(Color.WHITE);
							meta11.setDisplayName("§eTrousers");
							lore11.add("§5Reduced 10% of the suffered damage!");
							meta11.setLore(lore11);
							meta11.addEnchant(Enchantment.DURABILITY, 10, true);
							item11.setItemMeta(meta11);
							
							p.getInventory().setLeggings(item11);
						}else{
							p.sendMessage("§cYou don't have enough tokens to buy that or you already have a leggings!");
						}
					}else{
						if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Buy: §eWinter boots")){
							e.setCancelled(true);
							File file = new File("plugins//SnowballFight//players.yml");
							YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
							Player p = (Player) e.getWhoClicked();
							
							if(players.getInt("Players." + p.getName() + ".Tokens") == shop.getInt("Shop.Price.WinterBoots") && p.getInventory().getBoots() == null || players.getInt("Players." + p.getName() + ".Tokens") > shop.getInt("Shop.Price.WinterBoots") && p.getInventory().getBoots() == null){
								players.set("Players." + p.getName() + ".Tokens", players.getInt("Players." + p.getName() + ".Tokens") - shop.getInt("Shop.Price.WinterBoots"));
								try {
									players.save(file);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
								p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
								
								ArrayList<String> lore111 = new ArrayList<>();
								ItemStack item111 = new ItemStack(Material.LEATHER_BOOTS);
								LeatherArmorMeta meta111 = (LeatherArmorMeta) item111.getItemMeta();
								meta111.setColor(Color.RED);
								meta111.setDisplayName("§eWinter boots");
								lore111.add("§5Reduced 10% of the suffered damage!");
								meta111.setLore(lore111);
								meta111.addEnchant(Enchantment.DURABILITY, 10, true);
								item111.setItemMeta(meta111);
								
								p.getInventory().setBoots(item111);
							}else{
								p.sendMessage("§cYou don't have enough tokens to buy that or you already have boots!");
							}
						}else{
							if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Buy: §eBlindnessBall")){
								e.setCancelled(true);
								File file = new File("plugins//SnowballFight//players.yml");
								YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
								Player p = (Player) e.getWhoClicked();
								
								if(players.getInt("Players." + p.getName() + ".Tokens") == shop.getInt("Shop.Price.BlindnessBall") && players.getInt("Players." + p.getName() + ".BlindnessBall") == 0 && players.getInt("Players." + p.getName() + ".Grenade") == 0 || players.getInt("Players." + p.getName() + ".Tokens") > shop.getInt("Shop.Price.BlindnessBall") && players.getInt("Players." + p.getName() + ".BlindnessBall") == 0 && players.getInt("Players." + p.getName() + ".Grenade") == 0){
									players.set("Players." + p.getName() + ".Tokens", players.getInt("Players." + p.getName() + ".Tokens") - shop.getInt("Shop.Price.BlindnessBall"));
									players.set("Players." + p.getName() + ".BlindnessBall", 2);
									try {
										players.save(file);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
									p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
									
									ArrayList<String> lore3 = new ArrayList<>();
									ItemStack item3 = new ItemStack(Material.FIREWORK_CHARGE);
									ItemMeta meta3 = item3.getItemMeta();
									meta3.setDisplayName("§82x §eBlindnessBall");
									lore3.add("§5Makes your enemies blind!");
									meta3.setLore(lore3);
									item3.setAmount(2);
									item3.setItemMeta(meta3);
									
									p.getInventory().setItem(7, item3);
								}else{
									p.sendMessage("§cYou don't have enough tokens to buy that or you already have blindness-balls/a grenade!");
								}
							}else{
								if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Buy: §eKnife")){
									e.setCancelled(true);
									File file = new File("plugins//SnowballFight//players.yml");
									YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
									Player p = (Player) e.getWhoClicked();
									
									if(players.getInt("Players." + p.getName() + ".Tokens") == shop.getInt("Shop.Price.Knife") && players.getInt("Players." + p.getName() + ".Knife") == 0 || players.getInt("Players." + p.getName() + ".Tokens") > shop.getInt("Shop.Price.Knife") && players.getInt("Players." + p.getName() + ".Knife") == 0){
										players.set("Players." + p.getName() + ".Tokens", players.getInt("Players." + p.getName() + ".Tokens") - shop.getInt("Shop.Price.Knife"));
										players.set("Players." + p.getName() + ".Knife", 1);
										try {
											players.save(file);
										} catch (IOException e1) {
											e1.printStackTrace();
										}
										p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
										
										ArrayList<String> lore3 = new ArrayList<>();
										ItemStack item3 = new ItemStack(Material.IRON_SWORD);
										ItemMeta meta3 = item3.getItemMeta();
										meta3.setDisplayName("§eKnife");
										lore3.add("§5Killed instantly - right-click on a player!");
										meta3.setLore(lore3);
										item3.setItemMeta(meta3);
										
										p.getInventory().setItem(1, item3);
									}else{
										p.sendMessage("§cYou don't have enough tokens to buy that or you already have knife!");
									}
								}else{
									if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Buy: §eGrenade")){
										e.setCancelled(true);
										File file = new File("plugins//SnowballFight//players.yml");
										YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
										Player p = (Player) e.getWhoClicked();
										
										if(players.getInt("Players." + p.getName() + ".Tokens") == shop.getInt("Shop.Price.Grenade") && players.getInt("Players." + p.getName() + ".Grenade") == 0 && players.getInt("Players." + p.getName() + ".BlindnessBall") == 0 || players.getInt("Players." + p.getName() + ".Tokens") > shop.getInt("Shop.Price.Grenade") && players.getInt("Players." + p.getName() + ".Grenade") == 0 && players.getInt("Players." + p.getName() + ".BlindnessBall") == 0){
											players.set("Players." + p.getName() + ".Tokens", players.getInt("Players." + p.getName() + ".Tokens") - shop.getInt("Shop.Price.Grenade"));
											players.set("Players." + p.getName() + ".Grenade", 1);
											try {
												players.save(file);
											} catch (IOException e1) {
												e1.printStackTrace();
											}
											p.playSound(p.getLocation(), Sound.LEVEL_UP, 1F, 1F);
											
											ArrayList<String> lore31 = new ArrayList<>();
											ItemStack item31 = new ItemStack(Material.TNT);
											ItemMeta meta31 = item31.getItemMeta();
											meta31.setDisplayName("§eGrenade");
											lore31.add("§5Boooom!");
											meta31.setLore(lore31);
											item31.setItemMeta(meta31);
											
											p.getInventory().setItem(7, item31);
										}else{
											p.sendMessage("§cYou don't have enough tokens to buy that or you already have a grenade/blindness-balls!");
										}
									}
								}
							}
						}
					}
				}
			}
		}catch(NullPointerException ex){
		}
	}
}
