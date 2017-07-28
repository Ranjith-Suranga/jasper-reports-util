/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.jasper.test;

import lk.ijse.jasper.JasperUtil;
import net.sf.jasperreports.engine.JasperReportsContext;

/**
 *
 * @author ranjith-suranga
 */
public class Report {
    
    
    @lk.ijse.jasper.Report
    public static void test(){
        JasperUtil.getJasperContext();;
    }
    
        public static void test(String name){
        JasperUtil.getJasperContext();;
    }
    
}
