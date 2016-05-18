package org.kpull.apitestsuites.core;

import com.google.common.base.Strings;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.kpull.apitestsuites.runner.ExecutionContext;

import java.util.Objects;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class GroovyScriptPostCallExecution implements PostCallExecution {

    private String groovyScript;

    public GroovyScriptPostCallExecution(String groovyScript) {
        Objects.requireNonNull(groovyScript);
        this.groovyScript = groovyScript;
    }

    @Override
    public void execute(ExecutionContext context, ApiEnvironment environment) {
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
