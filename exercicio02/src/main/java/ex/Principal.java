package ex;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        exDAO dao = new exDAO();
        int opcao = -1;

        while (opcao != 5) {
            System.out.println("----- MENU CRUD DE LIVROS -----");
            System.out.println("1. Inserir");
            System.out.println("2. Listar");
            System.out.println("3. Atualizar");
            System.out.println("4. Excluir");
            System.out.println("5. Sair");
            System.out.println("-----------------------------");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha

            switch (opcao) {
                case 1:
                	System.out.print("Título: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Autor: ");
                    String autor = scanner.nextLine();
                    System.out.print("Ano de Publicação: ");
                    int ano = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Disponível (true/false): ");
                    boolean disponivel = scanner.nextBoolean();
                    scanner.nextLine();

                    Livro novoLivro = new Livro();
                    novoLivro.setTitulo(titulo);
                    novoLivro.setAutor(autor);
                    novoLivro.setAno_publicacao(ano);
                    novoLivro.setDisponivel(disponivel);

                    dao.inserir(novoLivro);
                    break;
                case 2:
                	for (Livro livro : dao.listar()) {
                        System.out.println("ID: " + livro.getId() + 
                                           " | Título: " + livro.getTitulo() + 
                                           " | Autor: " + livro.getAutor() + 
                                           " | Ano: " + livro.getAno_publicacao());
                    }
                    break;
                case 3:
                	System.out.print("ID do livro para atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine();

                    Livro livroAtualizar = new Livro();
                    livroAtualizar.setId(idAtualizar);

                    System.out.print("Novo Título: ");
                    livroAtualizar.setTitulo(scanner.nextLine());
                    System.out.print("Novo Autor: ");
                    livroAtualizar.setAutor(scanner.nextLine());
                    System.out.print("Novo Ano: ");
                    livroAtualizar.setAno_publicacao(scanner.nextInt());
                    scanner.nextLine();
                    System.out.print("Disponível (true/false): ");
                    livroAtualizar.setDisponivel(scanner.nextBoolean());
                    scanner.nextLine();

                    dao.atualizar(livroAtualizar);
                    break;
                case 4:
                	System.out.print("ID do livro para excluir: ");
                    int idExcluir = scanner.nextInt();
                    scanner.nextLine();

                    dao.excluir(idExcluir);
                    break;
                case 5:
                    System.out.println("Saindo do programa.");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }

        scanner.close();
    }
}