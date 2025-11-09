/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import DTO.cartDTO;
import DTO.cartItemDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import utils.DbUtils;

/**
 *
 * @author Admin
 */
public class cartDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    private cartItemDAO itemDAO = new cartItemDAO();

    public cartDTO getCartByUserId(String userId) {
        cartDTO cart = null;
        String sql = "SELECT * FROM tblCart WHERE userId = ?";

        try ( Connection con = DbUtils.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cart = new cartDTO();
                cart.setCartId(rs.getInt("cartId"));
                cart.setUserId(rs.getString("userId"));
                cart.setStatus(rs.getString("status"));
                cart.setCreatedDate(rs.getDate("createdDate"));
                // Gọi CartItemDAO để lấy các sản phẩm trong giỏ
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cart;
    }

    // lấy cartId hiện tại
    public int getCurrentCartId(String userId) throws Exception {
        String sql = "SELECT cartId FROM tblCart WHERE userId = ? AND status = 'ACTIVE'";
        Connection con = DbUtils.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, userId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("cartId");
        }
        return -1;
    }

    // tạo mới cart
    public int createNewCart(String userId) throws Exception {
        int newCartId = -1;
        try ( Connection cn = DbUtils.getConnection()) {
            String sql = "INSERT INTO tblCart(userId, status) OUTPUT INSERTED.cartId VALUES(?, 'ACTIVE')";
            PreparedStatement pst = cn.prepareStatement(sql);
            pst.setString(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                newCartId = rs.getInt(1);
            }
        }
        return newCartId;
    }
}
