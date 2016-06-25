package com.ailk.sets.grade.service;

import java.io.IOException;

import com.aliyun.openservices.oss.OSSClient;

/**
 * 阿里云文件操作
 * 
 * @author xugq
 *
 */
public interface IAliyunService {

	/**
	 * 获取OSS客户端
	 * 
	 * @return OSS客户端
	 */
	public OSSClient getOssClient();

	/**
	 * 创建bucket
	 * 
	 * @param bucketName
	 *            bucket名称
	 */
	public void createBucket(String bucketName);

	/**
	 * 上传文件
	 * 
	 * @param bucketName
	 *            bucket名称
	 * @param suffix
	 *            文件后缀
	 * @param bytes
	 *            字节数组
	 * @return 上传对象objectName
	 */
	public String putObject(String bucketName, String suffix, byte[] bytes);

	/**
	 * 下载文件
	 * 
	 * @param bucketName
	 *            bucket名称
	 * @param objectName
	 *            对象名称
	 * @return 字节数组
	 * @throws IOException
	 */
	public byte[] getObject(String bucketName, String objectName)
			throws IOException;

	/**
	 * 获取对象的URL
	 * 
	 * @param bucketName
	 *            bucket名称
	 * @param objectName
	 *            对象名称
	 * @param expiresAt
	 *            失效时间点
	 * @return URL
	 */
	public String getUrl(String bucketName, String objectName, long expiresAt);

	/**
	 * 获取对象的URL
	 * 
	 * @param bucketName
	 *            bucket名称
	 * @param objectName
	 *            对象名称
	 * @return URL
	 */
	public String getUrl(String bucketName, String objectName);

	/**
	 * 获取对象的URL
	 * 
	 * @param bucketName
	 *            bucket名称
	 * @param objectName
	 *            对象名称
	 * @param expiresAt
	 *            失效时间点
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @return URL
	 */
	public String getImgUrl(String bucketName, String objectName,
			long expiresAt, int width, int height);

	/**
	 * 获取对象的URL
	 * 
	 * @param bucketName
	 *            bucket名称
	 * @param objectName
	 *            对象名称
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @return URL
	 */
	public String getImgUrl(String bucketName, String objectName, int width,
			int height);

}
