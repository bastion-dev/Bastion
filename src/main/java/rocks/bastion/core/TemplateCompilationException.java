package rocks.bastion.core;

import java.util.Map;

/**
 * Thrown when the {@link TemplateContentCompiler} could not compile the specified template. The actual error thrown
 * by the underlying template library is typically set as the cause of this exception.
 */
public class TemplateCompilationException extends RuntimeException {

    private String templateSource;
    private Map<String, ?> variableAssignment;

    public TemplateCompilationException(String templateSource, Map<String, ?> variableAssignment) {
        this("An error occurred while compiling a template", templateSource, variableAssignment);
    }

    public TemplateCompilationException(String message, String templateSource, Map<String, ?> variableAssignment) {
        super(message);
        this.templateSource = templateSource;
        this.variableAssignment = variableAssignment;
    }

    public TemplateCompilationException(String message, Throwable cause, String templateSource, Map<String, ?> variableAssignment) {
        super(message, cause);
        this.templateSource = templateSource;
        this.variableAssignment = variableAssignment;
    }

    public TemplateCompilationException(Throwable cause, String templateSource, Map<String, ?> variableAssignment) {
        this("An error occurred while compiling a template", cause, templateSource, variableAssignment);
    }

    public String getTemplateSource() {
        return templateSource;
    }

    public Map<String, ?> getVariableAssignment() {
        return variableAssignment;
    }
}
