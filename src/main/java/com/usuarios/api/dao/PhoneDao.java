package com.usuarios.api.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.usuarios.api.config.PhoneRepst;
import com.usuarios.api.dto.Phone;

@Service
public class PhoneDao {
	@Autowired
	private PhoneRepst phoneRepository;
	
	
	public Phone addPhone(Phone phon) {
		phoneRepository.save(phon);
		return phon;
	}
	
	public List<Phone> listPhone(Long id){
		Iterable<Phone> ph = phoneRepository.findAll();
		return (List<Phone>) ph;
		
	}

}
