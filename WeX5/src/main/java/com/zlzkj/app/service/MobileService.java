package com.zlzkj.app.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zlzkj.app.mapper.NewsMapper;
import com.zlzkj.app.model.Activity;
import com.zlzkj.app.model.Chapter;
import com.zlzkj.app.model.News;
import com.zlzkj.app.model.Text;
import com.zlzkj.app.model.User;
import com.zlzkj.app.model.Video;
import com.zlzkj.app.util.CheckData;
import com.zlzkj.app.util.CommonUtil;
import com.zlzkj.app.util.UIUtils;
import com.zlzkj.core.mybatis.SqlRunner;
import com.zlzkj.core.sql.Row;
import com.zlzkj.core.sql.SQLBuilder;
import com.zlzkj.core.util.Fn;

@Service
@Transactional
public class MobileService {

	@Autowired
	private NewsMapper mapper;
	
	@Autowired
	private  SqlRunner sqlRunner;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private VideoService videoService;
	public Integer delete(Integer id){
		return mapper.deleteByPrimaryKey(id);
	}
	
	public Integer update(News entity) throws Exception{
		
		return mapper.updateByPrimaryKey(entity);
	}
	
	public Integer save(News entity) throws Exception{
		return mapper.insert(entity);
	}
	
	public News findById(Integer id){
		return mapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 获取资讯list
	 * @param where
	 * @param pageMap
	 * @return
	 */
	public List<Row> getUIGridData(Map<String, Object> where,
			Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(News.class);
		String sql = sqlBuilder
				.fields("id as id ,title as title ,image_url as imageUrl,content as content,had_look as hadLook")
				.where(where)
				//.parseUIPageAndOrder(pageMap)
				.order("sort_number", "desc")
				.page(1, 3)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		//{"fID":"001","fTitle":"解读习近平致金正恩亲署函","fImg":"./images/a1.jpg","fOmit":"借劳动党成立周年致亲署函，从公开报道看并无先例。","fPostNumber":"3"},
		for (Row row:list) {
			row.put("fID", row.get("id"));
			row.put("fTitle", row.getString("title").length()>20?row.getString("title").substring(0, 20)+"...":row.getString("title"));
			row.put("fImg", row.get("imageUrl"));
			String content = UIUtils.Html2Text(row.getString("content"));
			row.put("fOmit", content.length()>20?content.substring(0, 20)+"...":content);
			row.put("fPostNumber", row.get("hadLook"));
			row.remove("id");
			row.remove("title");
			row.remove("imageUrl");
			row.remove("content");
			row.remove("hadLook");
		}
		//获取总条数
//		String countSql = sqlBuilder.fields("count(*)").where(where).buildSql();
//		Integer count = sqlRunner.count(countSql);
		return list;
	}
	
	/**
	 * 获取资讯list
	 * @param where
	 * @param pageMap
	 * @return
	 */
	public List<Row> getAllUIGridData(Map<String, Object> where,
			Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(News.class);
		String sql = sqlBuilder
				.fields("id as id ,title as title ,image_url as imageUrl,content as content,had_look as hadLook")
				.where(where)
				//.parseUIPageAndOrder(pageMap)
				.order("sort_number", "desc")
				.page(1, 20)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		//{"fID":"001","fTitle":"解读习近平致金正恩亲署函","fImg":"./images/a1.jpg","fOmit":"借劳动党成立周年致亲署函，从公开报道看并无先例。","fPostNumber":"3"},
		for (Row row:list) {
			row.put("fID", row.get("id"));
//			row.put("fTitle", row.get("title"));
			row.put("fTitle", row.getString("title").length()>20?row.getString("title").substring(0, 20)+"...":row.getString("title"));
			row.put("fImg", row.get("imageUrl"));
			String content = UIUtils.Html2Text(row.getString("content"));
			row.put("fOmit", content.length()>20?content.substring(0, 20)+"...":content);
			row.put("fPostNumber", row.get("hadLook"));
			row.remove("id");
			row.remove("title");
			row.remove("imageUrl");
			row.remove("content");
			row.remove("hadLook");
		}
		//获取总条数
//		String countSql = sqlBuilder.fields("count(*)").where(where).buildSql();
//		Integer count = sqlRunner.count(countSql);
		return list;
	}
	
	 /**
		 * 获取图片资讯list
		 * @param where
		 * @param pageMap
		 * @return
		 * {"rows":[
					{"id":"1","fImgUrl":"./main/img/carouselBox61.jpg","fUrl":"./detail.w"},
					{"id":"2","fImgUrl":"./main/img/carouselBox63.jpg","fUrl":"./detail.w"},
					{"id":"3","fImgUrl":"./main/img/carouselBox62.jpg","fUrl":"./detail.w"}
				   ]
		   }

		 */
	public Map<String, Object> getImageUIGridData(Map<String, Object> where,
			Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(News.class);
		String sql = sqlBuilder
				.fields("id as id ,image_url as fImgUrl")
				.where(where)
				//.parseUIPageAndOrder(pageMap)
				//.page(1, 3)
				.order("sort_number", "desc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for (Row row:list) {
			row.put("fUrl", "./detail.w?id="+row.getString("id"));
		}
		//获取总条数
		String countSql = sqlBuilder.fields("count(*)").where(where).buildSql();
		Integer count = sqlRunner.count(countSql);
		return UIUtils.getGridData(count, list);
	}
	
	public List<Row> getVideoUIGridData(Map<String, Object> where,
			Map<String, String> pageMap,int number) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Video.class);
		String sql = sqlBuilder
				.fields("id as id ,video_url as fVideoPath,title as fVideoTitle ,video_image as fVideoImg,"
						+ "video_intro as fVideoOmit , click_count as fCount ,add_time as fAddTime ,video_type as Ftype")
				.where(where)
				//.parseUIPageAndOrder(pageMap)
				.page(1, number)
				.order("sort_number", "desc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for(Row row:list){
			String content = UIUtils.Html2Text(row.getString("fVideoOmit"));
			row.put("fVideoOmit", content.length()>5?content.substring(0, 5)+"...":content);
		}
//		FID
//		fVideoTitle
//		fVideoImg
//		fVideoOmit
//		fCount
//		fAddTime
//		fVideoPath
//		fType
		//获取总条数
//		STRING COUNTSQL = SQLBUILDER.FIELDS("COUNT(*)").WHERE(WHERE).BUILDSQL();
//		INTEGER COUNT = SQLRUNNER.COUNT(COUNTSQL);
		return list;
	}
	
	/**
	 * 获取chapterList
	 * @param where
	 * @param pageMap
	 * @param number
	 * @return
	 */
	public List<Row> getChapterUIGridData(Map<String, Object> where,
			Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Chapter.class);
		String sql = sqlBuilder
				.fields("id as id ,title as title")
				//.order("sort_number", "desc")
				.where(where)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		List chapterList=new ArrayList();
		for(int i=0;i<list.size();i++){
			Row row = list.get(i);
			row.put("isChapter", "1");
			row.put("video_time", "0");
			chapterList.add(row);
			//添加目录下video信息
			SQLBuilder VideoSqlBuilder = SQLBuilder.getSQLBuilder(Video.class);
			String videoSql = VideoSqlBuilder
					.fields("id as id ,title as title,video_time as videoTime")
					//.order("sort_number", "desc")
					.where("video_type="+row.getInt("id"))
					.buildSql();
			List<Row> videoList = sqlRunner.select(videoSql);
			for(int j=0;j<videoList.size();j++){
				Row videoRow = videoList.get(j);
				videoRow.put("isChapter", "0");
				chapterList.add(videoRow);
			}
		}
		return chapterList;
	}
	
	/**
	 * 获取chapterList
	 * @param where
	 * @param pageMap
	 * @param number
	 * @return
	 */
	public List<Row> getChapterTextUIGridData(Map<String, Object> where,
			Map<String, String> pageMap) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Chapter.class);
		String sql = sqlBuilder
				.fields("id as id ,title as title")
				.where(where)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		List chapterList=new ArrayList();
		for(int i=0;i<list.size();i++){
			Row row = list.get(i);
			row.put("isChapter", "1");
			chapterList.add(row);
			//添加目录下video信息
			SQLBuilder TextSqlBuilder = SQLBuilder.getSQLBuilder(Text.class);
			String textSql = TextSqlBuilder
					.fields("id as id ,title as title")
					//.order("sort_number", "desc")
					.where("chapter_id="+row.getInt("id"))
					.buildSql();
			List<Row> textList = sqlRunner.select(textSql);
			for(int j=0;j<textList.size();j++){
				Row textRow = textList.get(j);
				textRow.put("isChapter", "0");
				chapterList.add(textRow);
			}
		}
		return chapterList;
	}
	
	/**
	 * 获取视频首页数据
	 * @param where
	 * @param pageMap
	 * @param number
	 * @return
	 */
	public List<Row> getVideoMainUIGridData(Map<String, Object> where,
			Map<String, String> pageMap,int number) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Chapter.class);
		String sql = sqlBuilder
				.fields("*")
				.where(where)
				.page(1, number)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for(Row row :list){
			row.put("fCount", getVideoCountInChapter(row.getInt("id")));
			int top[] = getTopThreeVideoChapter(row.getInt("id"));
			for(int i=0;i<top.length;i++){
				if(top[i]!=0){
					Video video = videoService.findById(top[i]);
					row.put("fVideoTitle", video.getTitle());
					String content = UIUtils.Html2Text(video.getVideoIntro());
					row.put("fVideoOmit", content.length()>5?content.substring(0, 5)+"...":content);
					row.put("fVideoImg", video.getVideoImage());
					row.put("fVideoPath", video.getVideoUrl());
					row.put("fVideoTime", video.getVideoTime());
					row.put("fVideoId",video.getId() );
				}
			}
		}
