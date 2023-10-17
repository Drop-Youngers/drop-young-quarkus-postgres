package com.dropyoung.quarkus.serviceImpls;


import com.dropyoung.quarkus.enums.EFileStatus;
import com.dropyoung.quarkus.exceptions.CustomBadRequestException;
import com.dropyoung.quarkus.models.File;
import com.dropyoung.quarkus.models.User;
import com.dropyoung.quarkus.repositories.FileRepository;
import com.dropyoung.quarkus.services.IFileService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.MultivaluedMap;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class FileServiceImpl implements IFileService {

    @ConfigProperty(name = "profiles.uploads.directory")
    String uploadDirectory;

    @ConfigProperty(name = "deleted.uploads.directory")
    String deletedUploadsDirectory;

    @ConfigProperty(name = "server.path")
    String serverPath;

    private final FileRepository fileRepository;

    @Override
    @Transactional
    public File save(UUID uploadedById, MultipartFormDataInput input) {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<String> fileNames = new ArrayList<>();
        List<InputPart> inputParts = uploadForm.get("file");
        String fileName = null;
        File file = new File();
        UUID fileId = UUID.randomUUID();
        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header =
                        inputPart.getHeaders();
                fileName = fileId + "-" + getFileName(header);
                fileNames.add(fileName);
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                writeFile(inputStream, fileName);
                file.setId(fileId);
                file.setName(fileName);
                file.setPath(uploadDirectory + "/" + fileName);
                file.setSize((int) Files.size(Paths.get(uploadDirectory + "/" + fileName)));
                file.setType(Files.probeContentType(Paths.get(uploadDirectory + "/" + fileName)));
                file.setStatus(EFileStatus.SAVED);
                file.setUrl(serverPath + "/files/load-file/" + fileId);
                file.setUploadedById(uploadedById.toString());
            } catch (Exception e) {
                e.printStackTrace();
                throw new CustomBadRequestException(e.getMessage());
            }
        }
        this.fileRepository.persist(file);
        return file;
    }

    @Override
    public List<File> findAll() {
        return this.fileRepository.listAll();
    }


    private void writeFile(InputStream inputStream, String fileName)
            throws IOException {
        byte[] bytes = IOUtils.toByteArray(inputStream);
        java.io.File customDir = new java.io.File(uploadDirectory);
        fileName = customDir.getAbsolutePath() +
                java.io.File.separator + fileName;
        Files.write(Paths.get(fileName), bytes,
                StandardOpenOption.CREATE_NEW);
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.
                getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "";
    }

    @Override
    public File findById(UUID id) {
        return this.fileRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        java.io.File deleted = new java.io.File(uploadDirectory, this.fileRepository.findById(id).getName());
        boolean isMoved = deleted.renameTo(new java.io.File(deletedUploadsDirectory,  deleted.getName()));
        this.fileRepository.deleteById(id);
        if (!isMoved) {
            throw new CustomBadRequestException("Failed to remove file on disk");
        }
    }
}