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
        String opt;
        Scanner input = new Scanner (System.in);
        System.out.println("ketik dong");
        opt = input.next();
        System.out.println("isi yg tadi diketik: "+opt);
        if (opt.equals("/anda")) {
            opt = input.next();
            System.out.println("isi yg tadi diketik kedua: "+opt);
        }
//        System.out.println("ketik lagi dong");
//        opt = input.nextLine();
//        System.out.println("isi yg tadi diketik: "+opt);
    }
    
}
