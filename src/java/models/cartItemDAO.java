/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import DTO.cartItemDTO;
import DTO.productDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

/**
 *
 * @author Admin
 */
public class cartItemDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void deleteCartItem(int cartItemId) throws Exception {
        String sql = "DELETE FROM tblCartItem WHERE cartItemId = ?";
        try ( Connection con = DbUtils.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cartItemId);
            ps.executeUpdate();
        }
    }

    public List<cartItemDTO> getAllItemsByUserId(String userId) {
        List<cartItemDTO> list = new ArrayList<>();
        try {
            Connection conn = DbUtils.getConnection();
                String sql = "SELECT ci.cartItemId, ci.cartId, ci.quantity, "
                        + "p.productId, p.productName, p.price, p.imageBase64, p.color, p.size "
                        + "FROM tblCartItem ci "
                        + "JOIN tblCart c ON ci.cartId = c.cartId "
                        + "JOIN tblProduct p ON ci.productId = p.productId "
                        + "WHERE c.userId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Tạo ProductDTO
                productDTO p = new productDTO();
                p.setProductId(rs.getString("productId"));
                p.setProductName(rs.getString("productName"));
                p.setPrice(rs.getFloat("price"));
                p.setImage(rs.getString("imageBase64"));
                p.setColor(rs.getString("color"));
                p.setSize(rs.getString("size"));

                // Tạo CartItemDTO
                cartItemDTO item = new cartItemDTO();
                item.setCartItemId(rs.getInt("cartItemId"));
                item.setCartId(rs.getInt("cartId"));
                item.setOrderQuantity(rs.getInt("quantity"));
                item.setProduct(p); // 👈 gắn sản phẩm vào item

                list.add(item);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

  public cartItemDTO getCartItem(int cartId, String productId, String size, String color) throws Exception {
    String sql = "SELECT * FROM tblCartItem WHERE cartId=? AND productId=? AND ISNULL(size, '')=? AND ISNULL(color, '')=?";
    try (Connection con = DbUtils.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, cartId);
        ps.setString(2, productId);
        ps.setString(3, size == null ? "" : size);
        ps.setString(4, color == null ? "" : color);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            cartItemDTO item = new cartItemDTO();
            item.setCartItemId(rs.getInt("cartItemId"));
            item.setCartId(rs.getInt("cartId"));
            item.setOrderQuantity(rs.getInt("orderQuantity"));
            item.setSize(rs.getString("size"));
            item.setColor(rs.getString("color"));
            return item;
        }
    }
    return null;
}

public void addCartItem(int cartId, String productId, int quantity, String size, String color) throws Exception {
    String sql = "INSERT INTO tblCartItem (cartId, productId, quantity, size, color) VALUES (?, ?, ?, ?, ?)";
    try (Connection con = DbUtils.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, cartId);
        ps.setString(2, productId);
        ps.setInt(3, quantity);
        ps.setString(4, size);
        ps.setString(5, color);
        ps.executeUpdate();
    }
}

    public void addItemToCart(String userId, String productId, String size, String color, int quantity) {
        String sql = "INSERT INTO tblCartItem(userId, productId, size, color, quantity) VALUES (?, ?, ?, ?, ?)";
        try ( Connection con = DbUtils.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, userId);
            ps.setString(2, productId);
            ps.setString(3, size);
            ps.setString(4, color);
            ps.setInt(5, quantity);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateQuantity(int cartId, String productId, String size, String color, int newQty) throws Exception {
        String sql = "UPDATE tblCartItem SET quantity=? WHERE cartId=? AND productId=? AND ISNULL(size,'')=? AND ISNULL(color,'')=?";
        try ( Connection con = DbUtils.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, newQty);
            ps.setInt(2, cartId);
            ps.setString(3, productId);
            ps.setString(4, size == null ? "" : size);
            ps.setString(5, color == null ? "" : color);
            return ps.executeUpdate() > 0;
        }
    }

    // Lấy tất cả item trong cart (dùng để viewCart)
    public List<cartItemDTO> getAllItemsByCartId(int cartId) throws Exception {
        List<cartItemDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCartItem WHERE cartId=?";
        try ( Connection con = DbUtils.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            try ( ResultSet rs = ps.executeQuery()) {
                productDAO pdao = new productDAO();
                while (rs.next()) {
                    cartItemDTO it = new cartItemDTO();
                    it.setCartItemId(rs.getInt("cartItemId"));
                    it.setCartId(rs.getInt("cartId"));
                    it.setOrderQuantity(rs.getInt("quantity"));
                    it.setProduct(pdao.getProductById(rs.getString("productId")));
                    list.add(it);
                }
            }
        }
        return list;
    }
}
