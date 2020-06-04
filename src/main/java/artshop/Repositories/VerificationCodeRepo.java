package artshop.Repositories;

import artshop.Entities.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepo extends JpaRepository<VerificationCode,String> {

    public VerificationCode findByCode(String code);
}
