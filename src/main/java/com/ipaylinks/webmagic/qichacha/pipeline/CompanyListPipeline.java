package com.ipaylinks.webmagic.qichacha.pipeline;

import com.ipaylinks.webmagic.qichacha.dao.CompanyListQueneDAO;
import com.ipaylinks.webmagic.qichacha.dao.CompanyQueneDAO;
import com.ipaylinks.webmagic.qichacha.model.CompanyQuene;
import com.ipaylinks.webmagic.qichacha.model.CompanyQueneRes;
import com.ipaylinks.webmagic.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

public class CompanyListPipeline implements Pipeline {

    public void process(ResultItems resultItems, Task task) {

        //向新表插入数据
        CompanyQueneRes res = resultItems.get("res");
        //修改状态
        Integer id = res.getId();
        if (null == res.getQuenes() || res.getQuenes().size() == 0){
            SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
            CompanyQueneDAO companyQueneDAO = sqlSession.getMapper(CompanyQueneDAO.class);
            companyQueneDAO.updateStatusFailureByPrimaryKey(id);
            sqlSession.close();
            return;
        }
        SqlSession sqlSession = MyBatisUtil.getSqlSession(true);
        CompanyQueneDAO companyQueneDAO = sqlSession.getMapper(CompanyQueneDAO.class);
        companyQueneDAO.updateByPrimaryKey(id);
        sqlSession.close();

        SqlSession sqlSession2 = MyBatisUtil.getSqlSession(true);
        CompanyListQueneDAO companyListQuene = sqlSession2.getMapper(CompanyListQueneDAO.class);
        for (CompanyQuene quene:res.getQuenes()){
            companyListQuene.addFinalQuene(quene);
        }
        sqlSession2.close();

    }


}
