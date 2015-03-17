package com.okamipride.player;

import java.util.ArrayList;

public class MenuGroup {
	  private String Name;
	    private ArrayList<MenuChannelItem> ChannelItems;
	    
	    public MenuGroup() {
	    	Name = "";
	    	ChannelItems = new ArrayList<MenuChannelItem>();
	    }
	    public String getName() {
	        return Name;
	    }

	    public void setName(String name) {
	        this.Name = name;
	    }

	    public ArrayList<MenuChannelItem> getItems() {
	        return ChannelItems;
	    }

	    public void setItems(ArrayList<MenuChannelItem> Items) {
	        this.ChannelItems = Items;
	    }

}
