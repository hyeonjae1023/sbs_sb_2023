package com.phj.exam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phj.exam.demo.service.ArticleService;
import com.phj.exam.demo.vo.Article;

@Controller 
public class UserArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/user/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = articleService.writeArticle( title, body);
		
		return  article;
	}
	
	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return id+ "번 게시글이 존재하지 않습니다.";
		}
		articleService.deleteArticle(id);
		
		return  id + "번 게시글이 삭제 되었습니다.";
	}

	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		Article article = articleService.getArticle(id);
		
		if(article == null) {
			return id+ "번 게시글이 존재하지 않습니다.";
		}
		articleService.modifyArticle(id,title,body);
		
		return  id + "번 게시글이 수정 되었습니다.";
	}
	
	@RequestMapping("/user/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return  articleService.getArticles();
	}
	
	@RequestMapping("/user/article/getArticle")
	@ResponseBody
	public Object getArticleAction(int id) {
	Article article = articleService.getArticle(id);
		
		if(article == null) {
			return id+ "번 게시글이 존재하지 않습니다.";
		}
		return  article;
	}
	//액션 메서드 끝
}
