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
		
		public Article getForprintArticle(int actorId, int id) {
			Article article = articleRepository.getForprintArticle(id);
			
			updateforPrintData(actorId, article);
			return article;
		}

		public List<Article> getforPrintArticles(int actorId, int boardId) {
			List<Article> articles = articleRepository.getforPrintArticles(boardId);
			
			for(Article article : articles) {
				updateforPrintData(actorId, article);
			}
		return articles;
		}
		
		public void deleteArticle(int id) {
			articleRepository.removeArticle(id);
		}
		
		private void updateforPrintData(int actorId, Article article) {
			if(article == null) {
				return;
			}
			ResultData actorCanDeleteRd = actorCanDelete(actorId, article);
			article.setExta__actorCanDelete(actorCanDeleteRd.isSuccess());
			
			ResultData actorCanModifyRd = actorCanModify(actorId, article);
			article.setExta__actorCanModify(actorCanModifyRd.isSuccess());
			
		}
		
		public ResultData<Article> modifyArticle(int id, String title, String body) {
			articleRepository.modifyArticle(id, title, body);
			
			Article article = getForprintArticle(0,id);
			
			return ResultData.from("S-1",Ut.f("%d번 게시글이 수정 되었습니다.",id),article);
		}

		public ResultData actorCanModify(int actorId, Article article) {
		if(article == null) {
			return ResultData.from("F-1","권한이 없습니다.");
		}
		if(article.getMemberId() != actorId) {
			return ResultData.from("F-2","권한이 없습니다.");
		}
		
		return ResultData.from("S-1","수정 가능합니다.");
		}
		
		public ResultData actorCanDelete(int actorId, Article article) {
			if(article == null) {
				return ResultData.from("F-1","권한이 없습니다.");
			}
			if(article.getMemberId() != actorId) {
				return ResultData.from("F-2","권한이 없습니다.");
			}
			
				return ResultData.from("S-1","삭제 가능합니다.");
			}
}
