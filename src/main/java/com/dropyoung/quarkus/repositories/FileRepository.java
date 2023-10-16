package com.dropyoung.quarkus.repositories;

import com.dropyoung.quarkus.models.File;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.UUID;

@Transactional
@ApplicationScoped
public class FileRepository implements PanacheRepositoryBase<File, UUID> {
}
