package ru.sharphurt.synclyrics.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.sharphurt.synclyrics.entity.Session;
import ru.sharphurt.synclyrics.dto.auth.SessionDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SessionMapper {
    SessionMapper SESSION_MAPPER = Mappers.getMapper(SessionMapper.class);

    @Mapping(target = "expiresAt", source = "accessExpiresAt")
    SessionDto map(Session entity);
}
