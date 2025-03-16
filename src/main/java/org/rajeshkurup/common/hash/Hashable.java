package org.rajeshkurup.common.hash;

import java.util.Optional;

import lombok.NonNull;

public interface Hashable {

    public static enum HashType {
        MD5("MD5"), 
        SHA1("SHA1"), 
        SHA256("SHA-256"),
        SHA384("SHA-384"),
        SHA512("SHA-512");
        
        private final String value;

        private HashType(@NonNull final String value) {
            this.value = value;
        }

        public Optional<String> getValue() {
            return Optional.ofNullable(this.value);
        }
    }

    Optional<String> hash(String data, HashType hashType);

}
