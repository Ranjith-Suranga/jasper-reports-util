/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lk.ijse.jasper;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.FileResolver;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.LocalJasperReportsContext;
import net.sf.jasperreports.engine.util.SimpleFileResolver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author ranjith-suranga
 */
public class JasperUtil {

    private static HashMap<String, Context> jasperContexts = new HashMap<>();

    public static void init(Class anyClass) {
        Report reportAnnotation = (Report) anyClass.getAnnotation(Report.class);

        if (reportAnnotation == null) {
            throw new Error("Report annotation can't be found with the class");
        }

        if (!jasperContexts.containsKey(anyClass.getName())){
            jasperContexts.put(anyClass.getName(), new Context(anyClass, reportAnnotation.value()));
        }

    }
    
    public static JasperReportsContext getReportContext(){
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        
        if (jasperContexts.containsKey(className)){
            return jasperContexts.get(className).getJasperContext();
        }else{
            throw new Error("Initialization hasn't been done yet");
        }
    }
     
    public static JasperReport getCompiledReport(String reportName)throws JRException{
        
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        
        if (!jasperContexts.containsKey(className)){
            throw new Error("No context has been found");
        }        
        
        int lastIndexOfDot = reportName.lastIndexOf(".");
        
        String extenstion = reportName.substring(lastIndexOfDot, reportName.length());
        
        Context ctx = jasperContexts.get(className);
        
        if (extenstion.equalsIgnoreCase(".jrxml")){
            
            JasperDesign jasperDesign = JRXmlLoader.load(ctx.getJasperContext(), ctx.getContextClass().getResourceAsStream((ctx.getContextPath() + "/" + reportName).replace("//", "/")));
            
            JasperCompileManager compilerManager = JasperCompileManager.getInstance(ctx.getJasperContext());
            
            return compilerManager.compile(jasperDesign);
            
        }else if (extenstion.equalsIgnoreCase(".jasper")){
            
            return (JasperReport) JRLoader.loadObject(ctx.getJasperContext(), ctx.getContextClass().getResourceAsStream((ctx.getContextPath() + "/" + reportName).replace("//", "/")));
            
        }else{
            throw new Error("Invalid report name");
        }
        
    }

    private static class Context {

        private LocalJasperReportsContext jasperContext;
        private String contextPath;
        private Class contextClass;

        public Context(Class aClass, String packageName) {
            
            contextClass = aClass;

            jasperContext = new LocalJasperReportsContext(DefaultJasperReportsContext.getInstance());

            jasperContext.setClassLoader(aClass.getClassLoader());

            contextPath = getPath(packageName);
            
            System.out.println(contextPath);

            URI jasperURI = null;
            try {
                jasperURI = aClass.getResource(contextPath).toURI();
            } catch (URISyntaxException ex) {
                Logger.getLogger(JasperUtil.class.getName()).log(Level.SEVERE, null, ex);
                throw new Error("Invalid report context path");
            } catch (Exception ex){
                Logger.getLogger(JasperUtil.class.getName()).log(Level.SEVERE, null, ex);
                throw new Error("Invalid report context path");
            }

            File jasperDIR = new File(jasperURI);

            FileResolver jasperFileResolver = new SimpleFileResolver(jasperDIR);

            jasperContext.setFileResolver(jasperFileResolver);

        }

        private String getPath(String packageName) {
            return "/" + packageName.replace(".", "/");
        }

        /**
         * @return the jasperContext
         */
        public LocalJasperReportsContext getJasperContext() {
            return jasperContext;
        }

        /**
         * @return the contextPath
         */
        public String getContextPath() {
            return contextPath;
        }

        /**
         * @return the contextClass
         */
        public Class getContextClass() {
            return contextClass;
        }

    }
}
