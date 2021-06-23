package by.nyurush.blog.controller;

import by.nyurush.blog.dto.CommentDto;
import by.nyurush.blog.entity.Comment;
import by.nyurush.blog.security.jwt.JwtTokenProvider;
import by.nyurush.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/articles/{articleId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final JwtTokenProvider jwtTokenProvider;
    private final ConversionService conversionService;

    @PostMapping
    public void addComment(@PathVariable Long articleId,
                           @RequestBody CommentDto commentDto,
                           HttpServletRequest req) {
        String email = jwtTokenProvider.getEmail(req);
        Comment comment = conversionService.convert(commentDto, Comment.class);
        commentService.save(comment, email);
    }

    @GetMapping
    public List<CommentDto> getAllComments(@PathVariable Long articleId) {
        return commentService.findAllByArticle(articleId)
                .stream()
                .map(comment -> conversionService.convert(comment, CommentDto.class))
                .toList();
    }

    @GetMapping("/{commentId}")
    public CommentDto getComment(@PathVariable Long articleId,
                              @PathVariable Long commentId) {
        Comment comment = commentService.findById(commentId);
        return conversionService.convert(comment, CommentDto.class);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long articleId,
                              @PathVariable Long commentId,
                              HttpServletRequest req) {
        String email = jwtTokenProvider.getEmail(req);
        commentService.deleteById(articleId, commentId, email);
    }

}
