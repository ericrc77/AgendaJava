import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AgendaApp {
    private static ArrayList<String> contacts = new ArrayList<>(); // É um ArrayList que vai armazenar os contatos como strings.
    private static DefaultListModel<String> listModel; //É um modelo de lista que será usado para atualizar a JList que exibe os contatos.
    private static JList<String> contactList;// É a lista gráfica que vai exibir os contatos
    private static JTextField nameField;
    private static JTextField phoneField;
    private static JTextField emailField;
    // São caixas de texto para inserir informações do contato (nome, telefone
    private static int selectedIndex = -1;// Mantém o índice do contato selecionado na lista.


    public static void main(String[] args) {
        JFrame frame = new JFrame("Agenda");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setSize(400, 400);
        
        frame.setLayout(new BorderLayout());
        //Essas linhas criam um novo JFrame (uma janela) com o títul "Agenda". Ele será encerrado quando o usuário clicar no botão de fechar a janela. O tamanho é configurado para 400x400 pixels e o layout é definido como BorderLayout.
        

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JLabel nameLabel = new JLabel("Nome:");
        nameField = new JTextField(20);
        JLabel phoneLabel = new JLabel("Telefone:");
        phoneField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        // Essas linhas criam um painel (inputPanel) com um layout de grade 3x2 (três linhas e duas colunas) para organizar os rótulos e campos de texto. Também são criados rótulos (JLabel) para os campos de texto.

        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    addContact();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos obrigatórios.");
                }
            }
        });

        JButton editButton = new JButton("Editar");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    editContact();
                } else {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos obrigatórios.");
                }
            }
        });
        // Aqui são criados dois botões (addButton e editButton) e são adicionados ActionListener para lidar com os eventos quando esses botões são pressionados.O ActionListener é implementado como uma classe anônima, o que significa que o código é definido no local onde é instanciado. O método actionPerformed verifica se os campos são válidos usando validateFields() e chama os métodos addContact() ou editContact() conforme necessário. Se os campos não forem válidos, exibe uma mensagem de erro.


        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        ////Essas linhas adicionam os rótulos e campos de texto ao painel 'inputPanel'

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton deleteButton = new JButton("Excluir");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        //Essas linhas criam outro painel (buttonPanel) para organizar os botões. É usado um layout de fluxo (FlowLayout) para centralizar os botões horizontalmente. O botão deleteButton é criado e também tem um ActionListener associado para lidar com o evento quando ele é pressionado.Os botões são adicionados ao buttonPanel.


        listModel = new DefaultListModel<>();
        contactList = new JList<>(listModel);
        contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        contactList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedIndex = contactList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String contact = contacts.get(selectedIndex);
                    String[] info = contact.split(", ");
                    nameField.setText(info[0].substring(6));
                    phoneField.setText(info[1].substring(10));
                    emailField.setText(info[2].substring(7));
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(contactList);
        //Um painel de rolagem (JScrollPane) é criado para conter a lista de contatos. Isso é necessário caso haja muitos contatos e a lista seja grande o suficiente para exigir uma barra de rolagem.

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
        // Finalmente, os diferentes painéis (input, lista e botões) são adicionados ao frame usando o layout BorderLayout. O frame é configurado para ser visível.
    }

    private static void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String contactInfo = "Nome: " + name + ", Telefone: " + phone + ", Email: " + email;
        contacts.add(contactInfo);
        updateList();
        clearFields();
    }
    //Aqui começa o método addContact, que é chamado quando o usuário pressiona o botão de adicionar. Este método obtém os dados do contato dos campos de texto, cria uma string formatada com esses dados e a adiciona à lista de contatos. Em seguida, a lista é atualizada na interface gráfica e os campos de texto são limpos

    private static void editContact() {
        if (selectedIndex != -1) {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String contactInfo = "Nome: " + name + ", Telefone: " + phone + ", Email: " + email;
            contacts.set(selectedIndex, contactInfo);
            updateList();
            clearFields();
            selectedIndex = -1;
        }
    }
    //Este método verifica se um contato está selecionado. Se sim, obtém os dados do contato dos campos de texto, cria uma nova string formatada com esses dados e substitui o contato na lista de contatos. Em seguida, a lista é atualizada na interface gráfica, os campos de texto são limpos e o índice selecionado é resetado.


    private static void deleteContact() {
        if (selectedIndex != -1) {
            contacts.remove(selectedIndex);
            updateList();
            clearFields();
            selectedIndex = -1;
        }
    }
    //Este método verifica se um contato está selecionado. Se sim, remove o contato da lista de contatos, atualiza a lista na interface gráfica, limpa os campos de texto e reseta o índice selecionado.


    private static void updateList() {
        listModel.clear();
        for (String contact : contacts) {
            listModel.addElement(contact);
        }
    }
    // Este método começa limpando o modelo da lista (listModel). Em seguida, itera sobre todos os contatos na lista e os adiciona ao modelo da lista. Isso atualiza a interface gráfica com os contatos mais recentes. 


    private static void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }
    //Este método simplesmente define o texto dos campos de texto como uma string vazia, o que remove qualquer texto presente.


    private static boolean validateFields() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        return !name.isEmpty() && !phone.isEmpty() && !email.isEmpty();
    }
}
    //  Este método obtém o texto dos campos de texto e verifica se nenhum deles está vazio. Se todos estiverem preenchidos, retorna true, indicando que os campos são válidos. Se algum deles estiver vazio, retorna false.