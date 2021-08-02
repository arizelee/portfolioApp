package com.portfolio.model;

import com.portfolio.ItemNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Portfolio {

    private String id;
    private String name;
    private double profit;
    private List<StockTicker> tickers;

    public Portfolio(String id, String name) {
        this.id = id;
        this.name = name;
        tickers = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public double getProfit() {
        return profit;
    }
    public void setProfit(double profit) {
        this.profit = profit;
    }

    public String getName() {
        return name;
    }

    public List<StockTicker> getTickers() {
        return tickers;
    }

    public void addTicker(StockTicker ticker) {
        tickers.add(ticker);
    }

    public void deleteTicker(String id) {
        tickers.removeIf(ticker -> ticker.getId().equalsIgnoreCase(id));
    }
}
