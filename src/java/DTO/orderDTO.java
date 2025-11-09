package DTO;

import java.sql.Date;

public class orderDTO {

    private int orderId;
    private String userId;
    private Date createdDate;
    private String status;
    private double amountPrice;

    public orderDTO() {
    }

    public orderDTO(int orderId, String userId, Date createdDate, String status, double amountPrice) {
        this.orderId = orderId;
        this.userId = userId;
        this.createdDate = createdDate;
        this.status = status;
        this.amountPrice = amountPrice;
    }

    public orderDTO(String userId, String status, double amountPrice) {
        this.userId = userId;
        this.status = status;
        this.amountPrice = amountPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmountPrice() {
        return amountPrice;
    }

    public void setAmountPrice(double amountPrice) {
        this.amountPrice = amountPrice;
    }

    @Override
    public String toString() {
        return "OrderDTO{"
                + "orderId=" + orderId
                + ", userId='" + userId + '\''
                + ", createdDate=" + createdDate
                + ", status='" + status + '\''
                + ", amountPrice=" + amountPrice
                + '}';
    }
}
