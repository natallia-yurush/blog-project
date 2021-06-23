package by.nyurush.blog.converter.tag;

import by.nyurush.blog.dto.TagDto;
import by.nyurush.blog.entity.Tag;
import org.springframework.core.convert.converter.Converter;

public class TagConverter implements Converter<TagDto, Tag> {
    @Override
    public Tag convert(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setId(tagDto.getId());
        tag.setName(tagDto.getName());
        return tag;
    }
}
