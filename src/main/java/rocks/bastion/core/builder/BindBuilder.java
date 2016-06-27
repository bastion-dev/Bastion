package rocks.bastion.core.builder;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public interface BindBuilder {

    <MODEL> AssertionsBuilder<? extends MODEL> bind(Class<MODEL> modelType);

}
