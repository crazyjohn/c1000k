package com.crazyjohn.playboy.logic.self;

import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.RoutingContext;

import java.util.Set;

public class SelfController {

	public static void uploadHead(RoutingContext context) {
		Set<FileUpload> uploads = context.fileUploads();
		uploads.stream().forEach(upload -> {
			System.out.println(upload.name());
		});
	}
}
