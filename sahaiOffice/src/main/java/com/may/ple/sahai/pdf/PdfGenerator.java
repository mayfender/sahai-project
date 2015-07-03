package com.may.ple.sahai.pdf;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.may.ple.sahai.domain.BuySaleTaskReq;
import com.may.ple.sahai.domain.ItemInfo;

/**
 * @author sarawut.inthong
 *
 */
public abstract class PdfGenerator extends BaseReportBuilder {
	protected static final Logger log = Logger.getLogger(PdfGenerator.class.getName());
	protected int marginLeft = 25;
	protected int marginRight = 15;
	protected int marginTop = 40;
//	protected int marginBottom = 120; if have discount and after discount on footer table.
	protected int marginBottom = 80;
	protected Document document;
	private List<Phrase> docNames;
    private PdfWriter writer;
    
    public PdfGenerator(BuySaleTaskReq taskReq) {
    	super(taskReq);
    }
    
    protected abstract PdfPTable partnerInfo(BuySaleTaskReq taskReq) throws Exception;
    
    private void process(Phrase docName) throws Exception {
    	 document.add(logo());
         
         // Write company information. 
         document.add(companyInfo());
          
         // []
         document.add(quotationLabel(writer, docName));
         
         // []
         noAndDate(writer, taskReq.getDocNo() ,taskReq.getCreatedDateTime());
         
         // []
         document.add(partnerInfo(taskReq));
         
         // Write table header.
         PdfPTable table = drawTableHeader();
         
         // []
         addContents(table, taskReq.getItems());
         
         // []
         document.add(table);
    }
        
    public byte[] genPdf() {
    	log.debug("Start");
        byte data[] = null;
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	
        try {
            document = new Document();
            writer = PdfWriter.getInstance(document, out);
            writer.setPageEvent(this);
            setPageNumberAlignment(Element.ALIGN_RIGHT);
            
            // L-R-T-B
            document.setMargins(marginLeft, marginRight, marginTop, marginBottom);
            document.setPageSize(PageSize.A4);
            document.open();
            
            process(docNames.get(0));
            
            for (int i = 1; i < docNames.size(); i++) {
            	document.newPage();
            	onCloseDocument(writer, document);
            	document.setPageCount(1);
            	process(docNames.get(i));
			}
            
            document.close();
            
            data = out.toByteArray();
        } catch (Exception e) {
        	log.error(e.toString());
        } finally {
        	try {
        		out.close();				
			} catch (Exception e2) {}
        }
        
        log.debug("End");
        return data;
    }
    
