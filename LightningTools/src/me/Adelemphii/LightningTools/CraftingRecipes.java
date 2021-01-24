package me.Adelemphii.LightningTools;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CraftingRecipes {
	static Main main;
	public CraftingRecipes(Main instance) {
		main = instance;
	}
	
	public static ShapedRecipe getRecipe() {
		
		ItemStack item = main.getItem();
		NamespacedKey key = new NamespacedKey(main, "lightning_sword");
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		
		recipe.shape("BDB",
				"BDB",
				"ODO");
		
		recipe.setIngredient('B', Material.BLAZE_POWDER);
		recipe.setIngredient('D', Material.DIAMOND_BLOCK);
		recipe.setIngredient('O', Material.OBSIDIAN);
		
		return recipe;
	}
}
