package com.may.ple.sahai.pdf;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.may.ple.sahai.domain.BuySaleTaskReq;

public class PurchaseOrderImpl extends PdfGenerator {
	
	public PurchaseOrderImpl(BuySaleTaskReq taskReq) {
		super(taskReq);
		
		List<Phrase> docNames = new ArrayList<Phrase>();
		
		Phrase docName = new Phrase();
		docName.add(new Chunk("ใบสั่งซื้อสินค้า\nPURCHASE ORDER", angsaBold14));		
		docNames.add(docName);
		
		setDocName(docNames);
		marginBottom = 117;
	}

	@Override
	protected PdfPTable partnerInfo(BuySaleTaskReq taskReq) throws Exception {
    	PdfPTable pdfPTable = null;
    	
    	try {
    		pdfPTable = new PdfPTable(8);
    		pdfPTable.setSpacingBefore(5);
    		pdfPTable.setSpacingAfter(5);
    		pdfPTable.setWidthPercentage(100f);
            pdfPTable.setWidths(new float[]{0.25f, 0.27f, 0.2f, 0.27f, 0.2f, 0.27f, 0.37f, 0.5f});
            
            PdfPCell ch = new PdfPCell(new Phrase("ชื่อบริษัท : ", angsa14));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getCompanyName()), angsa14));
            ch.setColspan(7);
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
            
            ch = new PdfPCell(new Phrase("เงื่อนใขการชำระเงิน : ", angsaBold14));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getPayCondition()), angsa14));
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
            
            ch = new PdfPCell(new Phrase("สถานที่ส่งของ : ", angsaBold14));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getPlaceToSend()), angsa14));
            ch.setBorder(Rectangle.NO_BORDER);	
            pdfPTable.addCell(ch);
            
            //--------------: Row :---------------------
            
            ch = new PdfPCell(new Phrase("สั่งซื้อโดย  : ", angsa14));
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
            
            ch = new PdfPCell(new Phrase("กำหนดส่งของ : ", angsaBold14));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getDateToSend()), angsa14));
            ch.setBorder(Rectangle.NO_BORDER);	
            pdfPTable.addCell(ch);
            
    		return pdfPTable;
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}

	@Override
	public void endPageSign(PdfWriter writer, Document document) throws Exception {
		PdfPTable table = new PdfPTable(3);
		table.setTotalWidth(PageSize.A4.getWidth() - (document.leftMargin()+ document.rightMargin()));
		table.setWidths(new float[]{1f, 1f, 1f});
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell ch = new PdfPCell(new Phrase("ผู้สั่งซื้อ...................................................", angsa14));
        ch.setHorizontalAlignment(Element.ALIGN_LEFT);
        ch.setVerticalAlignment(Element.ALIGN_BOTTOM);
        ch.setUseDescender(true);
        ch.setFixedHeight(40);
        table.addCell(ch);
        
        ch = new PdfPCell(new Phrase("ผู้ตรวจสอบ...................................................", angsa14));
        ch.setUseDescender(true);
        ch.setHorizontalAlignment(Element.ALIGN_LEFT);
        ch.setVerticalAlignment(Element.ALIGN_BOTTOM);
        ch.setFixedHeight(40);
        table.addCell(ch);
        
        ch = new PdfPCell(new Phrase("ผู้อนุมัติ...................................................", angsa14));
        ch.setUseDescender(true);
        ch.setHorizontalAlignment(Element.ALIGN_LEFT);
        ch.setVerticalAlignment(Element.ALIGN_BOTTOM);
        ch.setFixedHeight(40);
        table.addCell(ch);
        
        table.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin() - 64.5f, writer.getDirectContent());
	}

}
