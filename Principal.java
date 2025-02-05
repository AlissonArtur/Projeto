import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("João", LocalDate.of(1985, 5, 20), new BigDecimal("2500.00"), "Analista"));
        funcionarios.add(new Funcionario("Maria", LocalDate.of(1990, 7, 15), new BigDecimal("3000.00"), "Gerente"));
        funcionarios.add(new Funcionario("Carlos", LocalDate.of(1982, 10, 3), new BigDecimal("4000.00"), "Diretor"));
        funcionarios.add(new Funcionario("Fernanda", LocalDate.of(1992, 3, 10), new BigDecimal("2200.00"), "Assistente"));
        
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));
        
        System.out.println("\nLista de Funcionários:");
        for (Funcionario f : funcionarios) {
            f.imprimirFuncionario();
        }

        for (Funcionario f : funcionarios) {
            f.setSalario(f.getSalario().multiply(new BigDecimal("1.10")));
        }

        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        System.out.println("\nFuncionários agrupados por função:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            entry.getValue().forEach(f -> f.imprimirFuncionario());
        }

        System.out.println("\nFuncionários com aniversário em Outubro e Dezembro:");
        for (Funcionario f : funcionarios) {
            int mes = f.getDataNascimento().getMonthValue();
            if (mes == 10 || mes == 12) {
                f.imprimirFuncionario();
            }
        }

        System.out.println("\nFuncionário com maior idade:");
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);
        if (maisVelho != null) {
            int idade = LocalDate.now().getYear() - maisVelho.getDataNascimento().getYear();
            System.out.println("Nome: " + maisVelho.getNome() + " | Idade: " + idade);
        }

        System.out.println("\nFuncionários por ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(f -> f.imprimirFuncionario());

        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.printf("\nTotal dos salários: R$ %,.2f\n", totalSalarios.setScale(2, RoundingMode.HALF_UP).doubleValue());

        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nQuantos salários mínimos cada funcionário ganha:");
        for (Funcionario f : funcionarios) {
            BigDecimal quantSalariosMinimos = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.printf("%s: %.2f salários mínimos\n", f.getNome(), quantSalariosMinimos.doubleValue());
        }
    }
}
