package freezy.utils;

public class Constants {
    public static final String PURCHASE_ORDER_PREFIX = "PO";
    public static final String PURCHASE_ORDER_ITEM_PREFIX = "POI";
    public static final String SALES_ORDER_PREFIX = "SO";
    public static final String SALES_ORDER_ITEM_PREFIX = "SOI";
    public static final String INVENTORY_ORDER_PREFIX = "I";
    public static final String PRODUCT_ORDER_PREFIX = "PRO";
    public static final String PROJECT_ORDER_PREFIX = "PR";
    public static final String PAYABLE_PREFIX = "PA";
    public static final String RECEIVABLE_PREFIX = "RE";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String INVENTORY_IN = "IN";
    public static final String INVENTORY_OUT = "OUT";
    public static final String INVENTORY_INC = "INC";
    public static final String INVENTORY_DEDUCT = "DEC";
    public static final Integer STOCK_ALERT_QUANTITY = 50;
    public static final String CUSTOMER = "customer";
    public static final String VENDOR = "vendor";
    public static final String PO_BUDGET_STOCK_ERROR = "The Budget or Stock of new Sales Order is more than the ones in Purchase Order";
    public static final String PO_SO_BUDGET_STOCK_ERROR = "The total Budget and Stock of all Sales Orders in this Purchase Order is more than the one in Purchase Order";
}
