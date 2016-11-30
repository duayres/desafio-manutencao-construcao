package com.duayres.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duayres.model.TipoDeEquipamento;

@Repository
@Transactional
public interface ITipoDeEquipamentoRepository extends JpaRepository<TipoDeEquipamento, Long> {

}
