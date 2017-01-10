package com.dotcms.osgi;



import com.dotcms.repackage.org.osgi.framework.BundleContext;
import com.dotmarketing.osgi.GenericBundleActivator;

public class Activator extends GenericBundleActivator {


  final static String SERVLET_NAME = "screenShotServlet";


  public void start(BundleContext bundleContext) throws Exception {

    System.out.println("Activating!");
    new TomcatServletFilterUtil().addServlet(SERVLET_NAME, new ScreenShotServlet(), "/screenShot");


  }



  public void stop(BundleContext context) {
    System.out.println("stoppin!!");
    new TomcatServletFilterUtil().removeServlet(SERVLET_NAME);


  }


}


