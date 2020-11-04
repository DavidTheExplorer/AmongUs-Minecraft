package mazgani.amongus.utilities;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GlowEffect
{
	//Container of static methods
	private GlowEffect(){}

	/**
	 * Glows the {@code item} "without enchanting it" by injecting a useless enchantment to it(depends on its material), 
	 * combined with {@link ItemFlag.HIDE_ENCHANTS} to hide the trick
	 * (This also allows additional enchants to be added.)
	 * <p>
	 * <b>If you later want to enchant the item, first apply {@code GlowEffect.deleteGlow()} to it.</b>
	 * <p>
	 * 
	 * 	Useless Enchantment For:
	 * 	<ul>
	 * 		<li> Armor Item -> Infinity
	 * 		<li> Anything else -> Protection
	 * 	</ul>
	 * 
	 * @param item The item to glow
	 */
	public static void addGlow(ItemStack item) 
	{
		ItemMeta im = item.getItemMeta();
		addGlow(im, item.getType());
		item.setItemMeta(im);
	}
	public static void addGlow(ItemMeta to, Material forMaterial) 
	{
		to.addEnchant(getUselessEnchantmentFor(forMaterial), 1, true);
		to.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	}
	public static boolean hasGlow(ItemStack item)
	{
		return hasGlow(item.getItemMeta(), item.getType());
	}
	public static boolean hasGlow(ItemMeta im, Material ofMaterial) 
	{
		return im.hasEnchant(getUselessEnchantmentFor(ofMaterial));
	}

	/**
	 * Deletes the the {@code item}'s Glow Effect by deleting the useless enchantment it has, and the given {@link ItemFlag.HIDE_ENCHANTS}.
	 * <p>
	 * * If the item has additional enchantments, this would result a complete reveal of of them.
	 */
	public static void deleteGlow(ItemStack item)
	{
		item.removeEnchantment(getUselessEnchantmentFor(item.getType()));
		ItemMeta im = item.getItemMeta(); //No need to check for the ItemMeta's existence because enchantment(The useless one in this context) lay inside the ItemMeta
		im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(im);
	}
	public static void deleteGlow(ItemMeta im, Material ofMaterial) 
	{
		im.removeEnchant(getUselessEnchantmentFor(ofMaterial));
		im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
	}
	private static Enchantment getUselessEnchantmentFor(Material material) 
	{
		return isArmor(material) ? Enchantment.ARROW_INFINITE : Enchantment.PROTECTION_ENVIRONMENTAL;
	}
	private static boolean isArmor(Material material) 
	{
		String materialName = material.name();

		return materialName.endsWith("HELMET") ||
				materialName.endsWith("CHESTPLATE") ||
				materialName.endsWith("LEGGINGS") ||
				materialName.endsWith("BOOTS");
	}
}