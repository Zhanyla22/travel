package com.example.light_up_travel.services.serviceZ;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.light_up_travel.entity.Article;
import com.example.light_up_travel.exceptions.EmptyFileException;

import com.example.light_up_travel.exceptions.NotFoundResourceException;
import com.example.light_up_travel.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private ArticleRepository articleRepository;

    @SneakyThrows
    public String saveVideo(MultipartFile multipartfile) {
        if (multipartfile.isEmpty()) {
            throw new EmptyFileException("File is empty");
        }

        final String urlKey = "cloudinary://532431178934438:dziz4lD4M6_iip6t1tuF0an_N8Q@db5aw8xbo";

        Cloudinary cloudinary = new Cloudinary(urlKey);

        File saveFile = Files.createTempFile(
                        System.currentTimeMillis() + "",
                        Objects.requireNonNull
                                (multipartfile.getOriginalFilename(), "File must have an extension")
                )
                .toFile();
        multipartfile.transferTo(saveFile);


        Map upload = cloudinary.uploader().uploadLarge(saveFile, ObjectUtils.asMap( "resource_type", "video"));

        return (String) upload.get("url");
    }



    @SneakyThrows
    public String saveFile(MultipartFile multipartfile) {
        if (multipartfile.isEmpty()) {
            throw new EmptyFileException("File is empty");
        }

        final String urlKey = "cloudinary://532431178934438:dziz4lD4M6_iip6t1tuF0an_N8Q@db5aw8xbo";

        Cloudinary cloudinary = new Cloudinary(urlKey);

        File saveFile = Files.createTempFile(
                        System.currentTimeMillis() + "",
                        Objects.requireNonNull
                                (multipartfile.getOriginalFilename(), "File must have an extension")
                )
                .toFile();
        multipartfile.transferTo(saveFile);


        Map upload = cloudinary.uploader().upload(saveFile, ObjectUtils.emptyMap());

        return (String) upload.get("url");
    }
}
