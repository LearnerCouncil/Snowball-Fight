package tk.shadowcube.snowball;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

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
							
							for(Player p2: p.getWorld().getPlayers()){
								p2.hidePlayer(target);
							}
							Bukkit.getScheduler().runTaskLater(plugin, new Runnable(){		
								@Override
								public void run() {
									updateScoreboard(p);
								}
							}, 10);
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
