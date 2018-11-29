package com.mymall.service.impl;

import com.google.common.collect.Lists;
import com.mymall.service.IFileService;
import com.mymall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
public class FileServiceImpl  implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file, String path){
        String fileName = file.getOriginalFilename();
        //extension name
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("start upload, filename:{}","path:{}","new filename:{}",fileName,path,uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //->ftp ->delete
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));

            targetFile.delete();
        } catch (IOException e) {
            logger.error("exception upload", e);
            return null;
        }
        return targetFile.getName();
    }
}
