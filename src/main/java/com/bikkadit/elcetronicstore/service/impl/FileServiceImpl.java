package com.bikkadit.elcetronicstore.service.impl;

import com.bikkadit.elcetronicstore.exceptions.BadApiException;
import com.bikkadit.elcetronicstore.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {


    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        log.info("File Service is being initiated");

        String originalFilename = file.getOriginalFilename();

        log.info("File name : {} ", originalFilename);

        String fileName = UUID.randomUUID().toString();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileNameWithExtension = fileName + extension;
        String fullPathWithFileName = path + fileNameWithExtension;

        log.info("Full image path : {} ", fullPathWithFileName);
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {

            //file save
            log.info("File extension is  : {} ", extension);
            File folder = new File(path);

            if (!folder.exists()) {
                //create the folder
                folder.mkdirs();
            }

            //upload
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
            return fileNameWithExtension;

        } else {
            throw new BadApiException("File with this " + extension + " not allowed !!!");
        }

    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        log.info("Get File Resource Service is being initiated");

        String fullPath = path + File.separator + name;

        InputStream inputStream = new FileInputStream(fullPath);

        return inputStream;
    }
}
