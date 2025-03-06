package ms.core.persona.cliente.utils.impl;

import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class FiledsUtils {
    public static <T> void updateFieldIfPresent(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
