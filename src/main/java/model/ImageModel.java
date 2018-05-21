package model;

import java.awt.image.BufferedImage;

public class ImageModel {
	
	private BufferedImage icon;
	private String Ackno;
	
	public BufferedImage getIcon() {
		return icon;
	}
	public void setIcon(BufferedImage bufferedImage) {
		this.icon = bufferedImage;
	}
	public String getAckno() {
		return Ackno;
	}
	public void setAckno(String ackno) {
		Ackno = ackno;
	}

}
