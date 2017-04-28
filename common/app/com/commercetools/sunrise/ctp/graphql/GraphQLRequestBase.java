package com.commercetools.sunrise.ctp.graphql;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.client.HttpRequestIntent;
import io.sphere.sdk.http.HttpMethod;
import io.sphere.sdk.http.HttpResponse;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public abstract class GraphQLRequestBase<T> extends Base implements GraphQLRequest<T> {

    private final String fieldName;
    private TypeReference<T> typeReference;
    private final String query;
    @Nullable
    private final JsonNode variables;

    protected GraphQLRequestBase(final String fieldName, final TypeReference<T> typeReference, final String query, @Nullable final JsonNode variables) {
        this.fieldName = fieldName;
        this.typeReference = typeReference;
        this.query = query;
        this.variables = variables;
    }

    @Nullable
    @Override
    public T deserialize(final HttpResponse httpResponse) {
        final JsonNode rootJsonNode = SphereJsonUtils.parse(httpResponse.getResponseBody());
        if (rootJsonNode.has("data")) {
            final JsonNode data = rootJsonNode.get("data");
            if (data.has(fieldName)) {
                return SphereJsonUtils.readObject(data.get(fieldName), typeReference);
            } else {
                throw new GraphQLException("No field " + fieldName + " in response", httpResponse);
            }
        } else {
            throw new GraphQLException("No data found in response", httpResponse);
        }
    }

    @Override
    public HttpRequestIntent httpRequestIntent() {
        final String variablesPart = variables == null ? null : String.format("\"variables\": %s", variables);
        final String queryPart = String.format("\"query\": \"%s\"", query.replace("\n", "\\n").replace("\"", "\\\""));
        final String body = Stream.of(queryPart, variablesPart)
                .filter(Objects::nonNull)
                .collect(joining(",", "{", "}"));
        return HttpRequestIntent.of(HttpMethod.POST, "/graphql", body);
    }

    public String getQuery() {
        return query;
    }

    @Nullable
    public JsonNode getVariables() {
        return variables;
    }
}
