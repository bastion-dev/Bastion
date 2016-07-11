package rocks.bastion.core.builder;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public interface ExecuteRequestBuilder<MODEL> {

    PostExecutionBuilder<? extends MODEL> call();

}
