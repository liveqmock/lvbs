package com.daishumovie.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ImageUtils {

	private Graphics2D g = null;

	public BufferedImage fillContent(BufferedImage img, List<LineContent> contentList) {

		try {
			g = img.createGraphics();
			for (LineContent content : contentList) {
				if (content.getColor() != null) {
					g.setColor(content.getColor());
				}
				if (content.getFont() != null) {
					g.setFont(content.getFont());
				}
				g.drawString(content.getContent(), content.getX(), content.getY());
			}
			g.dispose();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return img;
	}

	/**
	 * 导入本地图片到缓冲区
	 */
	public BufferedImage loadImageLocal(String imgName) {
		try {
			return ImageIO.read(new File(imgName));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 导入网络图片到缓冲区
	 */
	public BufferedImage loadImageUrl(String imgName) {
		try {
			URL url = new URL(imgName);
			return ImageIO.read(url);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 生成新图片到本地
	 */
	public void writeImageLocal(String newImage, BufferedImage img) {
		if (newImage != null && img != null) {
			try {
				File outputfile = new File(newImage);
				ImageIO.write(img, "jpg", outputfile);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void main(String[] args) {

		if (args.length == 2) {
			ImageUtils tt = new ImageUtils();
			BufferedImage d = tt.loadImageLocal(args[0]);
			if (d == null) {
				System.out.println("input image file not found");
				System.exit(1);
			}
			List<LineContent> contentLst = new ArrayList<>();
			contentLst.add(new LineContent(90, 90, new Font("benmo_chenhei", Font.PLAIN, 40), "测试", Color.blue));
			contentLst.add(new LineContent(180, 180, new Font("AdobeHeitiStd-Regular", Font.PLAIN, 30), "测试", Color.green));
			contentLst.add(new LineContent(30, 10, new Font("wqy-zenhei", Font.PLAIN, 12), "文12", Color.green));
			contentLst.add(new LineContent(60, 10, new Font("wqy-zenhei", Font.PLAIN, 14), "文14", Color.green));
			contentLst.add(new LineContent(90, 10, new Font("wqy-zenhei", Font.PLAIN, 16), "文16", Color.green));
			contentLst.add(new LineContent(120, 10, new Font("wqy-zenhei", Font.PLAIN, 18), "文18", Color.green));
			contentLst.add(new LineContent(30, 270, new Font("msyh", Font.PLAIN, 12), "雅12", Color.green));
			contentLst.add(new LineContent(60, 270, new Font("msyh", Font.PLAIN, 14), "雅14", Color.green));
			contentLst.add(new LineContent(90, 270, new Font("msyh", Font.PLAIN, 16), "雅16", Color.green));
			tt.fillContent(d, contentLst);
			tt.writeImageLocal(args[1], d);
			System.out.println("success");
		}
	}

}

@Data
@AllArgsConstructor
class LineContent {

	private int x;

	private int y;

	private Font font;

	private String content;

	private Color color;
}