package io.github.nunumao.app.config.mybatis;
// +----------------------------------------------------------------------
// | 官方网站: https://github.com/N7MCoder/n7m-boot
// +----------------------------------------------------------------------
// | 功能描述:
// +----------------------------------------------------------------------
// | 时　　间: 2023-04-10
// +----------------------------------------------------------------------
// | 代码创建: numao <n7mcoder@gmail.com>
// +----------------------------------------------------------------------
// | 版本信息: V 0.0.1
// +----------------------------------------------------------------------
// | 代码修改:（修改人 - 修改时间）
// +----------------------------------------------------------------------

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;

@Component(value = "MyBatisPlusConfiguration")
public class MyBatisPlusConfiguration {

    @Value(value = "${entity.packages}")
    private String entityPackages;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MybatisConfiguration batisConfiguration;

    @Bean(name = "sqlSessionFactory")
    public MybatisSqlSessionFactoryBean createSqlSessionFactoryBean() throws IOException {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        // 设置 mybatis configuration
        bean.setConfiguration(batisConfiguration);
        // 添加mapper 扫描路径 多模块需要使用 classpath*
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**.xml"));
        // 设置dataSource
        bean.setDataSource(dataSource);
        // 设置实体类所在的 package 路径
        bean.setTypeAliasesPackage("io.github.nunumao.*.model," + entityPackages);
        return bean;
    }


}
