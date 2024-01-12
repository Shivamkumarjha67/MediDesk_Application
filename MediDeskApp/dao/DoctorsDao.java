/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MediDeskApp.dao;

import MediDeskApp.Pojo.DoctorPojo;
import MediDeskApp.dbutil.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shivam kumar jha
 */
public class DoctorsDao {
            private static Connection conn;
            
    public static void updateName(String currName,String newName) throws SQLException {
            conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("update doctors set doctor_name=? where doctor_name=?");
            ps.setString(1, newName);
            ps.setString(2, currName);
            
            ps.executeUpdate();
    }
    
    public static void removeDoctor(String empName) throws SQLException{
           conn = DbConnection.getConnection();
           PreparedStatement ps = conn.prepareStatement("delete from doctors where doctor_name=?");
           ps.setString(1, empName);
           
           ps.executeUpdate();
    }
    
    public static String getNextId() throws SQLException {
            conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("Select max(DOCTOR_ID) from doctors");
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            String docId = "101";
            String gotId = rs.getString(1);
            
            if(gotId != null) {
                    docId = gotId.substring(1);
                    int val = Integer.parseInt(docId) + 1;
                    docId = "" + val;
            }
            
            return ("D"+ docId);
    } 
    
    public static boolean registerNewDoctor(DoctorPojo dp) throws SQLException { 
            conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("insert into doctors values(?,?,?,?,?,?,?)");

            ps.setString(1, dp.getDocId());
            System.out.println(dp.getDocId());
            ps.setString(2, dp.getDocName());
            ps.setString(3, dp.getEmailId());
            ps.setString(4, dp.getContactNo());
            ps.setString(5, dp.getQualification());
            ps.setString(6, dp.getGender());
            ps.setString(7, dp.getSpecialist());
            
            return ps.executeUpdate() == 1;
    } 
    
    public static List<String> getAllDocId() throws SQLException {
            conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("Select doctor_id from doctors");
            ResultSet rs = ps.executeQuery();
            
            List<String> docId = new ArrayList<>();
            while(rs.next()) {
                    String id = rs.getString(1);
                    docId.add(id);
            }
            
            return docId;
    }
    
    public static boolean removeDoctorByDocId(String docId) throws SQLException {
            conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("Select doctor_name from doctors where doctor_id=?");
            ps.setString(1, docId);
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            String docName = rs.getString(1);
            UserDao.removeUser(docName);
            
            ps = conn.prepareStatement("delete from doctors where doctor_id=?");
            ps.setString(1, docId);
            
            return ps.executeUpdate() == 1;
    }
    
    public static List<DoctorPojo> getDoctorsDetails() throws SQLException {
            conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("select * from doctors");
            ResultSet rs = ps.executeQuery();
            
            List<DoctorPojo> docDetail = new ArrayList<>();
            while(rs.next()) {
                    DoctorPojo dp = new DoctorPojo();
                    dp.setDocId(rs.getString(1));
                    dp.setDocName(rs.getString(2));
                    dp.setEmailId(rs.getString(3));
                    dp.setContactNo(rs.getString(4));
                    dp.setQualification(rs.getString(5));
                    dp.setGender(rs.getString(6));
                    dp.setSpecialist(rs.getString(7));
                    
                    docDetail.add(dp);
            }
            
            return docDetail;
    }
    
    public static String getDocName(String docId) throws SQLException {
        conn = DbConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select doctor_name from doctors where doctor_id=?");
        ps.setString(1, docId);
        
        ResultSet rs = ps.executeQuery();
        rs.next();
        String docName = rs.getString(1);
        
        return docName;
    } 
    
    public static String getDocId(String docName) throws SQLException {
        conn = DbConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select doctor_id from doctors where doctor_name=?");
        ps.setString(1, docName);
        
        ResultSet rs = ps.executeQuery();
        rs.next();
        String docId = rs.getString(1);
        
        return docId;
    } 
}
