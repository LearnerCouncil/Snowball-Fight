package tk.shadowcube.snowball;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UpdateShop implements Listener{

	private main plugin;
	public UpdateShop(main p){
		this.plugin = p;
		
		p.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onShopUpdate(InventoryClickEvent e){
	try{	
		if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Buy: §eWoolly hat") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Buy: §eWinter coat") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Buy: §eTrousers") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Buy: §eWinter boots") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Buy: §eBlindnessBall") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Buy: §eKnife") || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8Buy: §eGrenade")){
			Inventory inv = e.getInventory();
			Player p = (Player) e.getWhoClicked();
			File file2 = new File("plugins//SnowballFight//shop.yml");
			YamlConfiguration shop = YamlConfiguration.loadConfiguration(file2);
			File file = new File("plugins//SnowballFight//players.yml");
			YamlConfiguration players = YamlConfiguration.loadConfiguration(file);
			
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
		}
	}catch(NullPointerException ex){
	}
	}
}
