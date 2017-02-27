package com.zuidaima.redistest.service;

import java.util.List;

import com.zuidaima.redistest.vo.ArticleModel;
import com.zuidaima.redistest.vo.CommentModel;
import com.zuidaima.redistest.vo.Pager;

public interface ArticleRedisService
{

	Pager getAll(Pager wm,int pageCurrent);

	void add(ArticleModel m);

	ArticleModel getByUuid(String uuid);

	Pager getByCondition(ArticleModel qm, Pager pager,int pageNow);

	void delete(ArticleModel byUuid);

	void update(ArticleModel m);

	void saveComment(String articleUuid, String content);

	List<CommentModel> getArticleComment(String uuid);

}