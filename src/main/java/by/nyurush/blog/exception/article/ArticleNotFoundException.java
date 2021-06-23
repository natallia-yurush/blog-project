package by.nyurush.blog.exception.article;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException() {
        super();
    }

    public ArticleNotFoundException(String message) {
        super(message);
    }
}
