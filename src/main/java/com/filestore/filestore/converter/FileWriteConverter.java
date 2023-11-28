package com.filestore.filestore.converter;

import com.filestore.filestore.entity.File;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class FileWriteConverter implements Converter<File, OutboundRow> {

    @Override
    public OutboundRow convert(File file) {
        OutboundRow row = new OutboundRow();
        if (file.getId() != null) {
            row.put("id", Parameter.from(file.getId()));
        }
        if (file.getOwner() != null && file.getOwner().getId() != null) {
            row.put("owner_id", Parameter.from(file.getOwner().getId()));
        }
        if (file.getFileName() != null) {
            row.put("file_name", Parameter.from(file.getFileName()));
        }
        if (file.getFilePath() != null) {
            row.put("file_path", Parameter.from(file.getFilePath()));
        }
        if (file.getStatus() != null) {
            row.put("status", Parameter.from(file.getStatus()));
        }
        if (file.getCreatedAt() != null) {
            row.put("created_at", Parameter.from(file.getCreatedAt()));
        }
        if (file.getUpdatedAt() != null) {
            row.put("updated_at", Parameter.from(file.getUpdatedAt()));
        }
        return row;
    }
}
