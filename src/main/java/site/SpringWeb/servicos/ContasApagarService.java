package site.SpringWeb.servicos;

import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;
import org.springframework.stereotype.Service;

import site.SpringWeb.modelos.ContasApagar;
import site.SpringWeb.repositorio.ContasApagarRepo;

@Service
public class ContasApagarService {

    private final ContasApagarRepo contasApagarRepo;

    public ContasApagarService(ContasApagarRepo contasApagarRepo) {
        this.contasApagarRepo = contasApagarRepo;
    }

    public Page<ContasApagar> buscarComPaginacao(int pagina, int tamanho) {
        return contasApagarRepo.findAll(PageRequest.of(pagina, tamanho));
    }

    // Métodos para operações relacionadas às contas a pagar

    public boolean existeContaPorId(int id) {
        return contasApagarRepo.existsById(id);
    }

    public List<ContasApagar> buscarTodos() {
        return (List<ContasApagar>) contasApagarRepo.findAll();
    }

    public Optional<ContasApagar> buscarPorId(int id) {
        return contasApagarRepo.findById(id);
    }

    @Transactional
    public void processarPagamento(Integer id, BigDecimal valorPagamento, LocalDate dataPagamento) {
        ContasApagar contaApagar = contasApagarRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        contaApagar.setValorPago(valorPagamento);
        contaApagar.setDataPagamento(dataPagamento);
        contasApagarRepo.save(contaApagar);
    }
}
