package com.demo.spring.core;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.function.Predicate;

/**
 * @author shengweisong
 * @desc 扫描类
 * @date 2021-11-24 8:45 PM
 **/
public class ClassScanner {


    public static List<Class<?>> scannerClasses(String packageName) throws IOException, ClassNotFoundException {
        String path = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Enumeration<URL> resources = classLoader.getResources(path);

        List<Class<?>> classList = Lists.newArrayList();

        Predicate<String> packagePredicate = s -> true;


        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String protocol = resource.getProtocol();
            if ("file".equals(protocol)) {
                String filePath = URLDecoder.decode(resource.getFile(), "UTF-8");
                // 扫描文件夹中的包和类
                classList.addAll(doScanPackageClassesByFile(packageName, filePath, true));
            }
        }
        return classList;
    }

    private static List<Class<?>> doScanPackageClassesByFile(String packageName, String packagePath,
                                                             boolean recursive) throws ClassNotFoundException {
        List<Class<?>> classList = Lists.newArrayList();

        // 转为文件
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return classList;
        }
        final boolean fileRecursive = recursive;
        // 列出文件，进行过滤
        // 自定义文件过滤规则
        File[] dirFiles = dir.listFiles((FileFilter) file -> {
            String filename = file.getName();

            if (file.isDirectory()) {
                if (!fileRecursive) {
                    return false;
                }
                return true;
            }
            return filename.endsWith(".class");
        });

        if (null == dirFiles) {
            return classList;
        }

        for (File file : dirFiles) {
            if (file.isDirectory()) {
                // 如果是目录，则递归
                classList.addAll(doScanPackageClassesByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive));
            } else {
                // 用当前类加载器加载 去除 fileName 的 .class 6 位
                String className = file.getName().substring(0, file.getName().length() - 6);
                Class<?> loadClass = Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
                classList.add(loadClass);
            }
        }
        return classList;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println(scannerClasses("com.demo.spring"));
    }
}