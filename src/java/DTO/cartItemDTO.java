/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.util.List;

/**
 *
 * @author Admin
 */
public class cartItemDTO {

    private int cartItemId;
    private int orderId;
    private String productId;
    private int quantity;
    private double unitPrice;
    private double totalPrice;
    private productDTO product;
    private String size;
    private String color;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public cartItemDTO() {
    }

    public cartItemDTO(int cartItemId, int orderId, String productId, int quantity, double unitPrice, double totalPrice) {
        this.cartItemId = cartItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public cartItemDTO(int orderId, String productId, int quantity, double unitPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = quantity * unitPrice;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = this.quantity * this.unitPrice; // cập nhật luôn tổng
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        this.totalPrice = this.quantity * this.unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public productDTO getProduct() {
        return product;
    }

    public void setProduct(productDTO product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "cartItemDTO{"
                + "cartItemId=" + cartItemId
                + ", orderId=" + orderId
                + ", productId='" + productId + '\''
                + ", quantity=" + quantity
                + ", unitPrice=" + unitPrice
                + ", totalPrice=" + totalPrice
                + '}';
    }
}
