package vlfsoft.common.annotations.di;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import vlfsoft.common.annotations.design.principles.SoftwareDesign;

/**
 * See <a href="https://en.wikipedia.org/wiki/Inversion_of_control">Inversion of control</a>
 */
@SoftwareDesign
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.TYPE})
public @interface InversionOfControlPrinciple {
}
