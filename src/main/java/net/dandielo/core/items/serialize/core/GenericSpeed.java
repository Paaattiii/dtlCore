package net.dandielo.core.items.serialize.core;

import java.util.ArrayList;
import java.util.List;

import net.dandielo.core.bukkit.NBTUtils;
import net.dandielo.core.bukkit.NBTUtils.Modifier;
import net.dandielo.core.items.dItem;
import net.dandielo.core.items.serialize.Attribute;
import net.dandielo.core.items.serialize.ItemAttribute;

import org.bukkit.inventory.ItemStack;

@Attribute(name="GenericSpeed", key="g", sub={"speed"}, priority = 5)
public class GenericSpeed extends ItemAttribute {
	private static String ATTRIBUTE = "generic.movementSpeed";
	private List<Modifier> modifiers;
	
	public GenericSpeed(dItem item, String key, String sub)
	{
		super(item, key, sub);
		modifiers = new ArrayList<Modifier>();
	}

	@Override
	public boolean deserialize(String data)
	{
		String[] mods = data.split(";");
		for ( String mod : mods )
			modifiers.add(new Modifier(mod.split("/")));
		return true;
	}

	@Override
	public String serialize()
	{
		String result = "";
		for ( Modifier mod : modifiers )
			result += ";" + mod.toString();
		return result.substring(1);
	}

	@Override
	public boolean onRefactor(ItemStack item)
	{
		List<Modifier> mods = NBTUtils.getModifiers(item, ATTRIBUTE);
		if ( mods == null || mods.isEmpty() ) return false;
		modifiers.addAll(mods);
		return true;
	}
	
	@Override
	public ItemStack onNativeAssign(ItemStack item, boolean endItem)
	{
		for ( Modifier mod : modifiers )
		{
			item = NBTUtils.setModifier(item, mod.getName(), ATTRIBUTE, mod.getValue(), mod.getOperation());
		}
		return item;
	}
}
