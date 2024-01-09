package com.xihua.easyctlserver.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopicDTO {
    private Long id;

    private String topic;

    private Integer stat;

    private List<TopicApiDTO> topicApiDTOList;
}

