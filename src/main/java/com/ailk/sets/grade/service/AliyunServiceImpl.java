package com.ailk.sets.grade.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyun.common.utils.IOUtils;
import com.aliyun.openservices.ClientConfiguration;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.openservices.oss.model.OSSObject;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;

/**
 * 阿里云文件操作
 * 
 * @author xugq
 *
 */
@Service
public class AliyunServiceImpl implements IAliyunService {

	private static final Logger logger = Logger
			.getLogger(AliyunServiceImpl.class);

	@Value("${aliyun.oss.endpoint}")
	private String endpoint;

	@Value("${aliyun.oss.accessKeyId}")
	private String accessKeyId;

	@Value("${aliyun.oss.accessKeySecret}")
	private String accessKeySecret;

	private OSSClient ossClient;

	@PostConstruct
	public void init() {
		ossClient = null;
	}

	@Override
	public synchronized OSSClient getOssClient() {
		if (ossClient == null)
			createOssClient();

		return ossClient;
	}

	@Override
	public void createBucket(String bucketName) {
		OSSClient client = getOssClient();

		client.createBucket(bucketName);
	}

	@Override
	public String putObject(String bucketName, String suffix, byte[] bytes) {
		String md5Str = MD5Util.getMD5String(bytes);
		String objectName = md5Str + "_" + System.currentTimeMillis() + "."
				+ suffix;

		ObjectMetadata meta = new ObjectMetadata();
		meta.setContentLength(bytes.length);

		OSSClient client = getOssClient();
		PutObjectResult result = client.putObject(bucketName, objectName,
				new ByteArrayInputStream(bytes), meta);
		if (result == null)
			return null;

		if (!md5Str.equals(result.getETag()))
			return null;

		return objectName;
	}

	@Override
	public byte[] getObject(String bucketName, String objectName)
			throws IOException {
		OSSClient client = getOssClient();
		OSSObject object = client.getObject(bucketName, objectName);
		InputStream in = object.getObjectContent();

		return IOUtils.readStreamAsBytesArray(in);
	}

	@Override
	public String getUrl(String bucketName, String objectName, long expiresAt) {
		Date expiration = new Date(expiresAt);
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				bucketName, objectName);
		generatePresignedUrlRequest.setExpiration(expiration);

		OSSClient client = getOssClient();
		return client.generatePresignedUrl(generatePresignedUrlRequest)
				.toString();
	}

	@Override
	public String getUrl(String bucketName, String objectName) {
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				bucketName, objectName);
		generatePresignedUrlRequest.setExpiration(new Date(System
				.currentTimeMillis() + 86400000000L));

		OSSClient client = getOssClient();
		return client.generatePresignedUrl(generatePresignedUrlRequest)
				.toString();
	}

	@Override
	public String getImgUrl(String bucketName, String objectName,
			long expiresAt, int width, int height) {
		String object = objectName + '@' + width + "w_" + height + "h.png";
		return getUrl(bucketName, object, expiresAt);
	}

	@Override
	public String getImgUrl(String bucketName, String objectName, int width,
			int height) {
		String object = objectName + '@' + width + "w_" + height + "h.png";
		return getUrl(bucketName, object);
	}

	/**
	 * 创建OSSClient对象
	 */
	private void createOssClient() {
		try {
			ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret,
					new ClientConfiguration());
			logger.info("阿里云客户端创建成功");
		} catch (Exception e) {
			logger.error("阿里云客户端创建失败", e);
		}
	}

}
