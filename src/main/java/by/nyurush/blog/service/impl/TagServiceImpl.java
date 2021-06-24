package by.nyurush.blog.service.impl;

import by.nyurush.blog.dto.tag.TagCloudInterface;
import by.nyurush.blog.entity.Tag;
import by.nyurush.blog.repository.TagRepository;
import by.nyurush.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public List<Tag> findAllByIdIn(List<Long> ids) {
        return tagRepository.findAllByIdIn(ids);
    }

    @Override
    public List<TagCloudInterface> findTagsCloud() {
        return tagRepository.findTagsCloud();
    }
}
