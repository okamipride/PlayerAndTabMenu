package com.okamipride.player;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class MenuListView extends ExpandableListView {
	
	private ArrayList<MenuGroup> menuListItems;
	private MenuListAdapter menuAdapter;
	
	
	public MenuListView(Context context) {
		super(context);
		menuListItems = makeDummyData();
		menuAdapter = new MenuListAdapter(menuListItems);
        this.setAdapter(menuAdapter);
		
	}
	
	public MenuListView(Context context, AttributeSet attrs)
	 {
		super(context, attrs);
		menuListItems = makeDummyData();
		menuAdapter = new MenuListAdapter(menuListItems);
        this.setAdapter(menuAdapter);
	 }
	private  ArrayList<MenuGroup> makeDummyData() {
		 String group_names[] = { "Official Channael", "Subscribe Channel", "Created Channel", "Paid Channel"};
		 String item_name_a[] = {"my movie", "Titanic","Thailand legend", "Youku theather", "MTV","Oscar"};
		 String item_name_b[] = {"your movie", "Space ship", "Love Letter", "Todou theather", "Music Award", "Canny award"};
		 
		 ArrayList<MenuGroup> group = new ArrayList<MenuGroup>();
		 for (int i=0; i < group_names.length; i ++) {
			 MenuGroup gp_item = new MenuGroup();
			 
			 if (i%2 == 0) {
				 ArrayList<MenuChannelItem> chitems = new ArrayList<MenuChannelItem>();
				 for (int j=0; j < item_name_a.length ; j++) {
					 MenuChannelItem item = new MenuChannelItem();
					 item.setName(item_name_a[j]);
					 chitems.add(item);
				 }
				 gp_item.setItems(chitems);
			 } else {
				 ArrayList<MenuChannelItem> chitems = new ArrayList<MenuChannelItem>();
				 for (int j=0; j < item_name_b.length ; j++) {
					 MenuChannelItem item = new MenuChannelItem();
					 item.setName(item_name_b[j]);
					 chitems.add(item);
				 }
				 gp_item.setItems(chitems);
			 }
			 
			 gp_item.setName(group_names[i]);
			 group.add(gp_item);
		 }
		return group;
	}

}
