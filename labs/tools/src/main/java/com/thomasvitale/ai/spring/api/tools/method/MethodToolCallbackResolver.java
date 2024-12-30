package com.thomasvitale.ai.spring.api.tools.method;

import com.thomasvitale.ai.spring.api.tools.Tool;
import com.thomasvitale.ai.spring.api.tools.ToolCallbackResolver;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodToolCallbackResolver implements ToolCallbackResolver {

    private final Object target;

    private MethodToolCallbackResolver(Object target) {
        Assert.notNull(target, "target cannot be null");
        this.target = target;
    }

    @Override
    public FunctionCallback[] getToolCallbacks() {
        List<FunctionCallback> callbacks = new ArrayList<>();
        
        // Get all methods from the target object
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(target.getClass());
        
        for (Method method : methods) {
            Tool toolAnnotation = method.getAnnotation(Tool.class);

            // Ignore methods without the @Tool annotation.
            if (toolAnnotation == null) {
                continue;
            }

            // Create FunctionCallback for methods with the @Tool annotation.
            FunctionCallback callback = FunctionCallback.builder()
                .method(method.getName(), method.getParameterTypes())
                .description(toolAnnotation.value())
                .targetObject(target)
                .build();
            callbacks.add(callback);
        }
        
        return callbacks.toArray(new FunctionCallback[0]);
    }

    public static class Builder {
        private Object target;

        public Builder target(Object target) {
            this.target = target;
            return this;
        }

        public MethodToolCallbackResolver build() {
            return new MethodToolCallbackResolver(target);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

}
