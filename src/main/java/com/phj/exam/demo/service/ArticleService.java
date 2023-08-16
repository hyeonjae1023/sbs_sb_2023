package com.phj.exam.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.phj.exam.demo.repository.ArticleRepository;
import com.phj.exam.demo.util.Ut;
import com.phj.exam.demo.vo.Article;
import com.phj.exam.demo.vo.ResultData;

@Service
public class ArticleService {
		ArticleRepository articleRepository;
		
		public ArticleService(ArticleRepository articleRepository) {
			this.articleRepository = articleRepository;
		}

		public ResultData<Integer> writeArticle(int memberId, String title, String body) {
			articleRepository.writeArticle(memberId,title, body);
			int id = articleRepository.getLastArticleId();
			
			return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), id);
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
