/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import DTO.categoryDTO;
import DTO.productDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.DbUtils;

/**
 *
 * @author Admin
 */
public class categoryDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<categoryDTO> getAllCategories() {
        List<categoryDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCategory WHERE status = 1";
        try ( Connection con = DbUtils.getConnection();  PreparedStatement pst = con.prepareStatement(sql);  ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                categoryDTO c = new categoryDTO(
                        rs.getString("categoryId"),
                        rs.getString("categoryName"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<productDTO> getProductByCategoryId(String id) {
        List<productDTO> list = new ArrayList<>();
        String qr = "select * from tblProduct where categoryId = ? AND status = 1";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(qr);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                productDTO p = new productDTO(
                        rs.getString("productId"),
                        rs.getString("productName"),
                        rs.getFloat("price"),
                        rs.getString("size"),
                        rs.getString("imageBase64"),
                        rs.getString("color"),
                        rs.getInt("quantity"),
                        rs.getString("description"),
                        rs.getString("categoryID")
                );
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public void insertCategory(categoryDTO category) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO tblCategory (categoryID, categoryName) VALUES (?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();

            if (conn != null) {

                ps = conn.prepareStatement(sql);
                ps.setString(1, category.getCategoryId());
                ps.setString(2, category.getCategoryName());

                ps.executeUpdate();
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void softDeleteCategory(String id) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE tblCategory SET status = 0 WHERE categoryID = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(sql);
                ps.setString(1, id);
                ps.executeUpdate();
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<categoryDTO> getDeletedCategories() {
        List<categoryDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblCategory WHERE status = 0"; // Lấy status = 0

        try ( Connection con = DbUtils.getConnection();  PreparedStatement pst = con.prepareStatement(sql);  ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(new categoryDTO(
                        rs.getString("categoryId"),
                        rs.getString("categoryName")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

// 2. HÀM MỚI: Khôi phục danh mục (ngược lại với softDelete)
    public void restoreCategory(String id) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE tblCategory SET status = 1 WHERE categoryID = ?"; // Set status = 1

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(sql);
                ps.setString(1, id);
                ps.executeUpdate();
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

// 1. HÀM MỚI: Lấy 1 danh mục bằng ID (để điền vào form sửa)
    public categoryDTO getCategoryById(String id) {
        String sql = "SELECT * FROM tblCategory WHERE categoryID = ? AND status = 1";

        try ( Connection con = DbUtils.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new categoryDTO(
                            rs.getString("categoryId"),
                            rs.getString("categoryName")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy
    }

// 2. HÀM MỚI: Cập nhật tên của danh mục
    public void updateCategory(String id, String name) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE tblCategory SET categoryName = ? WHERE categoryID = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(sql);
                ps.setString(1, name);
                ps.setString(2, id);
                ps.executeUpdate();
            }
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        categoryDAO cdao = new categoryDAO();
        List<productDTO> List = cdao.getProductByCategoryId("C01");
        for (productDTO p : List) {
            System.out.println(p);
        }
    }

}
