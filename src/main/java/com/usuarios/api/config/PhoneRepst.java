package com.usuarios.api.config;

import org.springframework.data.repository.CrudRepository;
import com.usuarios.api.dto.Phone;

public interface PhoneRepst  extends CrudRepository<Phone, Long> {

}
