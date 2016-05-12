package org.kpull.apitestsuites.factory;

import org.apache.commons.lang.StringUtils;
import org.kpull.apitestsuites.core.*;
import org.raml.model.Action;
import org.raml.model.ActionType;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.parser.loader.ResourceLoader;
import org.raml.parser.visitor.RamlDocumentBuilder;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class RamlApiSuiteFactory {

    private final String ramlFileLocation;

    private final Raml ramlModel;

    public RamlApiSuiteFactory(ResourceLoader loader, String ramlFileLocation) {
        Objects.requireNonNull(ramlFileLocation);
        this.ramlFileLocation = ramlFileLocation;
        ramlModel = new RamlDocumentBuilder(loader).build(ramlFileLocation);
    }

    public ApiSuite generate() {
        ApiEnvironment environment = new ApiEnvironment();
        environment.put("version", ramlModel.getVersion());
        environment.put("baseUri", ramlModel.getBaseUri().replaceAll("\\{.+?\\}", "{$0}"));

        ramlModel.getBaseUriParameters().entrySet().stream().forEach(baseUriParameter -> {
            String defaultValue = StringUtils.defaultString(baseUriParameter.getValue().getDefaultValue());
            environment.put(baseUriParameter.getKey(), defaultValue.replaceAll("\\{.+?\\}", "{$0}"));
        });

        LinkedList<ApiCall> apiCalls = new LinkedList<>();
        ramlModel.getResources().entrySet().stream().forEach(resource -> {
            Resource resourceContent = resource.getValue();
            resourceContent.getActions().entrySet().forEach(action -> {
                ActionType actionType = action.getKey();
                Action actionContent = action.getValue();
                String name = actionType.name() + " " + resourceContent.getRelativeUri();
                ApiCall apiCall = new ApiCall(name, StringUtils.defaultString(actionContent.getDescription()), new ApiRequest(actionType.name(), resourceContent.getUri(), Collections.emptyList(), "application/json", "", Collections.emptyList()), new ApiResponse(Collections.emptyList(), 0, "", ""), null, "");
                apiCalls.add(apiCall);
            });
        });

        return new ApiSuite("Api Suite", environment, apiCalls);
    }

}
