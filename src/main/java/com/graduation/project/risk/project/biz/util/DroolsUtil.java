package com.graduation.project.risk.project.biz.util;

import org.drools.core.event.DebugRuleRuntimeEventListener;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DroolsUtil {

    private Logger logger = LoggerFactory.getLogger(DroolsUtil.class);

    //KnowledgeBase 缓存(key：processCode)
    private static Map<String, KieBase> ruleMap = new ConcurrentHashMap<>();

    private DroolsUtil() {
    }

    private static class SingletonHolder {
        static DroolsUtil instance = new DroolsUtil();
    }

    public static DroolsUtil getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 根据规则字符串重新编译规则，并将编译后的KieBase存入缓存
     * @param rule
     * @param processCode
     * @return
     * @throws Exception
     */
    public KieSession getDrlSession(final String rule, final String processCode) throws Exception {

        try {
            // 设置时间格式
            System.setProperty("drools.dateformat", "yyyy-MM-dd");
            //为防止规则文件名字重复，此处加上时间戳( 格式：规则流程代码+时间戳+.drl)
            String ruleFileName = processCode + System.currentTimeMillis() + ".drl";

            KieServices kieServices = KieServices.Factory.get();
            KieFileSystem kfs = kieServices.newKieFileSystem();
            kfs.write("src/main/resources/com/drools/rules/" + ruleFileName, rule.getBytes("UTF-8"));
            KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
            if (kieBuilder.getResults().getMessages(Message.Level.ERROR).size() > 0) {
                throw new RuntimeException(kieBuilder.getResults().getMessages().toString());
            }
            KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
            KieBase kBase = kieContainer.getKieBase();
            //放入缓存
            ruleMap.put(processCode, kBase);
            KieSession kieSession = kBase.newKieSession();
            kieSession.addEventListener(new DebugRuleRuntimeEventListener());
            return kieSession;
        } catch (Exception e) {
            logger.error("规则引擎初始化失败，请查看错误信息:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }

    }


    /**
     * 根据processCode获取缓存中的kbase,然后创建session，如果返回null，则表示缓存中没有
     * @param processCode
     * @return
     * @throws Exception
     */
    public KieSession getDrlSessionInCache(final String processCode) throws Exception {
        try {
            KieBase kbase = ruleMap.get(processCode);
            if (kbase == null) {
                return null;
            } else {
                return kbase.newKieSession();
            }
        } catch (Exception e) {
            logger.error("获取 KieBase 信息错误:" + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 根据规则字符串重新编译规则，验证规则是否正确
     * @param rule
     * @return
     * @throws Exception
     */
    public Boolean compileRule(final String rule) throws Exception {

        //为防止规则文件名字重复，此处加上时间戳
        String ruleFileName = "testRule" + System.currentTimeMillis() + "testRule.drl";

        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/com/drools/rules/" + ruleFileName, rule.getBytes("UTF-8"));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
        if (kieBuilder.getResults().getMessages(Message.Level.ERROR).size() > 0) {
            logger.error("规则文件编译错误，请查看错误信息：{}" + kieBuilder.getResults().getMessages().toString());
            return false;
        } else {
            return true;
        }
    }


    /**
     * 移除对应的规则（供其它部分调用，比如规则的修改和删除）
     * @param key
     */
    public static void removeRuleMap(final String key) {
        ruleMap.remove(key);
    }

    /**
     * 清空规则缓存
     */
    public static void clearRuleMap() {
        ruleMap.clear();
    }
}
