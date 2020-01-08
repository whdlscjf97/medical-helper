package kr.ac.skuniv.medicalhelper.domain.reservation.service;

import kr.ac.skuniv.medicalhelper.domain.member.entity.Member;
import kr.ac.skuniv.medicalhelper.domain.member.exception.MemberNotFoundException;
import kr.ac.skuniv.medicalhelper.domain.member.repository.MemberRepository;
import kr.ac.skuniv.medicalhelper.domain.reservation.dto.ReservationGetResponse;
import kr.ac.skuniv.medicalhelper.domain.reservation.entity.Reservation;
import kr.ac.skuniv.medicalhelper.domain.reservation.exception.ReservationNotFoundException;
import kr.ac.skuniv.medicalhelper.domain.reservation.repository.ReservationRepository;
import kr.ac.skuniv.medicalhelper.global.jwt.JwtService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationGetService {
    private ReservationRepository reservationRepository;
    private MemberRepository memberRepository;
    private JwtService jwtService;

    public ReservationGetService(ReservationRepository reservationRepository, MemberRepository memberRepository, JwtService jwtService) {
        this.reservationRepository = reservationRepository;
        this.memberRepository = memberRepository;
        this.jwtService = jwtService;
    }

    public List<ReservationGetResponse> getAllReservations(String token) {

        String userId = jwtService.findUserIdByJwt(token);

        Optional<Member> member = Optional.ofNullable(memberRepository.findByUserId(userId).orElseThrow(MemberNotFoundException::new));

        List<Reservation> reservationList = reservationRepository.findAllByMember(member.get());

        if (reservationList.isEmpty())
            throw new ReservationNotFoundException();

        return reservationList.stream()
                .map(ReservationGetResponse::entity2dto)
                .collect(Collectors.toList());
    }


    public ReservationGetResponse getReservation(Long rno, String token) {
        String userId = jwtService.findUserIdByJwt(token);

        if(memberRepository.existsById(userId)){
            Optional<Reservation> reservation = Optional.ofNullable(reservationRepository.findById(rno).orElseThrow(ReservationNotFoundException::new));

            checkMember(reservation.get(), userId);

            return ReservationGetResponse.entity2dto(reservation.get());
        }
        throw new MemberNotFoundException();

    }

    private void checkMember(Reservation reservation, String requestUser) {
        if(reservation.getMember().getUserId().equals(requestUser))
            return;
        throw new ReservationNotFoundException();

    }
}
