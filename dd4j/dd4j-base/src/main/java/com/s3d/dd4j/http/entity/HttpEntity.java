package com.s3d.dd4j.http.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.s3d.dd4j.http.ContentType;

public interface HttpEntity {
	
	ContentType getContentType();

	long getContentLength();

	InputStream getContent() throws IOException;

	void writeTo(OutputStream outstream) throws IOException;
}
