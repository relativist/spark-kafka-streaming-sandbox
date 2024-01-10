package ru.element.lab.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ru.element.lab.oms.client.RawOms;
import ru.element.lab.utils.ICommonEmd;

import java.io.Serializable;

/**
 * Класс {@link RawOms} c полями String вместо OffsetDateTime и UploadSource.
 * OffsetDateTime содержит ZoneOffset, Spark не допускает работу с бесконечными циклами, поэтому он не может работать с ZoneOffset.
 * Также Spark не умеет работать с Enum, не может превращать его в String type.
 */
@SuperBuilder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RawOmsAsString implements ICommonEmd, Serializable {
    private String id;
    private byte[] blob;
    private String emdKind;
    private String version;
    private String regionId;
    private String fileName;
    private String createdTime;
    private String loadProcessId;
    private String fileProcessId;
    private String emdSource;
    private String correlationId;
    private String bucket;
    private String key;
    private String docCd;
    private String docNum;
    private String docDt;
    private String period;
}
