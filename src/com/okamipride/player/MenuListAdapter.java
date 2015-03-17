package com.okamipride.player;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.okamipride.player.R;



public class MenuListAdapter extends BaseExpandableListAdapter {
	private Context context;
	private ArrayList<MenuGroup> menu_group;

	
	class GroupHolder {
		 TextView title;
		 }
		 
	class ChildHolder {
		 TextView title;
	}
	
	public MenuListAdapter(ArrayList<MenuGroup> group) {
		context = context;
		menu_group = group;
		//setListEvent();
		
	}
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return menu_group.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		  ArrayList<MenuChannelItem> chList = menu_group.get(groupPosition).getItems();
	      return chList.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return menu_group.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<MenuChannelItem> chList = menu_group.get(groupPosition).getItems();
        return chList.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		  MenuGroup group = (MenuGroup) getGroup(groupPosition);
	        if (convertView == null) {
	        	 LayoutInflater inf = LayoutInflater.from(parent.getContext());
	            convertView = inf.inflate(R.layout.menu_group_item, null);
	        }
	        TextView tv = (TextView) convertView.findViewById(R.id.ch_gp_name);
	        tv.setText(group.getName());
	        return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		MenuChannelItem item = (MenuChannelItem) getChild(groupPosition, childPosition);
        if (convertView == null) {
        	 LayoutInflater inf = LayoutInflater.from(parent.getContext());
            convertView = inf.inflate(R.layout.menu_channel_item, null);
        }
        TextView chName = (TextView) convertView.findViewById(R.id.menu_channel_itme_name);
        chName.setText(item.getName());
        
        TextView chId = (TextView) convertView.findViewById(R.id.menu_channel_item_number);
        chId.setText(Integer.toString(groupPosition) + Integer.toString(childPosition));
        
        return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}
