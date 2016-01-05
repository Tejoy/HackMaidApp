package com.hack.xapp.fragment.dummy;

import com.hack.xapp.model.Maid;

import java.util.ArrayList;
import java.util.List;

//TODO: remove this class
public class DummyContent {

    public static List<Maid> MAID_ITEMS = new ArrayList<Maid>();

    static {
        // Add 3 sample items.
        addMaid(new Maid("Kamalabai", "1200-1800", 1500, 2000));
        addMaid(new Maid("Mariamma", "1200-1800", 1000, 1200));
/*        addMaid(new Maid("Kamalabai", "1200-1800", "2222222222"));
        addMaid(new Maid("Mariamma", "2000-3000", "3333333333"));
        addMaid(new Maid("Sridevi", "400-450", "4444444444"));
        addMaid(new Maid("Agabai", "1000-1500", "4444444444"));
        addMaid(new Maid("Chintu", "800-950", "4444444444"));
        addMaid(new Maid("Ramu", "1000-1200", "4444444444"));
        addMaid(new Maid("Deepa", "2000-2500", "4444444444"));
        addMaid(new Maid("Chiku", "800-1100", "4444444444"));
        addMaid(new Maid("Minu", "4000-4500", "4444444444"));
        addMaid(new Maid("Pappu", "500-900", "4444444444"));
        addMaid(new Maid("Dimpu", "1100-1700", "4444444444"));*/
    }

    private static void addMaid(Maid item) {
        MAID_ITEMS.add(item);

    }
}
