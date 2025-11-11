/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.userInformationDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;
import static utils.DbUtils.getConnection;

/**
 *
 * @author Admin
 */
public class userInformationDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public boolean addUserInformation(userInformationDTO uInfor) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO tblUserInformation(userId,name, phoneNumber, address) VALUES (?, ?, ?, ?)";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, uInfor.getUserId());
            ps.setString(2, uInfor.getName());
            ps.setString(3, uInfor.getPhoneNumber());
            ps.setString(4, uInfor.getAddress());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateUserInformation(userInformationDTO uInfor) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE tblUserInformation SET name=?, phoneNumber=?, address=? WHERE infoId=?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, uInfor.getName());
            ps.setString(2, uInfor.getPhoneNumber());
            ps.setString(3, uInfor.getAddress());
            ps.setString(4, uInfor.getInforId());
            return ps.executeUpdate() > 0;
        }
    }

    public userInformationDTO getUserInformationById(String inforId) {
        String sql = "SELECT * FROM tblUserInformation WHERE infoId = ?";
        try ( Connection conn = getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, inforId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userInformationDTO info = new userInformationDTO();
                info.setInforId(rs.getString("infoId"));
                info.setUserId(rs.getString("userId"));
                info.setName(rs.getString("name"));
                info.setPhoneNumber(rs.getString("phoneNumber"));
                info.setAddress(rs.getString("address"));
                return info;
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteUserInformation(String infoId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM tblUserInformation WHERE infoId=?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, infoId);
            return ps.executeUpdate() > 0;
        }
    }

    public List<userInformationDTO> getUserInformationsByUser(String userId) {
        List<userInformationDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM tblUserInformation WHERE userId=?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userInformationDTO info = new userInformationDTO();
                info.setInforId(rs.getString("infoId"));
                info.setUserId(rs.getString("userId"));
                info.setName(rs.getString("name"));
                info.setPhoneNumber(rs.getString("phoneNumber"));
                info.setAddress(rs.getString("address"));
                list.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args) {
        userInformationDAO dao = new userInformationDAO();
        List<userInformationDTO> list = dao.getUserInformationsByUser("CS000001");
        for(userInformationDTO u : list){
            System.out.println(u);
        }
    }
}
