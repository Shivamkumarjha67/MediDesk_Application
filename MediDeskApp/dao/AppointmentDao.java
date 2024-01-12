/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MediDeskApp.dao;

import MediDeskApp.Pojo.AppointmentPojo;
import MediDeskApp.dbutil.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shivam kumar jha
 */
public class AppointmentDao {
    private static Connection conn;
    
    public static boolean registerAppointment(AppointmentPojo ap) throws SQLException {
        conn = DbConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Insert into appointments values(?,?,?,?,?,?,?)");
        ps.setString(1, ap.getPatientId());
        ps.setString(2, ap.getPatName());
        ps.setString(3, ap.getStatus());
        ps.setString(4, ap.getOpd());
        ps.setString(5, ap.getDate());
        ps.setString(6, ap.getDocName());
        ps.setString(7, ap.getMobNo());
        
        return ps.executeUpdate() == 1;
    }
    
    public static AppointmentPojo getAppointment(String patId) throws SQLException {
          conn = DbConnection.getConnection();
          PreparedStatement ps = conn.prepareStatement("Select * from appointments where patient_id=?");
          ps.setString(1, patId);
          ResultSet rs = ps.executeQuery();
          rs.next();
          
          AppointmentPojo ap = new AppointmentPojo();
          
          ap.setPatientId(rs.getString(1));
          ap.setPatName(rs.getString(2));
          ap.setStatus(rs.getString(3));
          ap.setOpd(rs.getString(4));
          ap.setDate(rs.getString(5));
          ap.setDocName(rs.getString(6));
          ap.setMobNo(rs.getString(7));
          
          return ap;
    }
    
    public static boolean updateAppointmentByReceptionist(AppointmentPojo ap) throws SQLException {
        conn = DbConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Update appointments patient_name=?,set status=?,opd=?,date_time=?,doctor_name=?,mobile_no=? where patient_id=?");
        ps.setString(1, ap.getPatName());
        ps.setString(2, ap.getStatus());
        ps.setString(3, ap.getOpd());
        ps.setString(4, ap.getDate());
        ps.setString(5, ap.getDocName());
        ps.setString(6, ap.getMobNo());
        ps.setString(7, ap.getPatientId());
        
        return ps.executeUpdate()==1;
    }
    
    public static boolean updateAppointment(AppointmentPojo ap) throws SQLException {
        conn = DbConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Update appointments set status=?,date_time=? where patient_id=?");
        ps.setString(1, ap.getStatus());
        ps.setString(2, ap.getDate());
        ps.setString(3, ap.getPatientId());
        
        return ps.executeUpdate()==1;
    }
    
    public static List<AppointmentPojo> getConfirmedAppointment(String docName) throws SQLException {
        conn = DbConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select * from appointments where status='CONFIRMED' and doctor_name=? order by patient_id");
        ps.setString(1, docName);
        ResultSet rs = ps.executeQuery();
        
        List<AppointmentPojo> list = new ArrayList<>();
        
        while(rs.next()) {
            AppointmentPojo pp = new AppointmentPojo();
            
            pp.setPatientId(rs.getString(1));
            pp.setPatName(rs.getString(2));
            pp.setOpd(rs.getString(4));
            pp.setDate(rs.getString(5));
            
            list.add(pp);
        }
        
        return list;
    }
    
    public static boolean removePatientByPatId(String patId) throws SQLException {
            conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("Delete from appointments where patient_id=?");
            ps.setString(1, patId);
            
            return ps.executeUpdate() == 1;
    }
}
