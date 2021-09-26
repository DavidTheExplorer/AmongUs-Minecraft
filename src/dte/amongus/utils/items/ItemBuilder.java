package dte.amongus.utils.items;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder
{
	private final ItemStack item;
	private final ItemMeta itemMeta;
	
	public ItemBuilder(Material material)
	{
		this(new ItemStack(material));
	}
	
	public ItemBuilder(LeatherArmorPart part, Color armorColor)
	{
		this(part.getMaterial());

		withItemMeta(LeatherArmorMeta.class, leatherArmorMeta -> leatherArmorMeta.setColor(armorColor));
	}
	
	public ItemBuilder(ItemBuilder otherBuilder) 
	{
		this(otherBuilder.item);
	}
	
	public ItemBuilder(ItemStack other)
	{
		this.item = new ItemStack(other);
		this.itemMeta = other.getItemMeta().clone();
	}


	/*
	 * Static Builders
	 */
	@SuppressWarnings("deprecation")
	public static ItemBuilder buildHeadOf(String playerName)
	{
		return new ItemBuilder(Material.PLAYER_HEAD)
				.withItemMeta(SkullMeta.class, skullMeta -> skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(playerName)));
	}


	/*
	 * Item Methods
	 */
	public ItemBuilder ofType(Material type) 
	{
		this.item.setType(type);
		return this;
	}

	public ItemBuilder amounted(int amount) 
	{
		this.item.setAmount(amount);
		return this;
	}
	
	/*
	 * ItemMeta Methods
	 */
	public ItemBuilder named(String name)
	{
		this.itemMeta.setDisplayName(name);
		return this;
	}
	
	public ItemBuilder unbreakable() 
	{
		this.itemMeta.setUnbreakable(true);
		return this;
	}
	
	public ItemBuilder breakable()
	{
		this.itemMeta.setUnbreakable(false);
		return this;
	}

	public <T extends ItemMeta> ItemBuilder withItemMeta(Class<T> metaClass, Consumer<T> metaActions)
	{
		metaActions.accept(metaClass.cast(this.itemMeta));
		return this;
	}
	
	public ItemBuilder withEnchantment(Enchantment enchantment, int level)
	{
		this.itemMeta.addEnchant(enchantment, level, true);
		return this;
	}

	public ItemBuilder withEnchantments(Map<Enchantment, Integer> enchantments) 
	{
		if(GlowEffect.hasGlow(this.item))
			GlowEffect.deleteGlow(this.item);

		enchantments.forEach(this::withEnchantment);

		return this;
	}
	
	public ItemBuilder withoutItemFlags(ItemFlag... flags)
	{
		this.itemMeta.removeItemFlags(flags);
		return this;
	}

	public ItemBuilder withItemFlags(ItemFlag... flags)
	{
		this.itemMeta.addItemFlags(flags);
		return this;
	}

	public ItemBuilder glowing()
	{
		GlowEffect.addGlow(this.itemMeta, this.item.getType());
		return this;
	}
	
	public ItemBuilder withClickUsageInName(ClickSuffix clickSuffix, ChatColor bracketsColor, ChatColor mouseButtonsColor, ChatColor slashColor)
	{
		String suffix = clickSuffix.toString();

		if(suffix.contains("Left")) 
			suffix = suffix.replace("Left", mouseButtonsColor + "Left");
		
		if(suffix.contains("Right")) 
			suffix = suffix.replace("Right", mouseButtonsColor + "Right");
		
		suffix = suffix.replace("/", slashColor + "/");
		suffix = bracketsColor + "(" + suffix + bracketsColor + ")";
		
		String finalName = String.format("%s %s", 
				this.itemMeta.hasDisplayName() ? this.itemMeta.getDisplayName() + " " : "", 
				suffix);
		
		return named(finalName);
	}
	
	public ItemBuilder withLore(String... lore)
	{
		this.itemMeta.setLore(Arrays.asList(lore));
		return this;
	}
	
	public ItemBuilder withoutLore()
	{
		this.itemMeta.setLore(null);
		return this;
	}
	
	public ItemBuilder addToLore(boolean startFromNewLine, String... lines)
	{
		LinkedList<String> linesList = new LinkedList<>(Arrays.asList(lines));
		
		if(!this.itemMeta.hasLore())
		{
			this.itemMeta.setLore(linesList);
			return this;
		}
		LinkedList<String> currentLore = new LinkedList<>(this.itemMeta.getLore());
		
		if(!startFromNewLine)
			currentLore.add(currentLore.removeLast() + linesList.removeFirst());
		
		currentLore.addAll(linesList);
		
		this.itemMeta.setLore(currentLore);
		return this;
	}

	public ItemBuilder changeLoreLine(int lineIndex, String newLine) 
	{
		if(!this.itemMeta.hasLore()) 
			return this;
		
		List<String> lore = this.itemMeta.getLore();
		
		Validate.inclusiveBetween(0, lore.size()-1, lineIndex, format("index %d is either below 0 or bigger than the current lore's length(%d)", lineIndex, lore.size()));
		
		lore.set(lineIndex, newLine);
		this.itemMeta.setLore(lore);
		
		return this;
	}

	/**
	 * The final method of the chain-building.
	 * 
	 * @return A copy of the chain-built item.
	 */
	public ItemStack createCopy()
	{
		this.item.setItemMeta(this.itemMeta);
		
		return new ItemStack(this.item);
	}
	
	public ItemBuilder withExtendedMeta(Consumer<ItemMeta> itemMetaActions)
	{
		itemMetaActions.accept(this.itemMeta);
		return this;
	}
}