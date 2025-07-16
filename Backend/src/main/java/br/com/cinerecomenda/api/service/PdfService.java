package br.com.cinerecomenda.api.service;

import br.com.cinerecomenda.api.model.Avaliacao;
import br.com.cinerecomenda.api.model.ListaUsuario;
import br.com.cinerecomenda.api.model.Usuario;
import br.com.cinerecomenda.api.repository.AvaliacaoRepository; // <-- NOVA IMPORTAÇÃO
import br.com.cinerecomenda.api.repository.UsuarioRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PdfService {

    @Autowired
    private ListaUsuarioService listaUsuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AvaliacaoRepository avaliacaoRepository; // <-- NOVA DEPENDÊNCIA

    public byte[] gerarPdfListaUsuario(Long idUsuario) throws IOException {
        // 1. Busca os dados principais
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
        List<ListaUsuario> listaDoUsuario = listaUsuarioService.buscarListaDoUsuario(idUsuario);

        // 2. Busca todas as avaliações feitas por este usuário e as mapeia por ID do filme para fácil acesso
        List<Avaliacao> todasAsAvaliacoesDoUsuario = avaliacaoRepository.findByUsuarioIdUsuario(idUsuario);
        Map<Long, Avaliacao> mapaDeAvaliacoes = todasAsAvaliacoesDoUsuario.stream()
                .collect(Collectors.toMap(a -> a.getFilme().getIdFilme(), a -> a));

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                // --- CABEÇALHO DO PDF ---
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Relatório de Filmes de: " + usuario.getNome());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 700);

                if (listaDoUsuario.isEmpty()) {
                    contentStream.showText("Este usuário não possui filmes na lista.");
                } else {
                    // 3. Itera sobre cada item da lista do usuário para criar o conteúdo
                    for (ListaUsuario item : listaDoUsuario) {
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream.showText("Filme: " + item.getFilme().getNome());
                        contentStream.newLine();

                        contentStream.setFont(PDType1Font.HELVETICA, 10);
                        contentStream.showText("- Estado na sua lista: " + item.getEstado());
                        contentStream.newLine();

                        // --- ADICIONANDO A NOTA MÉDIA E A AVALIAÇÃO PESSOAL ---
                        // (Aqui assumimos que a nota média viria do serviço, vamos simplificar por agora)
                        // Em um passo futuro, o FilmeService poderia ter um método getNotaMedia(idFilme)

                        // Verifica se o usuário já avaliou este filme
                        Avaliacao minhaAvaliacao = mapaDeAvaliacoes.get(item.getFilme().getIdFilme());
                        if (minhaAvaliacao != null) {
                            contentStream.showText("- Sua Avaliação: " + minhaAvaliacao.getNota() + "/10");
                            contentStream.newLine();
                            contentStream.showText("- Seu Comentário: " + minhaAvaliacao.getComentario());
                            contentStream.newLine();
                        } else {
                            contentStream.showText("- Sua Avaliação: (Você ainda não avaliou este filme)");
                            contentStream.newLine();
                        }

                        // Adiciona um espaço entre os filmes
                        contentStream.newLine();
                    }
                }

                contentStream.endText();
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
    }
}