package es.upm.dit.isst.tfg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.blobstore.BlobstoreServicePb.BlobstoreService;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.t4.dao.TFGDAO;
import es.upm.dit.isst.t4.dao.TFGDAOImpl;
import es.upm.dit.isst.t4.model.TFG;

@SuppressWarnings("serial")
public class MostrarMemoriaServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		TFGDAO dao = TFGDAOImpl.getInstance();
		String user = (String) req.getSession().getAttribute("user");
		if(user != ""){
			String autor = req.getParameter("autor");
			TFG tfg2 = dao.readAutorTFG(autor).get(0);
		    com.google.appengine.api.blobstore.BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
			BlobKey blobKey = new BlobKey(tfg2.getFichero());
			blobstoreService.serve(blobKey,resp);				
		}
	}
}
