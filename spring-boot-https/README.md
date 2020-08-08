##### SpringMVC请求参数接收处理过程

在SpringMVC中，处理Controller中方法请求参数的基础接口为HandlerMethodArgumentResolver，定义如下：

    package org.springframework.web.method.support;
    
    import org.springframework.core.MethodParameter;
    import org.springframework.lang.Nullable;
    import org.springframework.web.bind.WebDataBinder;
    import org.springframework.web.bind.support.WebDataBinderFactory;
    import org.springframework.web.context.request.NativeWebRequest;
    
    public interface HandlerMethodArgumentResolver {
    
    	boolean supportsParameter(MethodParameter parameter);

    	@Nullable
    	Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
    			NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception;
    
    }
    
其常见的实现类有下列几种：

1. RequestParamMethodArgumentResolver：用于处理一些简单类型（String, int/Integer等）参数、@RequestParam 注解的参数，MultipartFile参数。

2. RequestResponseBodyMethodProcessor：用于处理 @RequestBody 注解的参数。

3. PathVariableMapMethodArgumentResolver：用于处理 @PathVariable 注解的参数。


处理返回参数的基础接口为HandlerMethodReturnValueHandler，定义如下：

    package org.springframework.web.method.support;
    
    import org.springframework.core.MethodParameter;
    import org.springframework.lang.Nullable;
    import org.springframework.web.context.request.NativeWebRequest;

    public interface HandlerMethodReturnValueHandler {

        boolean supportsReturnType(MethodParameter returnType);

        void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType,
                ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception;
    
    }

HandlerMethodArgumentResolver、HandlerMethodReturnValueHandler在实际使用中是通过HandlerMethodArgumentResolverComposite和HandlerMethodReturnValueHandlerComposite
来完成的。HandlerMethodArgumentResolverComposite和HandlerMethodReturnValueHandlerComposite分别包含了ArgumentResolver和ReturnValueHandler的列表。通过遍历列表找到对应的
解析器进行处理。

而不管是ArgumentResolver，还是ReturnValueHandler最终都会用到HttpMessageConverter消息转换器，HttpMessageConverter接口定义如下：


    package org.springframework.http.converter;
    
    import java.io.IOException;
    import java.util.List;
    
    import org.springframework.http.HttpInputMessage;
    import org.springframework.http.HttpOutputMessage;
    import org.springframework.http.MediaType;
    import org.springframework.lang.Nullable;
    
    public interface HttpMessageConverter<T> {
    
        boolean canRead(Class<?> clazz, @Nullable MediaType mediaType);
    
        boolean canWrite(Class<?> clazz, @Nullable MediaType mediaType);
    
        List<MediaType> getSupportedMediaTypes();
    
        // 转换 request 的请求数据
        T read(Class<? extends T> clazz, HttpInputMessage inputMessage)
                throws IOException, HttpMessageNotReadableException;
                
        // 转换 response 的响应信息
        void write(T t, @Nullable MediaType contentType, HttpOutputMessage outputMessage)
                throws IOException, HttpMessageNotWritableException;
    }

用于处理 @ResponseBody 和 @RequestBody 标注的方法或参数。常见的消息转换器有如下几种：

1. MappingJackson2HttpMessageConverter：通常用于处理 JSON 类型的请求或返回参数，此时ContentType类型为 application/json。

2. StringHttpMessageConverter: 通常用于处理 String 类型的请求或返回参数，此时ContentType类型为 text/plain。


在ArgumentResolver中，通常通过ContentType来决定使用何种消息转换器，参考AbstractMessageConverterMethodArgumentResolver.readWithMessageConverters()方法的部分代码：

    protected <T> Object readWithMessageConverters(HttpInputMessage inputMessage, MethodParameter parameter,
            Type targetType) throws IOException, HttpMediaTypeNotSupportedException, HttpMessageNotReadableException {

        MediaType contentType;
        boolean noContentType = false;
        try {
            // 从请求头中获取 ContentType
            contentType = inputMessage.getHeaders().getContentType();
        }
        catch (InvalidMediaTypeException ex) {
            throw new HttpMediaTypeNotSupportedException(ex.getMessage());
        }
        // 代码省略...

        EmptyBodyCheckingHttpInputMessage message;
        try {
            message = new EmptyBodyCheckingHttpInputMessage(inputMessage);
            // 遍历消息转换器列表，找到对应的可以处理请求参数的消息转换器
            for (HttpMessageConverter<?> converter : this.messageConverters) {
                Class<HttpMessageConverter<?>> converterType = (Class<HttpMessageConverter<?>>) converter.getClass();
                GenericHttpMessageConverter<?> genericConverter =
                        (converter instanceof GenericHttpMessageConverter ? (GenericHttpMessageConverter<?>) converter : null);
                if (genericConverter != null ? genericConverter.canRead(targetType, contextClass, contentType) :
                        (targetClass != null && converter.canRead(targetClass, contentType))) {
                    if (message.hasBody()) {
                        HttpInputMessage msgToUse =
                                getAdvice().beforeBodyRead(message, parameter, targetType, converterType);
                        body = (genericConverter != null ? genericConverter.read(targetType, contextClass, msgToUse) :
                                ((HttpMessageConverter<T>) converter).read(targetClass, msgToUse));
                        body = getAdvice().afterBodyRead(body, msgToUse, parameter, targetType, converterType);
                    }
                    else {
                        body = getAdvice().handleEmptyBody(null, message, parameter, targetType, converterType);
                    }
                    break;
                }
            }
        }
        catch (IOException ex) {
            throw new HttpMessageNotReadableException("I/O error while reading input message", ex, inputMessage);
        }

        // 代码省略...
        
        return body;
    }






















