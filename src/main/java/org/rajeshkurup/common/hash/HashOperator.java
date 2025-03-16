package org.rajeshkurup.common.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import lombok.NonNull;

public class HashOperator implements Hashable {

    @Override
    public Optional<String> hash(@NonNull final String data, @NonNull final HashType hashType) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(hashType.getValue().orElseThrow());
            byte[] digest = messageDigest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte dig : digest) {
                String hex = Integer.toHexString(0xff & dig);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return Optional.ofNullable(hexString.toString());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
