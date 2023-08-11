package com.phj.exam.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.phj.exam.demo.vo.Article;

@Component
public class ArticleRepository {
	private int articleLastId;
	private List<Article> articles;
	
	public ArticleRepository() {
		articleLastId = 0;
		articles = new ArrayList<Article>();
	}
	
	public void makeTestData() {
		for(int i = 1; i <= 10; i++) {
			String title = "제목" + i;
			String body = "내용" + i;
			Article article = writeArticle(title, body);
		}
	}

	public Article writeArticle(String title, String body) {
		int id = articleLastId + 1;
		
		Article article = new Article(id, title, body);
		
		articles.add(article);
		articleLastId = id;
		
		return article;
	}

	public Article getArticle(int id) {
		for(Article article : articles) {
			if(article.getId() == id ) {
				return article;
			}
		}
		return null;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void removeArticle(int id) {
		Article article = getArticle(id);
		articles.remove(article);
	}

	public void modifyArticle(int id, String title, String body) {
		Article article = getArticle(id);
		
		article.setTitle(title);
		article.setBody(body);
	}
}
