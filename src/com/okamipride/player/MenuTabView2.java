package com.okamipride.player;

import java.util.ArrayList;

import com.okamipride.player.R;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class MenuTabView2 extends TabHost {
	
	final static String TAG = "MenuTabView";
	Context mContext;
	TabHost tabHost; 
	//TextView txtv_name;
	//ExpandableListView exListView;
	private ArrayList<MenuGroup> menuListItems;

	   String[] values1 = new String[] { "Android1",
	           "iPhone",
	           "WindowsMobile",
	           "Blackberry",
	           "WebOS",
	           "Ubuntu",
	           "Windows7",
	           "Max OS X",
	           "Windows81",
	           "Deberra"
	   };
	   String[] values2 = new String[] { "Android2",
	           "iPhone2",
	           "WindowsMobile2",
	           "Blackberry2",
	           "WebOS2",
	           "Ubuntu2",
	           "Windows72",
	           "Max OS X2",
	           "Windows82",
	           "Deberra2"
	   };
	   
	   String[] values3 = new String[] { "Android3",
	           "iPhone3",
	           "WindowsMobile3",
	           "Blackberry3",
	           "WebOS3",
	           "Ubuntu3",
	           "Windows73",
	           "Max OS X3"
	   };
	//private MenuListAdapter menuAdapter;
	
	public MenuTabView2(Context context) {
		super(context);
		mContext = context;
		initView();
		initData();		
	}
	
	public MenuTabView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
		initData();
	}

	private void initView() {
		inflate(getContext(), R.layout.view_menu_tab2, this);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		Resources ressources = getResources();
		
		 
		TabSpec spec1 = tabHost.newTabSpec("spec1").setIndicator("",ressources.getDrawable(R.drawable.tab_select1)).setContent(R.id.lsv1);
		TabSpec spec2 = tabHost.newTabSpec("spec2").setIndicator("",ressources.getDrawable(R.drawable.tab_select2)).setContent(R.id.lsv2);
		TabSpec spec3 = tabHost.newTabSpec("spec3").setIndicator("",ressources.getDrawable(R.drawable.tab_select3)).setContent(R.id.lsv3);
		
		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		tabHost.addTab(spec3);
		tabHost.setCurrentTab(0);
	
	}
	
	private void initData() {
		//menuAdapter = new MenuListAdapter(menuListItems);	
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1, android.R.id.text1, values1);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1, android.R.id.text1, values2);
		ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1, android.R.id.text1, values3);
		ListView v1 = (ListView)findViewById(R.id.lsv1);
		ListView v2 = (ListView)findViewById(R.id.lsv2);
		ListView v3 = (ListView)findViewById(R.id.lsv3);
		v1.setAdapter(adapter1);
		v2.setAdapter(adapter2);
		v3.setAdapter(adapter3);
	}
	
	public void initFocus() {
		//Button bt1 = (Button)findViewById(R.id.button2);
		//bt1.requestFocus(View.FOCUS_DOWN);
	}
		
	OnKeyListener mOnKeyListener = new OnKeyListener() {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			Log.d(TAG, "viewL onkey = " + event.toString());
			return false;
		}		
	};
}
