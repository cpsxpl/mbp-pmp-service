package com.mbp.eng.example.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mbp.eng.module.example.cache.LocalCache;
import com.mbp.eng.framework.common.util.date.DateUtil;
import com.mbp.eng.module.example.domain.system.AuthFunction;
import com.mbp.eng.module.example.domain.system.AuthRole;
import com.mbp.eng.module.example.domain.system.AuthRolePermission;
import com.mbp.eng.module.example.domain.system.AuthUser;
import com.mbp.eng.module.example.domain.system.AuthUserGroup;
import com.mbp.eng.module.example.domain.system.AuthUserRole;
import com.mbp.eng.module.example.domain.system.FourAddressMapping;
import com.mbp.eng.module.example.domain.system.ResourceType;
import com.mbp.eng.module.example.domain.system.SceneType;
import com.mbp.eng.module.example.service.system.AuthFunctionService;
import com.mbp.eng.module.example.service.system.AuthRolePermissionService;
import com.mbp.eng.module.example.service.system.AuthRoleService;
import com.mbp.eng.module.example.service.system.AuthUserGroupService;
import com.mbp.eng.module.example.service.system.AuthUserRoleService;
import com.mbp.eng.module.example.service.system.AuthUserService;
import com.mbp.eng.module.example.service.system.FourAddressMappingService;
import com.mbp.eng.module.example.service.system.ResourceTypeService;
import com.mbp.eng.module.example.service.system.SceneTypeService;
import com.mbp.pmp.app.api.base.SpringBootServiceApplicationTests;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

//@Ignore
public class AuthServiceTests extends SpringBootServiceApplicationTests {

    private String className = this.getClass().getName();
    private String simpleClassName = this.getClass().getSimpleName();
    private static Logger logger = LoggerFactory.getLogger(AuthServiceTests.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    AuthFunctionService authFunctionService;

    @Autowired
    AuthRolePermissionService authRolePermissionService;

    @Autowired
    AuthRoleService authRoleService;

    @Autowired
    AuthUserGroupService authUserGroupService;

    @Autowired
    AuthUserRoleService authUserRoleService;

    @Autowired
    AuthUserService authUserService;

    @Autowired
    FourAddressMappingService fourAddressMappingService;

    @Autowired
    ResourceTypeService resourceTypeService;

    @Autowired
    SceneTypeService sceneTypeService;

    @Autowired
    LocalCache localCache;

    @Test
    public void testMap() throws Exception {
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
        logger.info("time: is {}", DateUtil.getFormatTime(time));
        FourAddressMapping qyery = new FourAddressMapping();
        qyery.setParentCode("110100");
        List<FourAddressMapping> fourAddressMappingList = fourAddressMappingService.selectEntryList(qyery);

        //将list转换map
        Map<String, String> listToMap = fourAddressMappingList.stream().collect(Collectors.toMap(FourAddressMapping -> FourAddressMapping.getId().toString(), FourAddressMapping::getName));
        Map<Long, String> listToMap_1 = fourAddressMappingList.stream().collect(Collectors.toMap(FourAddressMapping::getId, FourAddressMapping::getName));

        //得到 Map 的 value 为对象本身
        Map<Long, FourAddressMapping> listToMap_2 = fourAddressMappingList.stream().collect(Collectors.toMap(FourAddressMapping::getId, t -> t));
        Map<Long, FourAddressMapping> listToMap_3 = fourAddressMappingList.stream().collect(Collectors.toMap(FourAddressMapping::getId, Function.identity()));

        //Collectors.toMap 有三个重载方法:
        //toMap(AuthFunction<? super T, ? extends K> keyMapper, AuthFunction<? super T, ? extends U> valueMapper);
        //toMap(AuthFunction<? super T, ? extends K> keyMapper, AuthFunction<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction);
        //toMap(AuthFunction<? super T, ? extends K> keyMapper, AuthFunction<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier);

        /*参数含义分别是:
        1. keyMapper:Key 的映射函数
        2. valueMapper:Value 的映射函数
        3. mergeFunction:当 Key 冲突时,调用的合并方法
        4. mapSupplier:Map 构造器,在需要返回特定的 Map 时使用*/

        //如果 fourAddressMappingList 中 getParentCode 有相同的,使用上面的写法会抛异常.需要调用第二个重载方法,传入合并函数
        Map<String, String> listToMap_4 = fourAddressMappingList.stream().collect(Collectors.toMap(FourAddressMapping::getParentCode, FourAddressMapping::getName, (n1, n2) -> n1 + n2));
        // 输出结果:
        //110100-> 东城区西城区...


        //第四个参数（mapSupplier）用于自定义返回 Map 类型,比如我们希望返回的 Map 是根据 Key 排序的,可以使用如下写法:
        Map<String, String> listToMap_5 = fourAddressMappingList.stream().collect(Collectors.toMap(FourAddressMapping::getCode, FourAddressMapping::getName, (n1, n2) -> n1, TreeMap::new));
    }

    @Test
    public void localCacheTest() throws Exception {
        long time = System.currentTimeMillis();
        logger.info("time: is {}", DateUtil.getFormatTime(time));

        FourAddressMapping qyery = new FourAddressMapping();
        qyery.setParentCode("110100");
        List<FourAddressMapping> fourAddressMappingList = fourAddressMappingService.selectEntryList(qyery);
        Map<String, Object> map = fourAddressMappingList.stream().collect(Collectors.toMap(FourAddressMapping::getCode, Function.identity()));
        localCache.setFourAddressMappingMap(map);

        fourAddressMappingList = new ArrayList(localCache.getFourAddressMappingMap().values());
        for (FourAddressMapping fourAddressMapping : fourAddressMappingList) {
            System.out.println("==========" + objectMapper.writeValueAsString(fourAddressMapping));
        }
    }

    @Test
    public void mapToList() throws Exception {
        long time = System.currentTimeMillis();
        logger.info("time: is {}", DateUtil.getFormatTime(time));

        FourAddressMapping qyery = new FourAddressMapping();
        qyery.setParentCode("110100");
        List<FourAddressMapping> fourAddressMappingList1 = fourAddressMappingService.selectEntryList(qyery);
        List<FourAddressMapping> fourAddressMappingList2 = fourAddressMappingList1.stream().map(FourAddressMapping::new).collect(Collectors.toList());
        Map<String, Object> listToMap_3 = fourAddressMappingList2.stream().collect(Collectors.toMap(FourAddressMapping::getCode, Function.identity()));

        List<FourAddressMapping> keySet = new ArrayList(listToMap_3.keySet());
        for (FourAddressMapping fourAddressMapping : keySet) {
            System.out.println("==========" + objectMapper.writeValueAsString(fourAddressMapping));
        }

        List<FourAddressMapping> values = new ArrayList(listToMap_3.values());
        for (FourAddressMapping fourAddressMapping : values) {
            System.out.println("==========" + objectMapper.writeValueAsString(fourAddressMapping));
        }

        Set<Map.Entry<String, Object>> entries = listToMap_3.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            System.out.println("key=" + entry.getKey() + ",value=" + entry.getValue());
        }
    }


