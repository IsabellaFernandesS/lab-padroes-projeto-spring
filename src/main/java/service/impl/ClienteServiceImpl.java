package service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import model.Cliente;
import service.ClienteService;

public class ClienteServiceImpl implements ClienteService {

    // Buscar todos os clientes
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        // Buscar todos os clientes
        return clienteRepository.findAll();

        throw new UnsupportedOperationException("Unimplemented method 'buscarTodos'");
    }

    @Override
    public Cliente buscarPorId(Long id) {
        clienteRepository.findById(id);
        return cliente.get();
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }

    @Override
    public void inserir(Cliente cliente) {
        // verificar se o endereço do cliente ja existe(pelo cep)
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        // ver se o id existe
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if (clienteBd.isPresent()) {
            salvarClienteComCep(cliente);
        }
        throw new UnsupportedOperationException("Unimplemented method 'atualizar'");
    }

    private void salvarClienteComCep(Cliente cliente) {
        // verificar se o endereço do cliente ja existe(pelo cep)
        String cep = cliente.getEndereco().getCep();
        enderecoRepository.findById(cep).orElseGet(() -> {
            // caso não exista, integrar com o viacep e persistir o retorno
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setEndereco(endereco);
        // inserir o cliente
        clienteRepository.save(cliente);
    }

    @Override
    public void deletar(Long id) {
        // deletar cliente por ID
        clienteRepository.deleteById(id);
        throw new UnsupportedOperationException("Unimplemented method 'deletar'");
    }

}
