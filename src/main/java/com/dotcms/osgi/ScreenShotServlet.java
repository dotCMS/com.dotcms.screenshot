package com.dotcms.osgi;

import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.dotcms.screenshot.JBrowserScreenShot;
import com.dotmarketing.beans.Host;
import com.dotmarketing.beans.Identifier;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.business.DotStateException;
import com.dotmarketing.business.web.WebAPILocator;
import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.dotmarketing.portlets.htmlpageasset.model.HTMLPageAsset;
import com.liferay.portal.model.User;


public class ScreenShotServlet extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    String id = req.getParameter("id");
    System.err.println("Screenshot!!!!!");
    try {
      User user = APILocator.getUserAPI().getSystemUser();
      //user = com.liferay.portal.util.PortalUtil.getUser(req);

      
      Contentlet con = APILocator.getContentletAPI().find(id, user, false);


      HTMLPageAsset page = APILocator.getHTMLPageAssetAPI().fromContentlet(con);
      Identifier ident = APILocator.getIdentifierAPI().find(con);
      Host host = APILocator.getHostAPI().find(ident.getHostId(), user, false);

      String url = "http" + ((req.isSecure()) ? "s" : "") + "://" + req.getServerName() + ":" + req.getServerPort()
          + ident.getPath();

      String virtualHostName = host.getHostname();

      int width = 500;
      try {
        width = Integer.valueOf(req.getParameter("w"));
      } catch (Exception e) {
        System.err.println("e" + e);
      }

      int height = 500;
      try {
        height = Integer.valueOf(req.getParameter("h"));
      } catch (Exception e) {
        System.err.println("e2" + e);
      }
      ServletOutputStream out = resp.getOutputStream();
      FileInputStream in =
          new FileInputStream(new JBrowserScreenShot(url, virtualHostName, width, height).screenshot());
      IOUtils.copy(in, out);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      e.printStackTrace();
      resp.getWriter().println(e.getMessage());
      e.printStackTrace(resp.getWriter());
      //throw new DotStateException(e.getMessage(), e);
    }
  }



}
