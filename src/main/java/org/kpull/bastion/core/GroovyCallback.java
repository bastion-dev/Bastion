package org.kpull.bastion.core;

import com.google.common.base.Strings;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;

import java.util.Objects;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class GroovyCallback implements Callback<Object> {

    private String groovyScript;

    public GroovyCallback(String groovyScript) {
        Objects.requireNonNull(groovyScript);
        this.groovyScript = groovyScript;
    }

//    @Override
//    public void execute(int statusCode, ExecutionContext context) {
//        if (!Strings.isNullOrEmpty(groovyScript)) {
//            Binding binding = new Binding();
//            binding.setVariable("context", context);
//            binding.setVariable("httpRequest", context.getHttpRequest());
//            binding.setVariable("httpResponse", context.getHttpResponse());
//            binding.setVariable("jsonResponseBody", context.getJsonResponseBody());
//            binding.setVariable("model", context.getResponseModel());
//            GroovyShell groovy = new GroovyShell(binding);
//            groovy.evaluate(groovyScript);
//        }
//    }

    @Override
    public void execute(int statusCode, Object model) {
        if (!Strings.isNullOrEmpty(groovyScript)) {
            // TODO: Fill this in
            Binding binding = new Binding();
            binding.setVariable("model", model);
            GroovyShell groovy = new GroovyShell(binding);
            groovy.evaluate(groovyScript);
        }
    }
}
