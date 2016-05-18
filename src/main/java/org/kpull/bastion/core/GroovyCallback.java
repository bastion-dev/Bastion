package org.kpull.bastion.core;

import com.google.common.base.Strings;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.kpull.bastion.runner.ExecutionContext;

import java.util.Objects;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class GroovyCallback implements Callback {

    private String groovyScript;

    public GroovyCallback(String groovyScript) {
        Objects.requireNonNull(groovyScript);
        this.groovyScript = groovyScript;
    }

    @Override
    public void execute(int statusCode, ApiResponse response, ApiEnvironment environment, ExecutionContext context) {
        if (!Strings.isNullOrEmpty(groovyScript)) {
            Binding binding = new Binding();
            binding.setVariable("context", context);
            binding.setVariable("apiCall", context.getCall());
            binding.setVariable("apiRequest", context.getRequest());
            binding.setVariable("httpRequest", context.getHttpRequest());
            binding.setVariable("apiResponse", context.getCall().getResponse());
            binding.setVariable("httpResponse", context.getHttpResponse());
            binding.setVariable("jsonResponseBody", context.getJsonResponseBody());
            binding.setVariable("model", context.getResponseModel());
            binding.setVariable("environment", environment);
            GroovyShell groovy = new GroovyShell(binding);
            groovy.evaluate(groovyScript);
        }
    }
}
