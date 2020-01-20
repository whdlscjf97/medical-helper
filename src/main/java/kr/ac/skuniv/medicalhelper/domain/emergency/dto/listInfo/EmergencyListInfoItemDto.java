package kr.ac.skuniv.medicalhelper.domain.emergency.dto.listInfo;


import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@ToString
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class EmergencyListInfoItemDto {
    private String dutyAddr;
    private String dutyEmcls;
    private String dutyName;
    private String dutyTel2;
    private String dutyTel3;
    private String hpid;
    private String phpid;
    private int rnum;
    private String wgs84Lat;
    private String wgs84Lon;
}
