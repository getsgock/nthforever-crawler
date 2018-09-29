package com.ipaylinks.webmagic.qichacha.dao;

import com.ipaylinks.webmagic.qichacha.model.ScannerQuene;

import java.util.List;

public interface CompanyQueneDAO {

    /**
     * 记录所有公司的初始化信息
     * @param quene
     * @return
     */
    int addCompanyQuene(ScannerQuene quene);

    /**
     * 查询未search的公司，一次两条
     * @return
     */
    List<ScannerQuene> selectUndoList(ScannerQuene quene);

    int updateByPrimaryKey(int id);

    int updateStatusFailureByPrimaryKey(int id);
}
