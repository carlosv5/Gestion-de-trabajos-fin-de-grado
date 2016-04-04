package es.upm.dit.isst.tfg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.t4.dao.TFGDAO;
import es.upm.dit.isst.t4.dao.TFGDAOImpl;
import es.upm.dit.isst.t4.model.TFG;

public class AceptarTutorServlet extends HttpServlet {

public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	TFGDAO dao = TFGDAOImpl.getInstance();
	String user = (String) req.getSession().getAttribute("user");
	if(user != ""){
			String autor = req.getParameter("autor");
			TFG tfg2 = dao.readAutorTFG(autor).get(0);
		
		tfg2.setSecretario(req.getParameter("tutor"));
		tfg2.setEstado(2);
		dao.update(tfg2);
	
	}
	resp.sendRedirect("/isst_tfg_t4");
	}

}
