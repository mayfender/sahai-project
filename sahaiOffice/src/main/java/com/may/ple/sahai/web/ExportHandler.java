/*package com.may.ple.sahai.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.may.ple.sahai.office.action.TaskAction;
import com.may.ple.sahai.office.service.ExportService;

@WebServlet("/exportHandler")
public class ExportHandler extends HttpServlet {
	private static final long serialVersionUID = 517824762830486886L;
	private static final Logger log = Logger.getLogger(TaskAction.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		OutputStream out = null;
		ByteArrayInputStream in = null;
		
		try {
			log.debug("Start");
			
			String itemId = req.getParameter("itemId");
			if(itemId == null) throw new Exception("taskId is null");
			
			String isVat = req.getParameter("isVat");
			log.debug("isVat : " + isVat);
			
			ExportService export = new ExportService();
			byte[] data = export.pdfExport(itemId, isVat);
			in = new ByteArrayInputStream(data);
			
			String pdfFileName = export.getDocNo() + ".pdf";	
			resp.setContentType("application/pdf");
			resp.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
			resp.setContentLength((int) data.length);
	
			out = resp.getOutputStream();
			int bytes;
			
			while ((bytes = in.read()) != -1) {
				out.write(bytes);
			}
			
			log.debug("End");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(in != null) in.close();			
			if(out != null) out.close();			
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Post method");
		doGet(req, resp);
	}

}
*/