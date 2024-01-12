/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MediDeskApp.utility;

import java.util.Base64;

/**
 *
 * @author shivam kumar jha
 */
public class Encryption {
        public static String encrypt(String str) {
                Base64.Encoder enc = Base64.getEncoder();
                byte[] arr = str.getBytes();
                
                return enc.encodeToString(arr);
        }
}
