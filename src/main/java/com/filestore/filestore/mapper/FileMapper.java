package com.filestore.filestore.mapper;

import com.filestore.filestore.dto.FileDto;
import com.filestore.filestore.entity.File;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FileMapper {

    FileDto map(File file);

    File map(FileDto fileDto);
}
