package me.swipez.axolotlop;

import me.swipez.axolotlop.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class ItemManager {

    public static ItemStack FISH_FOOD = ItemBuilder.of(Material.ROTTEN_FLESH)
            .name(ChatColor.YELLOW+"Fish Food")
            .amount(32)
            .build();

    public static void initRecipes(){
        registerFishFoodRecipe();
    }

    private static void registerFishFoodRecipe(){
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(AxolotlOP.plugin, "fish_food"), FISH_FOOD)
                .shape("LLL","LFL","LLL")
                .setIngredient('L', new RecipeChoice.MaterialChoice(Material.PORKCHOP, Material.BREAD, Material.APPLE, Material.MUSHROOM_STEW, Material.COOKED_PORKCHOP, Material.GOLDEN_APPLE, Material.ENCHANTED_GOLDEN_APPLE, Material.COD,
                        Material.SALMON, Material.TROPICAL_FISH, Material.PUFFERFISH, Material.COOKED_COD, Material.COOKED_SALMON, Material.CAKE, Material.COOKIE, Material.MELON_SLICE, Material.DRIED_KELP, Material.BEEF, Material.COOKED_BEEF, Material.CHICKEN, Material.COOKED_CHICKEN,
                        Material.ROTTEN_FLESH, Material.SPIDER_EYE, Material.CARROT, Material.POTATO, Material.BAKED_POTATO, Material.POISONOUS_POTATO, Material.PUMPKIN_PIE, Material.RABBIT, Material.COOKED_RABBIT, Material.RABBIT_STEW, Material.MUTTON, Material.COOKED_MUTTON,
                        Material.BEETROOT, Material.BEETROOT_SOUP, Material.SWEET_BERRIES))
                .setIngredient('F', new RecipeChoice.MaterialChoice(Material.COD, Material.TROPICAL_FISH, Material.PUFFERFISH));
        Bukkit.addRecipe(shapedRecipe);
    }
}
