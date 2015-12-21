package tk.shadowcube.snowball;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.connorlinfoot.titleapi.TitleAPI;

public class main extends JavaPlugin{

	@Override
	public void onEnable(){
	
		new Snowball(this);
		new EventListener(this);
		new OtherDeath(this);
		new TokenShop(this);
		new BuyItem(this);
		new KnifeKill(this);
		new GrenadeKill(this);
		new SnowMan(this);
		new UpdateShop(this);
		new ShopTheme(this);
		
		initConfig();
		Player();
		JoinCoord();
		Shop();
		
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        
        if(getServer().getPluginManager().getPlugin("TitleAPI") == null){
        	System.out.println("[SnowballFight] TitleAPI not found!");
        	System.out.println("[SnowballFight] Disable plugin...");
        	Bukkit.getPluginManager().disablePlugin(this);
        }
	}
	
	/*TODO:
	 * 
	 * - Inventar wird gespeichert und wieder ausgegeben
	 * 
	 */
	
		private void initConfig(){
			this.reloadConfig();
			
			this.getConfig().options().header("Snowball Fight! v.1.4");
			this.getConfig().addDefault("Snowball.Spawn1.X", 0);
			this.getConfig().addDefault("Snowball.Spawn1.Y", 0);
			this.getConfig().addDefault("Snowball.Spawn1.Z", 0);
			this.getConfig().addDefault("Snowball.Spawn1.Pitch", 0);
			this.getConfig().addDefault("Snowball.Spawn1.Yaw", 0);
			this.getConfig().addDefault("Snowball.Spawn1.Worldname", "test");
			this.getConfig().addDefault("Snowball.Spawn2.X", 0);
			this.getConfig().addDefault("Snowball.Spawn2.Y", 0);
			this.getConfig().addDefault("Snowball.Spawn2.Z", 0);
			this.getConfig().addDefault("Snowball.Spawn2.Pitch", 0);
			this.getConfig().addDefault("Snowball.Spawn2.Yaw", 0);
			this.getConfig().addDefault("Snowball.Spawn2.Worldname", "test");
			this.getConfig().addDefault("Snowball.EnableBuild", false);
			this.getConfig().addDefault("Snowball.EnableAttackDamage", true);
			this.getConfig().addDefault("Snowball.ChanceToBecomeAToken", 50);
			this.getConfig().addDefault("Snowball.AmountOfTokens", 1);
			this.getConfig().addDefault("Snowball.EnableBungeecord", false);
			this.getConfig().addDefault("Snowball.Fallbackserver", "hub");
			
			this.getConfig().options().copyDefaults(true);
			this.saveConfig();
		}
	
