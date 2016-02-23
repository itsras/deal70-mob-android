package com.deal70.mapp.deal70.db;

import android.provider.BaseColumns;

/**
 * Created by pcvarma on 14/02/16.
 */
public class Deal70Db {
        public Deal70Db() {

        }

        /* Inner class that defines the table contents */
        public static abstract class DealEntryTable implements BaseColumns {
            public static final String TABLE_NAME = "deal_entry";
            public static final String COLUMN_NAME_NAME = "deal_name";
            public static final String COLUMN_NAME_DESCR = "deal_descr";
            public static final String COLUMN_NAME_URL = "deal_url";
            public static final String COLUMN_NAME_DISCOUNT = "deal_discount";
            public static final String COLUMN_NAME_PRICE = "deal_price";
            public static final String COLUMN_NAME_MRP = "deal_mrp";
        }

        /* Inner class that defines the table contents */
        public static abstract class InterestedCategoryTable implements BaseColumns {
            public static final String TABLE_NAME = "deal_entry";
            public static final String COLUMN_NAME_NAME = "deal_name";
            public static final String COLUMN_NAME_DESCR = "deal_descr";
            public static final String COLUMN_NAME_URL = "deal_url";
            public static final String COLUMN_NAME_DISCOUNT = "deal_discount";
            public static final String COLUMN_NAME_PRICE = "deal_price";
            public static final String COLUMN_NAME_MRP = "deal_mrp";
        }

        /* Inner class that defines the table contents */
        public static abstract class SeachCategoryTable implements BaseColumns {
            public static final String TABLE_NAME = "deal_entry";
            public static final String COLUMN_NAME_NAME = "deal_name";
            public static final String COLUMN_NAME_DESCR = "deal_descr";
            public static final String COLUMN_NAME_URL = "deal_url";
            public static final String COLUMN_NAME_DISCOUNT = "deal_discount";
            public static final String COLUMN_NAME_PRICE = "deal_price";
            public static final String COLUMN_NAME_MRP = "deal_mrp";
        }

        /* Inner class that defines the table contents */
        public static abstract class AuthDataTable implements BaseColumns {
            public static final String TABLE_NAME = "deal_entry";
            public static final String COLUMN_NAME_NAME = "deal_name";
            public static final String COLUMN_NAME_DESCR = "deal_descr";
            public static final String COLUMN_NAME_URL = "deal_url";
            public static final String COLUMN_NAME_DISCOUNT = "deal_discount";
            public static final String COLUMN_NAME_PRICE = "deal_price";
            public static final String COLUMN_NAME_MRP = "deal_mrp";
        }
    }
