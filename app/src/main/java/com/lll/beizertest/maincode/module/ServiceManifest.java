package com.lll.beizertest.maincode.module;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.lll.beizertest.maincode.module.InstanceProvider.Cache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * <p>
 * 一个 ServiceLoader 的补充
 * <p>
 * ServiceLoader 由于一开始要验证 签名，所以如果在应用启动的时候初始化它可能会导致启动时间变长，所以要跳过签名验证步骤。
 * <p>
 * 解决办法是配合我们的打包插件，在编译时将 resources/META-INF/services 复制到 assets/META-INF/services
 * <p>
 * 加载的时候会加载 assets/META-INF/services 里面的 services，跳过了验证过程
 */
@SuppressWarnings({"unchecked"})
@RestrictTo(RestrictTo.Scope.LIBRARY)
class ServiceManifest {

   private static final String TAG = "ServiceManifest";

   /**
    * 编译时解析到的 spi 关系映射表
    */
   private static final HashMap<String, List<Class>> holderMap = new HashMap<>();

   static {
      // 类加载的时候初始化 holder map
      init();
   }

   private static final Map<Class, List> SERVICE_LIST_MAP = new ConcurrentHashMap<>();

   static <T> List<T> getCachedServices(@NonNull Class<T> clazz) {
      return SERVICE_LIST_MAP.get(clazz);
   }

   /**
    * 删除已加载的 clazz 的 Service 缓存
    */
   static void unloadServices(@NonNull Class clazz) {
      SERVICE_LIST_MAP.remove(clazz);
   }

   /**
    * 获取 ServiceLoader 注册的类，这里返回的是不可变的列表
    *
    * @param clazz 对应的接口类型
    */
   @NonNull
   @RestrictTo(RestrictTo.Scope.LIBRARY)
   static <T> List<T> loadServices(Context context, @NonNull Class<T> clazz) {
      return loadServices(context, clazz, true);
   }

   static <T> List<T> loadServices(Context context, @NonNull Class<T> clazz, boolean allowFallback) {
      Cache cache = clazz.getAnnotation(Cache.class);
      //默认是使用 cache 的，所以如果 cache 为 null 则为 true
      boolean useCache = cache == null || cache.value();
      if (useCache) {
         return readServicesCache(context, clazz, allowFallback);
      } else {
         // 不使用缓存，每次都从头读
         return readServices(context, clazz, allowFallback);
      }
   }

   /**
    * 如果有缓存，就从缓存中读取实例，否则从 assets 或者 services 目录下读
    */
   private static <T> List<T> readServicesCache(Context context, @NonNull Class<T> clazz, boolean allowFallback) {
      // 如果使用缓存，就使用 double check 保持单例
      List<T> instances = SERVICE_LIST_MAP.get(clazz);
      if (instances == null) {
         synchronized (classLock(clazz)) {
            instances = SERVICE_LIST_MAP.get(clazz);
            if (instances == null) {
               // 如果不需要缓存或者需要缓存但是缓存不存在，则 service 中读取
               instances = readServices(context, clazz, allowFallback);
               // 如果需要缓存，那么会将结果写到 map 中去
               if (instances != null) {
                  SERVICE_LIST_MAP.put(clazz, instances);
               }
            }
            return instances;
         }
      }
      return instances;
   }

   /**
    * 从 assets 或者 services 目录下读出实例
    */
   @Nullable
   private static <T> List<T> readServices(Context context, @NonNull Class<T> clazz, boolean allowFallback) {
      // 首先尝试从 map 中读取
      List<T> instances = readServicesFromMap(clazz);
      if (instances != null && !instances.isEmpty()) {
         return instances;
      }
      // 然后才会加载 resources/META-INF/services 里面的 Service
      if (allowFallback) {
         Log.e(TAG, "ServiceManifest with map failed, fallback with ServiceLoader");
         instances = loadServiceLoaderInner(clazz);
      }
      return instances;
   }

   /**
    * 从 holder map 中解析实例
    */
   private static <T> List<T> readServicesFromMap(@NonNull Class<T> clazz) {
      try {
         List<Class> implClasses = holderMap.get(clazz.getName());
         if (implClasses != null && !implClasses.isEmpty()) {
            List<T> services = new LinkedList<>();
            for (Class implClass : implClasses) {
               services.add((T) implClass.newInstance());
            }
            return services;
         }
      } catch (Exception e) {
         // 忽略异常，我们有 fall back 机制
      }
      // 失败了
      return null;
   }

   /**
    * 使用 ServiceMenifest 加载 assets/META-INF/services 里面的类，假的 ServiceLoader
    */
   @NonNull
   static <T> List<T> loadServiceManifestInner(Context context, Class<T> clazz) {
      List<T> list = new ArrayList<>();
      try (InputStream stream = context.getResources().getAssets().open("META-INF/services/" + clazz.getName())) {
         BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
         //用于排重
         Set<String> distinctLines = new LinkedHashSet<>();
         String line;
         while ((line = bufferedReader.readLine()) != null) {
            line = line.trim();
            if (line.length() > 0 && !line.startsWith("#")) {
               distinctLines.add(line);
            }
         }
         for (String distinctLine : distinctLines) {
            Class<T> cls = (Class<T>) Class.forName(distinctLine);
            list.add(cls.newInstance());
            Log.d(TAG, "ServiceManifest has load " + cls.getName());
         }
      } catch (IllegalAccessException | InstantiationException | ClassNotFoundException e) {
         throw new IllegalArgumentException(e.getMessage(), e.getCause());
      } catch (IOException ignored) {
         // 忽略 io 异常，因为不会发生，一旦发生，会走 fallback
      }
      return Collections.unmodifiableList(list);
   }

   /**
    * 使用正宗的 ServiceLoader 加载类，resources/META-INF/services 下面的类
    */
   @NonNull
   static <T> List<T> loadServiceLoaderInner(Class<T> clazz) {
      List<T> list = new ArrayList<>();
      try {
         ServiceLoader<T> serviceLoader = ServiceLoader.load(clazz);
         for (T t : serviceLoader) {
            list.add(t);
         }
      } catch (ServiceConfigurationError e) {
         Log.e(TAG, "loadServiceLoaderInner: ", e);
      }
      return Collections.unmodifiableList(list);
   }

   /**
    * 用于返回一个 class 的锁，使用 String.intern() 保证唯一性
    * <p>
    * 如果直接锁 Class 可能会导致潜在的锁冲突（其他地方也可能在使用这个类锁）
    */
   static String classLock(Class clazz) {
      return ("INSTANCE-PROVIDER-" + clazz.getName()).intern();
   }

   private static void init() {
      // 往这里插入字节码，大概如下
      // addHolder("com.some.classname", SomeClass.class);
   }

   /**
    * 编译时插入字节码调用这里
    * * 切勿修改这个方法的名称！ *
    *
    * @param spiName spi interface 名称
    * @param spiImpl spi 实现 class
    */
   private static void addHolder(String spiName, Class spiImpl) {
      List<Class> implList = holderMap.get(spiName);
      if (implList == null) {
         implList = new LinkedList<>();
         implList.add(spiImpl);
         holderMap.put(spiName, implList);
      } else {
         implList.add(spiImpl);
      }
   }
}
