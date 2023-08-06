package com.jiwooja.jiwoojaserver.repository;



import com.jiwooja.jiwoojaserver.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
