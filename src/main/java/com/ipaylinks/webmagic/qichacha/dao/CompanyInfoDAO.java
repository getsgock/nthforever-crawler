package com.ipaylinks.webmagic.qichacha.dao;

import com.ipaylinks.webmagic.qichacha.model.CompanyInfo;

public interface CompanyInfoDAO {

    /**
     * 添加公司信息
     * @param info
     * @return
     */
    int addCompanyInfo(CompanyInfo info);
}
