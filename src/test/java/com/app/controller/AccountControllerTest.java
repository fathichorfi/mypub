package com.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.app.controller.v1.RequestPath;
import com.app.dto.UserDto;
import com.app.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserService accountService;

	@Autowired
	ObjectMapper objectMapper;

	//@Test
	void findTest() throws Exception {
		UserDto appUserDto = UserDto.builder().id(1l).build();
		Mockito.when(accountService.findById(Mockito.anyLong())).thenReturn(appUserDto);
		mockMvc.perform(get(RequestPath.APPUSER + "/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	//@Test
	void createAppUserTest() throws Exception {
		UserDto appUserDto = UserDto.builder().id(1l).email("t@t.com").firstName("myname").lastName("mylastname")
				.password("test").username("username100").build();
		Mockito.when(accountService.create(Mockito.any(UserDto.class))).thenReturn(appUserDto);
		mockMvc.perform(post(RequestPath.APPUSER).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(appUserDto))).andExpect(status().isOk());
	}

}
