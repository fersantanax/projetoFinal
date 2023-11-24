package trabalhofinal;

public class Refeicao {
    private String nome;
    private String[] alimentos;
    private int numAlimentos;
    private int calorias;

    public Refeicao(String nome, int capacidadeAlimentos) {
        this.nome = nome;
        this.alimentos = new String[capacidadeAlimentos];
        this.numAlimentos = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String[] getAlimentos() {
        return alimentos;
    }

    public void adicionarAlimento(String alimento) {
        if (numAlimentos < alimentos.length) {
            alimentos[numAlimentos] = alimento;
            numAlimentos++;
        } else {
            System.out.println("Capacidade máxima de alimentos atingida.");
        }
    }

    public void editarAlimento(int indice, String novoAlimento) {
        if (indice >= 0 && indice < numAlimentos) {
            alimentos[indice] = novoAlimento;
        } else {
            System.out.println("Índice inválido para edição.");
        }
    }

    public void removerAlimento(int indice) {
        if (indice >= 0 && indice < numAlimentos) {
            for (int i = indice; i < numAlimentos - 1; i++) {
                alimentos[i] = alimentos[i + 1];
            }
            alimentos[numAlimentos - 1] = null;
            numAlimentos--;
        } else {
            System.out.println("Índice inválido para remoção.");
        }
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }
}
