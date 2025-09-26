package meuprojeto.principal;

import meuprojeto.dao.ProdutoDAO;
import meuprojeto.model.Produto;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;

public class Main {
public static void main(String[] args) {
        
        // Define a porta do servidor
        port(4567);

        // Define a pasta onde estão os arquivos estáticos (HTML, CSS, JS)
        staticFiles.location("/public");

        // Rota para a página principal (listar produtos)
        get("/", (request, response) -> {
            ProdutoDAO dao = new ProdutoDAO();
            try {
                Map<String, Object> model = new HashMap<>();
                model.put("produtos", dao.listar());
                return new ModelAndView(model, "index.ftl");
            } finally {
                dao.fecharConexao();
            }
        }, new FreeMarkerEngine());

        // Rota para processar o formulário de cadastro
        post("/produtos", (request, response) -> {
            String nome = request.queryParams("nome");
            String descricao = request.queryParams("descricao");
            double preco = Double.parseDouble(request.queryParams("preco"));

            Produto produto = new Produto(nome, descricao, preco);
            ProdutoDAO dao = new ProdutoDAO();

            try {
                dao.inserir(produto);
            } finally {
                dao.fecharConexao();
            }

            response.redirect("/");
            return "";
        });

    }

}
