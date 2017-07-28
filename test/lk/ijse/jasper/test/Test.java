/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.jasper.test;

import java.util.HashMap;
import lk.ijse.jasper.JasperUtil;
import lk.ijse.jasper.Report;
import net.sf.jasperreports.engine.JasperReportsContext;

/**
 *
 * @author ranjith-suranga
 */
@Report("lk.ijse.jasper.test")
public class Test {
        
    public static void main(String[] args) {
        
        JasperUtil.init(Test.class);
        JasperReportsContext reportContext = JasperUtil.getReportContext();
        System.out.println(reportContext);
        
        Test t = new Test();
        t.a();
        
    }
    
    public void a(){
        JasperReportsContext reportContext = JasperUtil.getReportContext();
        System.out.println("Instance : " + reportContext);        
    }
    
}
