package com.vicheak.onlinestore.api.file;

import com.vicheak.onlinestore.api.file.web.FileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileServiceImpl implements FileService {
    @Value("${file.server-path}")
    private String serverPath;

    @Value("${file.base-uri}")
    private String fileBaseUri;

    @Override
    public FileDto uploadSingle(MultipartFile file) {
        return this.save(file);
    }

    @Override
    public List<FileDto> uploadMultiple(List<MultipartFile> files) {
        /*if(files.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Request is empty!");*/
        return files.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    public FileDto findByName(String name) throws IOException {
        Path path = Paths.get(serverPath + name);

        if (Files.exists(path)) {
            //Start loading the file by name
            Resource resource = UrlResource.from(path.toUri());

            /*try {
                File file = resource.getFile();
                System.out.println(file.getName());
                System.out.println(file.length());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/
            return FileDto.builder()
                    .name(name)
                    .uri(fileBaseUri + name)
                    .size(resource.contentLength())
                    .extension(this.getExtension(name))
                    .build();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Resource is not found");
    }

    @Override
    public List<FileDto> findAll() {
        Path path = Paths.get(serverPath);

        try {
            Stream<Path> paths = Files.list(path);

            return paths.map(p -> {
                        Resource resource = UrlResource.from(p.toUri());
                        try {
                            return FileDto.builder()
                                    .name(resource.getFilename())
                                    .uri(fileBaseUri + resource.getFilename())
                                    .size(resource.contentLength())
                                    .extension(this.getExtension(resource.getFilename()))
                                    .build();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByName(String name) {
        Path path = Paths.get(serverPath + name);

        try {
            boolean isDeleted = Files.deleteIfExists(path);

            if (!isDeleted)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Resource is not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        Path path = Paths.get(serverPath);

        try (
                Stream<Path> paths = Files.list(path);
        ) {
            List<Path> pathList = paths.toList();
            for (Path p : pathList) {
                Files.delete(p);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getExtension(String fileName) {
        //Get file extension
        int lastDotIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastDotIndex + 1);
    }

    private FileDto save(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "file is empty!");
        }

        //File's name must be unique
        String extension = this.getExtension(file.getOriginalFilename());
        String name = UUID.randomUUID() + "." + extension;
        String uri = fileBaseUri + name;
        Long size = file.getSize();

        //Create file path (absolute path)
        Path path = Paths.get(serverPath + name);

        //Copy file
        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return FileDto.builder()
                .name(name)
                .uri(uri)
                .size(size)
                .extension(extension)
                .build();
    }
}
