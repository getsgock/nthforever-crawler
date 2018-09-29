package com.ipaylinks.webmagic.qidian;

import com.ipaylinks.webmagic.qidian.dao.BookInfoDAO;
import com.ipaylinks.webmagic.qidian.model.BookInfo;
import com.ipaylinks.webmagic.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

public class MysqlPipeline implements Pipeline {


    public void process(ResultItems resultItems, Task task) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
        BookInfoDAO bookInfoDAO = sqlSession.getMapper(BookInfoDAO.class);
        BookInfo info = resultItems.get("info");
        bookInfoDAO.insertBookInfo(info);
    }
}
