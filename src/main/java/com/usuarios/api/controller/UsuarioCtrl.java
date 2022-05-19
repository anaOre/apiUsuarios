package com.usuarios.api.controller;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.usuarios.api.dao.UsuarioDao;
import com.usuarios.api.dto.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@RequestMapping(value = "/Usuarios")
@CrossOrigin("*")
public class UsuarioCtrl {
	
	@Autowired
	private UsuarioDao userDao;
	
	private static String SECRET_KEY= "as123456";
	
	
	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public ResponseEntity<Object> listar(@RequestHeader("Authorization") String token) {
		List<Usuario> listaUsuarios = null;
		List<String> list = new ArrayList<String>();
		try {
			
			listaUsuarios = (List<Usuario>) userDao.listUsuario();			
			int num=0;
			if(listaUsuarios.size() > 0 ) {
				for(Usuario user : listaUsuarios) {
					if(user.getToken().equalsIgnoreCase(token)) {
						num = num+1;
					}
				}				
				if(num>0) {
					return new ResponseEntity<>(listaUsuarios, HttpStatus.OK);
				}else {
					 list.add("Error de Autorizacion token");
		                return new ResponseEntity<>(list, HttpStatus.UNAUTHORIZED);					
				}					
			}else {
				list.add("Ha Ocurrido error en obtener lista");
				return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
			}					

		}catch(Exception e)
		{
			list.add("Ha Ocurrido error en obtener lista");
			e.printStackTrace();
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping(value = "/buscar/{id}")
    public ResponseEntity<Object> find(@PathVariable Long id) {
		try {
			
			Optional<Usuario> user = userDao.BuscarUsuarioId(id);				
			if(user.get().getId() != null) {
				return new ResponseEntity<>(user, HttpStatus.OK);	
			}else {
				List<String> list = new ArrayList<String>();
                list.add("No se ha encontrado el usuario");
                return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
			}
		}catch(Exception e)
		{
			List<String> list = new ArrayList<String>();
			list.add("Ha Ocurrido error en obtener el usuario");
			e.printStackTrace();
			return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

	@PostMapping(value = "/guardar")
	public ResponseEntity<Usuario> save(@RequestBody Usuario user) {
	user.setearUsuarios();
	String token = UsuarioCtrl.createJWT(user.getToken(), user.getName(), "creacion token", 300000);
	user.setToken(token);
	Usuario obj = userDao.addUsuario(user);
	return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
	}
    @DeleteMapping(value = "/borrar/{id}")
    public String delete(@PathVariable Long id) {
    	try {
    		userDao.BorrarUsuarioId(id);
    		return "Borrado Exitosamente";
    	}catch(Exception e)
		{
			e.printStackTrace();
			return "Error al borrar el usuario";
		}        
    }
    
    public static String createJWT(String id, String issuer, String subject, long ttlMillis)
    {

	    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	
	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	
	    JwtBuilder builder = Jwts.builder().setId(id)
	    .setIssuedAt(now)
	    .setSubject(subject)
	    .setIssuer(issuer)
	    .signWith(signatureAlgorithm, signingKey);
	
	    if (ttlMillis > 0) {
	    long expMillis = nowMillis + ttlMillis;
	    Date exp = new Date(expMillis);
	    builder.setExpiration(exp);
	    }
	
	    return builder.compact();
	    }
    
    public static Claims decodeJWT(String jwt) {
    	Claims claims = Jwts.parser()
    	.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
    	.parseClaimsJws(jwt).getBody();
    	return claims;
    	}

}
