package com.dotcms.screenshot;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.dotmarketing.image.filter.ResizeImageFilter;

public class ChromeScreenShot {
  final String url;
  final int width;
  final int height;
  //final String userAgent;
  
  
  
  private static ChromeDriverService service;

  static {
    System.setProperty("webdriver.chrome.driver", "/Users/will/git/chromedriver");
    service = new ChromeDriverService.Builder().usingAnyFreePort().build();
  }

  public ChromeScreenShot(String url, int width, int height) throws IOException {
    if (!service.isRunning()) {
      service.start();
    }
    this.width = width;
    this.url = url;
    this.height = height;
  }



  public File screenshot() throws IOException {

    DesiredCapabilities capabilities = DesiredCapabilities.chrome();

    //String userAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5376e Safari/8536.25";
   // Map<String, String> mobileEmulation = new HashMap<String, String>();
   // mobileEmulation.put("deviceName", "Google Nexus 5");

    ChromeOptions co = new ChromeOptions();

    co.addArguments("--headless");
    capabilities.setCapability(ChromeOptions.CAPABILITY, co);
    WebDriver driver = new RemoteWebDriver(service.getUrl(), capabilities);
    try {
      driver.manage().window().setSize(new Dimension(width, height));
      driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
      driver.get(url);
      File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
      if(true) return file;
      Map<String, String[]> parameters = new HashMap<>();
      parameters.put("resize_w", new String[] {String.valueOf(width)});
      parameters.put("assetInodeOrIdentifier", new String[] {"asdsaddasds" + width});

      return new ResizeImageFilter().runFilter(file, parameters);


    } finally {
      driver.quit();
    }
  }
}

