package mazgani.amongus.utilities;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Validate;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import mazgani.amongus.utilities.objectholders.Pair;

public class InventoryUtilities
{
	//Container of static methods
	private InventoryUtilities(){}

	public static void fillWith(Inventory inv, Material material)
	{
		verifyInvExists(inv);

		fillRange(inv, 0, inv.getSize()-1, new ItemStack(material));
	}
	public static void fillRow(Inventory inv, int row, ItemStack with) 
	{
		verifyInvExists(inv);
		verifyValidRow(inv, row);

		final int startIndex = ((row-1) * 9);
		final int endIndex = (startIndex + 8);
		
		fillRange(inv, startIndex, endIndex, with);
	}
	public static void fillColumn(Inventory inv, int column, ItemStack with) 
	{
		verifyInvExists(inv);
		Validate.inclusiveBetween(1, 9, column, "Illegal Column(Min: 1, Max: 9)");

		final int startIndex = (column-1);

		for(int i = startIndex; i < inv.getSize(); i += 9)
		{
			inv.setItem(i, with);
		}
	}
	public static void buildWalls(Inventory inv, Material material)
	{
		verifyInvExists(inv);

		final ItemStack window = new ItemStack(material);
		final int invSize = inv.getSize();

		if(invSize == 9)
		{
			fillRow(inv, 1, window);
			return;
		}
		fillColumn(inv, 1, window); //first column
		fillColumn(inv, 9, window); //last column

		//fill the First and Last columns EXCLUDING their corners, because those were already filled by the fillColumn() methods
		fillRange(inv, 1, 7, window); 
		fillRange(inv, invSize-7, invSize-2, window); 
	}
	public static int findFirstSlot(Inventory inv, Predicate<ItemStack> itemMatcher) 
	{
		verifyInvExists(inv);

		for(int i = 0; i < inv.getSize(); i++)
		{
			if(itemMatcher.test(inv.getItem(i))) 
			{
				return i;
			}
		}
		return -1;
	}
	public static int findLastSlot(Inventory inv, Predicate<ItemStack> itemMatcher) 
	{
		verifyInvExists(inv);

		for(int i = inv.getSize()-1; i >= 0; i--)
		{
			if(itemMatcher.test(inv.getItem(i))) 
			{
				return i;
			}
		}
		return -1;
	}
	public static int[] allSlotsThat(Inventory inv, Predicate<ItemStack> itemInSlotTest) 
	{
		return IntStream.range(0, inv.getSize())
				.filter(slot -> itemInSlotTest.test(inv.getItem(slot)))
				.toArray();
	}
	public static Stream<ItemStack> itemsStream(Inventory inv)
	{
		verifyInvExists(inv);
		
		return Arrays.stream(inv.getContents())
				.filter(Objects::nonNull);
	}
	public static Stream<Pair<Integer, ItemStack>> takenSlotsStream(Inventory inv)
	{
		verifyInvExists(inv);
		
		return IntStream.range(0, inv.getSize())
				.filter(slot -> inv.getItem(slot) != null)
				.mapToObj(takenSlot -> Pair.of(takenSlot, inv.getItem(takenSlot)));
	}
	public static IntStream emptySlotsStream(Inventory inv)
	{
		verifyInvExists(inv);
		
		return IntStream.range(0, inv.getSize())
				.filter(slot -> inv.getItem(slot) == null);
	}
	public static boolean isEmpty(Inventory inv) 
	{
		verifyInvExists(inv);

		return itemsStream(inv).count() == 0;
	}
	public static void fillEmptySlots(Inventory inv, ItemStack with)
	{
		verifyInvExists(inv);
		
		emptySlotsStream(inv).forEach(slot -> inv.setItem(slot, with));
	}
	
	private static void fillRange(Inventory inv, int startInclusive, int endInclusive, ItemStack with)
	{
		for(int i = startInclusive; i <= endInclusive; i++)
		{
			inv.setItem(i, with);
		}
	}
	private static void verifyInvExists(Inventory inv) 
	{
		Validate.notNull(inv, "The inventory cannot be null.");
	}
	private static void verifyValidRow(Inventory inv, int row) 
	{
		final int invRows = (inv.getSize()/9);
		final String errorMessage = String.format("Row %d is out of range(Min: 1, Max: %d)", row, invRows);

		Validate.inclusiveBetween(1, invRows, row, errorMessage);
	}
}