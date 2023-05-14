package io.github.nunumao.app;
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

import io.github.nunumao.util.controller.RequestPrefix;
import io.github.nunumao.util.controller.SuperController;
import org.reflections.Reflections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import org.springframework.util.ClassUtils;

import java.util.*;

import static org.reflections.scanners.Scanners.TypesAnnotated;

public class App {

    public static final String CORE_PACKAGE = "io.github.nunumao";
    public static final String CORE_PACKAGE_MAPPER = "io.github.nunumao.*.dao";

    private static List<String> APP_PACKAGE = new ArrayList<>();

    private static Map<String, Class<?>> prefixMap = new HashMap<>();

    public static void run(Class<?> cls, String[] args) {
        SpringApplication application = new SpringApplication(cls);
        application.setBeanNameGenerator(FullyQualifiedAnnotationBeanNameGenerator.INSTANCE);
        application.addListeners(new ApplicationPidFileWriter());
        initPathPrefix();
        application.run(args);
    }

    public static void setAppPackage(String... pkg) {
        APP_PACKAGE = Arrays.asList(pkg);
    }

    public static List<String> getAppPackage() {
        return APP_PACKAGE;
    }

    private static void initPathPrefix() {
        Set<Class<?>> ctrlSet = new HashSet<>();
        List<String> appPackage = App.getAppPackage();
        for (String pkg : appPackage) {
            Reflections reflections = new Reflections(pkg);
            Set<Class<?>> classes = reflections.get(TypesAnnotated.with(RequestPrefix.class).asClass());
            ctrlSet.addAll(classes);
        }
        for (Class<?> cls : ctrlSet) {
            try {
                String prefix = "";
                if (ClassUtils.isAssignable(cls.getSuperclass(), SuperController.class)) {
                    RequestPrefix annotation = cls.getAnnotation(RequestPrefix.class);
                    prefix = annotation.value();
                } else if (ClassUtils.isAssignable(cls.getSuperclass().getSuperclass(), SuperController.class)) {
                    RequestPrefix parentAnnotation = cls.getSuperclass().getAnnotation(RequestPrefix.class);
                    RequestPrefix annotation = cls.getAnnotation(RequestPrefix.class);
                    if (null != parentAnnotation) {
                        prefix = parentAnnotation.value() + "/" + annotation.value();
                    } else {
                        prefix = annotation.value();
                    }
                }
                prefixMap.put(prefix, cls);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static Map<String, Class<?>> getPrefixMap() {
        return prefixMap;
    }
}
