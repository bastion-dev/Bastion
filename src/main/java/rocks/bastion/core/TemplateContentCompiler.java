package rocks.bastion.core;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.MustacheException.Context;
import com.samskivert.mustache.Template;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An internal class which uses a templating engine (JMustache in this case) to provide templating functionality for Bastion.
 * The user supplies a Mustache template as an input to this class' constructor. They then execute {@link #addVariableAssignment(String, Object)}
 * and {@link #addAllVariableAssignments(Map)} to assign actual values to the variables declared in the template.
 * The {@link #getContent()} method then returns a fully resolved template with all variables resolved to their given values.
 * <p>
 * See the <a href="https://mustache.github.io/mustache.5.html">Mustache man page</a> for an explaination of how to write Mustache
 * templates that can be interpreted by this class.
 */
public class TemplateContentCompiler {

    private String template;
    private Template compiledTemplate;
    private Map<String, Object> variableAssignments;

    /**
     * Construct a new template compiler object for the given Mustache template source text. The variable delimeters for this
     * template are double opening/closing braces.
     *
     * @param template The Mustache template source text. Cannot be {@literal null}.
     */
    public TemplateContentCompiler(String template) {
        setTemplate(template);
        variableAssignments = new ConcurrentHashMap<>();
    }

    /**
     * Gets the currently set template source text. The template text is a Mustache template.
     *
     * @return The template source text.
     */
    public String getTemplate() {
        return template;
    }

    /**
     * Gets the currently set variable assignment for the template. Each key in the returned map is the name of the variable
     * to replace. The mapped value for each key is then the value which will be replaced for that particular variable in the
     * template.
     *
     * @return A non-{@literal null} assignment of variable names to values to use for the template
     */
    public Map<String, ?> getVariableAssignments() {
        return variableAssignments;
    }

    /**
     * Assign a new value to a particular variable name.
     *
     * @param variable   A non-{@literal null} variable name to assign a new value for.
     * @param assignment A non-{@literal null} value to assign to the given variable name.
     */
    public void addVariableAssignment(String variable, Object assignment) {
        Objects.requireNonNull(variable);
        Objects.requireNonNull(assignment);
        variableAssignments.put(variable, assignment);
    }

    /**
     * Assign an entire assignment of variables to their values. Calling this method is equivalent to calling the
     * {@link #addVariableAssignment(String, Object)} method for each entry in the supplied map.
     *
     * @param variableAssignments An entire map of variable assignments to use when resolving the template. Cannot be {@literal null}.
     */
    public void addAllVariableAssignments(Map<String, ?> variableAssignments) {
        Objects.requireNonNull(variableAssignments);
        this.variableAssignments.putAll(variableAssignments);
    }

    /**
     * Returns the fully resolved template. The variables in the template are resolved using the variable assignment given
     * using the {@link #addVariableAssignment(String, Object)} and {@link #addAllVariableAssignments(Map)} methods.
     *
     * @return A non-{@literal null} string resulting from resolving the template using the variable assignment.
     */
    public String getContent() {
        return resolveTemplate();
    }

    /**
     * Sets and compiles a new template written as a Mustache template.
     *
     * @param template The Mustache template source text. Cannot be {@literal null}.
     */
    private void setTemplate(String template) {
        Objects.requireNonNull(template);
        this.template = template;
        compiledTemplate = compile();
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
