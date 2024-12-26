package com.springboot3.template.service;

import com.springboot3.template.exception.NotFoundException;
import com.springboot3.template.model.entity.Sample;
import com.springboot3.template.model.req.SampleReq;
import com.springboot3.template.model.res.SampleRes;
import com.springboot3.template.repository.SampleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleRepository sampleRepository;

    public List<SampleRes> getAllSample() {

        List<Sample> sampleList = sampleRepository.findAll();
        List<SampleRes> sampleResList = sampleList.stream().map(m -> {
            return new SampleRes(m.getId(),m.getName(),m.getMobilePhone());
        }).collect(Collectors.toList());
        return sampleResList;
    }

    public SampleRes getSampleById(Long id) {
        Sample sample = sampleRepository.findById(id).orElseThrow(() -> new NotFoundException("sample not found"));
        return new SampleRes(sample.getId(),sample.getName(),sample.getMobilePhone());
    }


    @Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
    public Sample saveSample(SampleReq sampleReq) {
        Sample sample = Sample.builder().name(sampleReq.name()).mobilePhone(sampleReq.mobilePhone()).build();
        return sampleRepository.save(sample);
    }

    @Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
    public Sample editSample(SampleReq sampleReq) {
        Sample sample = sampleRepository.findById(sampleReq.id()).orElseThrow(() -> new NotFoundException("sample not found"));
        sample = Sample.builder().id(sampleReq.id()).name(sampleReq.name()).mobilePhone(sampleReq.mobilePhone()).build();
        return sampleRepository.save(sample);
    }

    @Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
    public Sample deleteByIdSample(Long id) {
        Sample sample = sampleRepository.findById(id).orElseThrow(() -> new NotFoundException("sample not found"));
        sampleRepository.deleteById(id);
        return sample;
    }

}
