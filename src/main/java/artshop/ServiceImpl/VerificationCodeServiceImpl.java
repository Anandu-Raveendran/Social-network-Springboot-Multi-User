package artshop.ServiceImpl;

import artshop.Entities.User;
import artshop.Entities.VerificationCode;
import artshop.Repositories.VerificationCodeRepo;
import artshop.Services.UserService;
import artshop.Services.VerificationCodeService;
import artshop.exception.CustomException;
import artshop.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    VerificationCodeRepo verificationCodeRepo;

    @Autowired
    UserService userService;

    @Override
    public boolean verifyCode(String code, Constants.VerificationCodeMode verificationCodeMode) throws CustomException, Exception {

        VerificationCode verificationCode = verificationCodeRepo.findByCode(code);
        if (verificationCode == null) {
            throw new CustomException("Verification code not found");
        }
        long expiryDate = verificationCode.getCreatedOn() + Constants.MONTH_IN_MILLIS * Constants.VERIFICATION_CODE_VALIDITY_PERIOD_IN_MONTHS;
        //    When code is same and created on is lesser than today
        if (verificationCode.getCreatedOn() < expiryDate) {
            if (verificationCode.getCode().equals(code)) {
                deleteCode(verificationCode.getvCodeId());
                    if (verificationCodeMode == Constants.VerificationCodeMode.EMAIL)
                        userService.emailVerified(verificationCode.getUserId());
                    else if (verificationCodeMode == Constants.VerificationCodeMode.PHONE)
                        userService.phoneVerified(verificationCode.getUserId());
                return true;
            } else {
                return false;
            }
        } else {
            throw new CustomException("Verification code expired");
        }
    }

    @Override
    public String createCode(String user_id, Constants.VerificationCodeMode verificationCodeMode) {

        VerificationCode verificationCode = new VerificationCode(user_id, UUID.randomUUID().toString(), verificationCodeMode.getValue(), System.currentTimeMillis());
        verificationCode = saveVerificationCOde(verificationCode);
        return verificationCode.getCode();
    }
    public VerificationCode saveVerificationCOde(VerificationCode verificationCode) {

        if (verificationCode.getvCodeId() == null) {
            VerificationCode Old;
            do {
                verificationCode.setvCodeId(String.valueOf(UUID.randomUUID()));
                Old = verificationCodeRepo.findOne(verificationCode.getvCodeId());
                // Then the UUID as user_id is already in use create a new UUID

            } while (Old != null);
        }
        return verificationCodeRepo.save(verificationCode);
    }

    @Override
    public boolean deleteCode(String id) {
        verificationCodeRepo.delete(id);
        return true;
    }
}
