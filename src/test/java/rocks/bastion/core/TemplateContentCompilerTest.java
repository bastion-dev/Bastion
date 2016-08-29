package rocks.bastion.core;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TemplateContentCompilerTest {

    @Test
    public void compile_validVariable_returnsCompiledMessage() throws Exception {
        TemplateContentCompiler compiler = new TemplateContentCompiler("{ \"name\": \"{{ name }}\" }");
        compiler.addVariableAssignment("name", "john doe");
        String content = compiler.getContent();
        assertThat(content).isEqualTo("{ \"name\": \"john doe\" }");
    }

    @Test(expected = TemplateCompilationException.class)
    public void compile_invalidVariable_throwsAnException() throws Exception {
        TemplateContentCompiler compiler = new TemplateContentCompiler("{ \"name\": \"{{ nom }}\" }");
        compiler.getContent();
    }

    @Test
    public void setTemplate_returnsNewCompiledMessage() throws Exception {
        TemplateContentCompiler compiler = new TemplateContentCompiler("{ \"name\": \"{{ name }}\" }");
        compiler.addVariableAssignment("name", "john doe");
        compiler.setTemplate("{ \"name1\": \"{{ name }}\" }");
        String content = compiler.getContent();
        assertThat(content).isEqualTo("{ \"name1\": \"john doe\" }");
    }
}