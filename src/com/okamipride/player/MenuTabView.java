package com.okamipride.player;

import java.util.ArrayList;

import com.okamipride.player.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

public class MenuTabView extends LinearLayout {
	
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
	
	public MenuTabView(Context context) {
		super(context);
		mContext = context;
		initView();
		initData();
		//tabHost.setOnKeyListener(mOnKeyListener);
		this.setOnKeyListener(mOnKeyListener);
	}
	
	public MenuTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context; 
		initView();
		initData();
		this.setOnKeyListener(mOnKeyListener);
		//tabHost.setOnKeyListener(mOnKeyListener);
	}

	private void initView() {
		Log.d(TAG, "initView");
		inflate(getContext(), R.layout.view_menu_tab, this);
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
		tabHost.setOnKeyListener(mOnKeyListener);
		final TabWidget tabWidget = tabHost.getTabWidget();
		final FrameLayout tabContent = tabHost.getTabContentView();
		// Get the original tab textviews and remove them from the viewgroup.
		TextView[] originalTextViews = new TextView[tabWidget.getTabCount()];
		
		for (int index = 0; index < tabWidget.getTabCount(); index++) {
			originalTextViews[index] = (TextView) tabWidget.getChildTabViewAt(index);
		}
		tabWidget.removeAllViews();
		
		// Ensure that all tab content childs are not visible at startup.
		for (int index = 0; index < tabContent.getChildCount(); index++) {
			tabContent.getChildAt(index).setVisibility(View.GONE);
		}
		
		// Create the tabspec based on the textview childs in the xml file.
		// Or create simple tabspec instances in any other way...
		for (int index = 0; index < originalTextViews.length; index++) {
			final TextView tabWidgetTextView = originalTextViews[index];
			final View tabContentView = tabContent.getChildAt(index);
			TabSpec tabSpec = tabHost.newTabSpec((String) tabWidgetTextView.getTag());
			TabContentFactory fact = new TabContentFactory(){

				@Override
				public View createTabContent(String tag) {
					return tabContentView;
				}
			};
			
			tabSpec.setContent(fact);
		if (tabWidgetTextView.getBackground() == null) {
			tabSpec.setIndicator(tabWidgetTextView.getText());
		} else {
			tabSpec.setIndicator(tabWidgetTextView.getText(), tabWidgetTextView.getBackground());
		}
			tabHost.addTab(tabSpec);
		} 
		
	}
	
	private void initData() {
		Log.d(TAG, "initData");
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
		Log.d(TAG, "initFocus");
		Button bt1 = (Button)findViewById(R.id.button2);
		bt1.requestFocus(View.FOCUS_DOWN);
	}
		
	OnKeyListener mOnKeyListener = new OnKeyListener() {
		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			Log.d(TAG, "tab on key event = " + event.toString());
			if (event.getAction() == KeyEvent.ACTION_DOWN && 
							keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
				int curTabIndex = tabHost.getCurrentTab();
				final TabWidget tabWidget = tabHost.getTabWidget();
				int count = tabWidget.getTabCount();
				
				if (curTabIndex == 0) {
					curTabIndex = count -1;
				} else {
					curTabIndex = curTabIndex - 1;
				} 
				Log.d(TAG, "tab KEYCODE_DPAD_LEFT curTabIndex = " + Integer.toString(curTabIndex));					
				tabHost.setCurrentTab(curTabIndex);				
				return true;
			} else if  (event.getAction() == KeyEvent.ACTION_DOWN && 
					keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
				int curTabIndex = tabHost.getCurrentTab();
				final TabWidget tabWidget = tabHost.getTabWidget();
				int count = tabWidget.getTabCount();
				
				if (curTabIndex == count -1) {
					curTabIndex = 0;
				} else {
					curTabIndex = curTabIndex + 1;
				} 
				Log.d(TAG, "tab KEYCODE_DPAD_LEFT curTabIndex = " + Integer.toString(curTabIndex));		
				tabHost.setCurrentTab(curTabIndex);				
				return true;
			}		
			return false;
		}		
	};
}
