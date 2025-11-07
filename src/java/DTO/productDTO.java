package DTO;

import java.io.Serializable;
import java.util.List;

public class productDTO implements Serializable {

    private String productId;
    private String productName;
    private float price;
    private String size;
    private String image;
    private String color;
    private int quantity;
    private String description;
    private String categoryId;
    private List<String> galleryImages;

    public productDTO() {
    }

    public productDTO(String productId, String productName, float price, String size,
            String image, String color, int quantity, String description, String categoryId) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.size = size;
        this.image = image;
        this.color = color;
        this.quantity = quantity;
        this.description = description;
        this.categoryId = categoryId;
    }

    // Getter & Setter
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<String> getGalleryImages() {
        return galleryImages;
    }

    public void setGalleryImages(List<String> galleryImages) {
        this.galleryImages = galleryImages;
    }

    @Override
    public String toString() {
        return "productDTO{" + "productId=" + productId + ", productName=" + productName + ", price=" + price + ", size=" + size + ", image=" + image + ", color=" + color + ", quantity=" + quantity + ", description=" + description + ", categoryId=" + categoryId + '}';
    }

}
