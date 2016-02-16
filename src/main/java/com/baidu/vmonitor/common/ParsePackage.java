package com.baidu.vmonitor.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ParsePackage {

    private static final String RESOURCE_PATH = ParsePackage.class.getResource("/").getPath().substring(1);

    private static final String FILE_SEPARATOR = File.separator;

    private static final String FILE_SUFFIX = ".class";

    private static final String SEPARATOR_RULE = "[\\\\/.]{1}";

    private static final String SEPARATOR_PROXY = " ";

    private static final String PACKAGE_SEPARATOR = ".";
    
    private static final String PACKAGE_NAME_RULE = "[.*a-zA-Z0-9_]+";

    private static final String PACKAGE_PROXY = SEPARATOR_PROXY + "*";

    private static final String PACKAGE_RULE = "[" + SEPARATOR_PROXY + "]{1}[a-zA-Z0-9_]+";

    private static final String OFFSPRING_PROXY = SEPARATOR_PROXY + "**";

    private static final String OFFSPRING_RULE = "(" + PACKAGE_RULE + ")*";

    public static void main(String[] args) throws IOException {
        String packageName = "com.baidu.*.log.**";
        List<String> classNames = getClassNames(packageName);
        for (String className : classNames) {
            System.out.println(className);
        }
//        System.out.println(OFFSPRING_RULE);
//        Pattern pattern = Pattern.compile("log([ ]{1}[a-zA-Z0-9_]+)*");
//        Matcher matcher = pattern.matcher("logger");
//        System.out.println(matcher.matches());
//        System.out.println("1:" + Thread.currentThread().getContextClassLoader().getResource(""));
//        System.out.println("2:" + ParsePackage.class.getClassLoader().getResource(""));
//        System.out.println("3:" + ClassLoader.getSystemResource(""));
//        System.out.println("4:" + ParsePackage.class.getResource(""));//IdcardClient.class文件所在路径
//        System.out.println("5:" + ParsePackage.class.getResource("/")); // Class包所在路径，得到的是URL对象，用url.getPath()获取绝对路径String
//        System.out.println("6:" + new File("/").getAbsolutePath());
//        System.out.println("7:" + System.getProperty("user.dir"));
//        System.out.println("8:" + System.getProperty("file.encoding"));//获取文件编码
    }

    /**
     * 根据包名获取包内所有类路径
     * 
     * @param packageName
     * @return
     */
    public static List<String> getClassNames(String packageName) {
        if (!Pattern.compile(PACKAGE_NAME_RULE).matcher(packageName).matches()) {
            throw new RuntimeException(packageName + " format error, A scope name can contain only .*a-zA-Z0-9_ characters.");
        }
        packageName = RESOURCE_PATH + packageName.replace(PACKAGE_SEPARATOR, FILE_SEPARATOR);
        List<String> classNames = new ArrayList<String>();
        List<String> packageNames = getPackageNames(packageName, new ArrayList<String>());
        for (String packageName0 : packageNames) {
            if (checkPackageName(packageName, packageName0)) {
                getClassNames(packageName0, classNames);
            }
        }
        return classNames;
    }

    /**
     * 检查packageName0是否符合packageName
     * 
     * @param packageName
     * @param packageName0
     * @return
     */
    private static boolean checkPackageName(String packageName, String packageName0) {
        packageName = packageName.replaceAll(SEPARATOR_RULE, SEPARATOR_PROXY).replace(OFFSPRING_PROXY, OFFSPRING_RULE)
                        .replace(PACKAGE_PROXY, PACKAGE_RULE);
        packageName0 = packageName0.replaceAll(SEPARATOR_RULE, SEPARATOR_PROXY)
                        .replace(OFFSPRING_PROXY, OFFSPRING_RULE).replace(PACKAGE_PROXY, PACKAGE_RULE);
        return Pattern.compile(packageName).matcher(packageName0).matches();
    }

    /**
     * 获取包全名，例如：com.XX.XX
     * 
     * @param packageName
     * @param packageNames
     * @return
     */
    private static List<String> getPackageNames(String packageName, List<String> packageNames) {
        File packageFile = new File(packageName);
        if (packageFile.exists()) {
            if (packageFile.isDirectory()) {
                packageNames.add(packageName);
                File[] childFiles = packageFile.listFiles();
                for (File childFile : childFiles) {
                    getPackageNames(childFile.getPath(), packageNames);
                }
            }
        } else {
            int index = packageName.lastIndexOf(FILE_SEPARATOR);
            if (index > -1) {
                getPackageNames(packageName.substring(0, index), packageNames);
            }
        }
        if (null == packageNames || 0 == packageNames.size()) {
            getPackageNames(RESOURCE_PATH, packageNames);
        }
        return packageNames;
    }

    /**
     * 获取类全名，不带后缀名，例如：com.XX.XX.className
     * 
     * @param classPath
     * @param classNames
     * @return
     */
    private static List<String> getClassNames(String classPath, List<String> classNames) {
        File classFile = new File(classPath);
        if (classFile.exists()) {
            if (classFile.isFile() && classFile.getPath().endsWith(FILE_SUFFIX)) {
                String className = classFile.getPath();
                className = className.substring(RESOURCE_PATH.length(), className.lastIndexOf(FILE_SUFFIX));
                className = className.replace(FILE_SEPARATOR, PACKAGE_SEPARATOR);
                classNames.add(className);
            } else if (classFile.isDirectory()) {
                File[] childFiles = classFile.listFiles();
                for (File childFile : childFiles) {
                    if (childFile.isFile()) {
                        getClassNames(childFile.getPath(), classNames);
                    }
                }
            }
        }
        return classNames;
    }

}
