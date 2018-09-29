package com.ipaylinks.webmagic.qichacha.dao;

import com.ipaylinks.webmagic.qichacha.model.CompanyQuene;

import java.util.List;

public interface CompanyListQueneDAO {
    int addFinalQuene(CompanyQuene quene);

    List<CompanyQuene> selectUndoList(CompanyQuene quene);

    int updateCompanyQueneById(int id);
    int updateCompanyQueneStatusFailureById(int id);
}
