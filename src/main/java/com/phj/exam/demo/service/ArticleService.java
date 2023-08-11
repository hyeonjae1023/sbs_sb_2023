package com.phj.exam.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.phj.exam.demo.repository.ArticleRepository;
import com.phj.exam.demo.vo.Article;

@Service
public class ArticleService {
		ArticleRepository articleRepository;
		
		public ArticleService(ArticleRepository articleRepository) {
			this.articleRepository = articleRepository;
		}

		public Article writeArticle(String title, String body) {
			return  articleRepository.writeArticle(title, body);
		}
		
		public Article getArticle(int id) {
			return articleRepository.getArticle(id);
		}
		
		public List<Article> getArticles() {
		return articleRepository.getArticles();
		}
		
		public void deleteArticle(int id) {
			articleRepository.removeArticle(id);
		}
		
		public void modifyArticle(int id, String title, String body) {
			articleRepository.modifyArticle(id, title, body);
		}
}
