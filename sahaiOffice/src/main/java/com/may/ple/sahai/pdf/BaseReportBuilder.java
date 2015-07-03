package com.may.ple.sahai.pdf;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.may.ple.sahai.domain.BuySaleTaskReq;
import com.may.ple.sahai.utils.Number2WordUtil;

public abstract class BaseReportBuilder extends PdfPageEventHelper {
	private static final Logger log = Logger.getLogger(BaseReportBuilder.class.getName());
    protected BaseFont baseFont;
    protected BaseFont baseFontBold;
    protected Font angsaBold12;
    protected Font angsaBold14;
    protected Font angsaHeader16;
    protected Font angsa14;
    protected Font angsa12;
    protected BuySaleTaskReq taskReq;
    private PdfTemplate totalPages;
    private PdfTemplate firstPrice;
    private PdfTemplate discountPrice;
    private PdfTemplate afterDiscount;
    private PdfTemplate vat;
    private PdfTemplate totalPrice;
    private PdfTemplate priceText;
    private float footerTextSize = 12f;
    private int pageNumberAlignment;
    
    protected PdfPCell createDesc() throws Exception {
    	PdfPCell cell = new PdfPCell(new Phrase("E & OE ผิด ตก ยกเว้น", angsaBold14));
        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
        cell.setVerticalAlignment(Rectangle.ALIGN_MIDDLE);
        cell.setRowspan(2);
        return cell;
    }
    
    protected abstract void endPageSign(PdfWriter writer, Document document) throws Exception;
 
    public BaseReportBuilder(BuySaleTaskReq taskReq) {
        super();
        this.taskReq = taskReq;
        
        /*
         * TH Niramit AS.ttf
         * THSarabun.ttf
         * angsau_0.ttf
         * angsa.ttf
         */
        
        baseFont = load(".", "THSarabun.ttf");
        baseFontBold = load(".", "THSarabun Bold.ttf");
        
        angsaBold12 = new Font(baseFont, 12);
        angsaBold12.setStyle(Font.BOLD);
        
        angsaBold14 = new Font(baseFont, 14);
        angsaBold14.setStyle(Font.BOLD);
        
        angsa14 = new Font(baseFont, 14);
        angsa12 = new Font(baseFont, 12);
        
        angsaHeader16 = new Font(baseFont, 16);
        angsaHeader16.setStyle(Font.BOLD);
    }
    
    private BaseFont load(String location, String fontname) {
    	ByteArrayOutputStream out = null;
    	InputStream in = null;
    	
        try {
            in = getClass().getClassLoader().getResourceAsStream(location + System.getProperty("file.separator") + fontname);
            out = new ByteArrayOutputStream();
            
            byte buf[] = new byte[1024];
 
            while (true) {
                int size = in.read(buf);
                if (size < 0)
                    break;
                out.write(buf, 0, size);
            }
            buf = out.toByteArray();
            return BaseFont.createFont(fontname, BaseFont.IDENTITY_H, true, true, buf, null);
        } catch (Exception ex) {
        	log.error(ex.toString());
            return null;
        } finally {
        	try {
        		if(in != null) in.close();				
			} catch (Exception e) {}
        	try {
        		if(out != null) out.close();				
			} catch (Exception e) {}
        }
    }
 
