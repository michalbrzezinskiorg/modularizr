package com.decentralizer.spreadr.modules.appconfig.postgres;

import com.decentralizer.spreadr.modules.appconfig.postgres.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
@Transactional
interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Page<UserEntity> getByName(String username, Pageable pageable);

    List<UserEntity> findAll();

    Collection<UserEntity> getBySurname(String surname);

    UserEntity getByLoginAndPasswordEncrypted(String login, String password);

    UserEntity getByLogin(String login);

    UserEntity getById(int user_id);

}
