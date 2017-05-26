package org.handup.backgrounds.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by truongpq on 26/05/2017.
 */

public class Utils {
    public static void slideFragment(int container, Fragment fragment, FragmentManager fragmentManager) {
        fragmentManager.beginTransaction().replace(container, fragment).addToBackStack(null).commit();
    }
}
