/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MediDeskApp.dao;

import MediDeskApp.Pojo.ReceptionistPojo;
import MediDeskApp.Pojo.User;
import MediDeskApp.dbutil.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author shivam kumar jha
 */
public class ReceptionistDao {
        private static Connection conn;
        public static void updateName(String currName, String newName) throws SQLException {
                conn = DbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("update receptionists set receptionist_name=? where receptionist_name=?");
                ps.setString(1, newName);
                ps.setString(2, currName);

                ps.executeUpdate();
        }
        
        public static void removeReceptionistByName(String empName) throws SQLException{
                conn = DbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("delete from receptionists where receptionist_name=?");
                ps.setString(1, empName);

                ps.executeUpdate();
        }
        
        public static String getNextRecpId() throws SQLException {
            conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("Select max(RECEPTIONIST_ID) from receptionists");
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            String recpId = "101";
            String gotId = rs.getString(1);
            
            if(gotId != null) {
                    recpId = gotId.substring(1);
                    int val = Integer.parseInt(recpId) + 1;
                    recpId = "" + val;
            }
            
            return ("R"+ recpId);
    } 
        
        public static boolean registerNewReceptionist(ReceptionistPojo rp) throws SQLException { 
            conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into receptionists values(?,?,?)");

            ps.setString(1, rp.getRecpId());
            ps.setString(2, rp.getRecpName());
            ps.setString(3, rp.getRecpGender());
            
            return ps.executeUpdate() == 1;
    } 
        
        public static List<ReceptionistPojo> getReceptionistDetails() throws SQLException {
            conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from receptionists");
            ResultSet rs = ps.executeQuery();
            
            List<ReceptionistPojo> recpDetail = new ArrayList<>();
            while(rs.next()) {
                    ReceptionistPojo rp = new ReceptionistPojo();
                    rp.setRecpId(rs.getString(1));
                    rp.setRecpName(rs.getString(2));
                    rp.setRecpGender(rs.getString(3));
                    
                    recpDetail.add(rp);
            }
            
            return recpDetail;
    }
        
        public static Map<String,String> getAllReceptionistDetail() throws SQLException {
                conn = DbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("Select * from receptionists");
                ResultSet rs = ps.executeQuery();
                Map<String,String> recpDetail = new HashMap<>();
                
                while(rs.next()) {
                        String id = rs.getString(1);
                        String name = rs.getString(2);
                        
                        recpDetail.put(id, name);
                }
                
                return recpDetail;
        }
        
        public static boolean updateReceptionist(ReceptionistPojo rp, User up) throws SQLException {
                conn = DbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("Select receptionist_name from receptionists where receptionist_id=?");
                ps.setString(1, rp.getRecpId());
                ResultSet rs = ps.executeQuery();
                rs.next();
                String recName = rs.getString(1);
                boolean result = UserDao.updateUser(up, recName);
                ps = conn.prepareStatement("Update receptionists set receptionist_name=?, gender=? where receptionist_id=?");
                ps.setString(1, rp.getRecpName());
                ps.setString(2, rp.getRecpGender());
                ps.setString(3, rp.getRecpId());
                
                return (ps.executeUpdate() == 1) && result;
        }
        
        public static List<String> getAllReceptionistID() throws SQLException {
                conn = DbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("Select receptionist_id from receptionists");
                ResultSet rs = ps.executeQuery();
                
                List<String> recpId = new ArrayList<>();
                
                while(rs.next()) {
                        String id = rs.getString(1);
                        recpId.add(id);
                }
                
                return recpId;
        }
        
        public static boolean removeReceptionistByID(String recpId) throws SQLException{
                conn = DbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("Delete from receptionists where receptionist_id=?");
                ps.setString(1, recpId);

                return ps.executeUpdate() == 1;
        }
        
        public static boolean deleteByReceptionistId(String recpId) throws SQLException {
                conn = DbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement("Select receptionist_name from receptionists where receptionist_id=?");
                ps.setString(1, recpId);
                ResultSet rs = ps.executeQuery();
                rs.next();
                String recpName = rs.getString(1);
                
                UserDao.removeUser(recpName);
                
                ps = conn.prepareStatement("Delete from receptionists where receptionist_id=?");
                ps.setString(1, recpId);
                
                return ps.executeUpdate()==1;
        }
 }
