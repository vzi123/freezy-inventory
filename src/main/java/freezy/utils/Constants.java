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
    public static final String QUOTATION_PREFIX = "QUO";
    public static final String QUOTATION_ITEM_PREFIX = "QUOI";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String INVENTORY_IN = "IN";
    public static final String INVENTORY_OUT = "OUT";
    public static final String INVENTORY_INC = "INC";
    public static final String INVENTORY_DEDUCT = "DEC";
    public static final String PROC_PREFIX = "PROC";
    public static final Integer STOCK_ALERT_QUANTITY = 50;
    public static final String CUSTOMER = "customer";
    public static final String VENDOR = "supplier";
    public static final String PO_BUDGET_STOCK_ERROR = "The Budget or Stock of new Sales Order is more than the ones in Purchase Order";
    public static final String PO_SO_BUDGET_STOCK_ERROR = "The total Budget and Stock of all Sales Orders in this Purchase Order is more than the one in Purchase Order";
    public static final String PO_SO_PRODUCT_ERROR = "The Products in the new Sales Order do not match the products in the Purchase Order";
    public static final String PO_STATE_NOT_ALLOWED = "Sales Order can be created only after the Purchase Order is approved";
    public static final String SEND_SMS = "+91-9740893534";
    public static final String SEND_SMS2 = "+91-8105944553";
}
