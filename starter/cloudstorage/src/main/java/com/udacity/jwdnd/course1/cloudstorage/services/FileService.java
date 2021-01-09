package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class FileService {
    private FilesMapper filesMapper;

    public FileService(FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
    }

    public int saveFile(MultipartFile data, int userId) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(data.getOriginalFilename()));
        String contentType = data.getContentType();
        String size = String.valueOf(data.getSize());
        File file = new File(fileName, contentType, size, userId, data.getBytes());
        return filesMapper.saveFile(file);
    }

    public File getFile(int fileId) {
        return filesMapper.getFileById(fileId);
    }

    public int deleteFile(int fileId) {
        return filesMapper.deleteFile(fileId);
    }

    public List<File> getFilesByUser(int userId) {
        return filesMapper.getFilesByUser(userId);
    }
}
