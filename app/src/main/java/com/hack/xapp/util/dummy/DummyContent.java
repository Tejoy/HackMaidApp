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
        Maid m1 = new Maid("Kamalabai", Util.FILTER_GENDER_FEMALE, "+919999999999", 1500, 2000);
        m1.addService(ServiceItem.cleaning);
        m1.addService(ServiceItem.cook);
        addMaid(m1);
        Maid m2 = new Maid("Mariamma", Util.FILTER_GENDER_FEMALE, "+915555555555", 1000, 1200);
        m2.addService(ServiceItem.cook);
        m2.addService(ServiceItem.cleaning);
        m2.addService(ServiceItem.wash);
        addMaid(m2);

        Maid m3 = new Maid("Maritta", Util.FILTER_GENDER_FEMALE, "+916666666666", 1800, 2200);
        m3.addService(ServiceItem.childcare);
        m3.addService(ServiceItem.cook);
        m3.addService(ServiceItem.cleaning);
        m3.addService(ServiceItem.wash);
        addMaid(m3);

        Maid m4 = new Maid("Kusha", Util.FILTER_GENDER_MALE, "+917777777777", 2500, 3000);
        m4.addService(ServiceItem.childcare);
        m4.addService(ServiceItem.elderlycare);
        m4.addService(ServiceItem.cook);
        m4.addService(ServiceItem.cleaning);
        m4.addService(ServiceItem.wash);
        addMaid(m4);


        Maid m5 = new Maid("Kamalabai", Util.FILTER_GENDER_FEMALE, "+919999999999", 1500, 2000);
        m5.addService(ServiceItem.cleaning);
        m5.addService(ServiceItem.cook);
        addMaid(m5);
        Maid m6 = new Maid("Mariamma", Util.FILTER_GENDER_FEMALE, "+915555555555", 1000, 1200);
        m6.addService(ServiceItem.cook);
        m6.addService(ServiceItem.cleaning);
        m6.addService(ServiceItem.wash);
        addMaid(m6);

        Maid m7 = new Maid("Maritta", Util.FILTER_GENDER_FEMALE, "+916666666666", 1800, 2200);
        m7.addService(ServiceItem.childcare);
        m7.addService(ServiceItem.cook);
        m7.addService(ServiceItem.cleaning);
        m7.addService(ServiceItem.wash);
        addMaid(m7);

        Maid m8 = new Maid("Kusha", Util.FILTER_GENDER_MALE, "+917777777777", 2500, 3000);
        m8.addService(ServiceItem.childcare);
        m8.addService(ServiceItem.elderlycare);
        m8.addService(ServiceItem.cook);
        m8.addService(ServiceItem.cleaning);
        m8.addService(ServiceItem.wash);
        addMaid(m8);

        Maid m9 = new Maid("Maritta", Util.FILTER_GENDER_FEMALE, "+916666666666", 1800, 2200);
        m9.addService(ServiceItem.childcare);
        m9.addService(ServiceItem.cook);
        m9.addService(ServiceItem.cleaning);
        m9.addService(ServiceItem.wash);
        addMaid(m9);

        Maid m10 = new Maid("Kusha", Util.FILTER_GENDER_MALE, "+917777777777", 2500, 3000);
        m10.addService(ServiceItem.childcare);
        m10.addService(ServiceItem.elderlycare);
        m10.addService(ServiceItem.cook);
        m10.addService(ServiceItem.cleaning);
        m10.addService(ServiceItem.wash);
        addMaid(m10);


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
