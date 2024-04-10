package br.com.demo.sfclient.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import br.com.demo.sfclient.configuration.EnableSfClientConfig;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(EnableSfClientConfig.class)
public @interface EnableSfClientLibrary {
}
