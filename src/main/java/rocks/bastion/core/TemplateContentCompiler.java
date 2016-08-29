package rocks.bastion.core;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.MustacheException.Context;
import com.samskivert.mustache.Template;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class TemplateContentCompiler {

    private String template;
    private Template compiledTemplate;
    private Map<String, Object> variableAssignments;

    public TemplateContentCompiler(String template) {
        setTemplate(template);
        variableAssignments = new ConcurrentHashMap<>();
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        Objects.requireNonNull(template);
        this.template = template;
        compiledTemplate = compile();
    }

    public Map<String, ?> getVariableAssignments() {
        return variableAssignments;
    }

    public void addVariableAssignment(String variable, Object assignment) {
        Objects.requireNonNull(variable);
        Objects.requireNonNull(assignment);
        variableAssignments.put(variable, assignment);
    }

    public void addAllVariableAssignments(Map<String, ?> variableAssignments) {
        Objects.requireNonNull(variableAssignments);
        this.variableAssignments.putAll(variableAssignments);
    }

    public String getContent() {
        return resolveTemplate();
    }

    private static Mustache.Compiler getCompiler() {
        Mustache.Compiler compiler = Mustache.compiler();
        compiler.escapeHTML(false);
        compiler.withDelims("{{ }}");
        return compiler;
    }

    private String resolveTemplate() {
        try {
            return compiledTemplate.execute(variableAssignments);
        } catch (Context compilationException) {
            throw new TemplateCompilationException(compilationException, template, variableAssignments);
        }
    }

    private Template compile() throws TemplateCompilationException {
        return getCompiler().compile(template);
    }

}
