package org.rajeshkurup.common.b64;

import java.util.Optional;

public interface B64Operations {

    Optional<String> encode(String data);

    Optional<String> decode(String data);

}
