package com.thomasvitale.ai.spring;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.aot.generate.GenerationContext;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.ReflectionHints;
import org.springframework.beans.factory.aot.BeanRegistrationAotContribution;
import org.springframework.beans.factory.aot.BeanRegistrationAotProcessor;
import org.springframework.beans.factory.aot.BeanRegistrationCode;
import org.springframework.beans.factory.support.RegisteredBean;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.util.stream.Stream;

import static org.springframework.core.annotation.MergedAnnotations.SearchStrategy.TYPE_HIERARCHY;

/**
 * AOT {@code BeanRegistrationAotProcessor} that detects the presence of the
 * {@link Tool} annotation on methods and creates the required reflection hints.
 */
class ToolBeanRegistrationAotProcessor implements BeanRegistrationAotProcessor {

    @Override
    @Nullable
    public BeanRegistrationAotContribution processAheadOfTime(RegisteredBean registeredBean) {
        Class<?> beanClass = registeredBean.getBeanClass();
        MergedAnnotations.Search search = MergedAnnotations.search(TYPE_HIERARCHY);

        boolean hasAnyToolAnnotatedMethods = Stream.of(ReflectionUtils.getDeclaredMethods(beanClass))
                .anyMatch(method -> search.from(method).isPresent(Tool.class));

        if (hasAnyToolAnnotatedMethods) {
            return new AotContribution(beanClass);
        }

        return null;
    }

    private static class AotContribution implements BeanRegistrationAotContribution {

        private final MemberCategory[] memberCategories = new MemberCategory[] { MemberCategory.INVOKE_DECLARED_METHODS,
                MemberCategory.INVOKE_PUBLIC_METHODS };

        private final Class<?> toolClass;

        public AotContribution(Class<?> toolClass) {
            this.toolClass = toolClass;
        }

        @Override
        public void applyTo(GenerationContext generationContext, BeanRegistrationCode beanRegistrationCode) {
            ReflectionHints reflectionHints = generationContext.getRuntimeHints().reflection();
            reflectionHints.registerType(toolClass, memberCategories);
        }

    }

}
