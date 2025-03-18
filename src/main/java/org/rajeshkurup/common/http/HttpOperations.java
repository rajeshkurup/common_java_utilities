package org.rajeshkurup.common.http;

import java.util.Optional;

public interface HttpOperations {

    Optional<HttpOpsResponse> get();

    Optional<HttpOpsResponse> post();

    Optional<HttpOpsResponse> put();

    Optional<HttpOpsResponse> delete();

}
