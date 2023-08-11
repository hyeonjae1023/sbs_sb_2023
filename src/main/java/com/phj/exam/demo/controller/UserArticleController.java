package com.phj.exam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phj.exam.demo.vo.Article;

@Controller 
public class UserArticleController {
	//인스턴스 변수 시작
	private int articleLastId;
	private List<Article> articles;
	//인스턴스 변수 끝
	//생성자 시작
	public UserArticleController() {
		articleLastId = 0;
		articles = new ArrayList<Article>();
		
		makeTestData();
	}
	//생성자 끝
	
	//서비스 메서드 시작
	private void makeTestData() {
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
		
		return  article;
	}
	
	private Article getArticle(int id) {
		for(Article article : articles) {
			if(article.getId() == id ) {
				return article;
			}
		}
		return null;
	}
	
	private void deleteArticle(int id) {
		Article article = getArticle(id);
		articles.remove(article);
	}
	
	private void modifyArticle(int id, String title, String body) {
		Article article = getArticle(id);
		
		article.setTitle(title);
		article.setBody(body);
	}
	//서비스 메서드 끝
	//액션 메서드 시작
	@RequestMapping("/user/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		Article article = writeArticle( title, body);
		
		return  article;
	}
	
	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		Article article = getArticle(id);
		
		if(article == null) {
			return id+ "번 게시글이 존재하지 않습니다.";
		}
		deleteArticle(id);
		
		return  id + "번 게시글이 삭제 되었습니다.";
	}

	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {
		Article article = getArticle(id);
		
		if(article == null) {
			return id+ "번 게시글이 존재하지 않습니다.";
		}
		modifyArticle(id,title,body);
		
		return  id + "번 게시글이 수정 되었습니다.";
	}
	
	@RequestMapping("/user/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return  articles;
	}
	
	@RequestMapping("/user/article/getArticle")
	@ResponseBody
	public Object getArticleAction(int id) {
	Article article = getArticle(id);
		
		if(article == null) {
			return id+ "번 게시글이 존재하지 않습니다.";
		}
		return  article;
	}
	//액션 메서드 끝
}
