package dte.amongus.utils;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.Pair;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dte.amongus.utils.items.ItemBuilder;

//README: Some methods do partial parameters validation, because they validate only what the methods they depend on didn't validate.
public class InventoryUtils
{
	//Container of static methods
	private InventoryUtils(){}
	
	
	/*
	 * General
	 */

	/**
	 * The opposite method to {@link #toLineAndIndex(int)} - Converts the provided {@code line} and {@code index in that line} to the corresponding inventory slot.
	 * <p>
	 * Input - Output Examples:
	 * <ul>
	 * 	<li>{@code toSlot(line = 1, index = 1)} returns 0</li>
	 *  <li>{@code toSlot(line = 2, index = 1)} returns 9</li>
	 *  <li>{@code toSlot(line = 1, index = 5)} returns 4</li>
	 * </ul>
	 * 
	 * @param line The line index(starting by 1).
	 * @param index The index in the given line(starting by 1).
	 * @return The corresponding inventory slot to the provided line and index in that line.
	 * @see #toLineAndIndex(int)
	 */
	public static int toSlot(int line, int index)
	{
		int slot = (index-1);
		
		if(line >= 1)
			slot += (9 * (line-1));
		
		return slot;
	}

	/**
	 * The opposite method to {@link #toSlot(int, int)} - Breaks the provided {@code inventory slot} to its line and its index in that line.
	 * <p>
	 * Input - Output Examples:
	 * <ul>
	 * 	<li>{@code toLineAndIndex(slot = 0)} returns [1, 1]</li>
	 * <li>{@code toLineAndIndex(slot = 1)} returns [1, 2]</li>
	 * <li>{@code toLineAndIndex(slot = 3)} returns [1, 3]</li>
	 * <li>{@code toLineAndIndex(slot = 9)} returns [2, 1]</li>
	 * </ul>
	 * 
	 * @param slot The slot to break to its line and its index in that line.
	 * @return The line and index in that line, of the provided inventory slot.
	 * @see #toSlot(int, int)
	 */
	public static int[] toLineAndIndex(int slot) 
	{
		int line, index;

		//if the index is in the first line
		if(slot <= 8)
		{
			line = 1;
			index = (slot+1);
		}
		else
		{
			line = ((slot/9) +1);

			//if the index is first slot in the line it belongs to
			if(slot % 9 == 0)
				index = 1;
			else
				index = ((slot % 9) +1);
		}
		return new int[]{line, index};
	}

	public static boolean isEmpty(Inventory inventory) 
	{
		return itemsStream(inventory, true).count() == 0;
	}
	
	public static int remove(Inventory inventory, ItemStack item) 
	{
		Map<Integer, ItemStack> similarItems = dataStream(inventory)
				.filter(itemData -> itemData.getValue().isSimilar(item))
				.collect(toMap(Entry::getKey, Entry::getValue));
		
		int amountLeft = item.getAmount();
		
		for(Map.Entry<Integer, ItemStack> entry : similarItems.entrySet())
		{
			int slot = entry.getKey();
			ItemStack similarItem = entry.getValue();
			
			int newAmount = similarItem.getAmount() - amountLeft;

			if(newAmount > 0)
			{
				similarItem.setAmount(newAmount);
				break;
			}
			else
			{
				inventory.clear(slot);
				amountLeft = -newAmount;

				if(amountLeft == 0) 
					break;
			}
		}
		return amountLeft;
	}


	/*
	 * Fill certain areas(lines, columns, etc)
	 */
	public static void fill(Inventory inventory, ItemStack with)
	{
		fillRange(inventory, 0, inventory.getSize(), with);
	}
	public static void fillRow(Inventory inventory, int row, ItemStack with)
	{
		validateRow(inventory, row);

		int startIndex = (row * 9);

		fillRange(inventory, startIndex, (startIndex + 9), with);
	}
	public static void fillColumn(Inventory inventory, int column, ItemStack with)
	{
		validateColumn(column);
		
		fillRange(inventory, column, inventory.getSize(), 9, with);
	}
	public static void fillEmptySlots(Inventory inventory, ItemStack with)
	{
		Validate.notNull(with);

		emptySlotsStream(inventory).forEach(slot -> inventory.setItem(slot, with));
	}
	public static void fillRange(Inventory inventory, int startInclusive, int endExclusive, ItemStack with)
	{
		fillRange(inventory, startInclusive, endExclusive, 1, with);
	}
	public static void fillRange(Inventory inventory, int startInclusive, int endExclusive, int jumpDistance, ItemStack with)
	{
		Validate.notNull(inventory);
		Validate.notNull(with);

		for(int i = startInclusive; i < endExclusive; i += jumpDistance)
			inventory.setItem(i, with);
	}


