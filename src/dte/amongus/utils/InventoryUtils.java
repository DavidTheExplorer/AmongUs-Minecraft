package dte.amongus.utils;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dte.amongus.utils.items.ItemBuilder;
import dte.amongus.utils.java.objectholders.Pair;

public class InventoryUtils
{
	//Container of static methods
	private InventoryUtils(){}
	
	/*
	 * General
	 */
	public static boolean isEmpty(Inventory inv) 
	{
		verifyNotNull(inv, "inventory");

		return itemsStream(inv).count() == 0;
	}
	public static int randomEmptySlot(Inventory inv) 
	{
		long emptySlotsAmount = emptySlotsStream(inv).count();
		
		if(emptySlotsAmount == 0)
			return -1;
		
		return emptySlotsStream(inv)
				.skip(ThreadLocalRandom.current().nextLong(emptySlotsAmount))
				.findFirst()
				.getAsInt();
	}
	
	/*
	 * Walls
	 */
	public static void buildWalls(Inventory inv, Material material) 
	{
		buildWalls(inv, new ItemBuilder(material, ChatColor.BLACK + ".").createCopy());
	}
	public static void buildWalls(Inventory inv, ItemStack item)
	{
		verifyNotNull(inv, "inventory");

		final int invSize = inv.getSize();

		//if the inventory is one line, fill it
		if(invSize == 9)
		{
			fillRow(inv, 0, item);
			return;
		}
		fillColumn(inv, 0, item); //first column
		fillColumn(inv, 8, item); //last column

		//fill the first & last columns *excluding* their corners, because those were already filled by the fillColumn()s.
		fillRange(inv, 1, 8, item); 
		fillRange(inv, invSize-8, invSize-1, item); 
	}
	
	/*
	 * Fill
	 */
	public static void fillWith(Inventory inv, ItemStack item)
	{
		verifyNotNull(inv, "inventory");
		verifyNotNull(inv, "item");

		fillRange(inv, 0, inv.getSize(), item);
	}
	public static void fillWith(Inventory inv, Material material) 
	{
		fillWith(inv, new ItemStack(material));
	}
	public static void fillRow(Inventory inv, int row, ItemStack with) 
	{
		verifyValidRow(inv, row);

		int startIndex = (row * 9);
		int endIndex = (startIndex + 8);

		fillRange(inv, startIndex, endIndex+1, with);
	}
	public static void fillRow(Inventory inv, int row, Material material) 
	{
		fillRow(inv, row, new ItemStack(material));
	}
	public static void fillColumn(Inventory inv, int column, ItemStack with) 
	{
		verifyNotNull(inv, "inventory");
		Validate.inclusiveBetween(0, 8, column, "Illegal Column(Min: 0, Max: 8)");

		fillRange(inv, column, inv.getSize(), 9, with);
	}
	public static void fillColumn(Inventory inv, int column, Material material) 
	{
		fillColumn(inv, column, new ItemStack(material));
	}
	public static void fillRange(Inventory inv, int startInclusive, int endExclusive, ItemStack with)
	{
		for(int i = startInclusive; i < endExclusive; i++)
			inv.setItem(i, with);
	}
	public static void fillRange(Inventory inv, int startInclusive, int endExclusive, int jumpDistance, ItemStack with)
	{
		for(int i = startInclusive; i < endExclusive; i += jumpDistance)
			inv.setItem(i, with);
	}
	public static void fillEmptySlots(Inventory inv, ItemStack with)
	{
		verifyNotNull(inv, "inventory");

		emptySlotsStream(inv).forEach(slot -> inv.setItem(slot, with));
	}

	/*
	 * Slot Searching
	 */
	public static int firstSlotThat(Inventory inv, Predicate<Integer> indexMatcher)
	{
		verifyNotNull(inv, "inventory");
		verifyNotNull(inv, "index matcher");

		for(int i = 0; i < inv.getSize(); i++)
		{
			if(indexMatcher.test(i))
				return i;
		}
		return -1;
	}
	public static int findLastSlot(Inventory inv, Predicate<Integer> indexMatcher) 
	{
		verifyNotNull(inv, "inventory");
		verifyNotNull(inv, "index matcher");

		for(int i = inv.getSize()-1; i >= 0; i--)
		{
			if(indexMatcher.test(i))
				return i;
		}
		return -1;
	}
	public static int firstSlotWhoseItem(Inventory inv, Predicate<ItemStack> slotItemMatcher) 
	{
		verifyNotNull(slotItemMatcher, "items matcher");

		return firstSlotThat(inv, slot -> 
		{
			ItemStack item = inv.getItem(slot);

			return item == null ? false : slotItemMatcher.test(item);
		});
	}
	public static int lastSlotWhoseItem(Inventory inv, Predicate<ItemStack> slotItemMatcher) 
	{
		verifyNotNull(slotItemMatcher, "items matcher");

		return findLastSlot(inv, slot -> 
		{
			ItemStack item = inv.getItem(slot);

			return item == null ? false : slotItemMatcher.test(item);
		});
	}

	/*
	 * Items/Slots Streams
	 */
	public static IntStream slotsStream(Inventory inv) 
	{
		return IntStream.range(0, inv.getSize());
	}
	public static Stream<ItemStack> itemsStream(Inventory inv)
	{
		verifyNotNull(inv, "inventory");

		return Arrays.stream(inv.getContents())
				.filter(Objects::nonNull);
	}
	public static Stream<Pair<Integer, ItemStack>> takenSlotsStream(Inventory inv)
	{
		verifyNotNull(inv, "inventory");

		return IntStream.range(0, inv.getSize())
				.filter(slot -> inv.getItem(slot) != null)
				.mapToObj(takenSlot -> Pair.of(takenSlot, inv.getItem(takenSlot)));
	}
	public static IntStream emptySlotsStream(Inventory inv)
	{
		verifyNotNull(inv, "inventory");

		return slotsStream(inv)
				.filter(slot -> inv.getItem(slot) == null);
	}
	public static IntStream allSlotsThat(Inventory inv, Predicate<ItemStack> slotItemTester) 
	{
		return slotsStream(inv)
				.filter(slot -> slotItemTester.test(inv.getItem(slot)));
	}

	/*
	 * Validation
	 */
	private static void verifyNotNull(Object object, String name) 
	{
		Validate.notNull(object, String.format("The provided %s cannot be null.", name));
	}
	private static void verifyValidRow(Inventory inv, int row) 
	{
		verifyNotNull(inv, "inventory");

		int invRows = (inv.getSize()/9);

		Validate.inclusiveBetween(0, invRows, row, String.format("Row %d is out of range(Min: 1, Max: %d)", row, invRows));
	}
}