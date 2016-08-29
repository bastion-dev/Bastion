package rocks.bastion.core;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class TemplateContentCompilerTest {

    @Test
    public void compile_validVariable_returnsCompiledMessage() throws Exception {
        TemplateContentCompiler compiler = new TemplateContentCompiler("{ \"name\": \"{{ name }}\" }");
        compiler.addVariableAssignment("name", "john doe");
        String content = compiler.getContent();
        assertThat(content).isEqualTo("{ \"name\": \"john doe\" }");
    }

    @Test
    public void compile_validVariables_returnsCompiledMessage() throws Exception {
        TemplateContentCompiler compiler = new TemplateContentCompiler("{ \"name\": \"{{ name }}\", \"surname\":\"{{ surname }}\" }");
        HashMap<String, Object> variableAssignments = Maps.newHashMap();
        variableAssignments.put("name", "john");
        variableAssignments.put("surname", "doe");
        compiler.addAllVariableAssignments(variableAssignments);
        String content = compiler.getContent();
        assertThat(content).isEqualTo("{ \"name\": \"john\", \"surname\":\"doe\" }");
    }

    @Test(expected = TemplateCompilationException.class)
    public void compile_invalidVariable_throwsAnException() throws Exception {
        TemplateContentCompiler compiler = new TemplateContentCompiler("{ \"name\": \"{{ nom }}\" }");
        compiler.getContent();
    }

}