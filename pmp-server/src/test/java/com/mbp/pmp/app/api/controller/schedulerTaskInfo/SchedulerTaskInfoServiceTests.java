package com.mbp.pmp.app.api.controller.schedulerTaskInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbp.eng.module.example.cache.LocalCache;
import com.mbp.eng.framework.common.util.date.DateUtil;
import com.mbp.eng.framework.common.util.email.CheckEmail;
import com.mbp.eng.framework.common.util.json.JsonUtil;
import com.mbp.eng.framework.common.util.num.NumUtil;
import com.mbp.eng.module.example.domain.schedulerTaskInfo.SchedulerTaskInfo;
import com.mbp.eng.module.example.domain.system.FourAddressMapping;
import com.mbp.eng.module.example.service.schedulerTaskInfo.SchedulerTaskInfoService;
import com.mbp.pmp.app.api.base.SpringBootServiceApplicationTests;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//@Ignore
public class SchedulerTaskInfoServiceTests extends SpringBootServiceApplicationTests {

    private String className = this.getClass().getName();
    private String simpleClassName = this.getClass().getSimpleName();
    private static Logger logger = LoggerFactory.getLogger(SchedulerTaskInfoServiceTests.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    SchedulerTaskInfoService schedulerTaskInfoService;

    @Autowired
    LocalCache localCache;

    @Test
    public void testMap() {
        long time = System.currentTimeMillis();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("{}.{} time: is {}", simpleClassName, methodName, DateUtil.getFormatTime(time));

        Map<String, String> map = new HashMap<>();
        List<String> result = new ArrayList(map.keySet());
        List<String> result2 = new ArrayList(map.values());
        List<String> result3 = map.keySet().stream().collect(Collectors.toList());
        List<String> result4 = map.values().stream().collect(Collectors.toList());
        List<String> result5 = map.values().stream().filter(x -> !"apple".equalsIgnoreCase(x)).collect(Collectors.toList());
    }

    @Test
    public void listToMapTest() {
        long time = System.currentTimeMillis();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("{}.{} time: is {}", simpleClassName, methodName, DateUtil.getFormatTime(time));

        SchedulerTaskInfo schedulerTaskInfo = new SchedulerTaskInfo();
        schedulerTaskInfo.setStatus(1);
        List<SchedulerTaskInfo> schedulerTaskInfoList = schedulerTaskInfoService.selectEntryList(schedulerTaskInfo);

        //将list转换map
        Map<String, String> listToMap = schedulerTaskInfoList.stream().collect(Collectors.toMap(SchedulerTaskInfo -> SchedulerTaskInfo.getId().toString(), SchedulerTaskInfo::getCreatedBy));
        Map<Integer, String> listToMap_1 = schedulerTaskInfoList.stream().collect(Collectors.toMap(SchedulerTaskInfo::getId, SchedulerTaskInfo::getCreatedBy));

        //得到 Map 的 value 为对象本身
        Map<Integer, SchedulerTaskInfo> listToMap_2 = schedulerTaskInfoList.stream().collect(Collectors.toMap(SchedulerTaskInfo::getId, t -> t));
        Map<Integer, SchedulerTaskInfo> listToMap_3 = schedulerTaskInfoList.stream().collect(Collectors.toMap(SchedulerTaskInfo::getId, Function.identity()));

        //Collectors.toMap 有三个重载方法:
        //toMap(AuthFunction<? super T, ? extends K> keyMapper, AuthFunction<? super T, ? extends U> valueMapper);
        //toMap(AuthFunction<? super T, ? extends K> keyMapper, AuthFunction<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction);
        //toMap(AuthFunction<? super T, ? extends K> keyMapper, AuthFunction<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier);

        /*参数含义分别是:
        1. keyMapper:Key 的映射函数
        2. valueMapper:Value 的映射函数
        3. mergeFunction:当 Key 冲突时,调用的合并方法
        4. mapSupplier:Map 构造器,在需要返回特定的 Map 时使用*/

        //如果 demoList 中 getStatus 有相同的,使用上面的写法会抛异常.需要调用第二个重载方法,传入合并函数
        Map<Integer, String> listToMap_4 = schedulerTaskInfoList.stream().collect(Collectors.toMap(SchedulerTaskInfo::getStatus, SchedulerTaskInfo::getCreatedBy, (n1, n2) -> n1 + n2));
        // 输出结果:
        //1-> test0test1test2test3


        //第四个参数(mapSupplier）用于自定义返回 Map 类型,比如我们希望返回的 Map 是根据 Key 排序的,可以使用如下写法:
        Map<Integer, String> listToMap_5 = schedulerTaskInfoList.stream().collect(Collectors.toMap(SchedulerTaskInfo::getId, SchedulerTaskInfo::getCreatedBy, (n1, n2) -> n1, TreeMap::new));
    }

    @Test
    public void localCacheTest() throws Exception {
        long time = System.currentTimeMillis();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("{}.{} time: is {}", simpleClassName, methodName, DateUtil.getFormatTime(time));

        SchedulerTaskInfo query = new SchedulerTaskInfo();
        query.setStatus(1);
        List<SchedulerTaskInfo> schedulerTaskInfoList = schedulerTaskInfoService.selectEntryList(query);
        Map<String, Object> map = schedulerTaskInfoList.stream().collect(Collectors.toMap(Demo -> Demo.getId().toString(), Function.identity()));
        //localCache.setDemoMap(map);

        schedulerTaskInfoList = new ArrayList(localCache.getDemoMap().values());
        for (SchedulerTaskInfo demo : schedulerTaskInfoList) {
            System.out.println("==========" + objectMapper.writeValueAsString(demo));
        }
    }

    @Test
    public void mapToList() throws Exception {
        long time = System.currentTimeMillis();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("{}.{} time: is {}", simpleClassName, methodName, DateUtil.getFormatTime(time));

        SchedulerTaskInfo schedulerTaskInfo = new SchedulerTaskInfo();
        schedulerTaskInfo.setStatus(1);
        List<SchedulerTaskInfo> fourAddressMappingList1 = schedulerTaskInfoService.selectEntryList(schedulerTaskInfo);
        List<SchedulerTaskInfo> fourAddressMappingList2 = fourAddressMappingList1.stream().map(SchedulerTaskInfo::new).collect(Collectors.toList());
        Map<Integer, Object> listToMap_3 = fourAddressMappingList2.stream().collect(Collectors.toMap(SchedulerTaskInfo::getId, Function.identity()));

        List<FourAddressMapping> keySet = new ArrayList(listToMap_3.keySet());
        for (FourAddressMapping fourAddressMapping : keySet) {
            System.out.println("==========" + objectMapper.writeValueAsString(fourAddressMapping));
        }

        List<FourAddressMapping> values = new ArrayList(listToMap_3.values());
        for (FourAddressMapping fourAddressMapping : values) {
            System.out.println("==========" + objectMapper.writeValueAsString(fourAddressMapping));
        }

        Set<Map.Entry<Integer, Object>> entries = listToMap_3.entrySet();
        for (Map.Entry<Integer, Object> entry : entries) {
            System.out.println("key=" + entry.getKey() + ",value=" + entry.getValue());
        }
    }

    @Test
    public void testListToMap() throws Exception {
        long time = System.currentTimeMillis();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("{}.{} time: is {}", simpleClassName, methodName, DateUtil.getFormatTime(time));

        SchedulerTaskInfo query = new SchedulerTaskInfo();
        List<SchedulerTaskInfo> schedulerTaskInfoList = schedulerTaskInfoService.selectEntryList(query);

        logger.info("SchedulerTaskInfoServiceTests.testListToMap schedulerTaskInfoList:{}", JsonUtil.toJSON(schedulerTaskInfoList));

        Map<Integer, Object> map = new HashMap<>();
        schedulerTaskInfoList.stream().forEach(SchedulerTaskInfo -> {
            map.put(SchedulerTaskInfo.getId(), SchedulerTaskInfo);
        });


        Set<Map.Entry<Integer, Object>> set = map.entrySet();
        Iterator<Map.Entry<Integer, Object>> iterator = set.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Object> me = iterator.next();
            System.out.println(me.getKey() + ": " + me.getValue());
        }
        System.out.println("==========" + objectMapper.writeValueAsString(map));
    }