		private void Player(){
		File file = new File("plugins//SnowballFight//players.yml");
		
		if(!file.exists()){
			try{
			file.createNewFile();
			} catch(IOException e){
				
			}
		}
		
		YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
		
		try{
			players.save(file);
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		
		private void JoinCoord(){
			File file = new File("plugins//SnowballFight//joincoords.yml");
			
			if(!file.exists()){
				try{
				file.createNewFile();
				} catch(IOException e){
				}
			}
			
			YamlConfiguration coord = YamlConfiguration.loadConfiguration(file);
			
			try{
				coord.save(file);
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		
		private void Shop(){
			File file = new File("plugins//SnowballFight//shop.yml");
			
			if(!file.exists()){
				try{
				file.createNewFile();
				} catch(IOException e){
					
				}
			}
			
			YamlConfiguration shop = YamlConfiguration.loadConfiguration(file);
			shop.addDefault("Shop.Price.Grenade", 8);
			shop.addDefault("GrenadeDamage", 16.0);
			shop.addDefault("Shop.Price.Knife", 3);
			shop.addDefault("Shop.Price.BlindnessBall", 2);
			shop.addDefault("Shop.Price.WoolyHat", 4);
			shop.addDefault("Shop.Price.WinterCoat", 4);
			shop.addDefault("Shop.Price.Trousers", 4);
			shop.addDefault("Shop.Price.WinterBoots", 4);
			shop.options().copyDefaults(true);
			
			try{
				shop.save(file);
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		int sched3;
		
		public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
			final Player p = (Player)sender;
			
			if(cmd.getName().equalsIgnoreCase("snowball")){
			  if(args.length == 0){
					p.sendMessage("§6>§e> §bSnowball §e<§6< §7You can use:");
					p.sendMessage("§7/snowball setspawn1");
					p.sendMessage("§7/snowball setspawn2");
					p.sendMessage("§7/snowball spawnsnowman");
					p.sendMessage("§7/snowball join");
					p.sendMessage("§7/snowball togglebuild");
					p.sendMessage("§7/snowball reload");
					return true;
			  }
			  
			  if(args.length == 1){
						if(args[0].equalsIgnoreCase("setspawn1")){
							if(p.hasPermission("snowball.admin")){
								Location loc = p.getLocation();
								String w = p.getWorld().getName();
								World world = p.getWorld();
								world.setGameRuleValue("mobGriefing", "false");
								
								this.getConfig().set("Snowball.Spawn1.X", loc.getX());
								this.getConfig().set("Snowball.Spawn1.Y", loc.getY());
								this.getConfig().set("Snowball.Spawn1.Z", loc.getZ());
								this.getConfig().set("Snowball.Spawn1.Pitch", loc.getPitch());
								this.getConfig().set("Snowball.Spawn1.Yaw", loc.getYaw());
								this.getConfig().set("Snowball.Spawn1.Worldname", w);
								this.saveConfig();
								this.reloadConfig();
								p.sendMessage("§6>§e> §bSnowball §e<§6< §7Spawnpoint 1 successfully saved!");
								return true;
							}else{
								 p.sendMessage("§4Sorry! You don't have enough permission!");
								 return true;
							}
						}else{
							if(args[0].equalsIgnoreCase("setspawn2")){
								if(p.hasPermission("snowball.admin")){
									Location loc = p.getLocation();
									String w = p.getWorld().getName();
									
									this.getConfig().set("Snowball.Spawn2.X", loc.getX());
									this.getConfig().set("Snowball.Spawn2.Y", loc.getY());
									this.getConfig().set("Snowball.Spawn2.Z", loc.getZ());
									this.getConfig().set("Snowball.Spawn2.Pitch", loc.getPitch());
									this.getConfig().set("Snowball.Spawn2.Yaw", loc.getYaw());
									this.getConfig().set("Snowball.Spawn2.Worldname", w);
									this.saveConfig();
									this.reloadConfig();
									p.sendMessage("§6>§e> §bSnowball §e<§6< §7Spawnpoint 2 successfully saved!");
									return true;
								}else{
									 p.sendMessage("§4Sorry! You don't have enough permission!");
									 return true;
								}
							}else{
								if(args[0].equalsIgnoreCase("reload")){
									if(p.hasPermission("snowball.admin")){
										p.sendMessage("§6>§e> §bSnowball §e<§6< §7Config successfully reloaded!");
										this.reloadConfig();
										return true;
									}else{
										 p.sendMessage("§4Sorry! You don't have enough permission!");
										 return true;
									}
								}else{
									if(args[0].equalsIgnoreCase("join")){
									if(p.hasPermission("snowball.join")){
									World w = Bukkit.getServer().getWorld(getConfig().getString("Snowball.Spawn1.Worldname"));
									World w2 = p.getWorld();
										
									  if(!(w == w2)){
										Random rand = new Random();
										int spawnpoint = rand.nextInt(2);
										
										Location login = p.getLocation();
										File file = new File("plugins//SnowballFight//joincoords.yml");
										YamlConfiguration coords = YamlConfiguration.loadConfiguration(file);
										coords.set("Players." + p.getName() + ".X", login.getX());
										coords.set("Players." + p.getName() + ".Y", login.getY());
										coords.set("Players." + p.getName() + ".Z", login.getZ());
										coords.set("Players." + p.getName() + ".Pitch", login.getPitch());
										coords.set("Players." + p.getName() + ".Yaw", login.getYaw());
										coords.set("Players." + p.getName() + ".Worldname", login.getWorld().getName());
										try {
											coords.save(file);
										} catch (IOException e) {
											e.printStackTrace();
										}
										
										File file1 = new File("plugins//SnowballFight//players.yml");
										YamlConfiguration players = YamlConfiguration.loadConfiguration(file1);
										if(players.getBoolean("Players." + p.getName() + ".Created") == false){
											players.set("Players." + p.getName() + ".Created", true);
											players.set("Players." + p.getName() + ".Tokens", 0);
											players.set("Players." + p.getName() + ".Kills", 0);
											players.set("Players." + p.getName() + ".Deaths", 0);
											players.set("Players." + p.getName() + ".BlindnessBall", 0);
											players.set("Players." + p.getName() + ".Knife", 0);
											players.set("Players." + p.getName() + ".Grenade", 0);
											try {
												players.save(file1);
											} catch (IOException e) {
												e.printStackTrace();
											}
										}
										p.setHealth(20);
										
										if(spawnpoint == 0){
											final Location loc = p.getLocation();
											
											Bukkit.getScheduler().runTaskLater(this, new Runnable(){

												@Override
												public void run() {
													double x = getConfig().getDouble("Snowball.Spawn1.X");
													double y = getConfig().getDouble("Snowball.Spawn1.Y");
													double z = getConfig().getDouble("Snowball.Spawn1.Z");
													double pitch = getConfig().getDouble("Snowball.Spawn1.Pitch");
													double yaw = getConfig().getDouble("Snowball.Spawn1.Yaw");
													String worldname = getConfig().getString("Snowball.Spawn1.Worldname");
													World world = Bukkit.getWorld(worldname);
													
													loc.setX(x);
													loc.setY(y);
													loc.setZ(z);
													loc.setPitch((float) pitch);
													loc.setYaw((float) yaw);
													loc.setWorld(world);
													p.setGameMode(GameMode.SURVIVAL);
													p.getInventory().clear();
													p.teleport(loc);
													
													for(Player p2: p.getWorld().getPlayers()){
														p2.hidePlayer(p);
													}
												}
											}, 15);
											
											Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable(){
												@Override
												public void run() {
													TitleAPI.sendTitle(p, 0, 20, 0, "§a3",null);
													p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
												}
											}, 20);
											
											Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable(){
												@Override
												public void run() {
													TitleAPI.sendTitle(p, 0, 20, 0, "§a2",null);
													p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
												}
											}, 40);
											
											Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable(){
												@Override
												public void run() {
													TitleAPI.sendTitle(p, 0, 20, 0, "§a1",null);
													p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
												}
											}, 60);
											Bukkit.getScheduler().runTaskLater(this, new Runnable(){
												@Override
												public void run() {
													for(Player p2: p.getWorld().getPlayers()){
														p2.showPlayer(p);
													}
													p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1F, 1F);
												}
											}, 80);
											
											Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable(){

												@Override
												public void run() {
													fullInventory(p);
													
													ItemStack item = new ItemStack(Material.DIAMOND_SPADE);
													ItemMeta meta = item.getItemMeta();
													meta.setDisplayName("§7Snowshovel");
													meta.addEnchant(Enchantment.DURABILITY, 10, true);
													item.setItemMeta(meta);
													p.getInventory().setItem(0, item);
													
													ItemStack item3 = new ItemStack(Material.SUGAR);
													ItemMeta meta3 = item3.getItemMeta();
													meta3.setDisplayName("§7Special-Itemslot");
													item3.setItemMeta(meta3);
													p.getInventory().setItem(1, item3);
													
													ItemStack item2 = new ItemStack(Material.NETHER_STAR);
													ItemMeta meta2 = item2.getItemMeta();
													meta2.setDisplayName("§7Shop");
													meta2.addEnchant(Enchantment.DURABILITY, 1, true);
													item2.setItemMeta(meta2);
													p.getInventory().setItem(4, item2);
													
													ItemStack item31 = new ItemStack(Material.SUGAR);
													ItemMeta meta31 = item31.getItemMeta();
													meta31.setDisplayName("§7Special-Itemslot");
													item31.setItemMeta(meta31);
													p.getInventory().setItem(7, item31);
													
													ItemStack item1 = new ItemStack(Material.SLIME_BALL);
													ItemMeta meta1 = item1.getItemMeta();
													meta1.setDisplayName("§7Leave game");
													item1.setItemMeta(meta1);
													p.getInventory().setItem(8, item1);
												}
											}, 40);
										}else{
											if(spawnpoint == 1){
												final Location loc = p.getLocation();
												
												Bukkit.getScheduler().runTaskLater(this, new Runnable(){

													@Override
													public void run() {
														double x = getConfig().getDouble("Snowball.Spawn2.X");
														double y = getConfig().getDouble("Snowball.Spawn2.Y");
														double z = getConfig().getDouble("Snowball.Spawn2.Z");
														double pitch = getConfig().getDouble("Snowball.Spawn2.Pitch");
														double yaw = getConfig().getDouble("Snowball.Spawn2.Yaw");
														String worldname = getConfig().getString("Snowball.Spawn2.Worldname");
														World world = Bukkit.getWorld(worldname);
														
														loc.setX(x);
														loc.setY(y);
														loc.setZ(z);
														loc.setPitch((float) pitch);
														loc.setYaw((float) yaw);
														loc.setWorld(world);
														p.getInventory().clear();
														p.setGameMode(GameMode.SURVIVAL);
														p.teleport(loc);
														
														for(Player p2: p.getWorld().getPlayers()){
															p2.hidePlayer(p);
														}
													}
												}, 15);
												
												Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable(){
													@Override
													public void run() {
														TitleAPI.sendTitle(p, 0, 20, 0, "§a3",null);
														p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
													}
												}, 20);
												
												Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable(){
													@Override
													public void run() {
														TitleAPI.sendTitle(p, 0, 20, 0, "§a2",null);
														p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
													}
												}, 40);
												
												Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable(){
													@Override
													public void run() {
														TitleAPI.sendTitle(p, 0, 20, 0, "§a1",null);
														p.playSound(p.getLocation(), Sound.NOTE_PLING, 1F, 1F);
													}
												}, 60);
												Bukkit.getScheduler().runTaskLater(this, new Runnable(){
													@Override
													public void run() {
														for(Player p2: p.getWorld().getPlayers()){
															p2.showPlayer(p);
														}
														p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1F, 1F);
													}
												}, 80);
												
												Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable(){

													@Override
													public void run() {
														fullInventory(p);
														
														ItemStack item = new ItemStack(Material.DIAMOND_SPADE);
														ItemMeta meta = item.getItemMeta();
														meta.setDisplayName("§7Snowshovel");
														meta.addEnchant(Enchantment.DURABILITY, 10, true);
														item.setItemMeta(meta);
														p.getInventory().setItem(0, item);
														
														ItemStack item3 = new ItemStack(Material.SUGAR);
														ItemMeta meta3 = item3.getItemMeta();
														meta3.setDisplayName("§7Special-Itemslot");
														item3.setItemMeta(meta3);
														p.getInventory().setItem(1, item3);
														
														ItemStack item2 = new ItemStack(Material.NETHER_STAR);
														ItemMeta meta2 = item2.getItemMeta();
														meta2.setDisplayName("§7Shop");
														meta2.addEnchant(Enchantment.DURABILITY, 1, true);
														item2.setItemMeta(meta2);
														p.getInventory().setItem(4, item2);
														
														ItemStack item31 = new ItemStack(Material.SUGAR);
														ItemMeta meta31 = item31.getItemMeta();
														meta31.setDisplayName("§7Special-Itemslot");
														item31.setItemMeta(meta31);
														p.getInventory().setItem(7, item31);
														
														ItemStack item1 = new ItemStack(Material.SLIME_BALL);
														ItemMeta meta1 = item1.getItemMeta();
														meta1.setDisplayName("§7Leave game");
														item1.setItemMeta(meta1);
														p.getInventory().setItem(8, item1);
													}
												}, 40);
											}
										}
									  }else{
										  p.sendMessage("§6>§e> §bSnowball §e<§6< §7You are already in a game!");
										  return true;
									  }
									  }else{
										  p.sendMessage("§4Sorry! You don't have enough permission!");
										  return true;
									  }
										return true;
									}else{
										if(args[0].equalsIgnoreCase("togglebuild")){
											if(p.hasPermission("snowball.admin")){
												if(this.getConfig().getBoolean("Snowball.EnableBuild") == false){
													this.getConfig().set("Snowball.EnableBuild", true);
													this.saveConfig();
													this.reloadConfig();
													
													p.sendMessage("§6>§e> §bSnowball §e<§6< §7Buildmode successfully §aenabled!");
													return true;
												}else{
													this.getConfig().set("Snowball.EnableBuild", false);
													this.saveConfig();
													this.reloadConfig();
													
													p.sendMessage("§6>§e> §bSnowball §e<§6< §7Buildmode successfully §4disabled!");
													return true;
												}
											}else{
												  p.sendMessage("§4Sorry! You don't have enough permission!");
												  return true;
											  }
										}else{
											if(args[0].equalsIgnoreCase("spawnsnowman")){
												if(p.hasPermission("snowball.admin")){
													Location loc = p.getLocation();
													
													Entity snow = p.getWorld().spawnEntity(loc, EntityType.SNOWMAN);
													snow.setCustomName("Snowball Fight");
													snow.setCustomNameVisible(true);
													
													p.sendMessage("§6>§e> §bSnowball §e<§6< §7Snowman successfully §acreated!");
													return true;
												}
											}else{
												if(args[0].equalsIgnoreCase("killsnowman")){
													if(p.hasPermission("snowball.admin")){
														File file = new File("plugins//SnowballFight//players.yml");
														YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
														players.set("Players." + p.getName() + ".KillSnowMan", true);
														try {
															players.save(file);
														} catch (IOException e) {
															e.printStackTrace();
														}
														p.sendMessage("§6>§e> §bSnowball §e<§6< §7Now attack the snowman you want to remove!");
														return true;
													}
												}else{
													p.sendMessage("§6>§e> §bSnowball §e<§6< §7You can use:");
													p.sendMessage("§7/snowball setspawn1");
													p.sendMessage("§7/snowball setspawn2");
													p.sendMessage("§7/snowball spawnsnowman");
													p.sendMessage("§7/snowball join");
													p.sendMessage("§7/snowball togglebuild");
													p.sendMessage("§7/snowball reload");
													return true;
												}
											}
										}
									}
									}
							}
										
					}
			  }else{
				  p.sendMessage("§6>§e> §bSnowball §e<§6< §7You can use:");
				  p.sendMessage("§7/snowball setspawn1");
				  p.sendMessage("§7/snowball setspawn2");
				  p.sendMessage("§7/snowball spawnsnowman");
				  p.sendMessage("§7/snowball join");
				  p.sendMessage("§7/snowball togglebuild");
				  p.sendMessage("§7/snowball reload");
				  return true;
			  }
			}
			
			return false;
		}
		
		int slot = 8;
		@SuppressWarnings("deprecation")
		public void fullInventory(final Player p){
			sched3 = Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable(){
				@Override
				public void run() {
					if(slot < 35){
						slot++;
						ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName("§1");
						item.setItemMeta(meta);
						p.getInventory().setItem(slot, item);
					}else{
						Bukkit.getScheduler().cancelTask(sched3);
						slot = 8;
					}
				}
			}, 2, 2);
		}
		
		public void onHit(Player p){
			Location loc = p.getLocation();
			loc.setY(loc.getY() + 1);
			
			p.getWorld().playEffect(loc, Effect.STEP_SOUND, Material.REDSTONE_BLOCK, 5);
		}
		
		public void Invisbility(final Player p){
			Bukkit.getScheduler().runTask(this, new Runnable(){
				@Override
				public void run() {
					p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 90, 0));
				}
			});
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
		
		@SuppressWarnings("deprecation")
		public void updateScoreboard(Player p) {
			File file = new File("plugins//SnowballFight//players.yml");
			YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
			int kills = players.getInt("Players." + p.getName() + ".Kills");
			int deaths = players.getInt("Players." + p.getName() + ".Deaths");
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective obj = board.registerNewObjective("aaa", "bbb");
					
			obj.setDisplayName("§L§9[§7Snowball Fight!§9]§R");
			obj.setDisplaySlot(DisplaySlot.SIDEBAR);
					
			Score four = obj.getScore(Bukkit.getOfflinePlayer("§4§LKills§9: "));
			Score three = obj.getScore(Bukkit.getOfflinePlayer("§a>§b " + kills + " §a<"));
			Score two = obj.getScore(Bukkit.getOfflinePlayer("§4§LDeaths§9: "));
			Score one = obj.getScore(Bukkit.getOfflinePlayer("§a>§b " + deaths + " §a<"));
			Score zero = obj.getScore(Bukkit.getOfflinePlayer("§9=============§R"));
					
			four.setScore(4);
			three.setScore(3);
			two.setScore(2);
			one.setScore(1);
			zero.setScore(0);
					
			p.setScoreboard(board);
		}
}
