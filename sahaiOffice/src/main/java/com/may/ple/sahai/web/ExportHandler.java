package com.may.ple.sahai.web;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.may.ple.sahai.service.ExportService;

@Component
@Path("/exportHandler")
public class ExportHandler {
	private static final Logger log = Logger.getLogger(ExportHandler.class.getName());
	private ExportService exportService;
	
	@Autowired
	public ExportHandler(ExportService exportService) {
		this.exportService = exportService;
	}
	
	@GET
	@Path("/getPdfFile")
	@Produces("application/pdf")
	public Response getPdfFile(@QueryParam("itemId") final String itemId, @QueryParam("isVat") final String isVat) throws Exception {
		
		try {
			final byte[] data = exportService.pdfExport(itemId, isVat);
		
			StreamingOutput stream = new StreamingOutput() {
				@Override
				public void write(OutputStream os) throws IOException, WebApplicationException {
					OutputStream out = null;
					ByteArrayInputStream in = null;
					
					try {
						log.debug("Start");
						
						if(itemId == null) throw new Exception("taskId is null");
						
						log.debug("isVat : " + isVat);
						
						
						in = new ByteArrayInputStream(data);
						out = new BufferedOutputStream(os);
						int bytes;
						
						while ((bytes = in.read()) != -1) {
							out.write(bytes);
						}
						
						log.debug("End");
					} catch (Exception e) {
						log.error(e.toString());
					} finally {
						if(in != null) in.close();			
						if(out != null) out.close();			
					}	
				}
			};
			
			String fileName = exportService.getDocNo() + ".pdf";	
			ResponseBuilder response = Response.ok(stream);
			response.header("Content-Disposition", "attachment; filename=" + fileName);
			return response.build();
		
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}

}
