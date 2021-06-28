package by.nyurush.blog.converter.tag;

import by.nyurush.blog.entity.Tag;
import by.nyurush.blog.dto.tag.TagDto;
import org.springframework.core.convert.converter.Converter;

public class TagDtoConverter implements Converter<Tag, TagDto> {
    @Override
    public TagDto convert(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tagDto.getName());
        return tagDto;
    }
}
