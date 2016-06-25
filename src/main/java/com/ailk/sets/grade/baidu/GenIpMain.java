package com.ailk.sets.grade.baidu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class GenIpMain {

	public static void main(String[] args) {
		OutputStream out = null;

		try {
			out = new FileOutputStream(new File(
					"/Users/xugq/Downloads/IpList.txt"));

			List<IpRange> ipRanges = ParseIpLocation.loadRanges();
			for (int i = 0; i < ipRanges.size(); i++) {
				IpRange ipRange = ipRanges.get(i);
				int[] beginIp = ipRange.getBeginIp();
				int[] endIp = ipRange.getEndIp();
				
				while (true) {
					String ip = beginIp[0] + "." + beginIp[1] + "."+ beginIp[2] + ".1\n";
					out.write(ip.getBytes());
					
					boolean equal = true;
					for (int j = 0; j < 3; j++) {
						if (beginIp[j] != endIp[j]) {
							equal = false;
							break;
						}
					}
					
					if (equal)
						break;
					
					beginIp[2]++;
					if (beginIp[2] == 256) {
						beginIp[2] = 0;
						beginIp[1]++;
						if (beginIp[1] == 256) {
							beginIp[1] = 0;
							beginIp[0]++;
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.exit(0);
	}

}
