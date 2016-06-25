/**
 * author :  lipan
 * filename :  StreamUtils.java
 * create_time : 2014年7月28日 下午7:10:11
 */
package com.ailk.sets.platform.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author : lipan
 * @create_time : 2014年7月28日 下午7:10:11
 * @desc : IO工具类
 * @update_person:
 * @update_time :
 * @update_desc :
 * 
 */
public class StreamUtils
{

    /**
     * InputStream ——> byte[]
     * 
     * @param in
     * @return
     * @throws Exception
     */
    public static byte[] getBytes(InputStream in)
    {
        ByteArrayOutputStream out;
        try
        {
            out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = in.read(b)) != -1)
            {
                out.write(b, 0, n);
            }
            in.close();
            return out.toByteArray();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * InputStream ——> String
     * 
     * @param in
     * @return
     * @throws Exception
     */
    public static String inputStream2String(InputStream in, String charset)
    {
        try
        {
            return new String(getBytes(in), charset);
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * String —— > InputStream
     * 
     * @param str
     * @return
     */
    public static InputStream String2InputStream(String str)
    {
        ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
        return stream;
    }

    /**
     * File ——>InputStream
     * 
     * @param ins
     * @param file
     * @return
     */
    public InputStream file2Inputstream(InputStream ins, File file)
    {
        try
        {
            return new FileInputStream(file);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * InputStream ——> File
     * 
     * @param ins
     * @param file
     */
    public void inputstream2File(InputStream ins, File file)
    {
        OutputStream os;
        try
        {
            os = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1)
            {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
