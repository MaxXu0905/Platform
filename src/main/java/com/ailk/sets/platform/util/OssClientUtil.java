package com.ailk.sets.platform.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.openservices.ClientConfiguration;
import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;

public class OssClientUtil {
	private static Logger logger = LoggerFactory.getLogger(OssClientUtil.class);

	private static final String OSS_ENDPOINT = "http://oss-cn-beijing.aliyuncs.com/";

	private static String accessId = "";
	private static String accessKey = "";
	private OSSClient client;

	public OssClientUtil() {
		ClientConfiguration config = new ClientConfiguration();
		client = new OSSClient(OSS_ENDPOINT, accessId, accessKey, config);
	}

	/**
	 * 上传文件
	 * 
	 * @param bucketName
	 * @param objectName
	 * @param is
	 * @param contentType
	 * @return
	 * @throws OSSException
	 * @throws ClientException
	 * @throws IOException
	 */
	public String uploadFile(String bucketName, String objectName, InputStream is, String contentType) throws OSSException, ClientException, IOException {

		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(is.available());
		objectMeta.setContentType(contentType);
		objectMeta.setContentEncoding("GBK");
		objectMeta.setContentDisposition("inline");

		PutObjectResult result = client.putObject(bucketName, objectName, is, objectMeta);
		if (result != null)
			return result.getETag();
		else
			return null;
	}

	public void downloadFile(OSSClient client, String bucketName, String key, String filename) throws OSSException, ClientException {
		client.getObject(new GetObjectRequest(bucketName, key), new File(filename));
	}

	private String setSHA1Code(String httpMethodName, String bucketName, String objectName) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, 100);
		String content = httpMethodName + "\n" + "\n" + "\n" + (calendar.getTimeInMillis() / 1000) + "\n" + "/" + bucketName + "/" + objectName;
		try {
			SecretKeySpec signingKey = new SecretKeySpec(accessKey.getBytes(), "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(content.getBytes("UTF-8"));
			return URLEncoder.encode(Base64.encodeBase64String(rawHmac), "UTF-8");
		} catch (NoSuchAlgorithmException e) {
			logger.error("error call setSHA1Code", e);
		} catch (InvalidKeyException e) {
			logger.error("error call setSHA1Code", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("error call setSHA1Code", e);
		}
		return "";
	}

	private long getExpireTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, 100);
		return calendar.getTimeInMillis() / 1000;
	}

	public String getAddress(String bucketName, String objectName) {
		return "http://" + bucketName + ".oss-cn-beijing.aliyuncs.com/" + objectName + "?Expires=" + getExpireTime() + "&OSSAccessKeyId=" + accessId + "&Signature="
				+ setSHA1Code("GET", bucketName, objectName);
	}
}
