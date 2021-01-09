package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {

    @Results(id = "filesDbMapping", value = {
            @Result(property = "fileName", column = "filename"),
            @Result(property = "contentType", column = "contenttype"),
            @Result(property = "fileSize", column = "filesize"),
            @Result(property = "userId", column = "userid"),
            @Result(property = "fileData", column = "filedata")
    })
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int saveFile(File file);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getFilesByUser(int userId);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFileById(int fileId);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    int deleteFile(int fileId);
}
