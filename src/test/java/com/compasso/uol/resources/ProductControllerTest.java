package com.compasso.uol.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductControllerTest {
	@Autowired
	public MockMvc mockMvc;

	private static final String BASE_URI = "/api/products/";

	@Test
	public void test_Listar_Produtos_Com_Sucesso() throws Exception {
		mockMvc.perform(get(BASE_URI)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
    public void test_Filtrar_Produto_Por_Parametros_Q_MinPrice_MaxPrice() throws Exception {
		mockMvc.perform(get(BASE_URI + "search?min_price=1000&max_price=1500&q=nitro")).andExpect(status().isOk())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
    public void test_Filtrar_Produto_Por_Parametros_MinPrice_MaxPrice() throws Exception {
		mockMvc.perform(get(BASE_URI + "search?min_price=100&max_price=1350")).andExpect(status().isOk())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
    public void test_Filtrar_Produto_Pelo_Parametro_Q() throws Exception {
		mockMvc.perform(get(BASE_URI + "search?q=hot")).andExpect(status().isOk())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
    public void test_Filtrar_Produto_Por_Parametros_Q_MinPrice() throws Exception {
		mockMvc.perform(get(BASE_URI + "search?min_price=2000&q=iphone")).andExpect(status().isOk())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
    public void test_Filtrar_Produto_Por_Parametros_Q_MaxPrice() throws Exception {
		mockMvc.perform(get(BASE_URI + "search?max_price=2500&q=smart")).andExpect(status().isOk())
			   .andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void test_Buscar_Produto_Por_Id_Com_Sucesso() throws Exception {
		mockMvc.perform(get(BASE_URI + "{id}", "1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void test_Cadastrar_Produto_Com_Sucesso() throws Exception {
		String product = "{\"name\": \"Produto teste foo bar\", \"description\": \"Descrição do Produto foo bar\", \"price\": 198.30}";
		mockMvc.perform(post(BASE_URI).content((product)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void test_Editar_Produto_Com_Sucesso() throws Exception {
		String product = "{\"name\": \"Produto teste foo\", \"description\": \"Descrição do Produto foo\", \"price\": 298.30}";
		mockMvc.perform(put(BASE_URI + "{id}", "23").content((product)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void test_Excluir_Produto_Com_Sucesso() throws Exception {
		mockMvc.perform(delete(BASE_URI + "{id}", "11")).andExpect(status().isOk());
	}

	@Test
	public void test_Buscar_Produto_Inexistente_Por_Id() throws Exception {
		mockMvc.perform(get(BASE_URI + "{id}", "31")).andExpect(status().isNotFound())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void test_Cadastrar_Produto_Com_Entrada_De_Dados_Invalidos() throws Exception {
		String product = "{\"name\": \"Produto teste foo\", \"description\": \"Descrição do Produto foo\", \"price\": -20.90}";
		mockMvc.perform(post(BASE_URI).content((product)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void test_Editar_Produto_Com_Entrada_De_Dados_Invalidos() throws Exception {
		String product = "{\"name\": \"\", \"description\": \"Descrição do Produto foo\", \"price\": -20.90}";
		mockMvc.perform(put(BASE_URI + "{id}", "1").content((product)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void test_Excluir_Produto_Inexistente_Ou_Ja_Excluido() throws Exception {
		mockMvc.perform(delete(BASE_URI + "{id}", "350")).andExpect(status().isNotFound());
	}

}