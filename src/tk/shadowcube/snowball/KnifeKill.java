package tk.shadowcube.snowball;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffectType;

import com.connorlinfoot.titleapi.TitleAPI;

public class KnifeKill implements Listener{

	private main plugin;
	public KnifeKill(main p){
		this.plugin = p;
		
		p.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEntityEvent e){
		final Player p = e.getPlayer();
		World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
		World w2 = e.getPlayer().getWorld();
		final File file = new File("plugins//SnowballFight//players.yml");
		final YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
		if(e.getRightClicked() instanceof Player){
			if(p.getItemInHand().getType() == Material.IRON_SWORD){
				if(w == w2){
					final Player target = (Player)e.getRightClicked();
					target.playSound(target.getLocation(), Sound.HURT_FLESH, 1F, 1F);
					plugin.onHit(target);
					target.setHealth(0);
					
					Random rand = new Random();
					final int chance = rand.nextInt(100);
					
					p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
					p.sendMessage("§7You §akilled §e" + target.getName() + "§7!");
					if(chance < plugin.getConfig().getInt("Snowball.ChanceToBecomeAToken")){
					p.sendMessage("§7+ " + plugin.getConfig().getInt("Snowball.AmountOfTokens") +" §eToken!");
					}
					target.sendMessage("§7You was §4killed §7by §e" + p.getName() + "§7!");
					
					ItemStack item = new ItemStack(Material.SUGAR);
					ItemMeta meta = item.getItemMeta();
					meta.setDisplayName("§7Special-Itemslot");
					item.setItemMeta(meta);
					p.getInventory().setItem(1, item);
					
					Bukkit.getScheduler().runTask(plugin, new Runnable(){
						@Override
						public void run() {
							players.set("Players." + p.getName() + ".Kills", players.getInt("Players." + p.getName() + ".Kills") + 1);
							players.set("Players." + p.getName() + ".Knife", 0);
							if(chance < plugin.getConfig().getInt("Snowball.ChanceToBecomeAToken")){
							int tokens = plugin.getConfig().getInt("Snowball.AmountOfTokens");
							players.set("Players." + p.getName() + ".Tokens", players.getInt("Players." + p.getName() + ".Tokens") + tokens);
							}
							try {
								players.save(file);
							} catch (IOException e) {
								e.printStackTrace();
							}
							plugin.updateScoreboard(p);
						}
					});
					Respawn(target);
				}
			}
		}
	}
	
	public void Respawn(final Player p){
		Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){

			@Override
			public void run() {
				p.spigot().respawn();
				Random rand = new Random();
				int spawnpoint = rand.nextInt(2);
				
				if(spawnpoint == 0){
					Location loc = p.getLocation();
					double x = plugin.getConfig().getDouble("Snowball.Spawn1.X");
					double y = plugin.getConfig().getDouble("Snowball.Spawn1.Y");
					double z = plugin.getConfig().getDouble("Snowball.Spawn1.Z");
					double pitch = plugin.getConfig().getDouble("Snowball.Spawn1.Pitch");
					double yaw = plugin.getConfig().getDouble("Snowball.Spawn1.Yaw");
					String worldname = plugin.getConfig().getString("Snowball.Spawn1.Worldname");
					World world = Bukkit.getWorld(worldname);
					
					loc.setX(x);
					loc.setY(y);
					loc.setZ(z);
					loc.setPitch((float) pitch);
					loc.setYaw((float) yaw);
					loc.setWorld(world);
					p.setGameMode(GameMode.SURVIVAL);
					p.teleport(loc);
				}else{
					Location loc = p.getLocation();
					double x = plugin.getConfig().getDouble("Snowball.Spawn2.X");
					double y = plugin.getConfig().getDouble("Snowball.Spawn2.Y");
					double z = plugin.getConfig().getDouble("Snowball.Spawn2.Z");
					double pitch = plugin.getConfig().getDouble("Snowball.Spawn2.Pitch");
					double yaw = plugin.getConfig().getDouble("Snowball.Spawn2.Yaw");
					String worldname = plugin.getConfig().getString("Snowball.Spawn2.Worldname");
					World world = Bukkit.getWorld(worldname);
					
					loc.setX(x);
					loc.setY(y);
					loc.setZ(z);
					loc.setPitch((float) pitch);
					loc.setYaw((float) yaw);
					loc.setWorld(world);
					p.setGameMode(GameMode.SURVIVAL);
					p.teleport(loc);
				}
				
				p.setAllowFlight(false);
				p.playSound(p.getLocation(), Sound.FIZZ, 1F, 1F);
				plugin.clearScoreboard(p);
				final File file = new File("plugins//SnowballFight//players.yml");
				final YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
				
				players.set("Players." + p.getName() + ".Deaths", players.getInt("Players." + p.getName() + ".Deaths") + 1);
				if(p.getInventory().getHelmet() != null){
					players.set("Players." + p.getName() + ".Armor.Helmet", true);
					p.getInventory().setHelmet(null);
				}
				if(p.getInventory().getChestplate() != null){
					players.set("Players." + p.getName() + ".Armor.Chestplate", true);
					p.getInventory().setChestplate(null);
				}
				if(p.getInventory().getLeggings() != null){
					players.set("Players." + p.getName() + ".Armor.Leggings", true);
					p.getInventory().setLeggings(null);
				}
				if(p.getInventory().getBoots() != null){
					players.set("Players." + p.getName() + ".Armor.Boots", true);
					p.getInventory().setBoots(null);
				}
				try {
					players.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable(){
					@Override
					public void run() {
						TitleAPI.sendTitle(p, 0, 20, 0, "§a3",null);
						plugin.Invisbility(p);
					}
				}, 20);
				
				Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable(){
					@Override
					public void run() {
						TitleAPI.sendTitle(p, 0, 20, 0, "§a2",null);
					}
				}, 40);
				
				Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable(){
					@Override
					public void run() {
						TitleAPI.sendTitle(p, 0, 20, 0, "§a1",null);
					}
				}, 60);
				
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){
					@Override
					public void run() {
						if(players.getBoolean("Players." + p.getName() + ".Armor.Helmet") == true){
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
							players.set("Players." + p.getName() + ".Armor.Helmet", false);
						}
						if(players.getBoolean("Players." + p.getName() + ".Armor.Chestplate") == true){
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
							players.set("Players." + p.getName() + ".Armor.Chestplate", false);
						}
						if(players.getBoolean("Players." + p.getName() + ".Armor.Leggings") == true){
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
							players.set("Players." + p.getName() + ".Armor.Leggings", false);
						}
						if(players.getBoolean("Players." + p.getName() + ".Armor.Boots") == true){
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
							players.set("Players." + p.getName() + ".Armor.Boots", false);
						}
						try {
							players.save(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
						p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
						p.removePotionEffect(PotionEffectType.INVISIBILITY);
						plugin.updateScoreboard(p);
					}
				}, 80);
			}
		}, 10);
	}
}
