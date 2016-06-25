package com.ailk.sets.grade.baidu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailk.sets.grade.baidu.IpLocation.AddressDetail;
import com.ailk.sets.grade.baidu.IpLocation.Content;
import com.ailk.sets.grade.baidu.IpLocation.Point;
import com.ailk.sets.grade.dao.intf.IIpInfoDao;
import com.ailk.sets.grade.jdbc.IpInfo;
import com.google.gson.Gson;

@Service
@Transactional
public class ParseIpLocation {

	private static Gson gson = new Gson();

	@Autowired
	private IIpInfoDao ipInfoDao;

	public static List<IpRange> loadRanges() {
		InputStream in = null;
		List<IpRange> ipRanges = new ArrayList<IpRange>();

		try {
			in = new FileInputStream(new File("/Users/xugq/Downloads/Ip.txt"));
			byte[] bytes = IOUtils.toByteArray(in);
			String data = new String(bytes, "UTF-8");
			String[] lines = data.split("\n");
			for (String line : lines) {
				if (line == null || line.isEmpty())
					continue;

				String[] fields = line.split(" ");
				if (fields.length < 2)
					continue;

				IpRange ipRange = new IpRange();

				String[] nums = fields[0].split("\\.");
				if (nums.length != 4) {
					System.out.println(fields[0] + " is not a correct ip");
					continue;
				}

				int[] beginIp = new int[4];
				for (int i = 0; i < 4; i++)
					beginIp[i] = Integer.parseInt(nums[i]);
				ipRange.setBeginIp(beginIp);

				nums = fields[1].split("\\.");
				if (nums.length != 4) {
					System.out.println(fields[0] + " is not a correct ip");
					continue;
				}

				int[] endIp = new int[4];
				for (int i = 0; i < 4; i++)
					endIp[i] = Integer.parseInt(nums[i]);
				ipRange.setEndIp(endIp);

				ipRanges.add(ipRange);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}

		return ipRanges;
	}

	public static IpInfo getIpLocation(String ak, String ip) {
		String ipKey = ip.substring(0, ip.length() - 2);

		for (int i = 0; i < 10; i++) {
			HttpURLConnection urlConn = null;

			try {
				StringBuilder builder = new StringBuilder();
				builder.append("http://api.map.baidu.com/location/ip?ak=" + ak
						+ "&coor=bd09ll&ip=");
				builder.append(ip);
				String urlStr = builder.toString();

				URL url = new URL(urlStr);
				urlConn = (HttpURLConnection) url.openConnection();
				urlConn.setDoOutput(true);
				urlConn.setRequestMethod("GET");

				InputStream input = urlConn.getInputStream();
				InputStreamReader isr = new InputStreamReader(input, "utf-8");
				BufferedReader read = new BufferedReader(isr);
				String str = "";
				String s;
				while ((s = read.readLine()) != null)
					str += s;

				IpLocation ipLocation = gson.fromJson(str, IpLocation.class);
				if (ipLocation == null)
					return null;

				if (ipLocation.getStatus() != 0)
					return null;

				if (ipLocation.getContent() == null)
					return null;

				Content content = ipLocation.getContent();
				if (content.getAddress_detail() == null)
					return null;

				String[] fields = ipLocation.getAddress().split("\\|");
				if (!fields[0].equals("CN"))
					return null;

				AddressDetail addressDetail = content.getAddress_detail();
				Point point = content.getPoint();

				IpInfo ipInfo = new IpInfo();
				ipInfo.setIp(ipKey);
				ipInfo.setProvince(addressDetail.getProvince());
				ipInfo.setCity(addressDetail.getCity());
				ipInfo.setCityCode(addressDetail.getCity_code());
				ipInfo.setDistrict(addressDetail.getDistrict());
				ipInfo.setStreet(addressDetail.getStreet());
				ipInfo.setStreetNumber(addressDetail.getStreet_number());
				ipInfo.setLongitude(Double.parseDouble(point.getX()));
				ipInfo.setLatitude(Double.parseDouble(point.getY()));
				ipInfo.setNetName(fields[4]);
				return ipInfo;
			} catch (ConnectException e) {
				continue;
			} catch (Exception e) {
				e.printStackTrace();
				break;
			} finally {
				if (urlConn != null)
					urlConn.disconnect();
			}
		}

		return null;
	}

	public boolean exist(String ip) {
		String ipKey = ip.substring(0, ip.length() - 2);
		IpInfo ipInfo = ipInfoDao.getEntity(ipKey);
		if (ipInfo != null)
			return true;
		else
			return false;
	}

	public void save(List<IpInfo> ipInfos) {
		for (IpInfo ipInfo : ipInfos) {
			ipInfoDao.save(ipInfo);
		}
	}

}
