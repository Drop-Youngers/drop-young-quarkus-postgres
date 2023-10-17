package com.dropyoung.quarkus.controllers;

import com.dropyoung.quarkus.models.File;
import com.dropyoung.quarkus.payload.ApiResponse;
import com.dropyoung.quarkus.services.IFileService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Tag(ref = "Files")
@Path("files")
@RequiredArgsConstructor
public class FileController {

    @ConfigProperty(name = "profiles.uploads.directory")
    String uploadsDirectory;

    private final IFileService fileService;

    @GET
    @Path("all")
    @RolesAllowed({"ADMIN"})
    public Response getAllFiles() {
        return Response.ok(ApiResponse.success("Files fetched successfully", this.fileService.findAll())).build();
    }

    @GET
    @Path("{id}")
    public Response findById(@PathParam("id") UUID id) {
        return Response.ok(ApiResponse.success("File fetched successfully", this.fileService.findById(id))).build();
    }

    @GET
    @Path("load-file/{id}")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response loadFile(@PathParam("id") UUID id) {
        File file = this.fileService.findById(id);
        java.io.File download = new java.io.File(uploadsDirectory, file.getName());
        if (download.exists()) {
            try {
                byte[] data = Files.readAllBytes(download.toPath());
                return Response.ok(data).header("Content-Disposition", "attachment; filename=" + file.getName()).build();
            } catch (IOException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to download file").build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("File not found").build();
        }
    }

    @DELETE
    @Transactional
    @Path("{id}")
    public Response deleteFile(
            @PathParam("id") UUID id
    ) {
        this.fileService.delete(id);
        return Response.ok(ApiResponse.success("File deleted successfully", null)).build();
    }


}
