package com.springboot3.template.repository;

import com.springboot3.template.model.entity.Sample;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface SampleRepository extends JpaRepository<Sample, Long> {

}
