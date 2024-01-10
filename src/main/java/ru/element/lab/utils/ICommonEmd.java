package ru.element.lab.utils;

/**
 * Общее поведение для всех EMD.
 */
public interface ICommonEmd {
    String getId();

    byte[] getBlob();

    String getBucket();

    String getKey();
}
