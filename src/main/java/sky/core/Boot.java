package sky.core;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Server;
import org.apache.catalina.Wrapper;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.JarScanFilter;
import org.apache.tomcat.util.scan.StandardJarScanFilter;
import org.apache.tomcat.util.scan.StandardJarScanner;

import java.io.File;

import javax.servlet.ServletException;


/**
 * @program: ExUpload
 * @description:
 * @author: Zhaoziqi
 * @create: 2018-07-30 11:37
 **/
public class Boot {
    public static final String path0 = System.getProperty("user.dir") + File.separator;
    public static final String path = "/Users/zhaoziqi/Documents/Study/JAVA/IntelliJProject/ExUpload/target/classes/";
    public static final String WEB_APP = path + "webapp";
    public static final int port=7070;

    public static void main(String[] args) throws ServletException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        Host host = tomcat.getHost();
        System.out.println("path = " + path);
        Context context = tomcat.addWebapp("", path + "webapp");
        StandardContext c = (StandardContext) context;
//        c.setDefaultWebXml(path + "config/web.xml");
        StandardJarScanner jarScanner = new StandardJarScanner();
        jarScanner.setScanManifest(false);
        jarScanner.setScanClassPath(false);
        jarScanner.setScanBootstrapClassPath(false);
        c.setJarScanner(jarScanner);
        c.setTldValidation(false);
        JarScanFilter jarScanFilter = jarScanner.getJarScanFilter();

        if (jarScanFilter instanceof StandardJarScanFilter) {
            StandardJarScanFilter scanner = (StandardJarScanFilter) jarScanFilter;
            scanner.setDefaultPluggabilityScan(false);
            scanner.setDefaultTldScan(false);
            scanner.setTldSkip("*");
            scanner.setPluggabilitySkip("*");
        }
        Server server = tomcat.getServer();
        try {
            Wrapper wrapper = Tomcat.addServlet(c, "MServlet",
                    "sky.web.MServlet");
            wrapper.addMapping("/MServlet");
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        server.await();
    }
}
