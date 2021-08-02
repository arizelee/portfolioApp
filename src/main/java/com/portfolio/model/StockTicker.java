package com.portfolio.model;

public class StockTicker {

    private String id;
    private String symbolName;
    private int sharesPurchased;
    private long dateOfPurchase;
    private double pricePaid;

    public StockTicker(String id, String symbolName, int sharesPurchased, long dateOfPurchase, double pricePaid) {
        this.id = id;
        this.symbolName = symbolName;
        this.sharesPurchased = sharesPurchased;
        this.dateOfPurchase = dateOfPurchase;
        this.pricePaid = pricePaid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSymbolName() {
        return symbolName;
    }

    public int getSharesPurchased() {
        return sharesPurchased;
    }

    public void setSharesPurchased(int sharesPurchased) {
        this.sharesPurchased = sharesPurchased;
    }

    public long getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(long dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public double getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(double pricePaid) {
        this.pricePaid = pricePaid;
    }
}
