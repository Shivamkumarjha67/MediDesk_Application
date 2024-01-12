/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MediDeskApp.dao;

import MediDeskApp.Pojo.EmpPojo;
import MediDeskApp.dbutil.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author shivam kumar jha
 */
public class EmpDao {
          private static Connection conn;
    
        public static String getNextId() throws SQLException {
                    conn = DbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement("select max(emp_id) from employees");
                    ResultSet rs = ps.executeQuery();
                    
                    String nextId = null;
                    
                    if(rs.next())
                    nextId = rs.getString(1);
                    
                    int val = Integer.parseInt(nextId.substring(1));
                    
                    return ("E"+(++val));
         }
         
         public static void AddEmployees(EmpPojo aep) throws SQLException {
                    conn = DbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement("insert into employees values(?,?,?,?)");
                    ps.setString(1, aep.empId);
                    ps.setString(2, aep.empName);
                    String sal = String.valueOf(aep.empSal);
                    ps.setString(4, sal);
                    ps.setString(3, aep.empJob);
                    
                    int val = ps.executeUpdate();
                    
                    if(val == 1) {
                               JOptionPane.showMessageDialog(null, "Inserted Successfully in DB", "Success", JOptionPane.INFORMATION_MESSAGE);
                               return;
                    }
         }
         
         public static List ViewAllEmp() throws SQLException {
                    conn = DbConnection.getConnection();
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("Select * from employees order by emp_id");
                    
                    List<EmpPojo> empList = new ArrayList<>();
                    while(rs.next()) {
                              EmpPojo aep = new EmpPojo();
                              aep.setEmpId(rs.getString(1));
                              aep.setEmpName(rs.getString(2));
                              aep.setEmpJob(rs.getString(3));
                              aep.setEmpSal(rs.getDouble(4));
                              
                              empList.add(aep);
                    }
                    
                    return empList;
         }
         
         public static List getAllEmployeesId() throws SQLException {
                    conn = DbConnection.getConnection();
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("select emp_id from employees");
                    
                    List <String> empIdList = new ArrayList<>();
                    while(rs.next()) {
                              String empId = rs.getString(1);
                              empIdList.add(empId);
                    }
                    
                    return empIdList;
         }
         
         public static EmpPojo getDetailForId(String empId) throws SQLException {
                    conn = DbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select * from employees where emp_id = ?");
                    ps.setString(1, empId);
                    
                    ResultSet rs= ps.executeQuery();
                    EmpPojo epojo = new EmpPojo();
                    
                    if(rs.next()) {
                              epojo.setEmpId(rs.getString(1));
                              epojo.setEmpName(rs.getString(2));
                              epojo.setEmpJob(rs.getString(3));
                              epojo.setEmpSal(rs.getDouble(4));
                    }
                    
                    return epojo;
         }
         
         public static boolean updateEmployee(EmpPojo epojo) throws SQLException {
                    updateUserName(epojo);
                    Connection conn=DbConnection.getConnection();
                    PreparedStatement ps=conn.prepareStatement("Update employees set emp_name=?,emp_salary=? where emp_id=?");
                    ps.setString(1, epojo.getEmpName());
                    ps.setDouble(2, epojo.getEmpSal());
                    ps.setString(3, epojo.getEmpId());
                    return ps.executeUpdate()==1;
         }

        private static void updateUserName(EmpPojo epojo) throws SQLException {
                    conn = DbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select emp_name from employees where emp_id = ?");
                    ps.setString(1, epojo.getEmpId());
                    ResultSet rs = ps.executeQuery();
                    
                    rs.next();
                    String currName = rs.getString(1);
                    String newName = epojo.getEmpName();
                    
                    UserDao.updateName(currName,newName);
                    if(epojo.getEmpJob().equalsIgnoreCase("Receptionist"))
                        ReceptionistDao.updateName(currName,newName);
                    else if(epojo.getEmpJob().equalsIgnoreCase("Doctor"))
                        DoctorsDao.updateName(currName,newName);
        }
        
        public static boolean removeEmployee(String empId) throws SQLException {
                    conn = DbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select * from employees where emp_id = ?");
                    ps.setString(1, empId);
                    ResultSet rs = ps.executeQuery();
                    
                    rs.next();
                    String empName = rs.getString(2);
                    String empDep = rs.getString(3);
                    
                    UserDao.removeUser(empName);
                    if(empDep.equalsIgnoreCase("Receptionist"))
                        ReceptionistDao.removeReceptionistByName(empName);
                    else if(empDep.equalsIgnoreCase("Doctor"))
                        DoctorsDao.removeDoctor(empName);
                    
                    ps = conn.prepareStatement("delete from employees where emp_id=?");
                    ps.setString(1, empId);
                    
                    return (ps.executeUpdate()==1);
        }
        
        public static Map getUnregisteredDoctors() throws SQLException {
                Map<String,String> unregDoc = new HashMap<>();
                conn = DbConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("Select emp_id , emp_name from employees where emp_department ='Doctor' and emp_name not in(Select user_name from users where user_type='Doctor') order by emp_id");
                while(rs.next()) {
                    String empId = rs.getString(1);
                    String empName = rs.getString(2);
                    unregDoc.put(empId, empName);
                }
                return unregDoc;
        }
        
        public static Map getUnregisteredReceptionist() throws SQLException {
                Map<String,String> unregRecp = new HashMap<>();
                conn = DbConnection.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("Select emp_id , emp_name from employees where emp_department ='Receptionist' and emp_name not in(Select user_name from users where user_type='Receptionist') order by emp_id");
                while(rs.next()) {
                    String empId = rs.getString(1);
                    String empName = rs.getString(2);
                    unregRecp.put(empId, empName);
                }
                return unregRecp;
        }
}
