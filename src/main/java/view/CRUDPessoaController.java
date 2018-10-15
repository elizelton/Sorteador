
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import config.Config;
import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.Config.i18n;
import static config.DAO.pessoaRepository;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.data.domain.Sort;

/**
 *
 * @author Elizelton
 */
public class CRUDPessoaController
{

    @FXML
    private TextField txtFldNome;
    @FXML
    private TextField txtFldUrl;
    private PrincipalController controllerPai;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnSalvar;

    @FXML
    private void btnCancelarClick()
    {
        anchorPane.getScene().getWindow().hide();
//        controllerPai.tblViewAlunos.requestFocus();
    }

    @FXML
    private void btnSalvarClick()
    {
        controllerPai.pessoa.setNome(txtFldNome.getText());
        controllerPai.pessoa.setUrl(txtFldUrl.getText());

        try
        {
            switch (controllerPai.acao)
            {
                case INCLUIR:
                    pessoaRepository.insert(controllerPai.pessoa);
                    break;
                case ALTERAR:
                    pessoaRepository.save(controllerPai.pessoa);
                    break;
                case EXCLUIR:
                    pessoaRepository.delete(controllerPai.pessoa);
                    break;
            }
            controllerPai.tblViewPessoa.setItems(
                    FXCollections.observableList(pessoaRepository.findAll(
                            new Sort(new Sort.Order("nome")))));
            controllerPai.tblViewPessoa.refresh();
            controllerPai.tblViewPessoa.getSelectionModel().clearSelection();
            controllerPai.tblViewPessoa.getSelectionModel().select(controllerPai.pessoa);
            anchorPane.getScene().getWindow().hide();
        } catch (Exception e)
        {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Erro");
//            alert.setHeaderText("Cadastro de Disciplina");
//            if (e.getMessage().contains("duplicate key"))
//            {
//                alert.setContentText("Código já cadastrado");
//            } else
//            {
//                alert.setContentText(e.getMessage());
//            }
//            alert.showAndWait();
        }
    }

    public void setCadastroController(PrincipalController controllerPai)
    {
        this.controllerPai = controllerPai;
        txtFldNome.setText(controllerPai.pessoa.getNome());
        txtFldUrl.setText(controllerPai.pessoa.getUrl());
        txtFldNome.setDisable(controllerPai.acao == EXCLUIR);
        txtFldUrl.setDisable(controllerPai.acao == EXCLUIR);
        btnSalvar.disableProperty().bind(txtFldNome.textProperty().isEmpty().or(txtFldUrl.textProperty().isEmpty()));

        if (controllerPai.acao == EXCLUIR)
        {
            btnSalvar.setText(i18n.getString("lbl.excluir.text"));
        }

    }

//    @Override
//    public void initialize(URL url, ResourceBundle rb)
//    {
//    }
}
