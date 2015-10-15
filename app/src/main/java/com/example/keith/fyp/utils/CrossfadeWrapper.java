package com.example.keith.fyp.utils;

import com.example.keith.fyp.views.fragments.CreatePatientInfoFormFragment;
import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;

/**
 * A wrapper class to be able to use mini drawer from <a href="https://github.com/mikepenz/MaterialDrawer">Material Drawer</a> library.
 *
 * @author      Sutrisno Suryajaya Dwi Putra
 */
public class CrossfadeWrapper implements ICrossfader {
    private Crossfader mCrossfader;

    public CrossfadeWrapper(Crossfader crossfader) {
        this.mCrossfader = crossfader;
    }

    @Override
    public void crossfade() {
        mCrossfader.crossFade();
    }

    @Override
    public boolean isCrossfaded() {
        return mCrossfader.isCrossFaded();
    }
}
