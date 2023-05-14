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

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration(value = "MyBatisConfiguration")
public class MyBatisConfiguration {

    @Bean
    public MybatisConfiguration batisConfiguration() {
        MybatisConfiguration configuration = new MybatisConfiguration();
        // 使用列标签替换列别名 默认:true
        configuration.setUseColumnLabel(true);
        // 使用jdbc的useGeneratedKeys获取数据库自增主键值
        configuration.setUseGeneratedKeys(true);
        // 开启驼峰命名转换
        configuration.setMapUnderscoreToCamelCase(true);
        // SQL语句控制台打印
        configuration.setLogImpl(StdOutImpl.class);
        // 设置插件
        configuration.addInterceptor(mybatisPlusInterceptor());

        return configuration;
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 防止全表更新和删除插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());

        return interceptor;
    }

}