//		{"addTime":1460635628,
//		"id":1,
//		"remark":"介绍最基本的WeX5信息",
//		"status":1,
//		"title":"第一章 初识WeX5",
//		"userId":26917,
//		"fCount":0,
//
//		"fVideoTitle1":"初识WeX5",
//		"fVideoOmit1":"WeX5采用Apache许可证开源模式，商业友好，完全免费。基于WeX5开发出来的应用，每一行代码都在你手里，发布部署无任何限制，完全自由免费。",
//		"fVideoImg1":"http://42.96.159.122:8080/WeX5_data/image/20160416/BoqKrTcRPh_!!600x337.png",
//		"fVideoPath1":"http://42.96.159.122:8080/WeX5_data/video/20160418/lEqUTPShNg_!!1595490.mp4",
//
//		"fVideoTitle2":"页面组件介绍",
//		"fVideoOmit2":"页面代码介绍",
//		"fVideoImg2":"http://42.96.159.122:8080/WeX5_data/image/20160416/VOQUhtnkfR_!!1429x796.png",
//		"fVideoPath2":"http://42.96.159.122:8080/WeX5_data/video/20160410/BOQKcJwSAm_!!67305472.mp4",
//
//		"fVideoTitle3":"开发欢乐捕鱼app",
//		"fVideoOmit":"开发欢乐捕鱼app",
//		"fVideoImg3":"http://42.96.159.122:8080/WeX5_data/image/20160416/BequrTctfd_!!1430x800.png",
//		"fVideoPath3":"http://42.96.159.122:8080/WeX5_data/video/20160416/LOQARhJeHD_!!36607636.mp4",
//		},
		return list;
	}
	
	/**
	 * 获取某个章节内的视频总数
	 * @param chapterId
	 * @return
	 */
	public  int getVideoCountInChapter(int chapterId){
		Map<String, Object> where = new HashMap<String,Object>();
		where.put("video_type", chapterId);
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Video.class);
		String countSql = sqlBuilder.fields("count(*)").where(where).buildSql();
		Integer count = sqlRunner.count(countSql);
		return count;
	}
	/**
	 * 获取某个章节的前三个视频的Id
	 * @param chapterId
	 * @return
	 */
	public  int[] getTopThreeVideoChapter(int chapterId){
		Map<String, Object> where = new HashMap<String,Object>();
		where.put("video_type", chapterId);
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Video.class);
		String sql = sqlBuilder
				.fields("id as id")
				.where(where)
				.page(1, 1)
				.order("sort_number", "desc")
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		int[] top = {0,0,0};
		int x =0;
		for(Row row:list){
			top[x] = row.getInt("id");
			x++;
		}
		return top;
	}
	
	/**
	 * 获取资讯list
	 * @param where
	 * @param pageMap
	 * @return
	 */
	public List<Row> getActivityUIGridData(Map<String, Object> where,
			Map<String, String> pageMap,int number) {
		SQLBuilder sqlBuilder = SQLBuilder.getSQLBuilder(Activity.class);
		String sql = sqlBuilder
				.fields("*")
				.where(where)
				.order("sort_number", "desc")
				.page(1, number)
				.buildSql();
		List<Row> list = sqlRunner.select(sql);
		for (Row row:list) {
			row.put("addUser", userService.findNameByid(row.getString("addUser")));
			row.put("addTime", Fn.date(Integer.valueOf(row.getString("addTime")), "yyyy-MM-dd HH:mm:ss"));
			row.put("endTime", Fn.date(Integer.valueOf(row.getString("endTime")), "yyyy-MM-dd HH:mm:ss"));
			String content = UIUtils.Html2Text(row.getString("content"));
			row.put("content", content.length()>20?content.substring(0, 20)+"...":content);
		}
		return list;
	}
}
