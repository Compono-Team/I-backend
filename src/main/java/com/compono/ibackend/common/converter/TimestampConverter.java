package com.compono.ibackend.common.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Converter
public class TimestampConverter implements AttributeConverter<LocalDateTime, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDateTime attribute) {
        return attribute == null
                ? null
                : Date.from(attribute.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date dbData) {
        return dbData == null
                ? null
                : LocalDateTime.ofInstant(
                        Instant.ofEpochMilli(dbData.getTime()), ZoneId.systemDefault());
    }
}
