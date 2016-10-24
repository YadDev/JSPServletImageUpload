package in.dev.imgUpload;
import java.io.BufferedReader;
import java.io.File;  
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
/**
 * Servlet implementation class Uploadservlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String UPLOAD_DIRECTORY = "";
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(ServletFileUpload.isMultipartContent(request)){
			
			ServletContext context = request.getServletContext();
			UPLOAD_DIRECTORY=context.getRealPath("/img/");
            try {
            	String fname = null;
            	String fsize = null;
            	String ftype = null;
            	String loc = null;
                @SuppressWarnings("unchecked")
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                for(FileItem item : multiparts){
                    if(!item.isFormField()){
                        fname = new File(item.getName()).getName();
                       System.out.println("File name is: " + fname);
                        fsize = new Long(item.getSize()).toString();
                        ftype = item.getContentType();
                        fname="dev"+".jpeg";
                        item.write( new File(UPLOAD_DIRECTORY + File.separator + fname));
                       

                    }else{
                    	  String fieldname = item.getFieldName();
                          String fieldvalue = item.getString();
                    	  System.out.println("Name "+fieldname+" \tValue "+fieldvalue);
                    }
                }
               //File uploaded successfully
//                try{
//                	  URL whatismyip = new URL("http://checkip.amazonaws.com");
//                      BufferedReader in = null;
//                      try {
//                          in = new BufferedReader(new InputStreamReader(
//                                  whatismyip.openStream()));
//                          String ip = in.readLine();
//                          request.setAttribute("loc", ip);
//                      } finally {
//                          if (in != null) {
//                              try {
//                                  in.close();
//                              } catch (IOException e) {
//                                  e.printStackTrace();
//                              }
//                          }
//                      }
//                }
//                catch(Exception e){
//                	e.printStackTrace();
//                }
              
               request.setAttribute("message", "File Uploaded Successfully");
               request.setAttribute("name", fname);
               request.setAttribute("size", fsize);
               request.setAttribute("type", ftype);
             
            } catch (Exception ex) {
               request.setAttribute("message", "File Upload Failed due to " + ex);
            }          
         
        }else{
            request.setAttribute("message","Sorry this Servlet only handles file upload request");
        }
    
        request.getRequestDispatcher("/result.jsp").forward(request, response);
     
    }		
		// TODO Auto-generated method stub
	}