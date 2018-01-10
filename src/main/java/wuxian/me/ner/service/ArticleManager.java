package wuxian.me.ner.service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wuxian on 1/1/2018.
 */
public class ArticleManager {

    private ArticleManager() {
    }

    public static class Article {
        String title;
        Long articleId;
        List<String> wordList;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Article)) return false;

            Article article = (Article) o;

            if (title != null ? !title.equals(article.title) : article.title != null) return false;
            return articleId != null ? articleId.equals(article.articleId) : article.articleId == null;

        }

        @Override
        public int hashCode() {
            int result = title != null ? title.hashCode() : 0;
            result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
            return result;
        }
    }

    static List<Article> articleList = new ArrayList<>();


    public static List<String> getWordsBy(Long articleId) {

        Article article = findArticleBy(articleId);

        return article == null ? null : article.wordList;
    }


    public static Long addAritle(String title, String path) throws Exception {
        if (title == null || title.length() == 0) {
            throw new Exception("title not valid!");
        }

        Article article = findArticleBy(title);
        if (article != null) {
            return article.articleId;
        }
        Article art = new Article();
        art.title = title;
        String content = FileUtil.readFromFile(path);
        if (content == null) {
            throw new IOException("read content from: " + path + " fail!");
        }
        String[] list = content.split("\\s");
        art.wordList = Arrays.asList(list);
        art.articleId = genId(art.title);
        articleList.add(art);
        return art.articleId;
    }

    private static AtomicLong id = new AtomicLong(0);

    private static Long genId(String title) {
        return id.getAndIncrement();
    }

    public static boolean removeArticle(String title) {
        Article article = findArticleBy(title);
        if (article == null) {
            return false;
        }
        articleList.remove(article);
        return true;
    }

    public static boolean removeArticle(Long articleId) {
        Article article = findArticleBy(articleId);
        if (article == null) {
            return false;
        }
        articleList.remove(article);
        return true;
    }

    public static Long addArtile(String title, List<String> wordList) {
        Article article = findArticleBy(title);
        if (article != null) {
            return article.articleId;
        }

        Article art = new Article();
        art.title = title;
        art.wordList = wordList;
        art.articleId = genId(art.title);
        articleList.add(art);

        return art.articleId;
    }

    public static Article findArticleBy(String title) {
        for (Article article : articleList) {
            if (article.title.equals(title)) {
                return article;
            }
        }
        return null;
    }

    public static Article findArticleBy(Long articleId) {
        for (Article article : articleList) {
            if (article.articleId.equals(articleId)) {
                return article;
            }
        }
        return null;
    }

    public static boolean contains(String title) {
        for (Article article : articleList) {
            if (article.title.equals(title)) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(Long articleId) {
        for (Article article : articleList) {
            if (article.articleId.equals(articleId)) {
                return true;
            }
        }
        return false;
    }
}
