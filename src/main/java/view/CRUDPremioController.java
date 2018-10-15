
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.Config.i18n;
import static config.DAO.premioRepository;
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
public class CRUDPremioController
{
    
    @FXML
    private TextField txtFldPremio;
    @FXML
    private TextField txtFldDescricao;
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
        controllerPai.premio.setNome(txtFldPremio.getText());
        controllerPai.premio.setDescricao(txtFldDescricao.getText());
        
        try
        {
            switch (controllerPai.acao)
            {
                case INCLUIR:
                    premioRepository.insert(controllerPai.premio);
                    break;
                case ALTERAR:
                    premioRepository.save(controllerPai.premio);
                    break;
                case EXCLUIR:
                    premioRepository.delete(controllerPai.premio);
                    break;
            }
            controllerPai.tblViewPremio.setItems(
                    FXCollections.observableList(premioRepository.findAll(
                            new Sort(new Sort.Order("nome")))));
            controllerPai.tblViewPremio.refresh();
            controllerPai.tblViewPremio.getSelectionModel().clearSelection();
            controllerPai.tblViewPremio.getSelectionModel().select(controllerPai.pessoa);
            anchorPane.getScene().getWindow().hide();
            System.out.println(premioRepository.findAll());
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
        txtFldPremio.setText(controllerPai.premio.getNome());
        txtFldDescricao.setText(controllerPai.premio.getDescricao());
        txtFldPremio.setDisable(controllerPai.acao == EXCLUIR);
        txtFldDescricao.setDisable(controllerPai.acao == EXCLUIR);
        if (controllerPai.acao == EXCLUIR)
        {
            btnSalvar.setText(i18n.getString("lbl.excluir.text"));
        }
        btnSalvar.disableProperty().bind(txtFldDescricao.textProperty().isEmpty().or(txtFldPremio.textProperty().isEmpty()));
//        txtFldCodigo.setText(controllerPai.aluno.getRa());
//        txtFldNome.setText(controllerPai.aluno.getNome());
//        txtFldEmail.setText(controllerPai.aluno.getEmail());
//        dtPckrNascimento.setValue(controllerPai.aluno.getDataNascimento());
//        List<Disciplina> todasDisciplinas = disciplinaRepository.findAll(new Sort(new Sort.Order("nome")));
//
//        if (controllerPai.aluno.getMatriculas() != null)
//        {
//            List<Disciplina> lstAtual = new ArrayList<>();
//            for (Matricula nv : controllerPai.aluno.getMatriculas())
//            {
//                lstAtual.add(nv.getDisciplina());
//
//            }
//            todasDisciplinas.removeAll(lstAtual);
//            lstSelDisciplina.getTargetItems().addAll(lstAtual);
//
//        }
//        lstSelDisciplina.getSourceItems().addAll(todasDisciplinas);
//        cmbCidade.setItems(FXCollections.observableList(
//                cidadeRepository.findAll(new Sort(new Sort.Order("nome")))));
//
//        if (controllerPai.acao != INCLUIR)
//        {
//            cmbCidade.getSelectionModel().select(controllerPai.aluno.getCidade());
//        }
//
//        txtFldCodigo.setDisable(controllerPai.acao == EXCLUIR);
//        txtFldNome.setDisable(controllerPai.acao == EXCLUIR);
//        txtFldEmail.setDisable(controllerPai.acao == EXCLUIR);
//        dtPckrNascimento.setDisable(controllerPai.acao == EXCLUIR);

    }

//    @Override
//    public void initialize(URL url, ResourceBundle rb)
//    {
//    }
}
