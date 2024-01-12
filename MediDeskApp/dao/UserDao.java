package MediDeskApp.dao;

import MediDeskApp.Pojo.User;
import MediDeskApp.dbutil.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shivam kumar jha
 */
public class UserDao {
         private static Connection conn;
         
         public static String validateUser(User pojo) throws SQLException {
                    conn = DbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement("Select user_name from users where login_id = ? and password = ? and user_type = ?");
                    ps.setString(1, pojo.getLoginId());
                    ps.setString(2,pojo.getPassword());
                    ps.setString(3, pojo.getUserType());
                    
                    ResultSet rs = ps.executeQuery();
                    String name = null;
                    
                    if(rs.next()) {
                             name = rs.getString("user_name");
                    }
                    
                    return name;
         }
       
         public static void updateName(String currName, String newName) throws SQLException {
                    conn = DbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement("Update users set user_name = ? where user_name = ?");
                    ps.setString(1, newName);
                    ps.setString(2, currName);
                    
                    ps.executeUpdate();
         }
         
         public static void removeUser(String userName) throws SQLException {
                    conn = DbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement("delete from users where user_name=?");
                    ps.setString(1, userName);
                    
                    ps.executeUpdate();
         }
         
         public static boolean addUser(User up) throws SQLException {
                    conn = DbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement("insert into users values(?,?,?,?)");
          
                    ps.setString(1, up.getLoginId());
                    ps.setString(2, up.getUserName());
                    ps.setString(3, up.getPassword());
                    ps.setString(4, up.getUserType());
                    
                    return ps.executeUpdate()==1;
         }
         
         public static boolean updateUser(User up ,String initialName) throws SQLException {
                    conn = DbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement("Update users set login_id=?, user_name=? ,password=? where user_name=?");
                    ps.setString(1, up.getLoginId());
                    ps.setString(2, up.getUserName());
                    ps.setString(3, up.getPassword());
                    ps.setString(4, initialName);
                    
                    return ps.executeUpdate() == 1;
         }
}