    private void addContents(PdfPTable table, List<ItemInfo> items) {
    	try {
    		if(items == null) return;
    		
    		PdfPCell cell;
    		String msg = "";
    		double d;
    		
    		for (int i = 0; i < items.size(); i++) {
	    		for (int j = 0; j < 7; j++) {				
	    			
	    			cell = new PdfPCell();
	    			
	    			if(j == 0) {
	    				msg = "" + (i + 1);
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    			}else if(j == 1) {
	    				msg = items.get(i).getPartNo();
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    			} else if (j == 2) {
	    				msg = items.get(i).getItemName();
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    			} else if (j == 3) {
	    				msg = String.format("%,.2f", (double)items.get(i).getQuantity());	    				
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    			} else if (j == 4) {
	    				msg = items.get(i).getUnit();	    				
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    			} else if (j == 5) {
	    				if(items.get(i).getUnitPrice() != null) {
	    					d = items.get(i).getUnitPrice();
	    					if(d != 0) {
	    						msg = String.format("%,.2f", d);	    						    						
	    					} else {
	    						msg = "";
	    					}
	    				} else {
	    					msg = "";
	    				}
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    			} else if (j == 6) {
	    				if(items.get(i).getAmount() != null) {
	    					d = items.get(i).getAmount();
	    					
	    					if(d != 0) {
	    						msg = String.format("%,.2f", items.get(i).getAmount());	    					    						    						
	    					} else {
	    						msg = "";
	    					}
	    				} else {
	    					msg = "";
	    				}
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    			}
	    			
	    			cell.setPhrase(new Phrase(msg, angsa14));
	    			cell.setBorder(Rectangle.NO_BORDER);
	    			table.addCell(cell);
	    			
				}
    		}
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
    }
    
    private void noAndDate(PdfWriter writer, String docNo, String createdDate) throws Exception {
    	float width = 120;
    	
    	PdfPTable table = new PdfPTable(2);
		table.setTotalWidth(width);
		table.setWidths(new float[]{0.07f, 0.15f});
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        PdfPCell ch = new PdfPCell(new Phrase("เลขที่ : ", angsa14));
        ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
        ch.setBorder(Rectangle.NO_BORDER);
        ch.setUseDescender(true);
        table.addCell(ch);
        
        ch = new PdfPCell(new Phrase(docNo, angsa14));
        ch.setBorder(Rectangle.NO_BORDER);
        ch.setUseDescender(true);
        table.addCell(ch);
        
        ch = new PdfPCell(new Phrase("วันที่ : ", angsa14));
        ch.setBorder(Rectangle.NO_BORDER);
        ch.setUseDescender(true);
        ch.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(ch);
        
        ch = new PdfPCell(new Phrase(createdDate, angsa14));
        ch.setBorder(Rectangle.NO_BORDER);
        ch.setUseDescender(true);
        table.addCell(ch);	
        
        //-------------------
        
        float leftPos = (PageSize.A4.getWidth() - document.rightMargin());
        float hight = 50;
        PdfContentByte cb = writer.getDirectContent();
        cb.setLineWidth(0.5f);
        cb.setColorFill(BaseColor.WHITE);
        cb.roundRectangle(leftPos - width, 656, width, hight, 5);
        cb.fillStroke();
        cb.setColorFill(BaseColor.BLACK);
        
        table.writeSelectedRows(0, -1, leftPos - width, 703, writer.getDirectContent());	
    }
    
    private PdfPTable quotationLabel(PdfWriter writer, Phrase docName) throws Exception {
    	PdfPTable pdfPTable = null;
    	
    	try {
    		pdfPTable = new PdfPTable(1);
    		pdfPTable.setSpacingBefore(5);
    		pdfPTable.setSpacingAfter(5);
    		pdfPTable.setWidthPercentage(100f);
            pdfPTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            PdfPCell ch = new PdfPCell(docName);
            ch.setBorder(Rectangle.NO_BORDER);
            ch.setVerticalAlignment(Element.ALIGN_MIDDLE);
            ch.setHorizontalAlignment(Element.ALIGN_CENTER);
            ch.setUseDescender(true);
            pdfPTable.addCell(ch);
            
    		return pdfPTable;
		} catch (Exception e) {
			log.error(e.toString());
			throw e;
		}
    }
    
    private Image logo() throws Exception {
    	try {
    		
    		Image logo = Image.getInstance("D:\\workspace\\32-bit\\juno\\restaurant\\javaPdf\\img\\logo.png");
            logo.scalePercent(40);
            logo.setAbsolutePosition(70, 720);
            
            return logo;
		} catch (Exception e) {
			throw e;
		}
    }
    
    private PdfPTable companyInfo() {
        PdfPTable pdfPTable = null;
        
        try {
            
            pdfPTable = new PdfPTable(1);
            pdfPTable.setWidthPercentage(100f);
            
            PdfPCell ch = new PdfPCell(new Phrase("หจก. สหาย ซัพพลาย(สํานักงานใหญ่)\nSAHAI SUPPLY LTD., PART. (Head Office)", angsaHeader16));
            ch.setBorder(Rectangle.NO_BORDER);
            ch.setVerticalAlignment(Element.ALIGN_TOP);            
            ch.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(ch);
            
            String msg = "19/178 หมู่ที่ 11 แขวงโคกแฝด  เขตหนองจอก  กรุงเทพฯ  10530\n"
                       + "19/178 Moo11 Kokfad Sub District, Nongchok District, Bangkok 10530\n" 
                       + "TEL. 0-2989-1508 FAX. 0-2989-1508    E-MAIL : sahai.supply.ltd@gmail.com\n"
                       + "เลขประจําตัวผู้เสียภาษี : 0103558000713";
            
            ch = new PdfPCell(new Phrase(msg, angsa14));
            ch.setHorizontalAlignment(Element.ALIGN_CENTER);
            ch.setBorder(Rectangle.NO_BORDER);
            pdfPTable.addCell(ch);
                                    
            return pdfPTable;
        } catch (Exception e) {
        	log.error(e.toString());
            throw e;
        }
    }
    
    private PdfPTable drawTableHeader() throws Exception {
        PdfPTable pdfPTable = null;
        
        try {
            pdfPTable = new PdfPTable(7);
            pdfPTable.setWidthPercentage(100f);
            pdfPTable.setWidths(new float[]{0.4f, 0.8f, 3f, 0.5f, 0.5f, 0.7f, 0.7f});
            PdfPCell ch;
            
            ch = new PdfPCell(new Phrase("ลำดับที่ \nItem", angsaBold12));
            ch.setUseDescender(true);
            ch.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            ch.setVerticalAlignment(Element.ALIGN_MIDDLE);
            ch.setFixedHeight(30);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase("รหัสสินค้า\nProduct Code", angsaBold12));
            ch.setUseDescender(true);
            ch.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            ch.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase("รายการ\nDescription", angsaBold12));
            ch.setUseDescender(true);
            ch.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            ch.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase("จำนวน\nQuantity", angsaBold12));
            ch.setUseDescender(true);
            ch.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            ch.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase("หน่วย\nUnit", angsaBold12));
            ch.setUseDescender(true);
            ch.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            ch.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase("ราคา/หน่วย\nUnit Price", angsaBold12));
            ch.setUseDescender(true);
            ch.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            ch.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfPTable.addCell(ch);
            
            ch = new PdfPCell(new Phrase("จำนวนเงิน\nAmount", angsaBold12));
            ch.setUseDescender(true);
            ch.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            ch.setVerticalAlignment(Element.ALIGN_MIDDLE);
            pdfPTable.addCell(ch);
            
            pdfPTable.setHeaderRows(1);
            
            return pdfPTable;
        } catch (Exception e) {
        	log.error(e.toString());
            throw e;
        }    
    }
    
    protected String emp(String str) {
    	if(str == null || str.isEmpty()) 
    		return "-";
    	else 
    		return str;
    }

	protected List<Phrase> getDocNames() {
		return docNames;
	}

	protected void setDocName(List<Phrase> docNames) {
		this.docNames = docNames;
	}
    
}