    @Override
    public void onOpenDocument(PdfWriter writer, Document document) {
        totalPages = writer.getDirectContent().createTemplate(100, 100);
        totalPages.setBoundingBox(new Rectangle(-20, -20, 100, 100));
    }
 
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
    	try {
    		
	    	firstPrice = writer.getDirectContent().createTemplate(105, 20);   
	    	discountPrice = writer.getDirectContent().createTemplate(105, 20);   
	    	afterDiscount = writer.getDirectContent().createTemplate(105, 20);   
	    	vat = writer.getDirectContent().createTemplate(105, 20);   
	    	totalPrice = writer.getDirectContent().createTemplate(105, 20);   
	    	priceText = writer.getDirectContent().createTemplate(400, 16);   
	    	
	        PdfContentByte cb = writer.getDirectContent();
	        cb.saveState();
	        String text = String.format("หน้า %s/", writer.getPageNumber());
	 
	        float textBase = document.top() + 10;
	        float textSize = baseFont.getWidthPoint(text, footerTextSize);
	        
	        cb.beginText();
	        cb.setFontAndSize(baseFont, footerTextSize);
	        if(Element.ALIGN_CENTER == pageNumberAlignment) {
	            cb.setTextMatrix((document.right() / 2), textBase);
	            cb.showText(text);
	            cb.endText();
	            cb.addTemplate(totalPages, (document.right() / 2) + textSize, textBase);
	        } else if(Element.ALIGN_LEFT == pageNumberAlignment) {
	            cb.setTextMatrix(document.left(), textBase);
	            cb.showText(text);
	            cb.endText();
	            cb.addTemplate(totalPages, document.left() + textSize, textBase);
	        } else {
	            float adjust = baseFont.getWidthPoint("0", footerTextSize);
	            cb.setTextMatrix(document.right() - textSize - adjust, textBase);
	            cb.showText(text);
	            cb.endText();
	            cb.addTemplate(totalPages, document.right() - adjust, textBase);
	        }
	        cb.restoreState();   
	        
	        //----------------------: Draw Line :-----------------------
	        int posY;
			if (document.getPageNumber() == 1) {
				posY = 548;
			} else {
				posY = 774;
			}
	        
	        cb.moveTo(document.leftMargin(), posY);
	        cb.lineTo(document.leftMargin(), document.bottomMargin());
	        cb.closePath();
	        
	        cb.moveTo(document.leftMargin() + 33.64f, posY);
	        cb.lineTo(document.leftMargin() + 33.64f, document.bottomMargin());
	        cb.closePath();
	        
	        cb.moveTo(document.leftMargin() + 100.92f, posY);
	        cb.lineTo(document.leftMargin() + 100.92f, document.bottomMargin());
	        cb.closePath();
	        
	        cb.moveTo(document.leftMargin() + 353.17f, posY);
	        cb.lineTo(document.leftMargin() + 353.17f, document.bottomMargin());
	        cb.closePath();
	        
	        cb.moveTo(document.leftMargin() + 395.23f, posY);
	        cb.lineTo(document.leftMargin() + 395.23f, document.bottomMargin());
	        cb.closePath();
	        
	        cb.moveTo(document.leftMargin() + 437.27f, posY);
	        cb.lineTo(document.leftMargin() + 437.27f, document.bottomMargin());
	        cb.closePath();
	        
	        cb.moveTo(document.leftMargin() + 496.15f, posY);
	        cb.lineTo(document.leftMargin() + 496.15f, document.bottomMargin());
	        cb.closePath();
	        
	        cb.moveTo(PageSize.A4.getWidth() - document.rightMargin(), posY);
	        cb.lineTo(PageSize.A4.getWidth() - document.rightMargin(), document.bottomMargin());
	        cb.closePath();
	        //----------------------: Footer :--------------------------
	        
	        PdfPTable footer = new PdfPTable(new float[]{4.2f, 1f, 1.4f});
	        footer.setTotalWidth(PageSize.A4.getWidth() - (document.leftMargin()+ document.rightMargin()));
	        footer.setLockedWidth(true);
	        
//	        PdfPCell cell = new PdfPCell(new Phrase("E & OE ผิด ตก ยกเว้น", angsaBold14));
//	        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
//	        cell.setVerticalAlignment(Rectangle.ALIGN_MIDDLE);
//	        cell.setRowspan(2); // have to set as 4 if have discount and after discount
	        footer.addCell(createDesc());
	        
	        PdfPCell cell= new PdfPCell(new Phrase("รวมเงิน", angsaBold14));
	        cell.setUseDescender(true);
	        footer.addCell(cell);
	        cell = new PdfPCell(Image.getInstance(firstPrice));
	        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
	        footer.addCell(cell);
	        
	        /*cell = new PdfPCell(new Phrase("ส่วนลด", angsaBold14));
	        cell.setUseDescender(true);
	        footer.addCell(cell);
	        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
	        cell = new PdfPCell(Image.getInstance(discountPrice));
	        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
	        footer.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("หลังหักส่วนลด", angsaBold14));
	        cell.setUseDescender(true);
	        footer.addCell(cell);
	        cell = new PdfPCell(Image.getInstance(afterDiscount));
	        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
	        footer.addCell(cell);*/
	        
	        cell = new PdfPCell(new Phrase("ภาษีมวลค่าเพิ่ม 7%", angsaBold14));
	        cell.setUseDescender(true);
	        footer.addCell(cell);
	        cell = new PdfPCell(Image.getInstance(vat));
	        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
	        footer.addCell(cell);
	        
	        cell = new PdfPCell(Image.getInstance(priceText));
	        cell.setHorizontalAlignment(Rectangle.ALIGN_CENTER);
	        footer.addCell(cell);
	        
	        cell = new PdfPCell(new Phrase("จำนวนเงินทั้งสิ้น", angsaBold14));
	        cell.setUseDescender(true);
	        footer.addCell(cell);
	        cell = new PdfPCell(Image.getInstance(totalPrice));
	        cell.setHorizontalAlignment(Rectangle.ALIGN_RIGHT);
	        footer.addCell(cell);
	        
	        footer.writeSelectedRows(0, -1, document.leftMargin(), document.bottomMargin(), writer.getDirectContent());
	        
	        //----------: Add price calculation 
	        firstPrice.beginText();
	        firstPrice.setFontAndSize(baseFontBold, 14);
	        firstPrice.showTextAligned(Element.ALIGN_CENTER, "-", 47, 5, 0);
	        firstPrice.endText();
	        
	        /*discountPrice.beginText();
	        discountPrice.setFontAndSize(baseFontBold, 14);
	        discountPrice.showTextAligned(Element.ALIGN_CENTER, "-", 47, 5, 0);
	        discountPrice.endText();
	        
	        afterDiscount.beginText();
	        afterDiscount.setFontAndSize(baseFontBold, 14);
	        afterDiscount.showTextAligned(Element.ALIGN_CENTER, "-", 47, 5, 0);
	        afterDiscount.endText();*/
	        
	        vat.beginText();
	        vat.setFontAndSize(baseFontBold, 14);
	        vat.showTextAligned(Element.ALIGN_CENTER, "-", 47, 5, 0);
	        vat.endText();
	        
	        totalPrice.beginText();
	        totalPrice.setFontAndSize(baseFontBold, 14);
	        totalPrice.showTextAligned(Element.ALIGN_CENTER, "-", 47, 5, 0);
	        totalPrice.endText();
	        
	        priceText.beginText();
	        priceText.setFontAndSize(baseFontBold, 14);
	        priceText.showTextAligned(Element.ALIGN_CENTER, "-", 175, 2, 0);
	        priceText.endText();
	        
	        // Add sign footer.
	        endPageSign(writer, document);
	        
    	} catch (Exception e) {
    		log.error(e.toString());
		}
    }
 
    @Override
    public void onCloseDocument(PdfWriter writer, Document document) {
    	try {
	        totalPages.beginText();
	        totalPages.setFontAndSize(baseFont, footerTextSize);
	        totalPages.setTextMatrix(0, 0);
	        totalPages.showText(String.valueOf(writer.getPageNumber() - 1));
	        totalPages.endText();
	                
	        //----------------------------------------
	        
	        firstPrice.reset();
	        firstPrice.beginText();
	        firstPrice.setFontAndSize(baseFontBold, 14);
	        firstPrice.showTextAligned(Element.ALIGN_RIGHT, priceFormat(taskReq.getFirstPrice()), 97, 4, 0);
	        firstPrice.endText();
	        
	        discountPrice.reset();
	        discountPrice.beginText();
	        discountPrice.setFontAndSize(baseFontBold, 14);
	        discountPrice.showTextAligned(Element.ALIGN_RIGHT, priceFormat(taskReq.getDiscount()), 97, 4, 0);
	        discountPrice.endText();
	        
	        afterDiscount.reset();
	        afterDiscount.beginText();
	        afterDiscount.setFontAndSize(baseFontBold, 14);
	        afterDiscount.showTextAligned(Element.ALIGN_RIGHT, priceFormat(taskReq.getAfterDiscount()), 97, 4, 0);
	        afterDiscount.endText();
	        
	        vat.reset();
	        vat.beginText();
	        vat.setFontAndSize(baseFontBold, 14);
	        vat.showTextAligned(Element.ALIGN_RIGHT, priceFormat(taskReq.getVat()), 97, 4, 0);
	        vat.endText();
	        
	        String totalPriceText = priceFormat(taskReq.getTotalPrice());
	        
	        totalPrice.reset();
	        totalPrice.beginText();
	        totalPrice.setFontAndSize(baseFontBold, 14);
	        totalPrice.showTextAligned(Element.ALIGN_RIGHT, totalPriceText, 97, 4, 0);
	        totalPrice.endText();
	        
	        String numText = "";
	        if(!totalPriceText.equals("")) {
	        	numText = "( " + Number2WordUtil.bahtText(totalPriceText.replace(",", "")) + " )";
	        }
	        
	        priceText.reset();
	        priceText.beginText();
	        priceText.setFontAndSize(baseFontBold, 14);
	        priceText.showTextAligned(Element.ALIGN_CENTER, numText, 175, 0, 0);
	        priceText.endText();
        
    	} catch (Exception e) {
    		log.error(e.toString());
		}
    }
 
    public void setPageNumberAlignment(int pageNumberAlignment) {
        this.pageNumberAlignment = pageNumberAlignment;
    }
    
    private String priceFormat(Double price) {
    	String result;
    	
    	if(price == null || price == 0) {
    		result = "";
    	} else {
    		result = String.format("%,.2f", price);    		
    	}
    	return result;
    }
}