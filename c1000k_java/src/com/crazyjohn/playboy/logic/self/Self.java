package com.crazyjohn.playboy.logic.self;

import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

import java.util.Set;

import com.crazyjohn.playboy.log.Logger;

public class Self {

	/**
	 * upload the head photo
	 * 
	 * @param context
	 */
	public void uploadHead(RoutingContext context) {
		Logger.log("Upload request comming...");
		Set<FileUpload> uploads = context.fileUploads();
		uploads.stream().forEach(upload -> {
			System.out.println(upload.name());
		});
		context.response().end("Upload finished.");
	}
}
