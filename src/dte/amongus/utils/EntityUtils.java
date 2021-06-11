package dte.amongus.utils;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import dte.amongus.utils.java.IterableUtils;

public class EntityUtils
{
	//Container of static methods
	private EntityUtils(){}

	public static <E extends LivingEntity> List<E> getNearby(Entity source, Class<E> toFilerEntityClass, int radius)
	{
		return getNearby(source, toFilerEntityClass, radius, radius, radius);
	}
	
	public static <E extends LivingEntity> List<E> getNearby(Entity source, Class<E> toFilerEntityClass, int xRadius, int yRadius, int zRadius)
	{
		List<Entity> nearby = source.getNearbyEntities(xRadius, yRadius, zRadius);
		
		return IterableUtils.getElementsOf(toFilerEntityClass, nearby);
	}
}
