package rocks.bastion.core.assertions;

import rocks.bastion.core.ApiHeader;
import rocks.bastion.core.ModelResponse;
import rocks.bastion.core.RawResponse;

import java.io.ByteArrayInputStream;
import java.util.Collections;

class TestModelResponse {

    /**
     * Prepare a model response object with a status code of HTTP 200 and the given JSON body for testing purposes.
     *
     * @param jsonContent
     * @return A model response object.
     */
    static ModelResponse<String> prepare(String jsonContent) {
        return new ModelResponse<>(new RawResponse(200, "OK", Collections.singletonList(new ApiHeader("Content-type", "application/json")), new ByteArrayInputStream(jsonContent.getBytes())),
                                   jsonContent);
    }

}
