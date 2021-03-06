package com.ch.web.gateway.request;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 上传的文件类
 *
 * @author caich
 **/
public class RequestFile {

    private MultipartHttpServletRequest mRequest;

    public RequestFile(HttpServletRequest request) {
        this.mRequest = (MultipartHttpServletRequest) request;
    }

    /**
     * 获取文件
     *
     * @param name
     * @return
     */
    public List<MultipartFile> getFiles(String name) {
        return mRequest.getFiles(name);
    }

    public List<MultipartFile> getFiles() {
        List<MultipartFile> files = new ArrayList<MultipartFile>();
        if (isNotEmpty()) {
            for (MultipartFile file : mRequest.getFileMap().values()) {
                files.add(file);
            }
        }
        return files;
    }

    /**
     * 获取默认文件
     *
     * @return
     */
    public MultipartFile getFile() {
        Iterator<String> filenames = mRequest.getFileNames();
        if (filenames.hasNext()) {
            return mRequest.getFile(filenames.next());
        }
        return null;
    }

    /**
     * 获取文件
     *
     * @param name
     * @return
     */
    public MultipartFile getFile(String name) {
        return mRequest.getFile(name);
    }

    /**
     * 获取文件数量
     *
     * @return
     */
    public int getFileCount() {
        int count = 0;
        try {
            Iterator<String> names = mRequest.getFileNames();
            while (names.hasNext()) {
                List<MultipartFile> list = mRequest.getFiles(names.next());
                for (MultipartFile item : list) {
                    if (item != null && item.getBytes() != null && item.getBytes().length > 0 && !item.isEmpty())
                        count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 判断是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return getFileCount() < 1;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    /**
     * 保存文件
     *
     * @param path
     * @param file
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public void saveFile(String path, MultipartFile file) throws IllegalArgumentException, IOException {
        try (FileOutputStream outputStream = new FileOutputStream(new File(path));
             InputStream inputStream = file.getInputStream()) {
            int len = 0;
            byte[] buff = new byte[1024 * 1024];
            while ((len = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
            }
        }
    }
}