	/*
	 * Replace items by "Regex"(Predicate) and other properties
	 */
	public static void replace(Inventory inventory, Predicate<ItemStack> itemTester, UnaryOperator<ItemStack> itemReplacer) 
	{
		allSlotsThat(inventory, itemTester).forEach(i -> 
		{
			ItemStack newItem = itemReplacer.apply(inventory.getItem(i));

			inventory.setItem(i, newItem);
		});
	}
	public static void replace(Inventory inventory, Predicate<ItemStack> itemTester, ItemStack newItem)
	{
		replace(inventory, itemTester, item -> newItem);
	}
	public static void replace(Inventory inventory, Predicate<ItemStack> itemTester, Material newMaterial)
	{
		replace(inventory, itemTester, item -> new ItemStack(newMaterial));
	}
	public static void replace(Inventory inventory, Material target, Material newMaterial)
	{
		replace(inventory, target, new ItemStack(newMaterial));
	}
	public static void replace(Inventory inventory, Material target, ItemStack newItem)
	{
		replace(inventory, item -> item.getType() == target, newItem);
	}
	

	/*
	 * Slot Searching
	 */
	public static int firstSlotThat(Inventory inventory, Predicate<ItemStack> itemMatcher)
	{
		Validate.notNull(itemMatcher);
		
		return allSlotsThat(inventory, itemMatcher)
				.min()
				.orElse(-1);
	}
	public static int lastSlotThat(Inventory inventory, Predicate<ItemStack> itemMatcher) 
	{
		Validate.notNull(itemMatcher);
		
		return allSlotsThat(inventory, itemMatcher)
				.max()
				.orElse(-1);
	}
	public static int randomEmptySlot(Inventory inventory) 
	{
		Integer[] emptySlots = emptySlotsStream(inventory).boxed().toArray(Integer[]::new);

		return emptySlots.length == 0 ? -1 : RandomUtils.randomElement(emptySlots);
	}
	public static int randomSlotThat(Inventory inventory, Predicate<ItemStack> itemMatcher) 
	{
		Integer[] matchingSlots = allSlotsThat(inventory, itemMatcher).boxed().toArray(Integer[]::new);

		return matchingSlots.length == 0 ? -1 : RandomUtils.randomElement(matchingSlots);
	}
	
	
	/*
	 * Items/Slots Streams
	 */
	public static IntStream slotsStream(Inventory inventory)
	{
		Validate.notNull(inventory);

		return IntStream.range(0, inventory.getSize());
	}
	public static Stream<ItemStack> itemsStream(Inventory inventory, boolean includeSpecialSlots)
	{
		Validate.notNull(inventory);

		return Arrays.stream(includeSpecialSlots ? inventory.getContents() : inventory.getStorageContents())
				.filter(Objects::nonNull);
	}
	public static IntStream takenSlotsStream(Inventory inventory) 
	{
		return slotsStream(inventory)
				.filter(slot -> inventory.getItem(slot) != null);
	}
	public static IntStream emptySlotsStream(Inventory inventory)
	{
		return slotsStream(inventory)
				.filter(slot -> inventory.getItem(slot) == null);
	}
	public static IntStream allSlotsThat(Inventory inventory, Predicate<ItemStack> itemMatcher) 
	{
		Validate.notNull(itemMatcher);

		return dataStream(inventory)
				.filter(itemData -> itemMatcher.test(itemData.getValue()))
				.mapToInt(Pair::getKey);
	}
	public static Stream<Pair<Integer, ItemStack>> dataStream(Inventory inventory)
	{
		return takenSlotsStream(inventory)
				.mapToObj(slot -> Pair.of(slot, inventory.getItem(slot)));
	}

	
	/*
	 * Decoration of certain areas(walls, etc)
	 */
	public static void buildWalls(Inventory inventory, ItemStack with)
	{
		Validate.notNull(inventory);

		int size = inventory.getSize();

		//if the inventory is one line, fill it
		if(size == 9)
		{
			fillRow(inventory, 0, with);
			return;
		}
		fillColumn(inventory, 0, with); //first column
		fillColumn(inventory, 8, with); //last column

		//fill the first & last columns *excluding* their corners, because they were already filled
		fillRange(inventory, 1, 8, with);
		fillRange(inventory, size-8, size-1, with);
	}
	

	/*
	 * Validation
	 */
	private static void validateRow(Inventory inventory, int row)
	{
		Validate.notNull(inventory);

		int rowsAmount = (inventory.getSize()/9)-1;

		Validate.inclusiveBetween(0, rowsAmount, row, String.format("Row %d is out of range! (Min: 0, Max: %d)", row, rowsAmount));
	}
	private static void validateColumn(int column) 
	{
		Validate.inclusiveBetween(0, 8, column, "Column %d is out of range! (Min: 0, Max: 8)", column);
	}


	/*
	 * Item Factories
	 */
	public static ItemStack createWall(Material material) 
	{
		return new ItemBuilder(material)
				.named(ChatColor.BLACK + ".")
				.createCopy();
	}
}