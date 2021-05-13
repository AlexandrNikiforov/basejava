package ru.javawebinar.basejava.storage;

import java.io.File;

public enum SerializationType {
    FILE {
        public SerializationTechnology getSerializer(String directory) {
            File file = new File(directory);
            return new ObjectStreamStorage (file);
        }
    },
    PATH {
        public SerializationTechnology getSerializer(String directory) {
            return new ObjectStreamPathStorage(directory);
        }
    };

    abstract SerializationTechnology getSerializer(String directory);
}
