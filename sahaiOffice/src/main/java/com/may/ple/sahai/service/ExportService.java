package com.may.ple.sahai.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.may.ple.sahai.domain.BuySaleTaskReq;
import com.may.ple.sahai.pdf.PurchaseOrderImpl;
import com.may.ple.sahai.pdf.QuotationImpl;
import com.may.ple.sahai.pdf.QuotationRequestImpl;
import com.may.ple.sahai.pdf.VatImpl;
import com.may.ple.sahai.repository.TaskDao;
import com.may.ple.sahai.utils.DocTypeConstantUtil;

@Service
public class ExportService {
	private static final Logger log = Logger.getLogger(ExportService.class.getName());
	private String docNo;
	private TaskDao taskDao;
	
	@Autowired
	public ExportService(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	public byte[] pdfExport(String taskId, String isVat) throws Exception {
		BuySaleTaskReq taskReq = taskDao.findTask(taskId);
		byte[] data;
		
		if(!StringUtils.isBlank(isVat) && isVat.trim().equals("1")) {
			log.debug("Gen pdf file to VAT format.");
			taskReq.setCreatedDateTime(taskReq.getVatObj().getVatCreatedDateTime());
			taskReq.setDocNo(taskReq.getVatObj().getVatDocNo());
			data = new VatImpl(taskReq).genPdf();
		} else {
			switch (DocTypeConstantUtil.parseType(Integer.parseInt(taskReq.getDocType()))) {
			case PURCHASE_ORDER:
				log.debug("PURCHASE_ORDER");
				data = new PurchaseOrderImpl(taskReq).genPdf();
				break;
			case QUOTATION:
				log.debug("QUOTATION");
				data = new QuotationImpl(taskReq).genPdf();
				break;
			case QUOTATION_REQUEST:
				log.debug("QUOTATION_REQUEST");
				data = new QuotationRequestImpl(taskReq).genPdf();
				break;
			default:
				log.error("Out of switch case");
				throw new Exception("Out of switch case");
			}
		}
		
		docNo = taskReq.getDocNo();
		
		return data;
	}

	public String getDocNo() {
		return docNo;
	}

}
