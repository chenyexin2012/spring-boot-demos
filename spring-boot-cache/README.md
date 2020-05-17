### 常用注解说明

1. @EnableCaching: 添加在启动类上，开启缓存。

2. @CacheConfig: 作用于类上，统一配置类的缓存属性，可以被类中方法上的属性覆盖。

3. @Cacheable：可作用于类或方法上，主要用于方法的配置，能够根据方法的参数对数据进行缓存。

4. @CachePut: 可作用于类或方法上，主要用于方法的配置，保证方法被调用时，数据被缓存。

5. @CacheEvict: 可作用于类或方法上，主要用于方法的配置，删除缓存，当指定allEntries = true时，清空指定cacheNames下所有缓存。

### 常用注解参数说明

1. cacheNames/value：缓存的名字，至少应该指定一个，如：@CacheConfig(cacheNames = "user")、@CacheConfig(cacheNames = {"user", "userData"})。

2. key：缓存的key，可以为空，需要按照SpEL表达式编写，默认按照方法的所有参数进行组合，如：@Cacheable(key = "#startPage + ':' + #pageSize")

3. keyGenerator：指定key生成策略，需要实现org.springframework.cache.interceptor.KeyGenerator接口，如：@Cacheable(keyGenerator = "userKeyGenerator")

    
    @Component
    public class UserKeyGenerator implements KeyGenerator {
    
        @Override
        public Object generate(Object target, Method method, Object... params) {
            StringBuilder key = new StringBuilder(target.getClass().getName());
            key.append('.');
            key.append(method.getName());
            if(params.length > 0) {
                key.append(":");
                int i = 0;
                for (; i < params.length - 1; i++) {
                    key.append(params[i]).append(':');
                }
                key.append(params[i]);
            }
    
            return key.toString();
        }
    }
    
4. beforeInvocation：@CacheEvict的属性，表示是否在方法执行前清空数据，默认为false。

5. allEntries：@CacheEvict的属性，表示是否清除当前缓存的所有内容，默认为false。
    
### 常见的SpEL上下文信息

1. target：当前被调用的目标对象实例，如：#root.target。

2. targetClass：当前被调用的类，如：#root.targetClass。

3. method：当前被调用的方法，如：#root.method.name。

4. methodName：当前被调用的方法名，如：#root.methodName。

5. args：当前被调用的方法参数，如：#root.#root.args[0]。

6. caches：当前方法调用使用的缓存列表，如：#root.methodname#root.caches[0].name。

7. Argument Name：直接使用方法参数，如：#startPage、#record.id。

8. result：方法的返回值，若作用在@CacheEvict注解上，需保证beforeInvocation=false，如：#result。






















