/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MediDeskApp.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author shivam kumar jha
 */
public class DbConnection {
        
        private static Connection conn = null;
    
        static {
                try {
                        Class.forName("oracle.jdbc.OracleDriver");
//                        System.out.println("Class Loaded!");
                }
                catch(ClassNotFoundException e) {
                        e.printStackTrace();
                        System.exit(0);
                }
                
                try {
                        conn = DriverManager.getConnection("jdbc:oracle:thin:@//LAPTOP-MH9A3Q21:1521/XE", "sanjeevani", "shkrjh");
                        System.out.println("Connection Done Sucessfully!");
                }
                catch(SQLException e) {
                        e.printStackTrace();
                        System.exit(0);
                }
        }
        
        public static Connection getConnection() {
                return conn;
        }
        
        public static void closeConnection() {
                if(conn != null) {
                        try {
                                conn.close();
                                System.out.println("Closed Sucessfully");
                        }
                        catch(SQLException e) {
                                e.printStackTrace();
                        }
                }
        }
}
