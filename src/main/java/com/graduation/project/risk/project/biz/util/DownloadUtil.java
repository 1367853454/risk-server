package com.graduation.project.risk.project.biz.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

public class DownloadUtil {
    public static File downloadFile(String address) {
        try {
            URL url = new URL(address);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            InputStream in = connection.getInputStream();
            File file = new File(UUID.randomUUID()+".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[512];
            while (true) {
                int len = in.read(buf);
                if (len == -1) {
                    break;
                }
                fos.write(buf, 0, len);
            }
            in.close();
            fos.flush();
            fos.close();
            return file;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
