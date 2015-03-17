package com.okamipride.player;

import java.util.ArrayList;

import com.okamipride.player.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuView extends RelativeLayout {
	
	final static String TAG = "MenuView";
	TextView txtv_name;
	ExpandableListView exListView;
	private ArrayList<MenuGroup> menuListItems;
	private MenuListAdapter menuAdapter;
	
	public MenuView(Context context) {
		super(context);
		initView();
		initData();
		
	}
	
	public MenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		initData();
	}

	private void initView() {
		inflate(getContext(), R.layout.view_menu, this);
        this.txtv_name = (TextView)findViewById(R.id.txtv_name);
        
        exListView = (ExpandableListView) findViewById(R.id.explistview);
		exListView.setOnKeyListener(mOnKeyListener);
		
 
	}
	
	private void initData() {
		menuListItems = makeDummyData();
		menuAdapter = new MenuListAdapter(menuListItems);
		exListView.setAdapter(menuAdapter);
	}
	
	public void initFocus() {
		Button bt1 = (Button)findViewById(R.id.button2);
		bt1.requestFocus(View.FOCUS_DOWN);
	}
	
	
	OnKeyListener mOnKeyListener = new OnKeyListener() {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			Log.d(TAG, "viewL onkey = " + event.toString());
			return false;
		}		
	};

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
