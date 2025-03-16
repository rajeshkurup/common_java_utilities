package org.rajeshkurup.common.b64;

import java.util.Base64;
import java.util.Optional;

import lombok.NonNull;

public class B64Operator implements B64Operations {

    @Override
    public Optional<String> encode(@NonNull final String data) {
        return Optional.ofNullable(Base64.getEncoder().encodeToString(data.getBytes()));
    }

    @Override
    public Optional<String> decode(@NonNull final String data) {
        try{
            return Optional.ofNullable(Base64.getDecoder().decode(data)).map(String::new);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
