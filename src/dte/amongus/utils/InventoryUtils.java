package dte.amongus.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dte.amongus.utils.items.ItemBuilder;
import dte.amongus.utils.java.RandomUtils;
import dte.amongus.utils.java.objectholders.Pair;

//Some methods don't include or do partial parameters validation, because they depend on other methods that do the validation.
public class InventoryUtils
{
	//Container of static methods
	private InventoryUtils(){}
	
	/*
	 * General
	 */
	public static boolean isEmpty(Inventory inventory) 
	{
		return itemsStream(inventory).count() == 0;
	}
	
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
	 * @return The corresponding inventory slot.
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
	 * The opposite method to {@link #toSlot(int, int)} - Breaks the provided {@code inventory slot} into its line and its index in that line.
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
	 * @return The provided slot, broken to its line and its index in that line.
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
	

	/*
	 * Walls
	 */
	public static void buildWalls(Inventory inventory, ItemStack item)
	{
		final int invSize = inventory.getSize();

		//if the inventory is one line, fill it
		if(invSize == 9)
		{
			fillRow(inventory, 0, item);
			return;
		}
		fillColumn(inventory, 0, item); //first column
		fillColumn(inventory, 8, item); //last column

		//fill the first & last columns *excluding* their corners, because those were already filled by the fillColumn()s.
		fillRange(inventory, 1, 8, item); 
		fillRange(inventory, invSize-8, invSize-1, item); 
	}
	

	/*
	 * Fill
	 */
	public static void fill(Inventory inventory, ItemStack item)
	{
		fillRange(inventory, 0, inventory.getSize(), item);
	}
	public static void fillRow(Inventory inventory, int row, ItemStack with) 
	{
		verifyValidRow(inventory, row);
		
		int startIndex = (row * 9);
		int endIndex = (startIndex + 8);

		fillRange(inventory, startIndex, endIndex+1, with);
	}
	public static void fillColumn(Inventory inventory, int column, ItemStack with) 
	{
		Validate.inclusiveBetween(0, 8, column, "Illegal Column(Min: 0, Max: 8)");

		fillRange(inventory, column, inventory.getSize(), 9, with);
	}
	public static void fillRange(Inventory inventory, int startInclusive, int endExclusive, ItemStack with)
	{
		fillRange(inventory, startInclusive, endExclusive, 1, with);
	}
	public static void fillRange(Inventory inventory, int startInclusive, int endExclusive, int jumpDistance, ItemStack with)
	{
		verifyNotNull(inventory, "inventory");
		
		for(int i = startInclusive; i < endExclusive; i += jumpDistance)
			inventory.setItem(i, with);
	}
	public static void fillEmptySlots(Inventory inventory, ItemStack with)
	{
		verifyNotNull(with, "item");
		
		emptySlotsStream(inventory).forEach(slot -> inventory.setItem(slot, with));
	}
	

	/*
	 * Slot Searching
	 */
	public static int firstSlotThat(Inventory inventory, Predicate<Integer> indexMatcher)
	{
		verifyNotNull(inventory, "inventory");
		verifyNotNull(indexMatcher, "index matcher");

		for(int i = 0; i < inventory.getSize(); i++)
		{
			if(indexMatcher.test(i))
				return i;
		}
		return -1;
	}
	public static int findLastSlot(Inventory inventory, Predicate<Integer> indexMatcher) 
	{
		verifyNotNull(inventory, "inventory");
		verifyNotNull(indexMatcher, "index matcher");

		for(int i = inventory.getSize()-1; i >= 0; i--)
		{
			if(indexMatcher.test(i)) 
				return i;
		}
		return -1;
	}
	public static int firstSlotWhoseItem(Inventory inventory, Predicate<ItemStack> itemMatcher) 
	{
		return firstSlotThat(inventory, slot -> 
		{
			ItemStack item = inventory.getItem(slot);
			
			if(item == null)
				return false;
			
			return item == null ? false : itemMatcher.test(item);
		});
	}
	public static int lastSlotWhoseItem(Inventory inventory, Predicate<ItemStack> itemMatcher) 
	{
		verifyNotNull(itemMatcher, "items matcher");
		
		return findLastSlot(inventory, slot -> 
		{
			ItemStack item = inventory.getItem(slot);
			
			return item == null ? false : itemMatcher.test(item);
		});
	}
	public static int randomEmptySlot(Inventory inventory) 
	{
		Integer[] emptySlots = ArrayUtils.toObject(emptySlotsStream(inventory).toArray());
		
		return emptySlots.length == 0 ? -1 : RandomUtils.randomElement(emptySlots);
	}
	

	/*
	 * Items/Slots Streams
	 */
	public static IntStream slotsStream(Inventory inventory)
	{
		verifyNotNull(inventory, "inventory");
		
		return IntStream.range(0, inventory.getSize());
	}
	public static IntStream emptySlotsStream(Inventory inventory)
	{
		verifyNotNull(inventory, "inventory");
		
		return slotsStream(inventory)
			.filter(slot -> inventory.getItem(slot) == null);
	}
	public static IntStream allSlotsThat(Inventory inventory, Predicate<ItemStack> slotItemTester) 
	{
		verifyNotNull(inventory, "inventory");
		verifyNotNull(slotItemTester, "slot item tester");
		
		return takenSlotsStream(inventory)
				.filter(itemData -> slotItemTester.test(itemData.getSecond()))
				.mapToInt(Pair::getFirst);
	}
	public static Stream<ItemStack> itemsStream(Inventory inventory)
	{
		verifyNotNull(inventory, "inventory");
		
		return Arrays.stream(inventory.getContents())
				.filter(Objects::nonNull);
	}
	public static Stream<Pair<Integer, ItemStack>> takenSlotsStream(Inventory inventory)
	{
		verifyNotNull(inventory, "inventory");
		
		return slotsStream(inventory)
				.filter(slot -> inventory.getItem(slot) != null)
				.mapToObj(takenSlot -> Pair.of(takenSlot, inventory.getItem(takenSlot)));
	}
	

	/*
	 * Validation
	 */
	private static void verifyNotNull(Object object, String name) 
	{
		Validate.notNull(object, String.format("The provided %s cannot be null.", name));
	}
	private static void verifyValidRow(Inventory inventory, int row)
	{
		verifyNotNull(inventory, "inventory");

		final int rowsAmount = (inventory.getSize()/9);

		Validate.inclusiveBetween(0, rowsAmount, row, String.format("Row %d is out of range(Min: 1, Max: %d)", row, rowsAmount));
	}
	

	/*
	 * Item Factories
	 */
	public static ItemStack createDummyItem(Material material) 
	{
		return new ItemBuilder(material, ChatColor.BLACK + ".").createCopy();
	}
}