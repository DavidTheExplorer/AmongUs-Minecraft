package mazgani.amongus.utilities;

import static java.lang.String.format;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.google.common.base.Preconditions;

public class ItemBuilder
{
	private final ItemStack item;
	private final ItemMeta im;

	public ItemBuilder(Material material)
	{
		Preconditions.checkArgument(material != null, "The Material to create an instance of ItemBuilder for cannot be null!");

		this.item = new ItemStack(material);
		this.im = Bukkit.getItemFactory().getItemMeta(material);
	}
	public ItemBuilder(Material material, String name) 
	{
		this(material);
		rename(name);
	}
	
	/**
	 * Constructs an ItemBuilder for Leather Armor.
	 * @param name the item's name
	 * @param part the leather armor part
	 * @param armorColor the leather armor color
	 */
	public ItemBuilder(String name, LeatherArmorPart part, Color armorColor)
	{
		this(part.material, name);
		applyItemMeta(LeatherArmorMeta.class, lam -> lam.setColor(armorColor));
	}
	public ItemBuilder(ItemStack other, boolean newItemMeta)
	{
		Preconditions.checkArgument(other != null, "The ItemStack to make an instance of ItemBuilder for cannot be null!");

		this.item = new ItemStack(other);
		this.im = (!newItemMeta ? other.getItemMeta().clone() : Bukkit.getItemFactory().getItemMeta(other.getType()));
	}
	public ItemBuilder(ItemBuilder otherBuilder) 
	{
		this.item = new ItemStack(otherBuilder.item);
		this.im = otherBuilder.im.clone();
	}

	/*
	 * Chain-Building Methods
	 */
	public ItemBuilder rename(String name)
	{
		this.im.setDisplayName(name);
		return this;
	}
	public ItemBuilder amount(int amount) 
	{
		this.item.setAmount(amount);
		return this;
	}
	public ItemBuilder newLore(String... lore)
	{
		this.im.setLore(Arrays.asList(lore));
		return this;
	}
	public <T extends ItemMeta> ItemBuilder applyItemMeta(Class<T> metaClass, Consumer<T> metaActions)
	{
		metaActions.accept(metaClass.cast(this.im));
		return this;
	}

	/*
	 * Lore Modification Methods
	 */
	public ItemBuilder deleteLore()
	{
		this.im.setLore(null);
		return this;
	}
	public ItemBuilder addToLore(boolean goDownALine, String... lines)
	{
		if(!this.im.hasLore())
		{
			this.im.setLore(Arrays.asList(lines));
			return this;
		}
		List<String> currentLore = this.im.getLore();

		if(goDownALine)
		{
			currentLore.addAll(Arrays.asList(lines));
		}
		else 
		{
			String oldLoreLastLine = currentLore.get(currentLore.size()-1);
			currentLore = Arrays.asList(lines);
			currentLore.set(0, oldLoreLastLine + currentLore.get(0));
		}
		this.im.setLore(currentLore);
		return this;
	}
	public ItemBuilder changeLoreLine(int lineIndex, String newLine) 
	{
		if(!this.im.hasLore()) 
			return this;

		if(lineIndex < 0 || lineIndex >= this.im.getLore().size()) 
			throw new IndexOutOfBoundsException(format("index %d is either below 0 or bigger than the current lore's length(%d)", lineIndex, this.im.getLore().size()));

		List<String> updatedLore = this.im.getLore();
		updatedLore.set(lineIndex, newLine);

		this.im.setLore(updatedLore);
		return this;
	}
	public ItemBuilder enchant(Enchantment enchantment, int level)
	{
		this.im.addEnchant(enchantment, level, true);
		return this;
	}
	public ItemBuilder withEnchantments(Map<Enchantment, Integer> enchantments) 
	{
		if(isGlowing())
			GlowEffect.deleteGlow(this.im, this.item.getType());
		
		this.item.addEnchantments(enchantments);
		return this;
	}
	public ItemBuilder itemFlags(ItemFlag... flags)
	{
		this.im.addItemFlags(flags);
		return this;
	}
	public ItemBuilder glowing()
	{
		GlowEffect.addGlow(this.im, this.item.getType());
		return this;
	}
	/**
	 * @param suffixMode Select a usage from the following which will be added to the item's name:
	 * <ul>
	 * 	<li>Left Click</li>
	 * 	<li>Right Click</li>
	 * 	<li>Left Click / Right Click</li>
	 * 	<li>Right Click / Left Click</li>
	 * </ul>
	 * @param mouseButtonsColor the color of the sides
	 * @param slashColor Relevant only when an option that involves both sides is selected - The '/' color that separates the sides
	 * @return This object.
	 */
	public ItemBuilder addClickUsageToName(ClickableMode suffixMode, ChatColor mouseButtonsColor, ChatColor slashColor)
	{
		StringBuilder name = new StringBuilder();

		if(this.im.hasDisplayName())
		{
			name.append(this.im.getDisplayName() + ' ');
		}
		name.append(suffixMode.getSuffix(mouseButtonsColor, slashColor));

		return rename(name.toString());
	}

	/**
	 * The final method of the chain-building.
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
	 * This library covers the <b>popular lore modifications</b>, However for when uncovered changes are needed - I added this method for manual changes, then you need to update them with {@code newLore()}
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


	public enum LeatherArmorPart
	{
		HELMET(Material.LEATHER_HELMET),
		CHESTPLATE(Material.LEATHER_CHESTPLATE),
		LEGGINGS(Material.LEATHER_LEGGINGS),
		BOOTS(Material.LEATHER_BOOTS);

		private final Material material;

		LeatherArmorPart(Material material)
		{
			this.material = material;
		}
		public Material getMaterial() 
		{
			return this.material;
		}
	}
	public enum ClickableMode
	{
		LEFT("Left Click"),
		RIGHT("Right Click"),
		LEFT_FIRST("Left Click / Right Click"),
		RIGHT_FIRST("Right Click / Left Click");

		private final String suffix;

		ClickableMode(String suffix)
		{
			this.suffix = suffix;
		}
		public String getSuffix(ChatColor mouseButtonsColor, ChatColor slashColor)
		{
			String colored = this.suffix;

			if(colored.contains("Left")) 
			{
				colored = colored.replace("Left", mouseButtonsColor + "Left");
			}
			if(colored.contains("Right")) 
			{
				colored = colored.replace("Right", mouseButtonsColor + "Right");
			}
			colored = colored.replace("/", slashColor + "/");
			
			return colored;
		}
	}
}