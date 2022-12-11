package com.due.oldmarket.payload;

public class UploadFileResponse {
    private String fileId;
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
    private String message;

    public String getMessage() {
        return message;
    }

    public UploadFileResponse() {

    }

    public UploadFileResponse(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public UploadFileResponse(String fileId, String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public UploadFileResponse(String fileId, String fileName, String fileDownloadUri, String fileType, long size, String message) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
