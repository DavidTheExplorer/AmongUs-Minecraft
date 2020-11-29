package mazgani.amongus.utilities.items;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.google.common.collect.Lists;

public class ItemBuilder
{
	private final ItemStack item;
	private final ItemMeta im;

	public ItemBuilder(Material material)
	{
		Validate.notNull(material, "The Material to create an ItemBuilder from cannot be null!");
		
		this.item = new ItemStack(material);
		this.im = Bukkit.getItemFactory().getItemMeta(material);
	}
	public ItemBuilder(Material material, String name) 
	{
		this(material);

		named(name);
	}

	/**
	 * Constructs a builder for Leather Armor.
	 * 
	 * @param name The item's name.
	 * @param part The leather armor part.
	 * @param armorColor The leather armor color.
	 */
	public ItemBuilder(LeatherArmorPart part, Color armorColor)
	{
		this(part.getMaterial());

		withItemMeta(LeatherArmorMeta.class, lam -> lam.setColor(armorColor));
	}
	
	/**
	 * Constructs a builder whose item has all the properties(name, lore, amount, etc) that the provided {@code item} has.
	 * 
	 * @param other The item to copy.
	 * @param useItemMeta Whether the provided {@code item}'s ItemMeta's copy will be used, or a new one would be created(depends on the item's type).
	 */
	public ItemBuilder(ItemStack other, boolean useItemMeta)
	{
		Validate.notNull(other, "The ItemStack to make an instance of ItemBuilder from cannot be null!");
		
		this.item = new ItemStack(other);
		this.im = (useItemMeta ? other.getItemMeta().clone() : Bukkit.getItemFactory().getItemMeta(other.getType()));
	}
	
	/*
	 * General
	 */
	public ItemBuilder amountOf(int amount) 
	{
		this.item.setAmount(amount);
		return this;
	}
	public <T extends ItemMeta> ItemBuilder withItemMeta(Class<T> metaClass, Consumer<T> metaActions)
	{
		metaActions.accept(metaClass.cast(this.im));
		return this;
	}

	/*
	 * Lore Modification
	 */
	public ItemBuilder withLore(String... lore)
	{
		this.im.setLore(Arrays.asList(lore));
		return this;
	}
	public ItemBuilder withoutLore()
	{
		this.im.setLore(null);
		return this;
	}
	public ItemBuilder addToLore(boolean startFromNewLine, String... lines)
	{
		List<String> currentLore = this.im.getLore();
		List<String> linesList = Lists.newArrayList(lines);

		if(startFromNewLine)
		{
			currentLore.addAll(linesList);
		}
		else
		{
			String lastCurrentLine = currentLore.get(currentLore.size()-1);
			String firstNewLine = linesList.get(0);
			
			currentLore.set(currentLore.size()-1, lastCurrentLine + firstNewLine);
			linesList.remove(0);
			currentLore.addAll(linesList);
		}
		this.im.setLore(currentLore);
		return this;
	}
	public ItemBuilder changeLoreLine(int lineIndex, String newLine) 
	{
		if(lineIndex < 0 || lineIndex >= this.im.getLore().size())
			throw new IndexOutOfBoundsException(format("index %d is either below 0 or bigger than the current lore's length(%d)", lineIndex, this.im.getLore().size()));

		if(!this.im.hasLore())
			return this;

		List<String> updatedLore = this.im.getLore();
		updatedLore.set(lineIndex, newLine);

		this.im.setLore(updatedLore);
		return this;
	}
	
	/*
	 * Enchantments
	 */
	public ItemBuilder enchantedWith(Enchantment enchantment, int level)
	{
		if(isGlowing())
			GlowEffect.deleteGlow(this.im, this.item.getType());

		this.im.addEnchant(enchantment, level, true);
		return this;
	}
	public ItemBuilder enchantedWith(Map<Enchantment, Integer> enchantments) 
	{
		if(isGlowing())
			GlowEffect.deleteGlow(this.im, this.item.getType());

		this.item.addEnchantments(enchantments);
		return this;
	}
	public ItemBuilder withFlags(ItemFlag... flags)
	{
		this.im.addItemFlags(flags);
		return this;
	}
	public ItemBuilder glowing()
	{
		GlowEffect.addGlow(this.im, this.item.getType());
		return this;
	}
	
	/*
	 * Name
	 */
	public ItemBuilder named(String name)
	{
		this.im.setDisplayName(name);
		return this;
	}
	
	/**
	 * Adds the a special suffix to the item's name that guides how this item should be used.
	 * 
	 * @param suffix The suffix that will appear after the item's name, can be one of the following:
	 * <ul>
	 * 	<li>Left Click</li>
	 * 	<li>Right Click</li>
	 * 	<li>Left Click / Right Click</li>
	 * 	<li>Right Click / Left Click</li>
	 * </ul>
	 * @param bracketsColor The color of the brackets.
	 * @param mouseButtonsColor The color of the sides.
	 * @param slashColor The <b>/</b> color that separates the 2 mouse buttons names(used only when 2 buttons suffix is chosen).
	 * @return This object.
	 */
	public ItemBuilder withClickableSuffix(ClickSuffix suffix, ChatColor bracketsColor, ChatColor mouseButtonsColor, ChatColor slashColor)
	{
		StringBuilder newName;

		if(this.im.hasDisplayName())
			newName = new StringBuilder(this.im.getDisplayName() + ' ');
		else 
			newName = new StringBuilder();

		newName.append(suffix.createSuffix(bracketsColor, mouseButtonsColor, slashColor));

		return named(newName.toString());
	}

	/**
	 * The final method of the chain-building, returns a copy of the built item.
	 * 
	 * @return A copy of the chain-built item.
	 */
	public ItemStack createCopy()
	{
		if(!this.item.hasItemMeta()) 
		{
			this.item.setItemMeta(this.im);
		}
		return new ItemStack(this.item);
	}

	/**
	 * This library covers the <i>popular lore modifications requirements</i>, 
	 * However for when uncovered changes are needed I added this method for manual changes, which you can update with {@code newLore()}.
	 * 
	 * @return The lore of this ItemBuilder's item, or null if no lore was set.
	 */
	public List<String> getLore()
	{
		return this.im.hasLore() ? this.im.getLore() : null;
	}
	private boolean isGlowing() 
	{
		return GlowEffect.hasGlow(this.im, this.item.getType());
	}
}