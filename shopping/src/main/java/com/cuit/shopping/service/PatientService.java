package com.cuit.shopping.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cuit.shopping.entity.Patient;
import com.cuit.shopping.mapper.PatientMapper;
import org.springframework.stereotype.Service;

/**
 * @author 沐辰
 */
@Service
public class PatientService extends ServiceImpl<PatientMapper, Patient> {
}
