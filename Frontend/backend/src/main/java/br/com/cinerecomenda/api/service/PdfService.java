package br.com.cinerecomenda.api.service;

import br.com.cinerecomenda.api.model.ListaUsuario;
import br.com.cinerecomenda.api.model.Usuario; // Importe a entidade Usuario
import br.com.cinerecomenda.api.repository.UsuarioRepository; // Importe o repositório de Usuario
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private ListaUsuarioService listaUsuarioService;


    @Autowired
    private UsuarioRepository usuarioRepository;

    public byte[] gerarPdfListaUsuario(Long idUsuario) throws IOException {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado para gerar PDF!"));


        List<ListaUsuario> listaDoUsuario = listaUsuarioService.buscarListaDoUsuario(idUsuario);

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);


                contentStream.showText("Relatório de Filmes de: " + usuario.getNome());
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(50, 700);

                if (listaDoUsuario.isEmpty()) {
                    contentStream.showText("Este usuário não possui filmes na lista.");
                } else {
                    for (ListaUsuario item : listaDoUsuario) {
                        String linha = "Filme: " + item.getFilme().getNome() + " | Estado: " + item.getEstado();
                        contentStream.showText(linha);
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