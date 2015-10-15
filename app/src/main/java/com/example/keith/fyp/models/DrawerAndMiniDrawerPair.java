package com.example.keith.fyp.models;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.MiniDrawer;

/**
 * DrawerAndMiniDrawerPair is a model to represent a pair of navigation drawer (navigation bar) and its mini drawer.
 * Navigation drawer and mini drawer is from the <a href="https://github.com/mikepenz/MaterialDrawer">MaterialDrawer</a> library.
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class DrawerAndMiniDrawerPair {
    private Drawer drawer;
    private MiniDrawer miniDrawer;

    /**
     * Create a new drawer and mini drawer pair with the specified {@code drawer} and {@code miniDrawer} values
     */
    public DrawerAndMiniDrawerPair(Drawer drawer, MiniDrawer miniDrawer) {
        this.drawer = drawer;
        this.miniDrawer = miniDrawer;
    }

    /**
     * @return navigation drawer (navigation bar)
     */
    public Drawer getDrawer() {
        return drawer;
    }

    /**
     * @param drawer navigation drawer (navigation bar) to set
     */
    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    /**
     * @return mini drawer (mini navigation bar)
     */
    public MiniDrawer getMiniDrawer() {
        return miniDrawer;
    }

    /**
     * @param miniDrawer mini drawer (mini navigation bar)
     */
    public void setMiniDrawer(MiniDrawer miniDrawer) {
        this.miniDrawer = miniDrawer;
    }
}