    @Test
    public void testListM() {
        long time = System.currentTimeMillis();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("{}.{} time: is {}", simpleClassName, methodName, DateUtil.getFormatTime(time));

        SchedulerTaskInfo query = new SchedulerTaskInfo();
        List<SchedulerTaskInfo> schedulerTaskInfoList1 = schedulerTaskInfoService.selectEntryList(query);
        logger.info("SchedulerTaskInfoServiceTests.testListM schedulerTaskInfoList1:{}", JsonUtil.toJSON(schedulerTaskInfoList1));

        /*Gson gson = new Gson();
        logger.info("SchedulerTaskInfoServiceTests.testListM schedulerTaskInfoList1:{}", gson.toJson(schedulerTaskInfoList1));*/

        /*Integer[] ids = schedulerTaskInfoList1.stream().map(SchedulerTaskInfo::getId).collect(Collectors.toList()).toArray(new Integer[schedulerTaskInfoList1.size()]);
        int[] arr2 = Arrays.stream(ids).mapToInt(Integer::valueOf).toArray();
        System.out.println(Arrays.toString(res));*/

        Set<Integer> set = schedulerTaskInfoList1.stream().map(SchedulerTaskInfo -> SchedulerTaskInfo.getStatus()).collect(Collectors.toSet());
        Integer[] statusIn = set.toArray(new Integer[0]);

        query = new SchedulerTaskInfo();
        query.setStatusIn(statusIn);
        List<SchedulerTaskInfo> schedulerTaskInfoList2 = schedulerTaskInfoService.selectUnfinishedTaskBy(query);

        //使用一个list过滤另一个list
        schedulerTaskInfoList1.stream().map(SchedulerTaskInfo -> {
            for (SchedulerTaskInfo s : schedulerTaskInfoList2) {
                if (SchedulerTaskInfo.getId() == s.getId()) {
                    SchedulerTaskInfo.setErrMsg(s.getApplicationId());
                }
                System.out.println("==========" + s.getErrMsg());
            }
            return SchedulerTaskInfo;
        }).collect(Collectors.toList());

    }

