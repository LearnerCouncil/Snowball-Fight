package tk.shadowcube.snowball;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
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
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffectType;

import com.connorlinfoot.titleapi.TitleAPI;

public class Snowball implements Listener{

	private main plugin;
	public Snowball(main p){
		this.plugin = p;
		
		p.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onCommand2(PlayerCommandPreprocessEvent e){
		if(e.getMessage().equals("/snowball join")){
			World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
			World w2 = e.getPlayer().getWorld();
			if(!(w == w2)){
				plugin.updateScoreboard(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void Damager(final EntityDamageByEntityEvent e){
		Entity target1 = e.getEntity();
		Entity attacker1 = e.getDamager();
		File file = new File("plugins//SnowballFight//players.yml");
		YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
		
		if(attacker1 instanceof org.bukkit.entity.Snowball){
			if(target1 instanceof Player){
				World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
				World w2 = e.getEntity().getWorld();
				if(w == w2){
					Player p = (Player)target1;
					Player shooter = (Player) ((Projectile) e.getDamager()).getShooter();
					if(!p.getName().equals(shooter.getName())){
						if(players.getInt("Players." + shooter.getName() + ".BlindnessBall") > 0){
							players.set("Players." + shooter.getName() + ".BlindnessBall", players.getInt("Players." + shooter.getName() + ".BlindnessBall") -1);
							try {
								players.save(file);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						
							if(players.getInt("Players." + shooter.getName() + ".BlindnessBall") == 1){
								ArrayList<String> lore3 = new ArrayList<>();
								ItemStack item3 = new ItemStack(Material.FIREWORK_CHARGE);
								ItemMeta meta3 = item3.getItemMeta();
								meta3.setDisplayName("§81x §eBlindnessBall");
								lore3.add("§5Makes your enemies blind!");
								meta3.setLore(lore3);
								item3.setItemMeta(meta3);
								shooter.getInventory().setItem(7, item3);
							}else{
								ItemStack item31 = new ItemStack(Material.SUGAR);
								ItemMeta meta31 = item31.getItemMeta();
								meta31.setDisplayName("§7Special-Itemslot");
								item31.setItemMeta(meta31);
								shooter.getInventory().setItem(7, item31);
							}
						
							double damage = 5.0;
							if(p.getInventory().getHelmet() != null){
								p.getInventory().getHelmet().setDurability((short) 0);
								damage = damage - 0.25;
							}
							if(p.getInventory().getChestplate() != null){
								p.getInventory().getChestplate().setDurability((short) 0);
								damage = damage - 0.25;
							}
							if(p.getInventory().getLeggings() != null){
								p.getInventory().getLeggings().setDurability((short) 0);
								damage = damage - 0.25;
							}
							if(p.getInventory().getBoots() != null){
								p.getInventory().getBoots().setDurability((short) 0);
								damage = damage - 0.25;
							}
							plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "effect " + p.getName() + " minecraft:blindness 6 0");
							plugin.onHit(p);
							e.setDamage(damage);
						
						}else{
							if(players.getInt("Players." + shooter.getName() + ".Grenade") == 1){
								return;
							}else{
								Player p1 = (Player)target1;
								double damage = 5.0;
								if(p1.getInventory().getHelmet() != null){
									p1.getInventory().getHelmet().setDurability((short) 0);
									damage = damage - 0.25;
								}
								if(p1.getInventory().getChestplate() != null){
									p1.getInventory().getChestplate().setDurability((short) 0);
									damage = damage - 0.25;
								}
								if(p1.getInventory().getLeggings() != null){
									p1.getInventory().getLeggings().setDurability((short) 0);
									damage = damage - 0.25;
								}
								if(p1.getInventory().getBoots() != null){
									p1.getInventory().getBoots().setDurability((short) 0);
									damage = damage - 0.25;
								}
								e.setDamage(damage);
								plugin.onHit(p1);
							}
						}
						}else{
							shooter.sendMessage("§cYou can't kill yourself!");
							plugin.onHit(p);
							e.setDamage(0);
					}
				}
			}
		}
		final Entity target = target1;
		final Entity attacker = attacker1;
		
			Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable(){
				
				@Override
				public void run() {
					if(target instanceof Player){
						if(attacker instanceof org.bukkit.entity.Snowball){
						if(target.isDead()){
							World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
							World w2 = e.getEntity().getWorld();
							if(w == w2){
								
							Random rand = new Random();
							final int chance = rand.nextInt(100);
							final Player p = (Player) target;
							final Player shooter = (Player) ((Projectile) e.getDamager()).getShooter();
							shooter.playSound(shooter.getLocation(), Sound.ORB_PICKUP, 1F, 1F);
							shooter.sendMessage("§7You §akilled §e" + target.getName() + "§7!");
							if(chance < plugin.getConfig().getInt("Snowball.ChanceToBecomeAToken")){
							shooter.sendMessage("§7+ " + plugin.getConfig().getInt("Snowball.AmountOfTokens") +" §eToken!");
							}
							p.sendMessage("§7You was §4killed §7by §e" + shooter.getName() + "§7!");
							
								Bukkit.getScheduler().runTask(plugin, new Runnable(){
									@Override
									public void run() {
											File file = new File("plugins//SnowballFight//players.yml");
											YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
											players.set("Players." + shooter.getName() + ".Kills", players.getInt("Players." + shooter.getName() + ".Kills") + 1);
											if(chance < plugin.getConfig().getInt("Snowball.ChanceToBecomeAToken")){
											int tokens = plugin.getConfig().getInt("Snowball.AmountOfTokens");
											players.set("Players." + shooter.getName() + ".Tokens", players.getInt("Players." + shooter.getName() + ".Tokens") + tokens);
											}
											try {
												players.save(file);
											} catch (IOException e) {
												e.printStackTrace();
											}
											plugin.updateScoreboard(shooter);
									}
								});
								
							Respawn(p);
							}
						}
						}
					}
				}
			});
		
		target1 = null;
		attacker1 = null;
		return;
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
		World w2 = e.getEntity().getWorld();
		if(w == w2){
			e.setKeepInventory(true);
			e.setDeathMessage(null);
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
	
	@EventHandler
	public void onClick(PlayerInteractEvent e){
		final Player p = e.getPlayer();
		World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
		World w2 = p.getWorld();
		
		if(w == w2){
			if(p.getItemInHand().getType() == Material.SLIME_BALL){
				if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
					if(plugin.getConfig().getBoolean("Snowball.EnableBungeecord") == false){
						Location loc = p.getLocation();
						File file = new File("plugins//SnowballFight//joincoords.yml");
						YamlConfiguration coords = YamlConfiguration.loadConfiguration(file);
						File file1 = new File("plugins//SnowballFight//players.yml");
						YamlConfiguration players = YamlConfiguration.loadConfiguration(file1);
						p.getInventory().clear();
						p.getInventory().setArmorContents(null);
						p.removePotionEffect(PotionEffectType.INVISIBILITY);
				
						players.set("Players." + p.getName() + ".Tokens", 0);
						players.set("Players." + p.getName() + ".BlindnessBall", 0);
						players.set("Players." + p.getName() + ".Knife", 0);
						players.set("Players." + p.getName() + ".Grenade", 0);
						try {
							players.save(file1);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				
						double x = coords.getDouble("Players." + p.getName() + ".X");
						double y = coords.getDouble("Players." + p.getName() + ".Y");
						double z = coords.getDouble("Players." + p.getName() + ".Z");
						double pitch = coords.getDouble("Players." + p.getName() + ".Pitch");
						double yaw = coords.getDouble("Players." + p.getName() + ".Yaw");
						String worldname = coords.getString("Players." + p.getName() + ".Worldname");
						World world = Bukkit.getWorld(worldname);
				
						loc.setX(x);
						loc.setY(y);
						loc.setZ(z);
						loc.setPitch((float)pitch);
						loc.setYaw((float)yaw);
						loc.setWorld(world);
						p.teleport(loc);
				
						Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){
							@Override
							public void run() {
								plugin.clearScoreboard(p);
							}
						}, 15);
					}else{
						Location loc = p.getLocation();
						File file = new File("plugins//SnowballFight//joincoords.yml");
						YamlConfiguration coords = YamlConfiguration.loadConfiguration(file);
						File file1 = new File("plugins//SnowballFight//players.yml");
						YamlConfiguration players = YamlConfiguration.loadConfiguration(file1);
						p.getInventory().clear();
						p.getInventory().setArmorContents(null);
						p.removePotionEffect(PotionEffectType.INVISIBILITY);
				
						players.set("Players." + p.getName() + ".Tokens", 0);
						players.set("Players." + p.getName() + ".BlindnessBall", 0);
						players.set("Players." + p.getName() + ".Knife", 0);
						players.set("Players." + p.getName() + ".Grenade", 0);
						try {
							players.save(file1);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				
						double x = coords.getDouble("Players." + p.getName() + ".X");
						double y = coords.getDouble("Players." + p.getName() + ".Y");
						double z = coords.getDouble("Players." + p.getName() + ".Z");
						double pitch = coords.getDouble("Players." + p.getName() + ".Pitch");
						double yaw = coords.getDouble("Players." + p.getName() + ".Yaw");
						String worldname = coords.getString("Players." + p.getName() + ".Worldname");
						World world = Bukkit.getWorld(worldname);
				
						loc.setX(x);
						loc.setY(y);
						loc.setZ(z);
						loc.setPitch((float)pitch);
						loc.setYaw((float)yaw);
						loc.setWorld(world);
						p.teleport(loc);
						
						ByteArrayOutputStream b = new ByteArrayOutputStream();
						DataOutputStream out = new DataOutputStream(b);
						 
						try {
						    out.writeUTF("Connect");
						    out.writeUTF(plugin.getConfig().getString("Snowball.Fallbackserver"));
						} catch (IOException ex) {
						}
						p.sendPluginMessage(this.plugin, "BungeeCord", b.toByteArray());
					}
				}
			}
		}
	}
}
