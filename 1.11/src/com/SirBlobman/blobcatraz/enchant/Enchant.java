package com.SirBlobman.blobcatraz.enchant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Enchant
{
	HEAL_ZOMBIE("&7Cure");
	
	private final String name;
	Enchant(String name) {this.name = name;}
	public String getName() {return name;}
	public static List<String> valid()
	{
		List<String> valid = new ArrayList<String>();
		for(Enchant e : values()) valid.add(e.name());
		Collections.sort(valid);
		return valid;
	}
}