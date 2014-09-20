package com.google.zxing.gwtexample.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.zxing.WriterException;
import com.google.zxing.gwt.client.ZxingCanvasWidget;

public class ZxingGwtExample implements EntryPoint {

	public void onModuleLoad() {
		final TextBox input = new TextBox();
		final ZxingCanvasWidget output = new ZxingCanvasWidget();
		output.setSize("300px","300px");

		RootPanel.get().add(input);
		RootPanel.get().add(output);

		input.setFocus(true);

		input.addKeyUpHandler(new KeyUpHandler() {

			public void onKeyUp(KeyUpEvent event) {
				try {
					output.setText(input.getText());
				} catch (WriterException e) {
					Window.alert("encoding failed");
				}
			}
		});
	}
}
