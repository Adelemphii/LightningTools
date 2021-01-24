package me.Adelemphii.LightningTools;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(new Events(this), this);
		Bukkit.addRecipe(CraftingRecipes.getRecipe());
	}

	@Override
	public void onDisable() {

	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("ls") || label.equalsIgnoreCase("lightningsword")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("Players only!");
				return true;
			}
			
			Player player = (Player) sender;
			if(player.getInventory().firstEmpty() == -1) {
				// inv is full
				Location loc = player.getLocation();
				World world = player.getWorld();
				
				world.dropItemNaturally(loc, getItem());
				player.sendMessage(ChatColor.GOLD + "The Minecraft legends have dropped a gift near you!");
				return true;
			}
			player.getInventory().addItem(getItem());
			player.sendMessage(ChatColor.GOLD + "The Minecraft legends have given you a gift!");
			return true;
		}
		
		return false;
	}
	
	public ItemStack getItem() {
		
		ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
		ItemMeta meta = sword.getItemMeta();
		
		meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Lightning Sword");
		List<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7(Right Click) &a&oSummon Lightning"));
		lore.add(ChatColor.translateAlternateColorCodes('&', "&7(Left Click) &a&oAttack with lightning or something."));
		meta.setLore(lore);
		
		meta.addEnchant(Enchantment.DAMAGE_ALL, 10, true);
		meta.addEnchant(Enchantment.SWEEPING_EDGE, 5, true);
		meta.setUnbreakable(true);
		
		sword.setItemMeta(meta);
		
		return sword;
		
	}
}
