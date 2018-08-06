package sky.core;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.Wrapper;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;

import sky.web.MServlet;

/**
 * @program: ExUpload
 * @description:
 * @author: Zhaoziqi
 * @create: 2018-07-30 15:53
 **/
public class Boot2 {

    private static final String root = Boot2.class.getResource("/").getPath();
    public static final String root2 = "/Users/zhaoziqi/Documents/Study/JAVA/IntelliJProject/ExUpload/src/main/resources/webapp";
    private static final int port = 7071;
    private static final String docBase = root + "webapp";
    private static final String docBase2 = root2 + "webapp";

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        Host host = tomcat.getHost();
        host.setAppBase(docBase2);
        String appBase = host.getAppBase();
        System.out.println("appBase = " + appBase);
        System.out.println("docBase = " + docBase);
        System.out.println("port = " + port);
        String contextPath = "";
        StandardContext context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());
        context.setDefaultWebXml("/Users/zhaoziqi/Documents/Study/JAVA/IntelliJProject/ExUpload/src/main/resources/config/web.xml");
        Wrapper wrapper = Tomcat.addServlet(context, "MServlet", new MServlet());
        wrapper.addMapping("/MServlet");
        host.addChild(context);
        context.setDocBase(docBase);
        context.addWelcomeFile("/Users/zhaoziqi/Documents/Study/JAVA/IntelliJProject/ExUpload/src/main/resources/webapp/up.html");
        tomcat.start();
        tomcat.getServer().await();
    }
}
