package com.dropyoung.quarkus.utils;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.core.MediaType;
import lombok.Data;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.jboss.resteasy.annotations.providers.multipart.PartType;

@Data
public class MultipartBody {

    @FormParam("file")
    @Schema(type = SchemaType.STRING, format = "binary", description = "file data")
    public String file;

}
