package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> getCredentials(int userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = ${id}")
    Credential getCredentialById(int id);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{id}")
    int deleteCredential(int id);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, password = #{password}, key = #{key} WHERE credentialId = #{credentialid}")
    int updateCredential(Credential credential);
}
