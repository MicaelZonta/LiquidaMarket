package org.market.servlets.produtoComercializado;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.market.entidades.ProdutoComercializado;
import org.market.negocios.EstabelecimentoNegocios;
import org.market.negocios.ProdutoComercializadoNegocios;
import org.market.servlets.estabelecimento.SalvarEstabelecimentoServlet;

@WebServlet("/RecuperarProdutosEstabelecimentoServlet")
public class RecuperarProdutosEstabelecimentoServlet extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(SalvarEstabelecimentoServlet.class);
	private static final long serialVersionUID = -8317387537061371194L;
	ProdutoComercializadoNegocios produtoComercializadoNegocios = new ProdutoComercializadoNegocios();
	EstabelecimentoNegocios estabelecimentoNegocios = new EstabelecimentoNegocios();

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setContentType("application/json");
		try {

			LOGGER.info("Request /RecuperarProdutosEstabelecimentoServlet");
			HttpSession sessao = req.getSession();
			LOGGER.info("Session idEstabelecimento :" + sessao.getAttribute("idUsuario"));
			long idUsuario = (long) sessao.getAttribute("idUsuario");

			String json = recuperarTodosProdutosComercializado(
					estabelecimentoNegocios.recuperarEstabelecimentoPorIdUsuario(idUsuario).getIdEstabelecimento());
			LOGGER.info("Request /RecuperarProdutosEstabelecimentoServlet /Json :" + json);

			PrintWriter out = resp.getWriter();
			out.print(json);
			out.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private String recuperarTodosProdutosComercializado(long idEstabelecimento) throws JsonProcessingException {

		List<ProdutoComercializado> produtosComercializados = produtoComercializadoNegocios
				.recuperarTodosProdutosComercializado(idEstabelecimento);
		try {
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(produtosComercializados);
			return json;
		} catch (Exception e) {
			return null;
		}

	}

}
