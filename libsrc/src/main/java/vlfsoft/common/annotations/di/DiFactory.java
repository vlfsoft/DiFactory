package vlfsoft.common.annotations.di;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import vlfsoft.common.annotations.design.patterns.gof.CreationalPattern;

/**
 * Annotation for Dependency injection implementation based on Abstract factory pattern
 */
@DependencyInjectionPrinciple
@CreationalPattern.AbstractFactory
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface DiFactory {

    /**
     * @return if true, default Factory implementation module is generated for annotated class.
     */
    boolean value() default true;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.METHOD})
    @interface Component {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
    @interface AbstractionTypeSuffix {

        /**
         * @return Suffix (abstractionClassSuffix) of annotated abstraction type(interface/class) name (classNameA).
         * If classNameA doesn't end with abstractionClassSuffix error will be raised.
         * if there is AbstractionTypeSuffix and value() != "", then names of all generated methods are based on
         * classNameA.substring(0, classNameA.length() - abstractionClassSuffix.length()).
         * if there is no AbstractionTypeSuffix or value() == "", then names of all generated methods are based on classNameA itself.
         * If ImplementationType.value() != null and !ImplementationType.value().isEmpty(),
         * then ImplementationType.value() is used to generate "new %s".
         * If ImplementationType.value() == null or ImplementationType.value().isEmpty(),
         * then classNameA.substring(0, classNameA.length() - abstractionClassSuffix.length()) is used to generate "new %s"
         */
        @Nonnull String value() default "A";

    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.METHOD})
    @interface ImplementationType {

        /**
         * @return Name (implementationClassName) of ImplementationType which extends AbstractionTypeSuffix.
         * If it is "", method ofClassName return null instead of return new implementationClassName()
         */
        @Nonnull String value() default "";

    }

    /**
     * if method in the interface-container is annotated,
     * singleton codeblock is generated for type(class/interface) of method parameter.
     */
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
    @interface Singleton {

        /**
         * @return Unique name suffixes of singleton instance. Non-default value should be used,
         * if there are more than one singleton instance for type(class/interface).
         */
        @Nonnull String[] value() default {};

        /**
         * Annotation to mark constructor parameters, which have to be injected automatically with methods of***, having SingletonNameSuffix != ""
         */
        @Documented
        @Retention(RetentionPolicy.SOURCE)
        @Target({ElementType.PARAMETER})
        @interface NameSuffix {
            /**
             * @return Name suffix of singleton instance. Non-default value should be used,
             * if there are more than one singleton instance for type(class/interface).
             */
            @Nonnull String value() default "";

        }

    }

    /**
     * Annotation to mark constructor, that has parameters, which have to be injected automatically with methods of***
     */
    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.CONSTRUCTOR})
    @interface ConstructorInject {
    }

}
