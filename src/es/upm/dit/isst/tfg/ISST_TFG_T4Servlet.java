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

@SuppressWarnings("serial")
public class ISST_TFG_T4Servlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		String user = "";
		if(req.getUserPrincipal() != null){
			user = req.getUserPrincipal().getName();
			url = userService.createLogoutURL(req.getRequestURI());
			urlLinktext = "Logout";
		}
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);

		//resp.getWriter().println("<p>Pulsa<a href="+url+">"+urlLinktext+"</a>.</p>");
		
		
		TFGDAO dao = TFGDAOImpl.getInstance();
		//dao.delete("test@example.com");

		//dao.create("test@example.com", "suTitulo", "Hola 2", "Jambrina@Jambrina.com", "Huecas@Huecas.com", "", 2);
		
		//Inicialización TFG
		TFG tfg = null;
		req.getSession().setAttribute("tfg", tfg);
		List<TFG> tfgs = Collections.EMPTY_LIST;
		req.getSession().setAttribute("tfgs", new ArrayList<TFG>(tfgs));

		
		//Comprobaciones de usuario
		if(user != ""){
			//Alumno
			if(!dao.readAutorTFG(user).isEmpty()){
				tfg = dao.readAutorTFG(user).get(0);
				req.getSession().setAttribute("tfg", tfg);
			}
			
			else {if(!dao.readTutorTFG(user).isEmpty()){
				tfgs = dao.readTutorTFG(user);
				req.getSession().setAttribute("tfgs", new ArrayList<TFG>(tfgs));
			}
			if(!dao.readSecretarioTFG(user).isEmpty()){
				tfgs = dao.readSecretarioTFG(user);
				req.getSession().setAttribute("tfgs", new ArrayList<TFG>(tfgs));
			}
			}
		}

		for(TFG tfg1 : dao.read()){
			resp.getWriter().println(tfg1);
		}
		resp.sendRedirect("/MostrarTFGView.jsp");

		
	}
}
