package com.may.ple.sahai.pdf;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.may.ple.sahai.domain.BuySaleTaskReq;

public class VatImpl extends PdfGenerator {
    
	public VatImpl(BuySaleTaskReq taskReq) {
		super(taskReq);
		
		List<Phrase> docNames = new ArrayList<Phrase>();
		
		Phrase docName = new Phrase();
		Font font = new Font(angsaBold14);
		font.setColor(BaseColor.RED);
		
		Chunk original = new Chunk("ต้นฉบับ", font);
		Chunk copy = new Chunk("สำเนา", font);
		
		docName.add(original);
		docName.add(new Chunk("ใบกำกับภาษี / ใบแจ้งหนี้ / ใบส่งของ\nTax invoice / Invoices / Invoice (ORIGINAL)", angsaBold14));		
		docNames.add(docName);
		
		docName = new Phrase();
		docName.add(copy);
		docName.add(new Chunk("ใบกำกับภาษี / ใบแจ้งหนี้ / ใบส่งของ\nTax invoice / Invoices / Invoice (COPY)", angsaBold14));
		docNames.add(docName);
		
		docName = new Phrase();
		docName.add(original);
		docName.add(new Chunk("ใบเสร็จรับเงิน\nReceipt (ORIGINAL)", angsaBold14));
		docNames.add(docName);
		
		docName = new Phrase();
		docName.add(copy);
		docName.add(new Chunk("ใบเสร็จรับเงิน\nReceipt (COPY)", angsaBold14));
		docNames.add(docName);
		
		setDocName(docNames);
		marginBottom = 215;
	}

