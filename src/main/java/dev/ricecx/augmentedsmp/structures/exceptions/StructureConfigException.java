package dev.ricecx.augmentedsmp.structures.exceptions;

public class StructureConfigException extends RuntimeException {

    public StructureConfigException(String message) {
        super("Invalid structure config:" + message);
    }
}
