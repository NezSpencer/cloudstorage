package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {
    private Integer fileid;
    private String fileName;
    private String contentType;
    private String fileSize;
    private Integer userId;
    private byte[] fileData;

    public File(String fileName, String contentType, String fileSize, Integer userId, byte[] fileData) {
        this.fileid = null;
        this.fileName = fileName;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.userId = userId;
        this.fileData = fileData;
    }

    public Integer getFileid() {
        return fileid;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public Integer getUserId() {
        return userId;
    }

    public byte[] getFileData() {
        return fileData;
    }
}
