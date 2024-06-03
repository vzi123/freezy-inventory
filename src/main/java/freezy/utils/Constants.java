package freezy.utils;

import java.util.Map;

import static java.util.Map.entry;

public class Constants {
    public static final String PURCHASE_ORDER_PREFIX = "PO";
    public static final String USER_PREFIX = "US";
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

    public static final Map DASHBOARD_WIDGETS = Map.ofEntries(
                                                    entry("stock_alerts", "getAllStockAlerts"),
                                                    entry("stock_available", "getStock"),
                                                    entry("inventory_log", "getInventoryLog"),
                                                    entry("open_quotations", "getOpenQuotations")
                                                );

    public static final String stock_alerts = "getAllStockAlerts";
    public static final String stock_available = "getStock";
    public static final String inventory_log = "getInventoryLog";
    public static final String open_quotations = "getOpenQuotations";
    public static final String payables = "getAllPayables";
    public static final String procurements = "getAllProcurements";
    public static final String receivables = "getAllReceivables";
    public static final String fulfillments = "getAllFulfillments";

    public static final String QUOTATION_CREATED_STRING = "Hello %s, a new quotation for amount Rs. %s /- is raised for customer %s. Team Freazy.";
    public static final String QUOTATION_APPROVED_STRING = "Hello %s, Quotation %s is now approved. A Purchase Order %s is auto created. Team Freazy.";
    public static final String SALES_ORDER_DELIVERED = "Hello %s, A Sales Order for PO %s is delivered. A receivable for amount %s is raised. Team Freazy.";

    public static final Map PO_STATUSES_CUSTOMER = Map.ofEntries(
            entry("6", "CLOSED"),
            entry("5", "FULL_PAYMENT_RECEIVED"),
            entry("4", "FULLY_DELIVERED"),
            entry("3", "PARTIAL_PAYMENT_RECEIVED"),
            entry("2", "PARTIALLY_DELIVERED"),
            entry("1", "APPROVED"),
            entry("0", "DRAFT")
    );

    public static final Map PO_STATUSES_SUPPLIER = Map.ofEntries(
            entry("6", "CLOSED"),
            entry("5", "FULL_PAYMENT_DONE"),
            entry("4", "FULLY_RECEIVED"),
            entry("3", "PARTIAL_PAYMENT_DONE"),
            entry("2", "PARTIALLY_RECEIVED"),
            entry("1", "GIVEN"),
            entry("0", "DRAFT")
    );

    public static final Map QUOTATION_STATUSES = Map.ofEntries(
            entry("4", "CONVERTED"),
            entry("3", "CANCELLED"),
            entry("2", "APPROVED"),
            entry("1", "SENT"),
            entry("0", "DRAFT")
    );

    /*
    RAISED,
    SHARED,

    TO_BE_DELIVERED,
    DELIVERED,
    RECEIVED,
    TO_BE_RECEIVED,

    TO_BE_PAID,
    PAID,
    TO_RECEIVE_PAYMENT,
    PAYMENT_RECEIVED,

    CLOSED
     */

    public static final Map SO_STATUSES_CUSTOMER = Map.ofEntries(
            entry("5", "CLOSED"),
            entry("4", "PAYMENT_DONE"),
            entry("3", "PAYMENT_TO_BE_DONE"),
            entry("2", "STOCK_DELIVERED"),
            entry("1", "STOCK_TO_BE_DELIVERED"),
            entry("0", "RAISED")
    );

    public static final Map SO_STATUSES_SUPPLIER = Map.ofEntries(
            entry("5", "CLOSED"),
            entry("4", "PAYMENT_RECEIVED"),
            entry("3", "PAYMENT_TO_BE_RECEIVED"),
            entry("2", "STOCK_RECEIVED"),
            entry("1", "STOCK_TO_BE_RECEIVED"),
            entry("0", "SHARED")
    );

    public static final String FILE_LOCATION = "classpath:pdfs/";
}
