package com.foxconn.aplatform.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeGenerator {
	public static void main(String[] args) {
		final int SIZE = 256;
		final String IMG_TYPE = "jpg";
		
		//String qrMessage = "https://192.168.1.248/Certificate/192.168.1.248.crt";
		//String saveto = "C:/Users/Dante/Desktop/Simple.QRCode_20150417/Simple.QRCode/QRcode/qrcode4.jpg";	
		//String icon = "";
		//String icon = System.getProperty("user.dir") + "\\goku.jpg";
		//String URL = "https://192.168.1.248/Plist/FaceRecognizer.plist";
		
		//String itemservices = "itms-services://?action=download-manifest&url=";
		String qrMessage = args[0];
		String icon = args[1];
		String saveto = args[2];
		
		
		File file = new File(saveto);
		try {
				Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
				hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
				QRCodeWriter qrCodeWriter = new QRCodeWriter();
				BitMatrix byteMatrix = qrCodeWriter.encode(qrMessage,
					BarcodeFormat.QR_CODE, SIZE, SIZE, hintMap);
				int width = byteMatrix.getWidth();
				BufferedImage image = new BufferedImage(width,
					width, BufferedImage.TYPE_INT_RGB);
				image.createGraphics();

				Graphics2D graphics = (Graphics2D) image.getGraphics();
				graphics.setColor(Color.WHITE);
				graphics.fillRect(0, 0, width, width);
				graphics.setColor(Color.BLACK);

				for (int i = 0; i < width; i++) 
				{
					for (int j = 0; j < width; j++) 
					{
						if (byteMatrix.get(i, j)) 
						{
						graphics.fillRect(i, j, 1, 1);
						}
					}
				}
			
			if (icon!=null) 
			{
				try {
						int tw = SIZE/4;
						int th = SIZE/4;
						BufferedImage bimgIcon = ImageIO.read(new File(icon));
						double sw = (double)tw/(double)bimgIcon.getWidth();
						double sh = (double)th/(double)bimgIcon.getHeight();
						if (sw>sh) 
						{
							sw = sh;
							tw = (int) (sh*bimgIcon.getWidth());
						} 
						else 
						{
							sh = sw;
							th = (int) (sw*bimgIcon.getHeight());
						}
						//System.out.println(tw +"," +th +"==>"+sw+","+sh);
						Image thumbnail = bimgIcon.getScaledInstance(tw, th, Image.SCALE_SMOOTH);
						graphics.drawImage(thumbnail,(SIZE-tw)/2,(SIZE-th)/2,null);
					} 
				catch (IOException ignore) 
				{
				}
			}
			
			ImageIO.write(image, IMG_TYPE, file);
			graphics.dispose();
		} 
		catch (WriterException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		 System.out.println("\n\nYou have successfully created QR Code.");
		 //System.out.println("user_dir:" + System.getProperty("user.dir")+"/picture");
		 System.out.println("qrMessage :"+qrMessage);
		 System.out.println("icon :"+icon);
		 System.out.println("saveto :"+saveto);
	}
}
