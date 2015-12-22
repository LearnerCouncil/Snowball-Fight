package tk.shadowcube.snowball;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

public class EventListener implements Listener{

	private main plugin;
	public EventListener(main p){
		this.plugin = p;
		
		p.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void oBlockBreak(BlockBreakEvent e){
		Player p = e.getPlayer();
		World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
		World w2 = p.getWorld();
		
		if(w == w2){
			if(plugin.getConfig().getBoolean("Snowball.EnableBuild") == false){
				e.setCancelled(true);
			if(e.getBlock().getType() == Material.SNOW){
				ItemStack item = new ItemStack(332);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§7Snowball");
				item.setItemMeta(meta);				
				p.getInventory().addItem(item);
			}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onShovelHit(EntityDamageByEntityEvent e){
		if(e.getEntity() instanceof Player){
			if(e.getDamager() instanceof Player){
				Player p = (Player)e.getDamager();
				Player p2 = (Player)e.getEntity();
				World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
				World w2 = p.getWorld();
				if(w == w2){
					if(plugin.getConfig().getBoolean("Snowball.EnableAttackDamage") == true){
						if(p.getItemInHand().getType() == Material.DIAMOND_SPADE){
							if(p2.getInventory().getHelmet() != null || p2.getInventory().getChestplate() != null || p2.getInventory().getLeggings() != null || p2.getInventory().getBoots() != null){
								e.setDamage((double) 1.0);
							}else{
								e.setDamage((double) 1.0);
							}
						}
						if(p.getItemInHand().getType() == Material.IRON_SWORD){
							if(p2.getInventory().getHelmet() != null || p2.getInventory().getChestplate() != null || p2.getInventory().getLeggings() != null || p2.getInventory().getBoots() != null){
								e.setDamage((double) 1.0);
							}else{
								e.setDamage((double) 1.0);
							}
						}
					}else{
						if(p.getItemInHand().getType() == Material.DIAMOND_SPADE){
							e.setDamage((double) 0);
						}
						if(p.getItemInHand().getTypeId() == 332){
							e.setDamage((double) 0);
						}
						if(p.getItemInHand().getType() == Material.SLIME_BALL){
							e.setDamage((double) 0);
						}
						if(p.getItemInHand().getType() == Material.AIR){
							e.setDamage((double) 0);
						}
					}
				}
			}
		}
		
		if(e.getEntity() instanceof Snowman){
			Snowman snow = (Snowman)e.getEntity();
			File file = new File("plugins//SnowballFight//players.yml");
			YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
			if(e.getDamager() instanceof Player){
				Player damager = (Player)e.getDamager();
				if(players.getBoolean("Players." + damager.getName() + ".KillSnowMan") == false){
					try{
						if(snow.getCustomName().equalsIgnoreCase("Snowball Fight")){
							e.setCancelled(true);
						}
					}catch(NullPointerException ex){
					}
				}else{
					try{
						if(snow.getCustomName().equalsIgnoreCase("Snowball Fight")){
							players.set("Players." + damager.getName() + ".KillSnowMan", false);
							try {
								players.save(file);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							snow.setHealth(0);
						}
					}catch(NullPointerException ex){
					}
				}
			}else{
				try{
					if(snow.getCustomName().equalsIgnoreCase("Snowball Fight")){
						e.setCancelled(true);
					}
				}catch(NullPointerException ex){
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void disableFood(PlayerMoveEvent e){
		final World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
		int sched = 0;
		
		if(!Bukkit.getScheduler().isCurrentlyRunning(sched)){
			sched = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable(){
				@Override
				public void run() {
					for(Player p: w.getPlayers()){
						p.setFoodLevel(20);
					}
				}
			}, 10*20, 10*20);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
		World w2 = e.getPlayer().getWorld();
		
		if(w == w2){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onJoin(final PlayerJoinEvent e){
		if(plugin.getConfig().getBoolean("Snowball.EnableBungeecord") == true){
			e.setJoinMessage(null);
			
			Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){
				@Override
				public void run() {
					e.getPlayer().performCommand("snowball join");
					plugin.updateScoreboard(e.getPlayer());
				}
			}, 20);
		}
	}
	
	@EventHandler
	public void onFallDamage(EntityDamageEvent e){
		if(e.getEntity() instanceof Player){
			if(e.getCause() == DamageCause.FALL){
				World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
				World w2 = e.getEntity().getWorld();
				if(w == w2){
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent e){
		World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
		World w2 = e.getWhoClicked().getWorld();
		
		if(w == w2){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Snowball.Spawn1.Worldname"));
		World w2 = e.getPlayer().getWorld();
		
		if(w == w2){
			Player p = e.getPlayer();
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
			e.setQuitMessage(null);
		}
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e){
		try{
		if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Snowshovel") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Leave game") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Snowball") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Shop")){
			e.setCancelled(true);
		}
		} catch(NullPointerException ex){
		}
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onInventoryClick2(InventoryClickEvent e){
		try{
		if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eWoolly hat") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eWinter coat") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eTrousers") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eWinter boots") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§82x §eBlindnessBall") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eKnife") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eGrenade")){
			e.setCancelled(true);
		}
		} catch(NullPointerException ex){
		}
	}
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onInventoryClick3(InventoryClickEvent e){
		try{
		if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Special-Itemslot") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§1") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eProfile: §5" + e.getWhoClicked().getName()) || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§81x §eBlindnessBall")){
			e.setCancelled(true);
		}
		} catch(NullPointerException ex){
		}
	}
}
