package ru.vtb.phoekoby.client.mapper;

import java.util.List;

public interface EntityMapper<ENTITY, DTO> {
    ENTITY toEntity(DTO dto);
    DTO toDto(ENTITY entity);

    List<ENTITY> toEntity(List<DTO> dtos);

    List<DTO> toDto(List<ENTITY> entities);
}
