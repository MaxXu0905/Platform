package com.ailk.sets.grade.baidu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

/**
 * 根据经纬度获取区域位置，更新用户位置
 * 
 * @author xugq
 * 
 */
public class ParseLocation {

	private static Gson gson = new Gson();

	public static void main(String[] args) {
		ParseLocation instance = new ParseLocation();
		GeoLocation location = new GeoLocation();

		double[] values = { 117.98494312, 26.0501183, 113.39481756,
				23.40800373, 114.41586755, 43.46823822, 108.92427443,
				23.55225469, 128.04741371, 47.35659164, 113.48680406,
				34.15718377, 106.7349961, 26.90282593, 106.15548127,
				37.32132311, 117.21600521, 31.85925242, 118.52766339,
				36.09928993, 119.36848894, 33.01379717, 126.26287593,
				43.67884619, 112.51549586, 37.86656599, 109.50378929,
				35.86002626, 115.66143362, 38.61383975, 119.95720242,
				29.15949412, 102.89915972, 30.36748094, 89.13798168,
				31.3673154, 111.72066355, 27.69586405, 115.67608237,
				27.75725844, 101.59295164, 24.8642128, 122.75359156,
				41.62160011, 112.41056219, 31.20931625, 85.61489934,
				42.12700096, 96.20254367, 35.499761, 102.4576246, 38.10326734,
				109.73375549, 19.1805008 };
		for (int i = 0; i < values.length; i += 2) {
			location.setLat(values[i + 1]);
			location.setLng(values[i]);
			GeoReverseResponse response = instance.getLocation(location);

			System.out.println(values[i] + "\t" + values[i + 1] + "\t"
					+ response.getResult().getAddressComponent().getCity());
		}
	}

	public GeoReverseResponse getLocation(GeoLocation location) {
		try {
			StringBuilder builder = new StringBuilder();
			builder.append("http://api.map.baidu.com/geocoder/v2/?ak=982570957E7bA589545fd19310d4be9e&location=");
			builder.append(location.lat);
			builder.append(",");
			builder.append(location.lng);
			builder.append("&output=json");
			String urlStr = builder.toString();

			URL url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("GET");

			InputStream input = urlConn.getInputStream();
			InputStreamReader isr = new InputStreamReader(input, "utf-8");
			BufferedReader read = new BufferedReader(isr);
			String str = "";
			String s;
			while ((s = read.readLine()) != null)
				str += s;

			return (GeoReverseResponse) gson.fromJson(str,
					GeoReverseResponse.class);
		} catch (Exception e) {
			return null;
		}
	}

}
