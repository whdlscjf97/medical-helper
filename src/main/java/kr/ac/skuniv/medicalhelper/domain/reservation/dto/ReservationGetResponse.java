package kr.ac.skuniv.medicalhelper.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.ac.skuniv.medicalhelper.domain.hospital.entity.Hospital;
import kr.ac.skuniv.medicalhelper.domain.reservation.entity.Reservation;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationGetResponse {
    private Long rno;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING, timezone="asia/Seoul")
    private LocalDateTime reserveDate;

    private String reserveTime;
    private String symptom;
    private Hospital hospital;

    @Builder
    public ReservationGetResponse(Long rno, LocalDateTime reserveDate, String reserveTime, String symptom, Hospital hospital) {
        this.rno = rno;
        this.reserveDate = reserveDate;
        this.reserveTime = reserveTime;
        this.symptom = symptom;
        this.hospital = hospital;
    }

    public static ReservationGetResponse entity2dto(Reservation reservation){
        return ReservationGetResponse.builder()
                .rno(reservation.getRno())
                .reserveDate(reservation.getReserveDate())
                .symptom(reservation.getSymptom())
                .hospital(reservation.getHospital())
                .build();
    }
}
