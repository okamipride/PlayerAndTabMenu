package com.okamipride.player;

import com.okamipride.player.R;


public class MenuChannelItem {
	private String Name;
    private int Image;
    
    public MenuChannelItem() {
    	Name = "My Channel";
    	Image = R.drawable.bg_profile;  			
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int Image) {
        this.Image = Image;
    }
}
