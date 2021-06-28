package by.nyurush.blog.service;

import by.nyurush.blog.dto.tag.TagCloudInterface;
import by.nyurush.blog.entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> findAllByIdIn(List<Long> ids);

    List<TagCloudInterface> findTagsCloud();
}
