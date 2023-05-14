package io.github.nunumao.app.config;
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

import io.github.nunumao.app.App;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        Map<String, Class<?>> prefixMap = App.getPrefixMap();
        prefixMap.forEach((key, value) -> {
            configurer.addPathPrefix(key, HandlerTypePredicate.forAssignableType(value));
        });
    }

}
