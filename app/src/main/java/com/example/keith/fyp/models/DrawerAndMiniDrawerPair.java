package com.example.keith.fyp.models;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.MiniDrawer;

/**
 * Created by Sutrisno on 30/9/2015.
 */
public class DrawerAndMiniDrawerPair {
    private Drawer drawer;
    private MiniDrawer miniDrawer;

    public DrawerAndMiniDrawerPair(Drawer drawer, MiniDrawer miniDrawer) {
        this.drawer = drawer;
        this.miniDrawer = miniDrawer;
    }

    public Drawer getDrawer() {
        return drawer;
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    public MiniDrawer getMiniDrawer() {
        return miniDrawer;
    }

    public void setMiniDrawer(MiniDrawer miniDrawer) {
        this.miniDrawer = miniDrawer;
    }
}
