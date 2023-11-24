package trabalhofinal;

import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroRefeicoes {
	private Map<String, Refeicao[]> refeicoesPorDia;

	public CadastroRefeicoes() {
		refeicoesPorDia = new HashMap<>();
		inicializarDiasSemana();
	}

	private void inicializarDiasSemana() {
		String[] diasSemana = { "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo" };
		for (String dia : diasSemana) {
			refeicoesPorDia.put(dia, new Refeicao[10]); // Ajuste a capacidade conforme necessário
		}
	}

	public void adicionarRefeicao(String dia, Refeicao refeicao) {
		Refeicao[] refeicoesDoDia = refeicoesPorDia.get(dia);

		// Garante que refeicoesDoDia não seja nulo
		if (refeicoesDoDia == null) {
			refeicoesDoDia = new Refeicao[10]; // Ajuste a capacidade conforme necessário
			refeicoesPorDia.put(dia, refeicoesDoDia);
		}

		// Agora você pode adicionar a refeição normalmente
		for (int i = 0; i < refeicoesDoDia.length; i++) {
			if (refeicoesDoDia[i] == null) {
				refeicoesDoDia[i] = refeicao;
				break;
			}
		}
	}

	public Refeicao[] obterRefeicoes(String dia) {
		return refeicoesPorDia.get(dia);
	}

	public void editarRefeicao(String dia, int indice, Refeicao novaRefeicao) {
		Refeicao[] refeicoesDoDia = refeicoesPorDia.get(dia);

		if (refeicoesDoDia != null && indice >= 0 && indice < refeicoesDoDia.length) {
			refeicoesDoDia[indice] = novaRefeicao;
		} else {
			System.out.println("Índice inválido para edição de refeição.");
		}
	}

	public void excluirRefeicao(String dia, int indice) {
		Refeicao[] refeicoesDoDia = refeicoesPorDia.get(dia);

		if (refeicoesDoDia != null && indice >= 0 && indice < refeicoesDoDia.length) {
			refeicoesDoDia[indice] = null;
		} else {
			System.out.println("Índice inválido para exclusão de refeição.");
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Cadastro de Refeições");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(400, 300);

			CadastroRefeicoes cadastroRefeicoes = new CadastroRefeicoes();

			JPanel panel = new JPanel();
			JLabel labelDiaSemana = new JLabel("Dia da Semana:");
			JTextField textFieldDiaSemana = new JTextField(10);

			JLabel labelRefeicao = new JLabel("Refeição:");
			JTextField textFieldRefeicao = new JTextField(10);

			JLabel labelAlimento = new JLabel("Alimento:");
			JTextField textFieldAlimento = new JTextField(10);

			JLabel labelCalorias = new JLabel("Calorias:");
			JTextField textFieldCalorias = new JTextField(10);

			JLabel labelMeta = new JLabel("Meta (Calorias):");
			JTextField textFieldMeta = new JTextField(10);

			JButton cadastrarButton = new JButton("Cadastrar");
			JButton editarButton = new JButton("Editar");
			JButton excluirButton = new JButton("Excluir");

			JTextArea resultadoTextArea = new JTextArea(10, 30);
			resultadoTextArea.setEditable(false);

			cadastrarButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String diaSemana = textFieldDiaSemana.getText();
					String nomeRefeicao = textFieldRefeicao.getText();
					String nomeAlimento = textFieldAlimento.getText();
					int calorias = Integer.parseInt(textFieldCalorias.getText());

					Refeicao refeicao = new Refeicao(nomeRefeicao, 5); // Ajuste a capacidade conforme necessário
					refeicao.adicionarAlimento(nomeAlimento);
					refeicao.setCalorias(calorias);

					cadastroRefeicoes.adicionarRefeicao(diaSemana, refeicao);

					// Exibir refeições cadastradas
					atualizarResultadoTextArea(cadastroRefeicoes, diaSemana, resultadoTextArea, textFieldMeta);
				}
			});

			editarButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String diaSemana = textFieldDiaSemana.getText();
					int indiceSelecionado = obterIndiceSelecionado(resultadoTextArea);

					if (indiceSelecionado >= 0) {
						String novoNomeRefeicao = textFieldRefeicao.getText();
						String novoNomeAlimento = textFieldAlimento.getText();
						int novasCalorias = Integer.parseInt(textFieldCalorias.getText());

						Refeicao[] refeicoesDoDia = cadastroRefeicoes.obterRefeicoes(diaSemana);

						if (indiceSelecionado < refeicoesDoDia.length && refeicoesDoDia[indiceSelecionado] != null) {
							Refeicao refeicaoExistente = refeicoesDoDia[indiceSelecionado];

							refeicaoExistente.setNome(novoNomeRefeicao);
							refeicaoExistente.adicionarAlimento(novoNomeAlimento);
							refeicaoExistente.setCalorias(novasCalorias);

							// Exibir refeições cadastradas
							atualizarResultadoTextArea(cadastroRefeicoes, diaSemana, resultadoTextArea, textFieldMeta);
						} else {
							System.out.println("Índice selecionado inválido ou refeição inexistente.");
						}
					}
				}
			});

			excluirButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String diaSemana = textFieldDiaSemana.getText();
					int indiceSelecionado = obterIndiceSelecionado(resultadoTextArea);

					if (indiceSelecionado >= 0) {
						Refeicao[] refeicoesDoDia = cadastroRefeicoes.obterRefeicoes(diaSemana);

						if (indiceSelecionado < refeicoesDoDia.length && refeicoesDoDia[indiceSelecionado] != null) {
							cadastroRefeicoes.excluirRefeicao(diaSemana, indiceSelecionado);

							// Exibir refeições cadastradas
							atualizarResultadoTextArea(cadastroRefeicoes, diaSemana, resultadoTextArea, textFieldMeta);
						} else {
							System.out.println("Índice selecionado inválido ou refeição inexistente.");
						}
					}
				}
			});

			panel.add(labelDiaSemana);
			panel.add(textFieldDiaSemana);
			panel.add(labelRefeicao);
			panel.add(textFieldRefeicao);
			panel.add(labelAlimento);
			panel.add(textFieldAlimento);
			panel.add(labelCalorias);
			panel.add(textFieldCalorias);
			panel.add(labelMeta);
			panel.add(textFieldMeta);
			panel.add(cadastrarButton);
			panel.add(editarButton);
			panel.add(excluirButton);
			panel.add(resultadoTextArea);

			frame.add(panel);
			frame.setVisible(true);
		});
	}

	private static int obterIndiceSelecionado(JTextArea resultadoTextArea) {
		int inicioSelecao = resultadoTextArea.getSelectionStart();
		int fimSelecao = resultadoTextArea.getSelectionEnd();
		String texto = resultadoTextArea.getText();

		int linhaInicio = 0;
		int indiceSelecionado = -1;

		// Encontrar a linha inicial da seleção
		for (int i = inicioSelecao - 1; i >= 0; i--) {
			if (texto.charAt(i) == '\n') {
				linhaInicio++;
			}
		}

		// Encontrar o índice do item selecionado
		String[] linhas = texto.split("\n");
		if (linhaInicio < linhas.length) {
			try {
				indiceSelecionado = Integer.parseInt(linhas[linhaInicio].split("\\.")[0]) - 1;
			} catch (NumberFormatException | ArrayIndexOutOfBoundsException ignored) {
			}
		}

		return indiceSelecionado;
	}

	private static void atualizarResultadoTextArea(CadastroRefeicoes cadastroRefeicoes, String dia,
			JTextArea resultadoTextArea, JTextField textFieldMeta) {
		Refeicao[] refeicoes = cadastroRefeicoes.obterRefeicoes(dia);
		int meta = Integer.parseInt(textFieldMeta.getText());
		int totalCalorias = 0;

		StringBuilder resultado = new StringBuilder("Refeições para " + dia + ":\n");

		for (int i = 0; i < refeicoes.length; i++) {
			Refeicao r = refeicoes[i];
			if (r != null) {
				resultado.append(i + 1).append(". ").append(r.getNome()).append(": ")
						.append(String.join(", ", r.getAlimentos())).append(" - Calorias: ").append(r.getCalorias())
						.append("\n");
				totalCalorias += r.getCalorias();
			}
		}

		resultado.append("\nTotal de Calorias: ").append(totalCalorias);

		if (totalCalorias >= meta) {
			resultado.append("\nMuito bem! Continue assim!");
		} else {
			resultado.append("\nNão desista! Continue tentando!");
		}

		resultadoTextArea.setText(resultado.toString());
	}
}
