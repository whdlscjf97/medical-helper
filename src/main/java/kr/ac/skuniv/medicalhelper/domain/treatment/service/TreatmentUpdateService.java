package kr.ac.skuniv.medicalhelper.domain.treatment.service;

import kr.ac.skuniv.medicalhelper.domain.member.exception.UnauthorizedUserException;
import kr.ac.skuniv.medicalhelper.domain.treatment.dto.TreatmentUpdateRequest;
import kr.ac.skuniv.medicalhelper.domain.treatment.entity.Treatment;
import kr.ac.skuniv.medicalhelper.domain.treatment.exception.TreatmentNotFoundException;
import kr.ac.skuniv.medicalhelper.domain.treatment.exception.TreatmentRequestInvalidException;
import kr.ac.skuniv.medicalhelper.domain.treatment.repository.TreatmentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TreatmentUpdateService {
    private TreatmentRepository treatmentRepository;

    public TreatmentUpdateService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    public void updateTreatment(TreatmentUpdateRequest treatmentUpdateRequest, String userId){
        Optional<TreatmentUpdateRequest> updateRequest = Optional.ofNullable(treatmentUpdateRequest);
        updateRequest.orElseThrow(TreatmentRequestInvalidException::new);

        Optional<Treatment> treatment = treatmentRepository.findById(treatmentUpdateRequest.getTno());
        treatment.orElseThrow(TreatmentNotFoundException::new);

        checkValidMember(treatment.get().getMember().getUserId(), userId);

        treatment.get().updateTreatment(treatmentUpdateRequest);

        treatmentRepository.save(treatment.get());
    }

    private void checkValidMember(String treatmentOwner, String updateTreatmentUser) {
        if(treatmentOwner.equals(updateTreatmentUser))
            return;
        throw new UnauthorizedUserException();
    }
}