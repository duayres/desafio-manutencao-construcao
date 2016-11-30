package com.duayres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.Membro;

@Repository
@Transactional
public interface IMembroRepository extends JpaRepository<Membro, Long> {

}
