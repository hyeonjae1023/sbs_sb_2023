package com.phj.exam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.phj.exam.demo.vo.Article;

@Controller 
public class UserArticleController {
	private int articleLastId;
	private List<Article> articles;
	
	public UserArticleController() {
		articleLastId = 0;
		articles = new ArrayList<Article>();
		
		makeTestData();
	}
	
	private void makeTestData() {
		for(int i = 1; i <= 10; i++) {
			int id = articleLastId + 1;
			String title = "제목" + i;
			String body = "내용" + i;
			Article article = new Article(id, title, body);
			
			articles.add(article);
			articleLastId = id;
		}
		
	}

	@RequestMapping("/user/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {
		int id = articleLastId + 1;
		
		Article article = new Article(id, title, body);
		
		articles.add(article);
		articleLastId = id;
		
		return  article;
	}
	
	@RequestMapping("/user/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {
		return  articles;
	}
}
