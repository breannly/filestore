package com.filestore.filestore.converter;

import com.filestore.filestore.entity.Event;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.r2dbc.core.Parameter;

@WritingConverter
public class EventWriteConverter implements Converter<Event, OutboundRow> {

    @Override
    public OutboundRow convert(Event event) {
        OutboundRow row = new OutboundRow();
        if (event.getId() != null) {
            row.put("id", Parameter.from(event.getId()));
        }
        if (event.getFile() != null && event.getFile().getId() != null) {
            row.put("file_id", Parameter.from(event.getFile().getId()));
        }
        if (event.getUser() != null && event.getUser().getId() != null) {
            row.put("user_id", Parameter.from(event.getUser().getId()));
        }
        if (event.getAction() != null) {
            row.put("action", Parameter.from(event.getAction()));
        }
        if (event.getStatus() != null) {
            row.put("status", Parameter.from(event.getStatus()));
        }
        if (event.getCreatedAt() != null) {
            row.put("created_at", Parameter.from(event.getCreatedAt()));
        }
        if (event.getUpdatedAt() != null) {
            row.put("updated_at", Parameter.from(event.getUpdatedAt()));
        }
        return row;
    }
}
