package org.kpull.bastion.junit;

import org.kpull.bastion.annotation.Variable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface BastionSuite {

    String name() default "";

    Variable[] environment() default { };

}
