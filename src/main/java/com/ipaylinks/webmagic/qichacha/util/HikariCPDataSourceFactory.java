package com.ipaylinks.webmagic.qichacha.util;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

public class HikariCPDataSourceFactory extends UnpooledDataSourceFactory {

    public HikariCPDataSourceFactory() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setMaximumPoolSize(80);
        dataSource.setConnectionTimeout(3000);
//        dataSource.setValidationTimeout();
        this.dataSource = dataSource;

    }
}
