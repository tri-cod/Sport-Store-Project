/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class paymentDTO {

    private int paymentId;
    private int cartId;
    private String userId;
    private String paymentMethod;
    private Timestamp paymentDate;
    private float amount;
    private String status;

    public paymentDTO(int paymentId, int cartId, String userId, String paymentMethod, Timestamp paymentDate, float amount, String status) {
        this.paymentId = paymentId;
        this.cartId = cartId;
        this.userId = userId;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.status = status;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "paymentDTO{" + "paymentId=" + paymentId + ", cartId=" + cartId + ", userId=" + userId + ", paymentMethod=" + paymentMethod + ", paymentDate=" + paymentDate + ", amount=" + amount + ", status=" + status + '}';
    }
    
}
