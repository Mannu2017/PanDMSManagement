package admin;

import javax.swing.JLabel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.io.FilenameUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfString;

import utility.DataUtility;


public class GeneratePDF extends Thread{
	private JLabel status;
	private DefaultTableModel model;
	
	DataUtility utility=new DataUtility();

	public GeneratePDF(DefaultTableModel model, JLabel lblStatus) {
		this.model=model;
		this.status=lblStatus;
		try {
			String baseUrl = FilenameUtils.getPath("\\\\srv-kdms-file2\\images");
			File fname=new File(baseUrl);
			Runtime rt = Runtime.getRuntime();
			if(!fname.exists()) {
				rt.exec(new String[] {"cmd.exe","/c","net use \\\\srv-kdms-file2\\images /user:logicalaccess@karvy.com India@123"});
			}
			String baseUrl1 = FilenameUtils.getPath("\\\\192.168.78.197\\pan-scan6");
			File fname1=new File(baseUrl1);
			Runtime rt1 = Runtime.getRuntime();
			if(!fname1.exists()) {
				rt1.exec(new String[] {"cmd.exe","/c","net use \\\\192.168.78.197\\pan-scan6 /user:tininward Karvy123$"});
			}
			
			} catch (Exception e2) {
				e2.printStackTrace();
			}
	}

	public void run() {
		for(int i=0; i<model.getRowCount(); i++) {
			String inward=(String) model.getValueAt(i, 0);
			String ackno=(String) model.getValueAt(i, 1);
			status.setText("Data: "+inward+"/"+ackno);
			try {
				dataprocess(inward, ackno);
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	
	protected void dataprocess(Object inwardno, Object ackno) throws DocumentException, IOException {
		DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
		File ff=new File("\\\\192.168.78.197\\pan-scan6\\"+df.format(new Date()));
		if(!ff.exists()) {
			ff.mkdir();
		}
		List<String> filepath=utility.getFilePath(ackno.toString());
		if(filepath.size()>0) {
			utility.adminUpdate(ackno.toString());
			utility.SaveData(inwardno, ackno);
			Document document=new Document();
			PdfAWriter writer=PdfAWriter.getInstance(document,
					new FileOutputStream(ff+"\\"+inwardno.toString()+"_"+ackno.toString()+".pdf"), PdfAConformanceLevel.PDF_A_1A);
			document.addAuthor("Author");
			document.addSubject("Karvy-Pan");
			document.addLanguage("nl-nl");
	        document.addCreationDate();
	        document.addCreator("Karvy-Mannu");
	        writer.setTagged();
	        writer.createXmpMetadata();
			document.open();
			for(String str:filepath) {
				Image img=makeGray(str.toString());	
				img.setAccessibleAttribute(PdfName.ALT, new PdfString("Logo"));
				document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
				document.setMargins(0, 0, 0, 0);
				document.newPage();
				document.add(img);
			}
			document.close();
			writer.close();

		} else {
			System.out.println("Image Not found in Smart Server");
		}
	}
	private Image makeGray(String filepath) throws IOException, DocumentException{
		try {
			String baseUrl = FilenameUtils.getPath(filepath);
			File fname=new File(baseUrl);
			Runtime rt = Runtime.getRuntime();
			if(!fname.exists()) {
				rt.exec(new String[] {"cmd.exe","/c","net use "+filepath+" /user:logicalaccess@karvy.com India@123"});
			}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		BufferedImage bi = ImageIO.read(new File(filepath));
        BufferedImage newBi = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        newBi.getGraphics().drawImage(bi, 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newBi, "png", baos);
        return Image.getInstance(baos.toByteArray());
	}
	
}
