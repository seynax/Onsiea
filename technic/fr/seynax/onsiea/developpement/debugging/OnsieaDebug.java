package fr.seynax.onsiea.developpement.debugging;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.LOCAL_VARIABLE, ElementType.FIELD,
		ElementType.METHOD, ElementType.PACKAGE, ElementType.PARAMETER, ElementType.TYPE })

@Retention(RetentionPolicy.SOURCE)

public @interface OnsieaDebug
{
}
