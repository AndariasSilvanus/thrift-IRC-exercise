/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pat_irc;

import java.util.Scanner;

/**
 *
 * @author Andarias Silvanus
 */
public class PAT_IRC {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        String opt;
//        Scanner input = new Scanner (System.in);
//        System.out.println("ketik dong");
//        opt = input.next();
//        System.out.println("isi yg tadi diketik: "+opt);
//        if (opt.equals("/anda")) {
//            opt = input.next();
//            System.out.println("isi yg tadi diketik kedua: "+opt);
//        }
        
//        System.out.println("ketik lagi dong");
//        opt = input.nextLine();
//        System.out.println("isi yg tadi diketik: "+opt);
        
        java.util.Date today = new java.util.Date();
        java.sql.Timestamp ts1 = new java.sql.Timestamp(today.getTime());
        java.sql.Timestamp ts2 = java.sql.Timestamp.valueOf("2005-04-06 09:01:10");

        long tsTime1 = ts1.getTime();
        long tsTime2 = ts2.getTime();

        System.out.println(tsTime1);
        System.out.println(tsTime2);
    }
    
}
