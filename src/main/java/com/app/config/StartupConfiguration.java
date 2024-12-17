package com.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import com.app.repository.RoleRepository;
import com.app.repository.UserRepository;

import jakarta.transaction.Transactional;

//@Component
public class StartupConfiguration implements CommandLineRunner {

	@Autowired
	UserRepository appUserRepository;

	@Autowired
	RoleRepository appRoleRepository;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
//		Role role1 = Role.builder().name("DEL_USER").label("Supprimer un utilisateur").build();
//		Role role2 = Role.builder().name("ADD_USER").label("Ajouter un utilisateur").build();
//		Role role3 = Role.builder().name("LIST_USER").label("Lister les uutilisateurs").build();
//		User user1 = User.builder().email("test1@gmail.com").firstName("firstname1").lastName("lastname1")
//				.password("pass1").roles(Set.of(role1, role2, role3)).build();
//		appUserRepository.save(user1);
		/*
		 * AppRole role4 =
		 * AppRole.builder().code("DEL_USER2").label("Supprimer un utilisateur2").build(
		 * ); AppUser user2 =
		 * AppUser.builder().email("test2@gmail.com").firstName("firstname1").lastName(
		 * "lastname1") .password("pass1").build(); role4.setId(29l);
		 * user2.setRoles(Set.of(appRoleRepository.findById(29l).get()));
		 * appUserRepository.save(user2);
		 * 
		 * 
		 * appUserRepository.deleteById(24l);
		 */
	}

}
