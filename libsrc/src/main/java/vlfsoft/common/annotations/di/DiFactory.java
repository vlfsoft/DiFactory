package vlfsoft.common.annotations.di;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

import vlfsoft.common.annotations.design.patterns.gof.CreationalPattern;

/**
 * Annotation for Dependency injection implementation based on Abstract factory pattern
 */
@DependencyInjectionPrinciple
@CreationalPattern.AbstractFactory
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DiFactory {

    /**
     * @return Package singletonInstanceName of generated factory module.
     */
    @Nonnull String factoryPackageName() default "";

    /**
     * @return Class singletonInstanceName of default factory implementation module, to which annotated class is included.
     * Class singletonInstanceName of factory abstraction module is factoryClassName() + "A"
     */
    @Nonnull String factoryClassName() default "";

    /**
     * @return if true, default Factory implementation module is generated for annotated class.
     */
    boolean generateDefaultFactoryImplementation() default true;

    /**
     * @return if true, singleton codeblock is generated for annotated class.
     */
    boolean singleton() default false;

    /**
     * @return Names of singleton instance. Non-default value should be used,
     * if there are more than one singleton instance for annotated class.
     */
    @Nonnull String[] singletonInstanceNames() default {};

    /**
     * @return Suffix of annotated abstraction class singletonInstanceName.
     * If annotated abstraction class singletonInstanceName doesn't end with abstractionClassSuffix error will be raised.
     * If there is no abstraction class and just implementation class exists,
     * then abstractionClassSuffix should be assigned equal to implementationClassSuffix
     */
    @Nonnull String abstractionClassSuffix() default ""; /* "A" */

    /**
     * @return If implementationClassSuffix is "-",
     * method ofClassName return null instead of return new ClassName()
     */
    @Nonnull String implementationClassSuffix() default "";

    /**
     * Annotation to mark constructor, that has parameters, which have to be injected automatically with methods of***
     */
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.CONSTRUCTOR})
    public @interface Inject {
        /**
         * @return Names of singleton instance. Non-default value should be used,
         * if there are more than one singleton instance for annotated class.
         */
        @Nonnull String[] singletonInstanceNames() default {};
    }

}
