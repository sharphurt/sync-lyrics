package ru.sharphurt.synclyrics.spotify.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String country;
    private String display_name;
    private String email;
    private ExplicitContentDto explicit_content;
    private ExternalUrlsDto external_urls;
    private FollowersDto followers;
    private String href;
    private String id;
    private List<ImageDto> images;
    private String product;
    private String type;
    private String uri;
}
