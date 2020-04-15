package com.zuo.model.utils.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class DownloadUtil {


    public static void downloadFileResponse(String fileName, byte[] data, HttpServletRequest request, HttpServletResponse response) {
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setCharacterEncoding("utf-8");

        //断线续传
        long p = 0;
        long fileLength = data.length;
        long contentLength = fileLength;
        response.setHeader("Accept-Ranges", "bytes");
        String range = request.getHeader("Range");
        if (range != null && !range.trim().isEmpty() && !range.equals("null")) {
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            String[] rangeArr = range.replaceAll("bytes=", "").trim().split("-");
            p = Long.valueOf(rangeArr[0].trim());
            if (rangeArr.length == 2) {
                contentLength = Long.valueOf(rangeArr[1].trim()) - p + 1;
            } else {
                contentLength = fileLength - p + 1;
            }
        }
        response.setHeader("Content-Length", String.valueOf(contentLength));
        if (p != 0) {
            //Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
            String contentRange = new StringBuilder("bytes ")
                    .append(String.valueOf(p))
                    .append("-")
                    .append(String.valueOf(p+contentLength >= fileLength ? fileLength-1 : p+contentLength))
                    .append("/")
                    .append(String.valueOf(fileLength))
                    .toString();
            response.setHeader("Content-Range", contentRange);
        }

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new ByteArrayInputStream(data));
            bos = new BufferedOutputStream(response.getOutputStream());
            bis.skip(p);
            byte[] buff = new byte[1024];
            int bytesRead;
            long readLength = 0;
            while (readLength <= contentLength - buff.length && -1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                readLength += bytesRead;
                try {
                    bos.write(buff, 0, bytesRead);
                } catch (IOException e) {
                    break;
                }
            }
            if (contentLength > readLength && contentLength-readLength <= 1024  && -1 != (bytesRead = bis.read(buff, 0, (int)(contentLength-readLength)))) {
                try {
                    bos.write(buff, 0, bytesRead);
                } catch (IOException e) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
