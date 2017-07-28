/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.jasper;

import net.sf.jasperreports.engine.JasperReportsContext;

/**
 *
 * @author ranjith-suranga
 */
public class JasperUtil {
    
    public static void getJasperContext(){
        System.out.println(Thread.currentThread().getStackTrace()[2].getClassName());
    }
    
}


