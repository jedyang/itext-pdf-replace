package com.jed.test;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class ITextPdf7Test {
    /**
     *生成一个简单的pdf文件
     * 
     */
    public static void createPdf(String filePath) throws IOException {

        //处理中文问题
        PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);

        PdfWriter writer = new PdfWriter(new FileOutputStream(new File(filePath)));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        Paragraph p = new Paragraph("hello word!我创建的第一个pdf文件");
        p.setFont(font);
        p.setFontSize(12);
        p.setBorder(new SolidBorder(ColorConstants.RED, 0.5f));//边框
        p.setBackgroundColor(ColorConstants.GREEN);//绿色你懂的
        document.add(p);
        document.close();
        writer.close();
        pdf.close();
    }
            /**
     *生成一个简单的模板pdf文件
     * 
     */
    public static void createTempPdf(String filePath){
        try {    
            PdfWriter writer = new PdfWriter(new FileOutputStream(new File(filePath)));
            PdfDocument pdf = new PdfDocument(writer);    
            Document document = new Document(pdf);
            addAcroForm(document);
            document.close();
            writer.close();
            pdf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 添加一个简单的模板
     * @param doc
     * @throws IOException 
     */
    public static void addAcroForm(Document doc) throws IOException {
        PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
        Paragraph title = new Paragraph("社会主义核心价值观")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(16);
        title.setFont(font);
        doc.add(title);
        doc.add(new Paragraph("名称:").setFont(font));
        doc.add(new Paragraph(" 一:").setFont(font));
        doc.add(new Paragraph(" 二:").setFont(font)); 
        doc.add(new Paragraph(" 三:").setFont(font));
        PdfAcroForm form = PdfAcroForm.getAcroForm(doc.getPdfDocument(), true);

        PdfTextFormField nameField = PdfTextFormField.createText(doc.getPdfDocument(),
                new Rectangle(99, 753, 425, 15), "名称:", "");//填充坐标
        PdfTextFormField nameField1 = PdfTextFormField.createText(doc.getPdfDocument(),
                new Rectangle(50, 725, 425, 15), "一:", "");
        PdfTextFormField nameField2 = PdfTextFormField.createText(doc.getPdfDocument(),
                new Rectangle(50, 695, 425, 15), "二:", "");
        PdfTextFormField nameField3 = PdfTextFormField.createText(doc.getPdfDocument(),
                new Rectangle(50, 667, 425, 15), "三:", "");
        form.addField(nameField);
        form.addField(nameField1);
        form.addField(nameField2);
        form.addField(nameField3);
      }
    
    /**
     * 使用pdf 模板生成 pdf 文件
     *      */
    public static void fillTemplate(String tempPdfPath,String targetPdfPath) {// 利用模板生成pdf
        try {
            //Initialize PDF document
            PdfDocument pdf = new PdfDocument(new PdfReader(tempPdfPath), new PdfWriter(targetPdfPath));
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
            Map<String, PdfFormField> fields = form.getFormFields();
            
            //处理中文问题  
            PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);  
            String[] str = {
                    "社会主义核心价值观",
                    "富强 民主 文明 和谐",
                    "自由 平等 公正 法制",
                    "爱国 敬业 诚信 友善"
                    };
            int i = 0;
            java.util.Iterator<String> it = fields.keySet().iterator();
            while (it.hasNext()) {
                //获取文本域名称
                String name = it.next().toString();
                //填充文本域
                fields.get(name).setValue(str[i++]).setFont(font).setFontSize(12);
            }    
            form.flattenFields();//设置表单域不可编辑            
            pdf.close();
 
 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException {
        createPdf("D:/firstPdf.pdf");
        createTempPdf("D:/tempPdf.pdf");
        fillTemplate("D:/tempPdf.pdf","D:/targetPdf.pdf");

    }
}