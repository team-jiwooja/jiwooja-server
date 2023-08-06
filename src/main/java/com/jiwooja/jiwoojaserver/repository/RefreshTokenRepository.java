package com.jiwooja.jiwoojaserver.repository;



import com.jiwooja.jiwoojaserver.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
