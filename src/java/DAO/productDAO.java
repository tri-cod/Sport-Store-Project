package DAO;

import DTO.productDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import utils.DbUtils;

public class productDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<productDTO> getALlProduct() {
        List<productDTO> list = new ArrayList<>();
        String qr = "select * from tblProduct";
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(qr);
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
        }

        return list;
    }

    public productDTO getProductById(String id) {
        List<productDTO> list = new ArrayList<>();
        String qr = "select * from tblProduct where productId=?";
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
                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<productDTO> searchByName(String txtSearch) {
        List<productDTO> list = new ArrayList<>();
        String qr = "select * from tblProduct where [productName] like ?";
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(qr);
            ps.setString(1, "%" + txtSearch + "%");
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
        }

        return list;
    }

    public void insertProduct(productDTO product) {

        // Đã sửa lỗi cú pháp (thêm dấu +)
        String sql = "INSERT INTO tblProduct (productId, productName, price, size, imageBase64, color, quantity, description, categoryID)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Dùng Connection và PreparedStatement cục bộ
        Connection localConn = null;
        PreparedStatement localPs = null;

        try {
            localConn = DbUtils.getConnection();
            if (localConn != null) {
                localPs = localConn.prepareStatement(sql);

                localPs.setString(1, product.getProductId());
                localPs.setString(2, product.getProductName());
                localPs.setFloat(3, product.getPrice());
                localPs.setString(4, product.getSize());
                localPs.setString(5, product.getImage()); // Lưu 'image' vào 'imageBase64'
                localPs.setString(6, product.getColor());
                localPs.setInt(7, product.getQuantity());
                localPs.setString(8, product.getDescription());
                localPs.setString(9, product.getCategoryId()); // Khớp với 'categoryID'

                localPs.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (localPs != null) {
                    localPs.close();
                }
                if (localConn != null) {
                    localConn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
