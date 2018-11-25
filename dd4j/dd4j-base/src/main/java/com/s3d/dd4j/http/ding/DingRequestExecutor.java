package com.s3d.dd4j.http.ding;

import java.util.Map;

import com.alibaba.fastjson.JSONException;
import com.s3d.dd4j.exception.DingException;
import com.s3d.dd4j.http.ContentType;
import com.s3d.dd4j.http.HttpClient;
import com.s3d.dd4j.http.HttpClientException;
import com.s3d.dd4j.http.HttpHeaders;
import com.s3d.dd4j.http.HttpMethod;
import com.s3d.dd4j.http.HttpParams;
import com.s3d.dd4j.http.HttpRequest;
import com.s3d.dd4j.http.HttpResponse;
import com.s3d.dd4j.http.apache.FormBodyPart;
import com.s3d.dd4j.http.apache.HttpMultipartMode;
import com.s3d.dd4j.http.apache.MultipartEntity;
import com.s3d.dd4j.http.entity.FormUrlEntity;
import com.s3d.dd4j.http.entity.HttpEntity;
import com.s3d.dd4j.http.entity.StringEntity;
import com.s3d.dd4j.http.factory.HttpClientFactory;
import com.s3d.dd4j.logging.InternalLogger;
import com.s3d.dd4j.logging.InternalLoggerFactory;
import com.s3d.dd4j.model.Consts;
import com.s3d.dd4j.xml.XmlStream;

/**
 * 
 * @author sulta
 *
 */
public class DingRequestExecutor {

	protected final InternalLogger logger = InternalLoggerFactory
			.getInstance(getClass());

	protected final HttpClient httpClient;
	protected final HttpParams httpParams;

	public DingRequestExecutor() {
		this(new HttpParams());
	}

	public DingRequestExecutor(HttpParams httpParams) {
		this.httpClient = HttpClientFactory.getInstance();
		this.httpParams = httpParams;
	}

	public DingResponse get(String url) throws DingException {
		HttpRequest request = new HttpRequest(HttpMethod.GET, url);
		return doRequest(request);
	}

	public DingResponse get(String url, Map<String, String> parameters)
			throws DingException {
		StringBuilder buf = new StringBuilder(url);
		if (parameters != null && !parameters.isEmpty()) {
			if (url.indexOf("?") < 0) {
				buf.append("?");
			} else {
				buf.append("&");
			}
			buf.append(FormUrlEntity.formatParameters(parameters));
		}
		return doRequest(new HttpRequest(HttpMethod.GET, buf.toString()));
	}

	public DingResponse post(String url) throws DingException {
		HttpRequest request = new HttpRequest(HttpMethod.POST, url);
		return doRequest(request);
	}

	public DingResponse post(String url, String body) throws DingException {
		HttpEntity entity = new StringEntity(body);
		HttpRequest request = new HttpRequest(HttpMethod.POST, url);
		request.setEntity(entity);
		return doRequest(request);
	}

	public DingResponse post(String url, FormBodyPart... bodyParts)
			throws DingException {
		MultipartEntity entity = new MultipartEntity(
				HttpMultipartMode.BROWSER_COMPATIBLE, null, Consts.UTF_8);
		for (FormBodyPart bodyPart : bodyParts) {
			entity.addPart(bodyPart);
		}
		HttpRequest request = new HttpRequest(HttpMethod.POST, url);
		request.setEntity(entity);
		return doRequest(request);
	}

	protected DingResponse doRequest(HttpRequest request)
			throws DingException {
		request.setParams(httpParams);
		try {
			logger.info("weixin request >> " + request.getMethod() + " "
					+ request.getURI().toString());
			HttpResponse httpResponse = httpClient.execute(request);
			HttpHeaders headers = httpResponse.getHeaders();
			DingResponse response = new DingResponse(httpResponse);
			logger.info("weixin response << " + httpResponse.getProtocol()
					+ httpResponse.getStatus() + ":" + response.getAsString());
			String contentType = headers.getFirst(HttpHeaders.CONTENT_TYPE);
			String disposition = headers
					.getFirst(HttpHeaders.CONTENT_DISPOSITION);
			// json
			if (contentType
					.contains(ContentType.APPLICATION_JSON.getMimeType())
					|| (disposition != null && disposition.indexOf(".json") > 0)) {
				checkJson(response);
			} else if (contentType.contains(ContentType.TEXT_XML.getMimeType())) {
				checkXml(response);
			} else if (contentType.contains(ContentType.TEXT_PLAIN
					.getMimeType())
					|| contentType
							.contains(ContentType.TEXT_HTML.getMimeType())) {
				try {
					checkJson(response);
					return response;
				} catch (JSONException e) {
					;
				}
				try {
					checkXml(response);
					return response;
				} catch (IllegalArgumentException ex) {
					;
				}
				throw new DingException(response.getAsString());
			}
			return response;
		} catch (HttpClientException e) {
			throw new DingException(e);
		}
	}

	protected void checkJson(DingResponse response) throws DingException {
		JsonResult jsonResult = response.getAsJsonResult();
		response.setJsonResult(true);
		if (jsonResult.getCode() != 0) {
			throw new DingException(Integer.toString(jsonResult.getCode()),
					jsonResult.getDesc());
		}
	}

	protected void checkXml(DingResponse response) throws DingException {
		String xmlContent = response.getAsString();
		if (xmlContent.length() != xmlContent.replaceFirst("<retcode>",
				"<return_code>").length()) {
			// <?xml><root><data..../data></root>
			xmlContent = xmlContent.replaceFirst("<root>", "<xml>")
					.replaceFirst("<retcode>", "<return_code>")
					.replaceFirst("</retcode>", "</return_code>")
					.replaceFirst("<retmsg>", "<return_msg>")
					.replaceFirst("</retmsg>", "</return_msg>")
					.replaceFirst("</root>", "</xml>");
		}
		XmlResult xmlResult = XmlStream.fromXML(xmlContent, XmlResult.class);
		response.setText(xmlContent);
		response.setXmlResult(true);
		if ("0".equals(xmlResult.getReturnCode())) {
			return;
		}
		if (!Consts.SUCCESS.equalsIgnoreCase(xmlResult.getReturnCode())) {
			throw new DingException(xmlResult.getReturnCode(),
					xmlResult.getReturnMsg());
		}
		if (!Consts.SUCCESS.equalsIgnoreCase(xmlResult.getResultCode())) {
			throw new DingException(xmlResult.getErrCode(),
					xmlResult.getErrCodeDes());
		}
	}

	public HttpClient getExecuteClient() {
		return httpClient;
	}

	public HttpParams getExecuteParams() {
		return httpParams;
	}
}
