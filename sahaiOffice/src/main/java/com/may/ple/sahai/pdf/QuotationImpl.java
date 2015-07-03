package com.may.ple.sahai.pdf;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.may.ple.sahai.domain.BuySaleTaskReq;

public class QuotationImpl extends PdfGenerator {
	
	public QuotationImpl(BuySaleTaskReq taskReq) {
		super(taskReq);
		
		List<Phrase> docNames = new ArrayList<Phrase>();
		
		Phrase docName = new Phrase();
		docName.add(new Chunk("ใบเสนอราคา\nQUOTATION", angsaBold14));
		docNames.add(docName);
		
		setDocName(docNames);
	}

	@Override
	protected PdfPTable partnerInfo(BuySaleTaskReq taskReq) throws Exception {
    	PdfPTable pdfPTable = null;
    	
    	try {
    		pdfPTable = new PdfPTable(6);
    		pdfPTable.setSpacingBefore(5);
    		pdfPTable.setSpacingAfter(5);
    		pdfPTable.setWidthPercentage(100f);
            pdfPTable.setWidths(new float[]{0.4f, 0.6f, 0.3f, 0.5f, 0.3f, 0.5f});
            
            PdfPCell ch = new PdfPCell(new Phrase("ชื่อบริษัท : ", angsa14));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getCompanyName()), angsa14));
            ch.setColspan(5);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            //--------------: Row :---------------------
            
            ch = new PdfPCell(new Phrase("ที่อยู่ : ", angsa14));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getAddress()), angsa14));
            ch.setColspan(5);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            //--------------: Row :---------------------
            
            ch = new PdfPCell(new Phrase("ติดต่อ : ", angsa14));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getContactPersonName()), angsa14));
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase("โทรศัพท์ : ", angsa14));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getContactPersonTel()), angsa14));
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase("แฟกซ์ : ", angsa14));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getContactPersonFax()), angsa14));
          ch.setBorder(Rectangle.NO_BORDER);	
            pdfPTable.addCell(ch);
            
            //--------------: Row :---------------------
            
            ch = new PdfPCell(new Phrase("ขอเสนอราคาโดย  : ", angsa14));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getUserName()), angsa14));
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase("โทรศัพท์ : ", angsa14));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getUserTel()), angsa14));
            ch.setBorder(Rectangle.NO_BORDER);	
            ch.setColspan(3);
            pdfPTable.addCell(ch);
            
    		return pdfPTable;
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}

	@Override
	protected void endPageSign(PdfWriter writer, Document document) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
