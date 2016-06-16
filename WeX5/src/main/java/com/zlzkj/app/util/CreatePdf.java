package com.zlzkj.app.util;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class CreatePdf {

 /**
  * @param args
  */
 public static void main(String[] args) throws Exception {
	 //create3("论文名称","专业名","结果1","结果2","结果3","结果4","总体5","mark","c://论文评测.pdf","C:\\1.png");
	 create3("EXCEL1","计算机科学与技术","1","1","1","1","差","可以的","c://pdf.pdf","C:\\1.png");
 }
 
 public static void create3(String name,String major,String result1,String result2,String result3,String 
		 result4,String result5,String mark,String filePath,String imagePath) throws Exception
 {
  //上、下、左、右页边距
  Document document = new Document(PageSize.A4.rotate(),10,10,1,1); 
  PdfWriter writer=null;
  try {
   writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
  }
  catch(Exception e)
  {
   
  }
//  设置页眉和页脚
  HeaderFooter   header   =   new   HeaderFooter(new   Phrase("页眉"),false);
  HeaderFooter   footer   =   new   HeaderFooter(new   Phrase("页脚"),false);  
  
  document.setHeader(header);   
  document.setFooter(footer);
  document.open();
  chapter5(name,major,result1,result2,result3,result4,result5,mark,filePath,imagePath,document,writer);
  document.close();
 }
 private static  void chapter5(String name,String major,String result1,String result2,String result3,String 
		 result4,String result5,String mark,String filePath,String imagePath,Document document,PdfWriter writer) throws Exception
 {
  
  BaseFont bfChinese=null;
  try {
	  String osName = System.getProperty("os.name").toLowerCase();
	  if(osName.indexOf("windows")!=-1){
		  bfChinese = BaseFont.createFont("c://windows//fonts//simsun.ttc,1",
              BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	  }
	  else{
		  bfChinese = BaseFont.createFont("/usr/local/tomcat7/webapps/Thesis/simsun.ttc,1",
	              BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
	  }
  } catch (Exception e) {
   // TODO 自动生成 catch 块
   e.printStackTrace();
   
  }
  Font fontChinesed = new Font(bfChinese, 20, Font.NORMAL);
  Font fontChinesed1 = new Font(bfChinese, 15, Font.NORMAL);
  Font fontChinesed2 = new Font(bfChinese, 15, Font.BOLD);
  
  Paragraph par = new Paragraph(name,fontChinesed);
  par.setAlignment("center");
  
  Paragraph par1 = new Paragraph(major,fontChinesed);
  par1.setAlignment("center");
  
  Paragraph p1 = new Paragraph("\t本资料表是根据论文的具体情况对论文进行的默认评价模板,如果与实际不一致，以实际为准。",fontChinesed1);
  p1.setAlignment(Element.ALIGN_CENTER);
  document.newPage();
  //设置图片
  Image png1 = Image.getInstance(imagePath);
  float h = png1.getHeight();
  float w = png1.getWidth();
  int p = 0;
  float p2 = 0.0f;
  p2 = 530 / w * 100;
  p = Math.round(p2);
  int percent = p;
  png1.setAlignment(Image.MIDDLE);
  png1.setAlignment(Image.TEXTWRAP);
  png1.scalePercent(percent + 3);
  
  float [] widths={0.3f,0.6f,0.1f};//宽度比例
  Table tab1=new Table(3);
  tab1.setWidths(widths);//设置表格2列所分配比例
  tab1.setPadding(5);   //表格间高度
  //tab1.setSpacing(5); //边框高度
  //第一行
  Paragraph par2 = new Paragraph("评议项目：",fontChinesed2);
  Cell cel1=new Cell(par2);
  cel1.setHorizontalAlignment("right");//垂直方向
  tab1.addCell(cel1);
  
  Paragraph par3 = new Paragraph("评价要素",fontChinesed2);
  Cell cel2=new Cell(par3);
  cel2.setHorizontalAlignment("left");//垂直方向
  tab1.addCell(cel2);
  
  Paragraph par4 = new Paragraph("成绩",fontChinesed2);
  Cell cel4=new Cell(par4);
  cel4.setHorizontalAlignment("left");//垂直方向
  tab1.addCell(cel4);
  
  Paragraph par5 = new Paragraph("选题与综述：",fontChinesed2);
  Cell cel5=new Cell(par5);
  cel5.setHorizontalAlignment("right");//垂直方向
  tab1.addCell(cel5);
  
  Paragraph par6 = new Paragraph("研究的理论意义、实用价值;对本学科及相关学领域国内外发展状况和学术动态的了解程度",fontChinesed2);
  Cell cel6=new Cell(par6);
  cel6.setHorizontalAlignment("left");//垂直方向
  tab1.addCell(cel6);
  
  Paragraph par7 = new Paragraph(comments(result1),fontChinesed2);
  Cell cel7=new Cell(par7);
  cel7.setHorizontalAlignment("left");//垂直方向
  tab1.addCell(cel7);
  
  Paragraph par8 = new Paragraph("创新性及论文价值：",fontChinesed2);
  Cell cel8=new Cell(par8);
  cel8.setHorizontalAlignment("right");//垂直方向
  tab1.addCell(cel8);
  
  Paragraph par9 = new Paragraph("论文提出的新见解、新方法所具有的价值;论文成果对技术进步、经济建设、国家安全等方面产生的影响或作用",fontChinesed2);
  Cell cel9=new Cell(par9);
  cel9.setHorizontalAlignment("left");//垂直方向
  tab1.addCell(cel9);
  
  Paragraph par10 = new Paragraph(comments(result2),fontChinesed2);
  Cell cel10=new Cell(par10);
  cel10.setHorizontalAlignment("left");//垂直方向
  tab1.addCell(cel10);
  
  Paragraph par11 = new Paragraph("基础知识和科研能力：",fontChinesed2);
  Cell cel11=new Cell(par11);
  cel11.setHorizontalAlignment("right");//垂直方向
  tab1.addCell(cel11);
  
  Paragraph par12 = new Paragraph("论文体现的理论基础的扎实程度;本学科及相关学科领域专业知识的系统性;分析问题、解决问题的能力;研究方法的科学性,是否采用先进技术、设备、信息等进行论文研究工作",fontChinesed2);
  Cell cel12=new Cell(par12);
  cel12.setHorizontalAlignment("left");//垂直方向
  tab1.addCell(cel12);
  
  Paragraph par13 = new Paragraph(comments(result3),fontChinesed2);
  Cell cel13=new Cell(par13);
  cel13.setHorizontalAlignment("left");//垂直方向
  tab1.addCell(cel13);
  
  Paragraph par14 = new Paragraph("论文规范性：",fontChinesed2);
  Cell cel14=new Cell(par14);
  cel14.setHorizontalAlignment("right");//垂直方向
  tab1.addCell(cel14);
  
  Paragraph par15 = new Paragraph("论文的规范性,学风的严谨性;论文语言表达的准确性,逻辑的严密性;论文结构的合理性,书写格式及图表的规范性",fontChinesed2);
  Cell cel15=new Cell(par15);
  cel15.setHorizontalAlignment("left");//垂直方向
  tab1.addCell(cel15);
  
  Paragraph par16 = new Paragraph(comments(result4),fontChinesed2);
  Cell cel16=new Cell(par16);
  cel16.setHorizontalAlignment("left");//垂直方向
  tab1.addCell(cel16);
  
  Paragraph par17 = new Paragraph("总体评价：",fontChinesed2);
  Cell cel17=new Cell(par17);
  cel17.setHorizontalAlignment("right");//垂直方向
  tab1.addCell(cel17);
  
  Paragraph par18 = new Paragraph("对于论文的总体评价",fontChinesed2);
  Cell cel18=new Cell(par18);
  cel18.setHorizontalAlignment("left");//垂直方向
  tab1.addCell(cel18);
  
  Paragraph par19 = new Paragraph(result5,fontChinesed2);
  Cell cel19=new Cell(par19);
  cel19.setHorizontalAlignment("left");//垂直方向
  tab1.addCell(cel19);
  
  Paragraph par20 = new Paragraph("备注：",fontChinesed2);
  Cell cel20=new Cell(par20);
  cel20.setHorizontalAlignment("right");//垂直方向
  tab1.addCell(cel20);
  
  Paragraph par21 = new Paragraph(mark,fontChinesed2);
  Cell cel21=new Cell(par21);
  cel21.setHorizontalAlignment("left");//垂直方向
  cel21.setColspan(2);
  tab1.addCell(cel21);
  
  try {
   //添加到PDF中
   document.add(par);   
   document.add(par1);
   document.add(p1);
   document.add(tab1);
   document.add(png1);
   
  } catch (Exception e) {
   // TODO 自动生成 catch 块
   e.printStackTrace();
   throw new Exception(e);
  }
 }

 public static void test1() throws Exception
 {
  Document document = new Document(PageSize.A4.rotate(),10,10,10,10);
  FileOutputStream fops = new FileOutputStream("c://test1.pdf");
  PdfWriter.getInstance(document, fops);
  document.open();
  BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
  Font FontChinesed = new Font(bfChinese, 59, Font.NORMAL);
  Paragraph par = new Paragraph("aaaaa",FontChinesed);
  document.add(par);
  Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
  par = new Paragraph("时间： 2007-2-1 -- 2007-4-8", FontChinese);
  document.add(par);
  par = new Paragraph(" ", FontChinese);

  Table table = new Table(3);
  		//边框大小
        table.setBorderWidth(5);
        table.setBorderColor(new Color(0, 0, 255));
        //边框与元素间距离
        table.setPadding(5);
        //间距
        table.setSpacing(5);
        Cell cell = new Cell("header");
        cell.setHeader(true);
        //合并横单元格
        cell.setColspan(3);
        table.addCell(cell);
        table.endHeaders();
        cell = new Cell("example cell with colspan 1 and rowspan 2");
        cell.setRowspan(2);
        //设置边框颜色
        cell.setBorderColor(new Color(255, 0, 0));
        table.addCell(cell);
        table.addCell("1.1");
        table.addCell("2.1");
        table.addCell("1.2");
        table.addCell("2.2");
        table.addCell("cell test1");
        cell = new Cell("big cell");
        cell.setRowspan(2);
        cell.setColspan(2);
        table.addCell(cell);
        table.addCell("cell test2"); 
        
        document.add(table);
        
        document.close();
        
 }
 public static void test2() throws Exception
 {
   /*打开已经定义好字段以后的pdf模板*/

  PdfReader reader = new PdfReader("c://chapter5.pdf");
  /*将要生成的目标PDF文件名称*/
//  PdfStamper stamp = new PdfStamper(reader, new FileOutputStream("c://iText报表结果.pdf"));
//  PdfContentByte under = stamp.getUnderContent(1);
  /*使用中文字体*/
  BaseFont bf = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
  Font FontChinese = new Font(bf, 12, Font.NORMAL);

  /*取出报表模板中的所有字段*/
  //AcroFields form = stamp.getAcroFields();
  /*为字段赋值,注意字段名称是区分大小写的*/
//  form.setField("Name", "裴贺先");
//  form.setField("Age","26");
//  form.setField("xueli","高二辍学");
//  form.setField("email","phx@x263.net");
//  form.setField("beizhu","用iText做报表简单吗？");
//  stamp.setFormFlattening(true);
  //stamp.close();
  
  /*必须要调用这个，否则文档不会生成的*/
  
  Document document   =   new   Document(reader.getPageSize(1));  
  PdfWriter   writer   =   PdfWriter.getInstance(document,   new   FileOutputStream("c://iText多行报表结果.pdf"));
  document.open();   
  PdfContentByte   cb   =   writer.getDirectContent();   
  int pageNumber=1;
  PdfImportedPage   page1   =   writer.getImportedPage(reader,pageNumber);   
  //cb.addTemplate(page1,0,1f,1f,1f,0,0); 
  // cb.addTemplate(page1,   1f,   0,   0,   1f,   0,   0); 
  //cb.addTemplate(page1, 0, 1, -1, 0, 600, 500);
  //cb.addTemplate(page1, 0, 1, 0, 1, 600, 500);
  //float textBase = document.bottom() - 20;
  //cb.addTemplate(page1,document.left(),textBase);
  //cb.addTemplate(page1, 1, -1, 1, 5, 10, 700);
  PdfTemplate template = cb.createTemplate(500, 200); 
  template.moveTo(0, 200);

  template.lineTo(500, 0);

  template.stroke();

  template.beginText();

  bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

  template.setFontAndSize(bf, 12);

  template.setTextMatrix(100, 100);

  template.showText("Text at the position 100,100 (relative to the template!)");

  template.endText(); 
  //cb.addTemplate(template, 0, 400);
//  cb.addTemplate(page1,(float)Math.cos(180/180),(float)Math.sin(180/180),(float)Math.sin(180/180),(float)Math.cos(180/180), 0, 400);
//  cb.beginText(); 
//  cb.setFontAndSize(bf,   12);  
//  cb.setTextMatrix(200,   20);   //这里就是用来定位的值   
//  cb.showText("sdfsdfsdf");   
//  cb.endText();   
  
  document.close();   
 }
public static String  comments(String i){
	if(i.equals("1")){
		return "优";
	}
	else if(i.equals("2")){
		return "良";
	}
	else if(i.equals("3")){
		return "中";
	}
	else if(i.equals("4")){
		return "合";
	}
	else if(i.equals("5")){
		return "差";
	}
	return "暂无成绩";
}
}