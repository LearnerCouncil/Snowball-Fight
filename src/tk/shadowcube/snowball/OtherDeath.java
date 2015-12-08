package tk.shadowcube.snowball;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

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
									
									for(Player p2: p.getWorld().getPlayers()){
										p2.hidePlayer((Player) p);
									}
									
									Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){		
										@Override
										public void run() {
											updateScoreboard(killer);
										}
									}, 10);
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
				clearScoreboard(p);
				File file = new File("plugins//SnowballFight//players.yml");
				YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
				
					players.set("Players." + p.getName() + ".Deaths", players.getInt("Players." + p.getName() + ".Deaths") + 1);
					try {
						players.save(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
				
				Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable(){
					@Override
					public void run() {
						TitleAPI.sendTitle(p, 0, 20, 0, "§a3",null);
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
						for(Player p2: p.getWorld().getPlayers()){
							p2.showPlayer(p);
						}
						p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
					}
				}, 80);	
				
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){
					@Override
					public void run() {
						updateScoreboard(p);
					}
				}, 90);
			}
		}, 10);
	}
	
	public void clearScoreboard(Player p) {
        Scoreboard board = null;
        board = p.getScoreboard();
       
        if(board == null)
            return;
       
        Objective obj = board.getObjective("aaa");
        if(obj != null)
            obj.unregister();
        board.clearSlot(DisplaySlot.SIDEBAR);
        p.setScoreboard(board);
    }
	
	public void updateScoreboard(Player p) {
				File file = new File("plugins//SnowballFight//players.yml");
				YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
				int kills = players.getInt("Players." + p.getName() + ".Kills");
				int deaths = players.getInt("Players." + p.getName() + ".Deaths");
				Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
				Objective obj = board.registerNewObjective("aaa", "bbb");
				
				obj.setDisplayName("§L§9[§7Snowball Fight!§9]§R");
				obj.setDisplaySlot(DisplaySlot.SIDEBAR);
				
				@SuppressWarnings("deprecation")
				Score four = obj.getScore(Bukkit.getOfflinePlayer("§4§LKills§9: "));
				@SuppressWarnings("deprecation")
				Score three = obj.getScore(Bukkit.getOfflinePlayer("§a>§b " + kills + " §a<"));
				@SuppressWarnings("deprecation")
				Score two = obj.getScore(Bukkit.getOfflinePlayer("§4§LDeaths§9: "));
				@SuppressWarnings("deprecation")
				Score one = obj.getScore(Bukkit.getOfflinePlayer("§a>§b " + deaths + " §a<"));
				@SuppressWarnings("deprecation")
				Score zero = obj.getScore(Bukkit.getOfflinePlayer("§9=============§R"));
				
				four.setScore(4);
				three.setScore(3);
				two.setScore(2);
				one.setScore(1);
				zero.setScore(0);
				
				p.setScoreboard(board);
			}
}
