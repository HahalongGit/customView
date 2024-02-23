package com.lll.beizertest.maincode.module;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import androidx.annotation.NonNull;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用：
 * 定义一个接口api,在业务module中实现， 比如 account 模块，对应有一个 account-api的module，account依赖 account-api模块
 * 如：在 gradle文件的 dependencies中使用  usingApi "account-api" // 引入自身的 api 模块，类似于使用 implementation引入
 * 在 account-api的module 定一个一个对外暴露 获取用户登录后信息的方法，比如 getUser()，在account module中实现这个接口。
 * 业务组件打包，提供组件给其他模块使用的时候，打包出来 两个包上传到 maven仓库，一个 account，一个是 account-api组件，其他业务组件依赖 account-api组件
 * 然后使用 InstanceProvider 访问接口，即可调用到实现的类
 *
 * 调用方式：（调用 LoginInterface接口中的 login方法）
 * InstanceProvider.get(LoginInterface::class.java).login(it, zhRawUrl)
 *
 *
 * @since 2019/03/28
 */

public class InstanceProvider {

   /**
    * 是否允许 service 查找执行 fallback 到 resource 机制
    * 默认不允许
    *
    * * 这是临时逻辑，稳定之后会移除，请勿依赖此字段 *
    */
   private static boolean allowFallbackToRes = ComponentBuildConfig.IS_MODULAR();

   /**
    * 当 ServiceManifest 失败时，是否允许 fallback 到 ServiceLoader
    *
    * 虽然主工程肯定不会有问题，但是很多基础库未使用 xxx plugin 时或者单元测试时，还是要 fallback 的
    *
    * 不要再使用这个接口了，fallback 强制使能
    */
   @Deprecated
   public static void allowServiceLoaderFallback(boolean allowFallback) {
      allowFallbackToRes = allowFallback;
   }

   private static final Map<Class, Object> INSTANCE_MAP = new ConcurrentHashMap<>();

   /**
    * 注册一个 clazz 的实现，多次注册以最后一个为准
    */
   public static <T> void register(Class<T> clazz, T o) {
      INSTANCE_MAP.put(clazz, o);
   }

   /**
    * 删除一个已经注册的实现
    */
   public static void unregister(Class clazz) {
      INSTANCE_MAP.remove(clazz);
   }

   /**
    * @deprecated 直接用  {@link #get(Class)}
    */
   @Deprecated
   public static <T> T provide(Class<T> clazz) {
      return get(clazz);
   }

   /**
    * 根据 class 返回一个注册的实例，如果是通过 {@link #register(Class, Object)} 注册的，会直接返回
    * <p>
    * 如果通过 ServiceLoader 并且只注册了一个，就会返回唯一的这一个
    * <p>
    * 如果注册了多个，说明这个接口是设计为多个实例的，所以什么也不返回（就算返回了第一个，也无法确定哪个是第一个）
    */
   @SuppressWarnings({"unchecked"})
   public static <T> T get(Class<T> clazz) {
      // 此处没有必要上锁：INSTANCE_MAP 是带锁的、loadService 也是带锁的
      T instance = (T) INSTANCE_MAP.get(clazz);
      if (instance == null) {
         List<T> services = loadService(clazz);
         if (services.size() == 1) {
            instance = services.get(0);
         }
      }
      return instance;
   }

   /**
    * @see InstanceProvider#get(Class)
    */
   public static <T> Optional<T> optional(Class<T> ofClass) {
      return Optional.ofNullable(get(ofClass));
   }

   /**
    * 返回通过 ServiceManifest/ServiceLoader 注册的所有实现
    */
   @NonNull
   public static <T> List<T> loadService(Class<T> clazz) {
                                                            // 此处需要基础全局的Context，按照项目情况替换
      List<T> loadedServices =  ServiceManifest.loadServices(BaseApplication.get(), clazz, allowFallbackToRes);
      if (loadedServices != null) {
         return loadedServices;
      }
      return Collections.emptyList();
   }

   /**
    * 删除已加载的 clazz 的 Service 缓存
    */
   public static void unloadService(Class clazz) {
      ServiceManifest.unloadServices(clazz);
   }

   /**
    * 返回通过 ServiceLoader 注册的所有实现，以及通过 {@link #register(Class, Object)} 方法注册的实现
    *
    * 注意返回的列表是不可变的
    */
   @SuppressWarnings({"unchecked"})
   @NonNull
   public static <T> List<T> getAll(Class<T> clazz) {
      // 此处没有必要上锁：INSTANCE_MAP 是带锁的、loadService 也是带锁的
      List<T> list = loadService(clazz);
      T registered = (T) INSTANCE_MAP.get(clazz);
      if (registered != null) {
         List<T> listAll = new ArrayList<>(list.size() + 1);
         listAll.add(registered);
         listAll.addAll(list);
         list = Collections.unmodifiableList(listAll);
      }
      return list;
   }

   /**
    * 删除所有接口实例缓存
    */
   public static void unloadAll(Class clazz) {
      unregister(clazz);
      unloadService(clazz);
   }

   /**
    * 注解，表示一个 Interface 注册的实例是否是可缓存的，默认为 true
    *
    * 仅对对过 InstanceProvider 读取的注册于 ServiceLoader 的实例生效
    */
   @Retention(RUNTIME)
   @Target(TYPE)
   public @interface Cache {
      boolean value() default true;
   }
}