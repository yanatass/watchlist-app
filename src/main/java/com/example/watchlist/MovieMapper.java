package com.example.watchlist;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(source = "category.name", target = "categoryName")
    MovieResponseDto toResponseDto(Movie movie);
}
