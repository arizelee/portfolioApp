package com.portfolio.service;

import com.portfolio.ItemNotFoundException;
import com.portfolio.model.Portfolio;
import com.portfolio.model.StockTicker;
import org.springframework.stereotype.Service;
import org.springframework.web.server.session.InMemoryWebSessionStore;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PortfolioService {

    List<Portfolio> portfolios;
    private static final String PORTFOLIO_DOES_NOT_EXIST = "Portfolio %s does not exist";
    private static final String STOCK_TICKER_DOES_NOT_EXIST = "Stock ticker %s does not exist in portfolio: %s";

    public PortfolioService() {
        portfolios = new LinkedList<>();
        portfolios.add(new Portfolio("it", "test"));
        portfolios.add(new Portfolio("it2", "test"));
    }
    public void createPortfolio(String name) {
        Portfolio portfolio = new Portfolio(generateId(name), name);
        portfolios.add(portfolio);
    }

    public void deletePortfolio(String id) {
        portfolios.removeIf(portfolio -> portfolio.getId().equalsIgnoreCase(id));
    }

    public List<Portfolio> getPortfolios() {
        return portfolios;
    }

    public void addTicker(String portfolioId, StockTicker ticker) {
        int portfolioIndex = findPortfolioIndex(portfolioId);
        Portfolio portfolio = portfolios.get(portfolioIndex);
        ticker.setId(generateId(ticker.getSymbolName()));

        portfolio.addTicker(ticker);
        generatePortfolioValue(portfolio);
        portfolios.add(portfolioIndex, portfolio);
    }

    public void deleteTicker(String portfolioId, String id) {
        int portfolioIndex = findPortfolioIndex(portfolioId);
        Portfolio portfolio = portfolios.get(portfolioIndex);
        portfolio.deleteTicker(id);

        generatePortfolioValue(portfolio);
        portfolios.add(portfolioIndex, portfolio);
    }

    public void editTicker(String portfolioId, StockTicker ticker) {
        int portfolioIndex = findPortfolioIndex(portfolioId);
        Portfolio portfolio = portfolios.get(portfolioIndex);
        applyChangesToTicker(ticker, portfolio);

        generatePortfolioValue(portfolio);
        portfolios.add(portfolioIndex, portfolio);
    }

    
    private int findPortfolioIndex(String portfolioId) {
        Optional<Portfolio> portfolioOptional = portfolios.stream().filter(p -> p.getId().equalsIgnoreCase(portfolioId))
                .findFirst();
        if(portfolioOptional.isPresent()) {
            Portfolio portfolio = portfolioOptional.get();
            return portfolios.indexOf(portfolio);
        } else {
            throw new ItemNotFoundException(String.format(PORTFOLIO_DOES_NOT_EXIST, portfolioId));
        }
    }

    public void applyChangesToTicker(StockTicker newTicker, Portfolio portfolio) {
        StockTicker currentTicker = null;

        Optional<StockTicker> stockTickerOptional = portfolio.getTickers().stream()
                .filter(stockTicker -> stockTicker.getId().equalsIgnoreCase(newTicker.getId()))
                .findFirst();

        if (stockTickerOptional.isPresent()) {
            currentTicker = stockTickerOptional.get();
        } else {
            throw new ItemNotFoundException(String.format(STOCK_TICKER_DOES_NOT_EXIST, portfolio.getId(), portfolio.getName()));
        }

        if(newTicker.getPricePaid() > 0 ) {
            currentTicker.setPricePaid(newTicker.getPricePaid());
        }

        if(newTicker.getDateOfPurchase() > 0) {
            currentTicker.setDateOfPurchase(newTicker.getDateOfPurchase());
        }

        if(newTicker.getSharesPurchased() > 0) {
            currentTicker.setSharesPurchased(newTicker.getSharesPurchased());
        }

        portfolio.deleteTicker(currentTicker.getId());
        portfolio.addTicker(currentTicker);
    }

    private String generateId(String value) {
        return value + System.currentTimeMillis();
    }

    private void generatePortfolioValue(Portfolio portfolio) {
        AtomicLong value = new AtomicLong(Double.doubleToLongBits(portfolio.getProfit()));

        ExecutorService ex = Executors.newFixedThreadPool(5);

        for(StockTicker ticker: portfolio.getTickers()) {
            ex.execute(new StockVal(value, ticker.getSymbolName()));
        }

        portfolio.setProfit(value.doubleValue());
    }

    public class StockVal implements Runnable {
        AtomicLong al = null;

        StockVal(AtomicLong al, String symbolName) {
            this.al = al;
        }

        @Override
        public void run() {
            //TODO CALL API with symbolName, and retrieve stockValue
            long stockValue = 0; //stock price
            this.al.addAndGet(stockValue);
        }
    }
}

