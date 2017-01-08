package com.lip.trading;

import java.util.Date;

/**
 * Created by Lip on 2016-12-14 11:28
 */
public class IndexTradeModel {
    public static final Integer STATUS_OPEN = 0;
    public static final Integer STATUS_PROFIT_CLOSE = 1;
    public static final Integer STATUS_LOSS_CLOSE = 2;
    public static final Integer STATUS_EXPIRE_CLOSE = 3;

    Long id;
    Long tranId;
    String userId;
    Long productId;
    Integer direction;
    Double multiplier;
    Double openPrice;
    Double amount;
    Integer expire;
    Double stopLoss;
    Double stopProfit;
    Date expireAt;
    Long counterpartyId;
    Double counterpartyAmount;
    Date openTime;
    Integer status;
    Date closeTime;
    Double closePrice;
    Double closeProfit;
    Double fee;
    String symbol;
    String productName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTranId() {
        return tranId;
    }

    public void setTranId(Long tranId) {
        this.tranId = tranId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(Double multiplier) {
        this.multiplier = multiplier;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    public Double getStopLoss() {
        return stopLoss;
    }

    public void setStopLoss(Double stopLoss) {
        this.stopLoss = stopLoss;
    }

    public Double getStopProfit() {
        return stopProfit;
    }

    public void setStopProfit(Double stopProfit) {
        this.stopProfit = stopProfit;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }

    public Long getCounterpartyId() {
        return counterpartyId;
    }

    public void setCounterpartyId(Long counterpartyId) {
        this.counterpartyId = counterpartyId;
    }

    public Double getCounterpartyAmount() {
        return counterpartyAmount;
    }

    public void setCounterpartyAmount(Double counterpartyAmount) {
        this.counterpartyAmount = counterpartyAmount;
    }

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Double getCloseProfit() {
        return closeProfit;
    }

    public void setCloseProfit(Double closeProfit) {
        this.closeProfit = closeProfit;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "IndexTradeModel{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", stopLoss=" + stopLoss +
                ", stopProfit=" + stopProfit +
                '}';
    }
}
