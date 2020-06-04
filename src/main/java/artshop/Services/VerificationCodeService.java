package artshop.Services;

import artshop.exception.CustomException;
import artshop.utils.Constants;

public interface VerificationCodeService {

     boolean verifyCode(String code, Constants.VerificationCodeMode verificationCodeMode) throws CustomException, Exception;

     String createCode(String email, Constants.VerificationCodeMode verificationCodeMode) throws CustomException;

     boolean deleteCode(String id);
}