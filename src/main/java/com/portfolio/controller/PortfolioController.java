package com.portfolio.controller;

import com.portfolio.model.Portfolio;
import com.portfolio.model.StockTicker;
import com.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portfolio")
@CrossOrigin(origins = "http://localhost:8080")
public class PortfolioController  {

    @Autowired
    PortfolioService portfolioService;


    @PostMapping("/portfolioName}")
    public ResponseEntity<String> createPortfolio(@PathVariable String portfolioName) {
        portfolioService.createPortfolio(portfolioName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{portfolioId}")
    public ResponseEntity<String> deletePortfolio(@PathVariable String portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/all")
    public List<Portfolio> getPortfolios() {
        return portfolioService.getPortfolios();
    }

    @PostMapping("/{portfolioName}")
    public ResponseEntity<String> addTicker(@PathVariable String portfolioName, @RequestBody StockTicker ticker) {
        portfolioService.addTicker(portfolioName, ticker);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{portfolioName}/{tickerId}")
    public ResponseEntity<String> deleteTicker(@PathVariable String portfolioName, @PathVariable String tickerId) {
        portfolioService.deleteTicker(portfolioName, tickerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{portfolioName}")
    public ResponseEntity<String> editTicker(@PathVariable String portfolioName, @RequestBody StockTicker ticker) {
        portfolioService.editTicker(portfolioName, ticker);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

