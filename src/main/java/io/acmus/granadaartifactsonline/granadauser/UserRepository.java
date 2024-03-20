package io.acmus.granadaartifactsonline.granadauser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<GranadaUser,Integer> {
}
