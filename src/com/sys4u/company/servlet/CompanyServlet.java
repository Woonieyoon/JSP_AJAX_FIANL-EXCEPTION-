package com.sys4u.company.servlet;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sys4u.company.command.Command;
import com.sys4u.company.command.CommandFactory;
import com.sys4u.company.command.Constants;
import com.sys4u.company.exception.CommandExecutionException;
import com.sys4u.company.exception.IllegalParameterException;

public class CompanyServlet extends HttpServlet {
	private static final long serialVersionUID = -291178543531058657L;
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServlet.class);

	private CommandFactory commandFactory;
	private DataSource dataSource;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init();

		try {
			dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/orcl");
		} catch (NamingException e) {
			LOGGER.error("", e);
		}

		String commandPropertiesFilePath = servletConfig.getServletContext()
				.getRealPath("/WEB-INF/conf/command.properties");
		commandFactory = new CommandFactory(commandPropertiesFilePath);
		commandFactory.init();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		String uri = req.getRequestURI();
		String viewName = "/WEB-INF/jsp/error/404.jsp";
		//req.setAttribute("contextPath", req.getContextPath());
		
		LOGGER.debug("uri : " + uri);
		Command command = commandFactory.createCommand(uri);
		if (command != null) {
			try {
				viewName = executeCommand(command, req, resp);
			}catch(IllegalParameterException ipe) {
				req.setAttribute("exception", ipe);
				viewName = "/WEB-INF/jsp/error/400.jsp";
			}
		}

		try {
			req.getRequestDispatcher(viewName).forward(req, resp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//forwardORedirect(req, resp, viewName);

	}

//	private void forwardORedirect(HttpServletRequest req, HttpServletResponse resp, String viewName) {
//
//		try {
//
//			if (viewName.startsWith(REDIRECT_PREFIX)) {
//				resp.sendRedirect(viewName.substring(REDIRECT_PREFIX.length()));
//			} else {
//				req.getRequestDispatcher(viewName).forward(req, resp);
//			}
//		} catch (Exception e) {
//			try {
//				throw new ServletException(e);
//			} catch (ServletException e1) {
//				e1.printStackTrace();
//			}
//		}
//	}

	private String executeCommand(Command command, HttpServletRequest req, HttpServletResponse resp) {
		String viewName = "/WEB-INF/jsp/error/500.jsp";
		//ajax 에러를 위함
		boolean isAjaxRequest = "true".equals(req.getHeader("X-XMLHttpRequest"));
		try {
			command.setConnection(dataSource.getConnection());
			viewName = command.execute(req, resp);
		} catch (Exception e) {
			//ajax 에러 처리를 위함
			String jsonResponse = "{\"errorText\" : \""+ e.getMessage() +"\" }";
			req.setAttribute("json", jsonResponse);
			resp.setStatus(500);
			System.out.println("응답:"+jsonResponse);
			LOGGER.error("", e);
			//throw new CommandExecutionException(e);
		} finally {
			command.destroy();
		}
		if(isAjaxRequest) {
			viewName = Constants.AJAX_List_VIEW;
		}

		return viewName;
	}
}
