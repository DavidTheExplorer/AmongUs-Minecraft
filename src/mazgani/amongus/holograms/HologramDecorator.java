package mazgani.amongus.holograms;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import com.gmail.filoghost.holographicdisplays.api.line.HologramLine;
import com.gmail.filoghost.holographicdisplays.api.line.ItemLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;

public class HologramDecorator implements Hologram
{
	protected final Hologram hologram;

	public HologramDecorator(Hologram hologram) 
	{
		this.hologram = hologram;
	}
	
	@Override
	public ItemLine appendItemLine(ItemStack arg0) 
	{
		return this.hologram.appendItemLine(arg0);
	}

	@Override
	public TextLine appendTextLine(String arg0) 
	{
		return this.hologram.appendTextLine(arg0);
	}

	@Override
	public void clearLines() 
	{
		this.hologram.clearLines();
	}

	@Override
	public void delete() 
	{
		this.hologram.delete();
	}

	@Override
	public long getCreationTimestamp() 
	{
		return this.hologram.getCreationTimestamp();
	}

	@Override
	public double getHeight() 
	{
		return this.hologram.getHeight();
	}

	@Override
	public HologramLine getLine(int arg0) 
	{
		return this.hologram.getLine(arg0);
	}

	@Override
	public Location getLocation() 
	{
		return this.hologram.getLocation();
	}

	@Override
	public VisibilityManager getVisibilityManager() 
	{
		return this.hologram.getVisibilityManager();
	}

	@Override
	public World getWorld() 
	{
		return this.hologram.getWorld();
	}

	@Override
	public double getX() 
	{
		return this.hologram.getX();
	}

	@Override
	public double getY() 
	{
		return this.hologram.getY();
	}

	@Override
	public double getZ() 
	{
		return this.hologram.getZ();
	}

	@Override
	public ItemLine insertItemLine(int arg0, ItemStack arg1) 
	{
		return this.hologram.insertItemLine(arg0, arg1);
	}

	@Override
	public TextLine insertTextLine(int arg0, String arg1) 
	{
		return this.hologram.insertTextLine(arg0, arg1);
	}

	@Override
	public boolean isAllowPlaceholders() 
	{
		return this.hologram.isAllowPlaceholders();
	}

	@Override
	public boolean isDeleted() 
	{
		return this.hologram.isDeleted();
	}

	@Override
	public void removeLine(int arg0) 
	{
		this.hologram.removeLine(arg0);
	}

	@Override
	public void setAllowPlaceholders(boolean arg0) 
	{
		this.hologram.setAllowPlaceholders(arg0);
	}

	@Override
	public int size()
	{
		return this.hologram.size();
	}

	@Override
	public void teleport(Location arg0) 
	{
		this.hologram.teleport(arg0);
	}

	@Override
	public void teleport(World arg0, double arg1, double arg2, double arg3) 
	{
		this.hologram.teleport(arg0, arg1, arg2, arg3);
	}
}