	@Override
	protected PdfPTable partnerInfo(BuySaleTaskReq taskReq) throws Exception {
		PdfPTable pdfPTable = null;
    	
    	try {
    		pdfPTable = new PdfPTable(4);
    		pdfPTable.setSpacingBefore(5);
    		pdfPTable.setSpacingAfter(5);
    		pdfPTable.setWidthPercentage(100f);
            pdfPTable.setWidths(new float[]{0.6f, 2f, 0.7f, 0.7f});
            
            PdfPCell ch = new PdfPCell(new Phrase("นามผู้ซื้อ Sold To : ", angsaBold12));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setVerticalAlignment(Element.ALIGN_MIDDLE);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getCompanyName()), angsa12));
            ch.setBorder(Rectangle.NO_BORDER);
            ch.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfPTable.addCell(ch);
            
            //--------------: Row :---------------------
            ch = new PdfPCell(new Phrase("เงื่อนใขการชำระเงิน : \n (Term Of Payment) ", angsaBold12));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getVatObj().getPayCondition()), angsa12));
            ch.setBorder(Rectangle.NO_BORDER);	
            pdfPTable.addCell(ch);
            
            //--------------: Row :---------------------
            
            ch = new PdfPCell(new Phrase("ที่อยู่ Address : ", angsaBold12));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setRowspan(2);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getVatObj().getAddress()), angsa12));
            ch.setBorder(Rectangle.NO_BORDER);
            ch.setRowspan(2);
            pdfPTable.addCell(ch);
            
            //--------------: Row :---------------------
            
            ch = new PdfPCell(new Phrase("กำหนดชำระเงิน : \n (Due Date)", angsaBold12));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getVatObj().getPayDate()), angsa12));
            ch.setBorder(Rectangle.NO_BORDER);	
            pdfPTable.addCell(ch);
            
            //--------------: Row :---------------------
            
            ch = new PdfPCell(new Phrase("เลขที่ใบสั่งสินค้า : \n (P.O. NO.)", angsaBold12));
            ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase(emp(taskReq.getVatObj().getPoNo()), angsa12));
            ch.setBorder(Rectangle.NO_BORDER);	
            pdfPTable.addCell(ch);
            
    		return pdfPTable;
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
	}

	@Override
	protected void endPageSign(PdfWriter writer, Document document) throws Exception {
		PdfPTable table = new PdfPTable(3);
		table.setTotalWidth(PageSize.A4.getWidth() - (document.leftMargin()+ document.rightMargin()));
		table.setWidths(new float[]{1f, 1f, 1f});
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell ch = new PdfPCell(new Phrase("ได้รับสินค้าตามรายการถูกต้องแล้ว", angsaBold14));
        ch.setHorizontalAlignment(Element.ALIGN_CENTER);
        ch.setColspan(2);
        table.addCell(ch);
        
        ch = new PdfPCell(new Phrase("ผู้มีอำนาจลงนาม", angsaBold14));
        ch.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(ch);
        
        /*----------------------------*/
        
        ch = new PdfPCell(new Phrase("......................................\nผู้รับสินค้า\n(........../........../..........)", angsa14));
        ch.setHorizontalAlignment(Element.ALIGN_CENTER);
        ch.setUseDescender(true);
        ch.setVerticalAlignment(Element.ALIGN_BOTTOM);
        ch.setFixedHeight(60);
        table.addCell(ch);
        
        ch = new PdfPCell(new Phrase("......................................\nผู้ส่งสินค้า\n(........../........../..........)", angsa14));
        ch.setUseDescender(true);
        ch.setHorizontalAlignment(Element.ALIGN_CENTER);
        ch.setVerticalAlignment(Element.ALIGN_BOTTOM);
        ch.setFixedHeight(60);
        table.addCell(ch);
        
        ch = new PdfPCell(new Phrase("......................................\nหจก. สหาย  ซัพพลาย\n(........../........../..........)", angsa14));
        ch.setUseDescender(true);
        ch.setHorizontalAlignment(Element.ALIGN_CENTER);
        ch.setVerticalAlignment(Element.ALIGN_BOTTOM);
        ch.setFixedHeight(60);
        table.addCell(ch);
        
        /*-------------: Check Box :-------------*/
        
        PdfContentByte canvas = writer.getDirectContent();
        Rectangle rect = new Rectangle(110, 45, 130, 60);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(0.6f);
        canvas.rectangle(rect);
        
        canvas = writer.getDirectContent();
        rect = new Rectangle(190, 45, 210, 60);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderWidth(0.6f);
        canvas.rectangle(rect);
        
        /*-------------: Check Box :-------------*/
        
        String msg = "ชำระเป็น                   เงินสด                  เช็คธนาคาร................................................" +
        		"\nสาขา...............................................................  เลขที่เช็ค....................................................." +
        		"\nลงวันที่............................................................  จำนวนเงิน...................................................";
        
        ch = new PdfPCell(new Phrase(msg, angsa14));
        ch.setColspan(2);
        ch.setHorizontalAlignment(Element.ALIGN_CENTER);
        ch.setUseDescender(true);
        ch.setVerticalAlignment(Element.ALIGN_BOTTOM);
        ch.setFixedHeight(60);
        table.addCell(ch);
        
        ch = new PdfPCell(new Phrase("......................................\nผู้รับเงิน\n(........../........../..........)", angsa14));
        ch.setUseDescender(true);
        ch.setHorizontalAlignment(Element.ALIGN_CENTER);
        ch.setVerticalAlignment(Element.ALIGN_BOTTOM);
        ch.setFixedHeight(60);
        table.addCell(ch);
        
        table.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin() - 64.5f, writer.getDirectContent());
	}

	@Override
	protected PdfPCell createDesc() throws Exception {
		String msg = "1. สินค้าตามรายการข้างต้น หากมีการเสียหาย ชำรุดหรือส่งของผิดความต้องการ โปรดแจ้งให้ทราบภายใน  3 วัน  นับจากวันที่ได้รับสินค้า มิฉะนั้นทางห้างฯ จะไม่รับการเรียกค่าชดใช้ใด ๆ ทั้งสิ้น"
					 + "\n          2. ห้างฯ จะคิดดอกเบี้ย 1.5% ต่อเดือน สำหรับอินวอยซ์ที่ไม่ชำระตามกำหนด";
		
		Phrase phrase = new Phrase();
		phrase.add(new Chunk("เงื่อนใข ", angsaBold12));
		phrase.add(new Chunk(msg, angsa12));
		
		PdfPCell cell = new PdfPCell(phrase);
        cell.setHorizontalAlignment(Rectangle.ALIGN_LEFT);
        cell.setRowspan(2);
        return cell;
	}

}
