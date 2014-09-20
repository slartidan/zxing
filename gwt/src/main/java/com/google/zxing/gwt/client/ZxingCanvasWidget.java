package com.google.zxing.gwt.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class ZxingCanvasWidget extends Composite {

	private static final String COLOR_0 = "#FFF";
	private static final String COLOR_1 = "#000";

	private final QRCodeWriter qrCodeWriter;
	private final Canvas canvas;
	private final Context2d context2d;
	private final boolean initialized;
	private String text;

	public ZxingCanvasWidget() {

		qrCodeWriter = new QRCodeWriter();
		canvas = Canvas.createIfSupported();
		if (canvas == null) {
			context2d = null;
			initialized = false;
			initWidget(new Label("Canvas not supported by this browser"));
		} else {
			context2d = canvas.getContext2d();
			initialized = true;
			initWidget(canvas);
		}
	}

	public void setText(String text) throws WriterException {
		if (initialized) {
			this.text = text;
			refresh();
		}
	}

	public void refresh() throws WriterException {
		if (initialized) {

			int clientHeight = canvas.getElement().getClientHeight();
			int clientWidth = canvas.getElement().getClientWidth();

			canvas.setCoordinateSpaceHeight(clientHeight);
			canvas.setCoordinateSpaceWidth(clientWidth);

			int getHeight = clientHeight;
			int getWidth = clientWidth;

			BitMatrix imageData = qrCodeWriter.encode(text,
					BarcodeFormat.QR_CODE, getWidth, getHeight);

			for (int x = 0; x < imageData.getWidth(); x++) {
				for (int y = 0; y < imageData.getHeight(); y++) {
					drawPixel(x, y, imageData.get(x, y));
				}
			}
		}
	}

	void drawPixel(int x, int y, boolean bit) {
		if (initialized) {
			context2d.setFillStyle(bit ? COLOR_1 : COLOR_0);
			context2d.fillRect(x, y, 1, 1);
		}
	}
}
