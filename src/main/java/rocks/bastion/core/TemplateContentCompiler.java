package rocks.bastion.core;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class TemplateContentCompiler {

    private String template;
    private Template compiledTemplate;
    private Map<String, Object> variableAssignments;

    public TemplateContentCompiler(String template) {
        this.template = template;
        compiledTemplate = getCompiler().compile(this.template);
        variableAssignments = new ConcurrentHashMap<>();
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        Objects.requireNonNull(template);
        this.template = template;
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
        return compiledTemplate.execute(variableAssignments);
    }

    private static Mustache.Compiler getCompiler() {
        Mustache.Compiler compiler = Mustache.compiler();
        compiler.escapeHTML(false);
        compiler.withDelims("{{ }}");
        return compiler;
    }

}
