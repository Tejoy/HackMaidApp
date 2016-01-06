package com.hack.xapp.util.dummy;

import com.hack.xapp.model.BookedItem;
import com.hack.xapp.model.Maid;
import com.hack.xapp.model.ServiceItem;
import com.hack.xapp.model.TimeInterval;
import com.hack.xapp.util.Util;

import java.util.ArrayList;
import java.util.List;

//TODO: remove this class
public class DummyContent {

    public static List<Maid> MAID_ITEMS = new ArrayList<Maid>();

    public static List<BookedItem> BOOKED_HISTORY_ITEMS = new ArrayList<BookedItem>();

    static {
        // Add 3 sample items.
        Maid m1 = new Maid("Kamalabai", Util.GENDER_FEMALE, "+919999999999", 1500, 2000);
        m1.addService(ServiceItem.cleaning);
        m1.addService(ServiceItem.cook);
        addMaid(m1);
        Maid m2 = new Maid("Mariamma", Util.GENDER_FEMALE, "+915555555555", 1000, 1200);
        m2.addService(ServiceItem.childcare);
        m2.addService(ServiceItem.elderlycare);
        m2.addService(ServiceItem.cook);
        m2.addService(ServiceItem.cleaning);
        m2.addService(ServiceItem.wash);
        addMaid(m2);

        addHistory(new BookedItem(m1, null, new TimeInterval("7:00", "8:00"), 1400, "Feb", 30));
        addHistory(new BookedItem(m2, null, new TimeInterval("9:00", "10:00"), 1800, "April", 60));

    }

    private static void addMaid(Maid item) {
        MAID_ITEMS.add(item);

    }

    private static void addHistory(BookedItem item) {
        BOOKED_HISTORY_ITEMS.add(item);

    }
}