    @Test
    public void testListToMap() throws Exception {
        long time = System.currentTimeMillis();
        logger.info("time: is {}", DateUtil.getFormatTime(time));
        FourAddressMapping qyery = new FourAddressMapping();
        qyery.setParentCode("110100");
        List<FourAddressMapping> fourAddressMappingList = fourAddressMappingService.selectEntryList(qyery);
        Map<String, Object> fourAddressMappingMap = new HashMap<>();
        fourAddressMappingList.stream().forEach(fourAddressMapping -> {
            fourAddressMappingMap.put(fourAddressMapping.getCode(), fourAddressMapping);
        });
        System.out.println("==========" + objectMapper.writeValueAsString(fourAddressMappingMap));
    }

    @Test
    public void query() throws Exception {
        long time = System.currentTimeMillis();
        logger.info("time: is {}", DateUtil.getFormatTime(time));
        AuthFunction authFunction = new AuthFunction();
        List<AuthFunction> authFunctionList = authFunctionService.selectEntryList(authFunction);
        for (AuthFunction data : authFunctionList) {
            System.out.println("==========" + objectMapper.writeValueAsString(data));
        }


        AuthRolePermission authRolePermission = new AuthRolePermission();
        List<AuthRolePermission> authRolePermissionList = authRolePermissionService.selectEntryList(authRolePermission);
        for (AuthRolePermission data : authRolePermissionList) {
            System.out.println("==========" + objectMapper.writeValueAsString(data));
        }


        AuthRole authRole = new AuthRole();
        List<AuthRole> authRoleList = authRoleService.selectEntryList(authRole);
        for (AuthRole data : authRoleList) {
            System.out.println("==========" + objectMapper.writeValueAsString(data));
        }


        AuthUserGroup authUserGroup = new AuthUserGroup();
        List<AuthUserGroup> authUserGroupList = authUserGroupService.selectEntryList(authUserGroup);
        for (AuthUserGroup data : authUserGroupList) {
            System.out.println("==========" + objectMapper.writeValueAsString(data));
        }


        AuthUserRole authUserRole = new AuthUserRole();
        List<AuthUserRole> authUserRoleList = authUserRoleService.selectEntryList(authUserRole);
        for (AuthUserRole data : authUserRoleList) {
            System.out.println("==========" + objectMapper.writeValueAsString(data));
        }


        AuthUser authUser = new AuthUser();
        List<AuthUser> authUserList = authUserService.selectEntryList(authUser);
        for (AuthUser data : authUserList) {
            System.out.println("==========" + data);
        }


        FourAddressMapping fourAddressMapping = new FourAddressMapping();
        List<FourAddressMapping> fourAddressMappingList = fourAddressMappingService.selectEntryList(fourAddressMapping);
        for (FourAddressMapping data : fourAddressMappingList) {
            System.out.println("==========" + data);
        }

        ResourceType resourceType = new ResourceType();
        List<ResourceType> resourceTypeList = resourceTypeService.selectEntryList(resourceType);
        for (ResourceType data : resourceTypeList) {
            System.out.println("==========" + data);
        }

        SceneType sceneType = new SceneType();
        List<SceneType> sceneTypeList = sceneTypeService.selectEntryList(sceneType);
        for (SceneType data : sceneTypeList) {
            System.out.println("==========" + data);
        }
    }

}