    /**
     * 使用一个list过滤另一个list
     *
     * @return
     * @throws Exception
     */
    @Test
    public void testListM2() {
        long time = System.currentTimeMillis();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("{}.{} time: is {}", simpleClassName, methodName, DateUtil.getFormatTime(time));

        SchedulerTaskInfo query = new SchedulerTaskInfo();

        List<SchedulerTaskInfo> schedulerTaskInfoList1 = schedulerTaskInfoService.selectEntryList(query);
        List<SchedulerTaskInfo> schedulerTaskInfoList2 = schedulerTaskInfoList1.stream().filter(e -> e.getId() != null).collect(Collectors.toList());

        List<SchedulerTaskInfo> schedulerTaskInfoList3 = schedulerTaskInfoList1.stream().filter(new Predicate<SchedulerTaskInfo>() {
            @Override
            public boolean test(SchedulerTaskInfo schedulerTaskInfo) {
                for (SchedulerTaskInfo s : schedulerTaskInfoList2) {
                    if (schedulerTaskInfo.getId().equals(s.getId())) {
                        return true;
                    }
                }
                return true;
            }
        }).collect(Collectors.toList());
    }

    @Test
    public void query() {
        long time = System.currentTimeMillis();
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info("{}.{} time: is {}", simpleClassName, methodName, DateUtil.getFormatTime(time));

        System.out.println("=========" + NumUtil.isNumeric2("null"));

        System.out.println("=====" + DateUtil.verifyDateLegal("2022-09-02"));
        System.out.println("=====" + DateUtil.verifyDateLegal("2022-09-02 15"));
        System.out.println("=====" + DateUtil.verifyDateLegal("2022-09-02 15:08"));
        System.out.println("=====" + DateUtil.verifyDateLegal("2022-09-02 15:08:13"));
        System.out.println("=====" + DateUtil.verifyDateLegal("Invalid date"));

        String mail = "wangshuanglong01@inke.cn";
        boolean email = CheckEmail.isEmail(mail);
        if (email) {
            mail = mail.substring(0, mail.indexOf("@"));
        }
        System.out.println("====" + mail);
        System.out.println("=======" + CheckEmail.getMailByWeb(mail));

        SchedulerTaskInfo schedulerTaskInfo = new SchedulerTaskInfo();
        /*List<SchedulerTaskInfo> schedulerTaskInfoList = schedulerTaskInfoService.selectEntryList(schedulerTaskInfo);
        //logger.info("==========schedulerTaskInfoList:{}", schedulerTaskInfoList);

        for (SchedulerTaskInfo data : schedulerTaskInfoList) {
            System.out.println("==========" + data);
        }*/

        //获取数据
        //schedulerTaskInfo.setStatus(1);
        List<Map<String, Object>> list = (List<Map<String, Object>>) schedulerTaskInfoService.getSchedulerTaskInfoData(schedulerTaskInfo).getResult();
        logger.info("==========list:{}", list);
        System.out.println("=======" + list.get(0).get("id"));
        //List<Map<String, Object>> mapList = schedulerTaskInfoService.getList(schedulerTaskInfo);
        //logger.info("==========mapList:{}", mapList);

        System.out.println("=========" + schedulerTaskInfoService.getList(schedulerTaskInfo).get(0).get("createdBy"));
    }
}
