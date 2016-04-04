package es.upm.dit.isst.tfg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.t4.dao.TFGDAO;
import es.upm.dit.isst.t4.dao.TFGDAOImpl;
import es.upm.dit.isst.t4.model.TFG;

@SuppressWarnings("serial")
public class CompruebaServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		TFGDAO dao = TFGDAOImpl.getInstance();
		String autor = (String) req.getSession().getAttribute("user");
		String titulo = req.getParameter("titulo");
		String resumen = req.getParameter("resumen");
		String tutor = req.getParameter("tutor");
		dao.create(autor, titulo, resumen, tutor, "", "", 1);
		System.out.println("Creado TFG por el usuario " + autor);
		resp.sendRedirect("/isst_tfg_t4");

	}
	

}
