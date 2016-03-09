package com.crazyjohn.playboy.controller.self;

import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

public class Self {

	/**
	 * upload the head photo
	 * 
	 * @param context
	 */
	public void form(RoutingContext context) {
		// context.request().uploadHandler(upload -> {
		// upload.streamToFileSystem("/upload/" + upload.filename());
		//
		// });
		// response
		context.response().putHeader("Content-Type", "text/plain");
		context.response().setChunked(true);
		for (FileUpload f : context.fileUploads()) {
			System.out.println("f");
			context.response().write("Filename: " + f.fileName());
			context.response().write("\n");
			context.response().write("Size: " + f.size());
		}
		context.response().end();
	}

	/**
	 * show the uploadfile form
	 * 
	 * @param context
	 */
	public void uploadForm(RoutingContext context) {
		context.response()
				.putHeader("content-type", "text/html")
				.end("<form action=\"/form\" method=\"post\" enctype=\"multipart/form-data\">\n" + "    <div>\n"
						+ "        <label for=\"name\">Select a file:</label>\n"
						+ "        <input type=\"file\" name=\"file\" />\n" + "    </div>\n"
						+ "    <div class=\"button\">\n" + "        <button type=\"submit\">Send</button>\n"
						+ "    </div>" + "</form>");
	}
}
