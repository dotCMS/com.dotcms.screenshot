package com.dotcms.screenshot;


import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.RequestHeaders;
import com.machinepublishers.jbrowserdriver.Settings;
import com.machinepublishers.jbrowserdriver.Timezone;
import com.machinepublishers.jbrowserdriver.UserAgent;

public class JBrowserScreenShot {
  final String url;
  final int width;
  final int height;
  final String virtualHostName;

  static {

  }

  public JBrowserScreenShot(String url, String virtualHostName, int width, int height) throws IOException {

    this.width = width;
    this.url = url;
    this.height=height;
    this.virtualHostName=virtualHostName;
    System.out.println(this);
  }



  @Override
  public String toString() {
    return "JBrowserScreenShot [url=" + url + ", width=" + width + ", height=" + height + ", virtualHostName="
        + virtualHostName + "]";
  }



  public File screenshot() throws Exception {

    
    LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
    headers.put("Host", virtualHostName);
    
    // You can optionally pass a Settings object here,
    // constructed using Settings.Builder
    JBrowserDriver driver = new JBrowserDriver(Settings.builder().
      timezone(Timezone.AMERICA_NEWYORK).build());

    // This will block for the page load and any
    // associated AJAX requests
    driver.get("http://dotcms.com");
    System.out.println("made it here: " + this);
    /*
    
    System.out.println("made it here: " + 1);
    
    RequestHeaders requestHeaders = new RequestHeaders(headers);
    System.out.println("made it here: " + 2);
    
    Settings settings = new Settings.Builder()
      .headless(true)
      .hostnameVerification(false)
      .requestHeaders(requestHeaders)
      .timezone(Timezone.AMERICA_NEWYORK)
      .userAgent(UserAgent.CHROME)
      .javascript(true)

      .screen(new Dimension(width, height)).build();
        

    

    
    
    
    
    System.out.println("made it here: " + this);
    
    JBrowserDriver driver = new JBrowserDriver(settings);
    */
    File file = null;
    try{
      
      System.out.println("made it!");
      
      driver.get(url);
      file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      return file;

    } finally {
      driver.quit();
      //if(file!=null)file.deleteOnExit();
    }
  }
}

