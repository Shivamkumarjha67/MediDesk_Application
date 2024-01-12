/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MediDeskApp.dao;

import MediDeskApp.Pojo.PatientsPojo;
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
public class PatientsDao {
    
    private static Connection conn;
    
    public static String getNewPatientId() throws SQLException {
        conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("Select max(PATIENT_ID) from patients");
            ResultSet rs = ps.executeQuery();
            
            rs.next();
            String patId = "101";
            String gotId = rs.getString(1);
            
            if(gotId != null) {
                    patId = gotId.substring(1);
                    int val = Integer.parseInt(patId) + 1;
                    patId = "" + val;
            }
            
            return ("P"+ patId);
    }
    
    public static boolean registerNewReceptionist(PatientsPojo pp) throws SQLException { 
            conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("Insert into patients values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

            ps.setString(1, pp.getPatId());
            ps.setString(2, pp.getFirstName());
            ps.setString(3, pp.getLastName());
            ps.setInt(4, pp.getAge());
            ps.setString(5, pp.getGender());
            ps.setString(6, pp.getM_status());
            ps.setString(7, pp.getAddress());
            ps.setString(8, pp.getCity());
            ps.setString(9, pp.getM_no());
            ps.setDate(10, pp.getDate());
            ps.setInt(11, pp.getOtp());
            ps.setString(12, pp.getOpd());
            ps.setString(13, pp.getDocId());
            ps.setString(14, pp.getStatus());
            
            return ps.executeUpdate() == 1;
    } 
    
    public static List<PatientsPojo> getAllPatientsDetails(String docId) throws SQLException {
        conn = DbConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select * from patients where status=? and doctor_id=? order by patient_id");
        ps.setString(1, "REQUEST");
        ps.setString(2, docId);
        
        ResultSet rs = ps.executeQuery();
        
        List<PatientsPojo> list = new ArrayList<>();
        
        while(rs.next()) {
            PatientsPojo pp = new PatientsPojo();
            
            pp.setPatId(rs.getString("patient_Id"));
            pp.setFirstName(rs.getString("first_name"));
            pp.setLastName(rs.getString("last_name"));
            pp.setAge(rs.getInt("age"));
            pp.setGender(rs.getString("gender"));
            pp.setM_status(rs.getString("m_status"));
            pp.setAddress(rs.getString("address"));
            pp.setCity(rs.getString("city"));
            pp.setM_no(rs.getString("mobile_no"));
            pp.setOpd(rs.getString("opd"));
            pp.setDocId(rs.getString("doctor_id"));
            pp.setStatus(rs.getString("status"));
            pp.setDate(rs.getDate("p_date"));
            
            list.add(pp);
        }
        
        return list;
    }
    
    public static List<String> getAllPatientsId() throws SQLException {
        conn = DbConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select patient_id from patients order by patient_id");
        ResultSet rs = ps.executeQuery();
        
        List<String> patIds = new ArrayList<>();
        
        while(rs.next()) {
            String patientId = rs.getString(1);
            patIds.add(patientId);
        }
        
        return patIds;
    }
    
    public static PatientsPojo getPatientDetailById(String patId) throws SQLException {
        conn = DbConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("Select * from patients where patient_id = ?");
        ps.setString(1, patId);
        ResultSet rs = ps.executeQuery();
        
        PatientsPojo pp = new PatientsPojo();
        
        rs.next();
        
            pp.setPatId(rs.getString("patient_Id"));
            pp.setFirstName(rs.getString("first_name"));
            pp.setLastName(rs.getString("last_name"));
            pp.setAge(rs.getInt("age"));
            pp.setGender(rs.getString("gender"));
            pp.setM_status(rs.getString("m_status"));
            pp.setAddress(rs.getString("address"));
            pp.setCity(rs.getString("city"));
            pp.setM_no(rs.getString("mobile_no"));
            pp.setOpd(rs.getString("opd"));
            pp.setDocId(rs.getString("doctor_id"));
            pp.setStatus(rs.getString("status"));
            pp.setDate(rs.getDate("p_date"));
            pp.setOtp(rs.getInt("otp"));
            
            return pp;
    }
    
    public static boolean updatePatientDetails(PatientsPojo pp) throws SQLException {
            conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("Update patients set first_name=?, last_name=?, age=?, gender=?, m_status=?, address=?, city=?, opd=?, doctor_id=?  where patient_id=?");

            ps.setString(1, pp.getFirstName());
            ps.setString(2, pp.getLastName());
            ps.setInt(3, pp.getAge());
            ps.setString(4, pp.getGender());
            ps.setString(5, pp.getM_status());
            ps.setString(6, pp.getAddress());
            ps.setString(7, pp.getCity());
            ps.setString(8, pp.getOpd());
            ps.setString(9, pp.getDocId());
            
            ps.setString(10, pp.getPatId());
            
            return ps.executeUpdate() == 1;
    }
    
    public static boolean deletePatient(String patId) throws SQLException {
            conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("Delete from patients where patient_id=?");
            ps.setString(1, patId);
            
            return ps.executeUpdate() == 1;
    }
    
    public static List<PatientsPojo> getAllPatients() throws SQLException {
        conn = DbConnection.getConnection();
        Statement st = conn.createStatement();
        
        ResultSet rs = st.executeQuery("Select * from patients");
        
        List<PatientsPojo> list = new ArrayList<>();
        
        while(rs.next()) {
            PatientsPojo pp = new PatientsPojo();
            
            pp.setPatId(rs.getString("patient_Id"));
            pp.setFirstName(rs.getString("first_name"));
            pp.setLastName(rs.getString("last_name"));
            pp.setAge(rs.getInt("age"));
            pp.setGender(rs.getString("gender"));
            pp.setM_status(rs.getString("m_status"));
            pp.setAddress(rs.getString("address"));
            pp.setCity(rs.getString("city"));
            pp.setM_no(rs.getString("mobile_no"));
            pp.setOpd(rs.getString("opd"));
            pp.setDocId(rs.getString("doctor_id"));
            pp.setStatus(rs.getString("status"));
            pp.setDate(rs.getDate("p_date"));
            
            list.add(pp);
        }
        
        return list;
    }
    
    public static boolean updateStatus(String patId) throws SQLException {
            conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement("Update patients set status='CONFIRMED' where patient_id=?");
            ps.setString(1, patId);
            
            return ps.executeUpdate() == 1;
    }
 }
