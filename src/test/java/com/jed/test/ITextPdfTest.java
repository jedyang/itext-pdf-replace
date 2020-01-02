package com.jed.test;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import com.itextpdf.layout.element.Text;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @作者 yunsheng
 * @时间 2020/1/1
 * @描述
 */
public class ITextPdfTest {
    /**
     * 生成pdf文件
     */
    @Test
    public void testCreatePdf() throws FileNotFoundException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter("D:/1.pdf");

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);

        //Add paragraph to the document
        document.add(new Paragraph("Hello World!"));

        //Close document
        document.close();
    }


    @Test
    public void setFixedPosition() throws Exception {
        // 模拟从模板读取，并在指定位置增加内容
        PdfReader template = new PdfReader("D:/2.pdf");
        PdfWriter writer = new PdfWriter("D:/3.pdf");
        PdfDocument pdf = new PdfDocument(template, writer);
        Document document = new Document(pdf);

        Text text = new Text(String.format("Page %d", pdf.getNumberOfPages()));
        text.setBackgroundColor(ColorConstants.WHITE);
//前面这个text主要是设置背景色为白色，如果text的位置上面有内容就会覆盖掉内容
        document.add(new Paragraph(text).setFixedPosition(
                1, 0, 0, 100)); //这里面width取决于留空的宽度，这里我们尽量取大一


        // 在指定位置加图片
        Image img = new Image(ImageDataFactory.create(
                "D:/f.jpg"), 100, 100, 100);
        document.add(img);
        document.close();
    }

}
