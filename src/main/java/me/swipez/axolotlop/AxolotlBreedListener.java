package me.swipez.axolotlop;

import me.swipez.axolotlop.utils.RandomLootPicker;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Random;

public class AxolotlBreedListener implements Listener {

    boolean isInteracting = false;
    Random random = new Random();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (AxolotlOP.isStarted){
            AxolotlOP.playerBreedStats.put(player.getUniqueId(), new BreedStats(new int[]{1,10,25,50,100}));
        }
    }

    @EventHandler
    public void onPlayerUseFishBucket(PlayerInteractAtEntityEvent event){
        if (!AxolotlOP.isStarted){
            return;
        }
        if (isInteracting){
            return;
        }
        isInteracting = true;
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.getType().equals(Material.TROPICAL_FISH_BUCKET)){
            if (entity instanceof Axolotl){
                if (hasFishFood(player)){
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            ItemStack newHeld = player.getInventory().getItemInMainHand();
                            if (newHeld.getType().equals(Material.WATER_BUCKET)){
                                newHeld.setType(Material.TROPICAL_FISH_BUCKET);
                                removeFishFood(player);
                            }
                        }
                    }.runTaskLater(AxolotlOP.plugin, 1);
                }
            }
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                isInteracting = false;
            }
        }.runTaskLater(AxolotlOP.plugin, 1);
    }

    @EventHandler
    public void onMobBreed(EntityBreedEvent event){
        if (!AxolotlOP.isStarted){
            return;
        }
        LivingEntity mother = event.getMother();
        LivingEntity breeder = event.getBreeder();

        if (breeder == null){
            return;
        }
        if (!(breeder instanceof Player)){
            return;
        }

        Player player = (Player) breeder;
        BreedStats playerStats = AxolotlOP.playerBreedStats.get(player.getUniqueId());

        double axolotlsBred = playerStats.getBredCount();

        if (mother.getType().equals(EntityType.AXOLOTL)){

            double opChance = random.nextDouble();
            double playerChance = axolotlsBred/100;

            int preLevel = playerStats.getLevel();
            RandomLootPicker randomLootPicker = new RandomLootPicker(AxolotlOP.plugin);

            if (playerChance > opChance){
                int randomRolls = random.nextInt(1)+1;
                for (int i = 0; i < randomRolls; i++){
                    ItemStack itemStack = randomLootPicker.randomItemStack();
                    checkForApply(itemStack, AxolotlOP.plugin);
                    mother.getWorld().dropItemNaturally(mother.getLocation(), itemStack);
                }
                player.sendMessage(ChatColor.LIGHT_PURPLE+"An axolotl has dropped an OP item!");
            }
            playerStats.addBredCount(1);
            if (preLevel != playerStats.getLevel()){
                int randomRolls = random.nextInt(1)+1;
                for (int i = 0; i < randomRolls; i++){
                    ItemStack itemStack = randomLootPicker.randomItemStack();
                    checkForApply(itemStack, AxolotlOP.plugin);
                    mother.getWorld().dropItemNaturally(mother.getLocation(), itemStack);
                }
                player.sendMessage(ChatColor.LIGHT_PURPLE+"An axolotl has dropped an OP item!");
            }
        }
    }

    private void removeFishFood(Player player){
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++){
            if (inventory.getItem(i) != null){
                ItemStack itemStack = inventory.getItem(i);
                if (itemStack.isSimilar(ItemManager.FISH_FOOD)){
                    itemStack.setAmount(itemStack.getAmount()-1);
                    break;
                }
            }
        }
    }

    private boolean hasFishFood(Player player){
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getSize(); i++){
            if (inventory.getItem(i) != null){
                ItemStack itemStack = inventory.getItem(i);
                if (itemStack.isSimilar(ItemManager.FISH_FOOD)){
                    return true;
                }
            }
        }
        return false;
    }

    private void checkForApply(ItemStack ritem, AxolotlOP plugin){
        List<String> enchants = plugin.getConfig().getStringList("enchants_list");
        List<String> peffects = plugin.getConfig().getStringList("potion_effects");

        if (ritem.getType() == Material.ENCHANTED_BOOK) {
            ItemMeta meta = ritem.getItemMeta();
            EnchantmentStorageMeta emeta = (EnchantmentStorageMeta) meta;
            int mine = 0;
            int maxe = enchants.size() - 1;
            double rench = Math.random() * (maxe - mine + 1) + mine;
            emeta.addStoredEnchant(Enchantment.getByKey(NamespacedKey.minecraft(enchants.get((int) rench).toLowerCase())), 10, true);
            ritem.setItemMeta(emeta);
        }
        if (ritem.getType() == Material.POTION) {
            ItemMeta meta = ritem.getItemMeta();
            PotionMeta pmeta = (PotionMeta) meta;
            int mine = 0;
            int maxe = peffects.size() - 1;
            double rench = Math.random() * (maxe - mine + 1) + mine;
            pmeta.addCustomEffect(new PotionEffect((PotionEffectType.getByName(peffects.get((int) rench).toUpperCase())), 1200, 2), true);
            pmeta.setColor(Color.fromRGB(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
            pmeta.setDisplayName(ChatColor.WHITE+"OP Potion");
            ritem.setItemMeta(pmeta);
        }
    }
}
