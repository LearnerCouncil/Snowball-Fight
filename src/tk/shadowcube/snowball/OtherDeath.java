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
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffectType;

import com.connorlinfoot.titleapi.TitleAPI;

public class OtherDeath implements Listener{

	private main plugin;
	public OtherDeath(main p){
		this.plugin = p;
		
		p.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e){
		final Entity target = e.getEntity();
		Entity attacker = e.getDamager();
		
		if(attacker instanceof Player){
			if(target instanceof Player){
				World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
				World w2 = e.getEntity().getWorld();
				if(w == w2){
					final Player p = (Player)target;
					final Player killer = (Player)attacker;
					
					if(p.getInventory().getHelmet() != null){
						p.getInventory().getHelmet().setDurability((short) 0);
					}
					if(p.getInventory().getChestplate() != null){
						p.getInventory().getChestplate().setDurability((short) 0);
					}
					if(p.getInventory().getLeggings() != null){
						p.getInventory().getLeggings().setDurability((short) 0);
					}
					if(p.getInventory().getBoots() != null){
						p.getInventory().getBoots().setDurability((short) 0);
					}
					plugin.onHit(p);
					
					Bukkit.getScheduler().runTask(plugin, new Runnable(){
						@Override
						public void run() {
							if(target.isDead()){
								killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
								killer.sendMessage("§7You §akilled §e" + target.getName() + "§7, but you don't get any tokens because you killed him/her without an snowball!");
								p.sendMessage("§7You was §4killed §7by §e" + killer.getName() + " §7!");
								
								File file = new File("plugins//SnowballFight//players.yml");
								YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
									players.set("Players." + killer.getName() + ".Kills", players.getInt("Players." + killer.getName() + ".Kills") + 1);
									try {
										players.save(file);
									} catch (IOException e) {
										e.printStackTrace();
									}
									plugin.updateScoreboard(killer);
									Respawn(p);
							}
						}
					});
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
